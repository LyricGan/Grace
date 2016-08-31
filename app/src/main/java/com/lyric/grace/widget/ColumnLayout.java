package com.lyric.grace.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.lyric.grace.R;
import com.lyric.grace.library.utils.DisplayUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 九宫格布局组件
 */
public class ColumnLayout extends LinearLayout implements View.OnClickListener {
    private final int DIVIDER_TAG = 1444;
    private LayoutInflater mInflater;
    private Context mContext;
    private int mChildLayoutId = -1;
    private int mColumns = 1;
    private OnClickListener mClickListener;
    private float mDividerHeight, mChildDividerWidth;
    private int mRowDivider = -1, mChildDivider = -1;
    private float childPadding = 0;
    private float childPaddingTop = 0;
    private float childPaddingBottom = 0;

    private ViewBinder mViewBinder;
    private ViewRecycler<LinearLayout> mParentRecycler;
    private ViewRecycler<View> mChildRecycler;

    private List mDatas;
    private boolean mAutoHeight;
    private boolean mShowFinalBottomLine;
    private boolean mShowFirstDividerLine;

    public ColumnLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mInflater = LayoutInflater.from(context);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ColumnLayout);
        mChildLayoutId = array.getResourceId(R.styleable.ColumnLayout_childLayout, -1);
        mRowDivider = array.getResourceId(R.styleable.ColumnLayout_rowDivider, -1);
        mChildDivider = array.getResourceId(R.styleable.ColumnLayout_childDivider, -1);
        mColumns = array.getInt(R.styleable.ColumnLayout_column, 1);
        mDividerHeight = array.getDimension(R.styleable.ColumnLayout_rowDividerHeight, -1);
        mChildDividerWidth = array.getDimension(R.styleable.ColumnLayout_childDividerWidth, -1);
        childPadding = array.getDimension(R.styleable.ColumnLayout_childPadding, 0);
        childPaddingTop = array.getDimension(R.styleable.ColumnLayout_childPaddingTop, 0);
        childPaddingBottom = array.getDimension(R.styleable.ColumnLayout_childPaddingBottom, 0);
        mAutoHeight = array.getBoolean(R.styleable.ColumnLayout_autoHeight, false);
        mShowFirstDividerLine = array.getBoolean(R.styleable.ColumnLayout_showFirstDividerLine, false);
        mShowFinalBottomLine = array.getBoolean(R.styleable.ColumnLayout_showFinalBottomLine, true);
        setOrientation(LinearLayout.VERTICAL);
        array.recycle();
    }

    public void setViewBinder(ViewBinder viewBinder) {
        mViewBinder = viewBinder;
        mParentRecycler = new ViewRecycler<>();
        mChildRecycler = new ViewRecycler<>();
    }

    public void setData(List datas) {
        if (null == mDatas) {
            mDatas = new ArrayList();
        } else {
            mDatas.clear();
        }
        if (datas != null && !datas.isEmpty()) {
            mDatas.addAll(datas);
        }
        notifyDataChanged();
    }

    public boolean removeItem(Object obj){
        if (obj != null && mDatas != null && mDatas.remove(obj)){
            notifyDataChanged();
            return true;
        }
        return false;
    }

    public Object getItem(int position) {
        return mDatas.get(position);
    }

    public int getCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    public void notifyDataChanged() {
        int childCount = getCount();
        if (childCount == 0 || mColumns == 0 || mViewBinder == null) {
            return;
        }
        if (childCount > 0) {
            cacheViews();
            removeAllViews();
            if (mShowFirstDividerLine) {
                View rowDivider = getRowDivider();
                addView(rowDivider);
            }
            int rows = (childCount + mColumns - 1) / mColumns;
            int temp = 0;
            for (int i = 0; i < rows; i++) {
                LinearLayout layout = mParentRecycler.getCacheView();
                if (layout == null) {
                    layout = new LinearLayout(mContext);
                    layout.setOrientation(LinearLayout.HORIZONTAL);
                    layout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
                }
                for (int j = 0; j < mColumns; j++) {
                    int position = i * mColumns + j;
                    View child = mChildRecycler.getCacheView();
                    if (child == null) {
                        child = mInflater.inflate(mChildLayoutId, null);
                    }
                    if (position < childCount) {
                        child = mViewBinder.setViewValue(child, position);
                    }
                    ViewGroup.LayoutParams originalParams = child.getLayoutParams();
                    int height = LayoutParams.WRAP_CONTENT;
                    if (originalParams != null) {
                        height = originalParams.height;
                    }
                    LayoutParams leafLayoutParams = new LayoutParams(0, height, 1);
                    child.setLayoutParams(leafLayoutParams);
                    if (mAutoHeight) {
                        child.setMinimumHeight((DisplayUtils.getScreenWidth(getContext()) - getPaddingLeft() - getPaddingRight()) / mColumns);
                    }
                    child.setPadding(DisplayUtils.dip2px(getContext(), childPadding), DisplayUtils.dip2px(getContext(), childPaddingTop),
                            DisplayUtils.dip2px(getContext(), childPadding), DisplayUtils.dip2px(getContext(), childPaddingBottom));
                    layout.addView(child);
                    int index = temp++;
                    if (index < childCount) {
                        child.setOnClickListener(this);
                        child.setVisibility(View.VISIBLE);
                    } else {
                        child.setVisibility(View.INVISIBLE);
                    }
                    // 添加 child mRowDivider
                    if ((index < childCount) && (j != mColumns - 1) && (mChildDivider != -1)) {
                        View childDivider = new View(mContext);
                        LayoutParams cdLayoutParams = new LayoutParams(getChildDividerWidth(), LayoutParams.MATCH_PARENT);
                        childDivider.setLayoutParams(cdLayoutParams);
                        childDivider.setBackgroundResource(mChildDivider);
                        childDivider.setTag(DIVIDER_TAG);
                        layout.addView(childDivider);
                    }
                }
                addView(layout);
                // 添加divider
                int rowDividerIndex = mShowFinalBottomLine ? rows : rows - 1;
                if ((i < rowDividerIndex) && (mRowDivider != -1)) {
                    View rowDivider = getRowDivider();
                    addView(rowDivider);
                }
            }
        }
    }

    private View getRowDivider() {
        View rowDivider = new View(mContext);
        rowDivider.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, getDividerHeight()));
        rowDivider.setBackgroundResource(mRowDivider);
        rowDivider.setTag(DIVIDER_TAG);
        return rowDivider;
    }

    public void setOnItemClickListener(OnClickListener listener) {
        this.mClickListener = listener;
    }

    @Override
    public void onClick(View v) {
        if (null != mClickListener) {
            mClickListener.onClick(v);
        }
    }

    public interface ViewBinder {

        View setViewValue(View childView, int position);
    }

    private int getChildDividerWidth() {
        int dividerWidth = (int) mChildDividerWidth;
        return (dividerWidth <= 0 ? DisplayUtils.dip2px(getContext(), 0.5f) : dividerWidth);
    }

    private int getDividerHeight() {
        int dividerHeight = (int) mDividerHeight;
        return (dividerHeight <= 0 ? DisplayUtils.dip2px(getContext(), 0.5f) : dividerHeight);
    }

    private void cacheViews() {
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            try {
                LinearLayout rowLayout = (LinearLayout) getChildAt(i);
                mParentRecycler.cacheView(rowLayout);
                cacheChildViews(rowLayout);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void cacheChildViews(LinearLayout rowLayout) {
        int count = rowLayout.getChildCount();
        for (int i = 0; i < count; i++) {
            View child = rowLayout.getChildAt(i);
            Object tag = child.getTag();
            if (tag == null) {//mRowDivider view设置了tag
                mChildRecycler.cacheView(child);
            }
        }
        rowLayout.removeAllViews();
    }
}
