package com.lyric.grace.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.lyric.grace.R;

public class ChartView extends View {
    private float mXTotalScale;// x轴总刻度
    private float mYTotalScale;// y轴总刻度
    private float mXScaleUnit;// x轴刻度
    private float mYScaleUnit;// y轴刻度
    private float mStartCoordinateX;// 起始x坐标
    private float mStartCoordinateY;// 起始y坐标

    private String[] mXTextArray;
    private String[] mYTextArray;
    private String[] mDataArray;

    private int mDataLineColors = 0XFFFBBC14;
    private float[][] mDataCoordinates;

    private Paint mXyAsiaPaint;// xy轴坐标刻度画笔
    private Paint mXTextPaint;// x轴坐标文字画笔
    private Paint mYTextPaint;// y轴坐标文字画笔
    private Paint mDataPaint;// 数据线和点画笔

    private Rect mBounds = new Rect();
    private boolean mClicked;// 是否点击数据点标识
    private int mClickedIndex = -1;// 点击数据点索引
    private PopupWindow mPopup;

    public ChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mXyAsiaPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mXTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mYTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mDataPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        mXyAsiaPaint.setColor(0xFFDEDCD8);
        mXTextPaint.setColor(0xFF999999);
        mYTextPaint.setColor(0xFF000000);
        mDataPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
        int height = getMeasuredHeight() - getPaddingTop() - getPaddingBottom();
        // 设置x轴及y轴长度
        mXTotalScale = width * 0.8f;
        mYTotalScale = height * 0.8f;
        float xSize = mXTextArray != null ? mXTextArray.length : 8f;
        float ySize = mYTextArray != null ? mYTextArray.length : 8f;
        // 设置x轴及y轴刻度
        mXScaleUnit = mXTotalScale / xSize;
        mYScaleUnit = mYTotalScale / ySize;
        // 设置绘制起始x，y坐标
        mStartCoordinateX = mXScaleUnit;
        mStartCoordinateY = mYScaleUnit;

        // 图表线条的线宽
        float chartLineWidth = mXScaleUnit / 50;
        // 数据线条的线宽
        float dataLineWidth = mXScaleUnit / 30;
        // 坐标刻度文字的大小
        float xyTextSize = mXScaleUnit / 3;

        // 设置画笔相关属性
        mXyAsiaPaint.setStrokeWidth(chartLineWidth);
        mXTextPaint.setTextSize(xyTextSize);
        mYTextPaint.setTextSize(xyTextSize);
        mDataPaint.setStrokeWidth(dataLineWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawXyAxisView(canvas);
        drawXyDataView(canvas);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();
        for (int i = 0; i < mDataCoordinates.length; i++) {
            float dataX = mDataCoordinates[i][0];
            float dataY = mDataCoordinates[i][1];
            // 判断点击是否在有效区域
            if (Math.abs(touchX - dataX) < mXScaleUnit * 0.5f && Math.abs(touchY - dataY) < mYScaleUnit * 0.5f) {
                mClicked = true;
                mClickedIndex = i;

                invalidate();
                showDetails(i);
                return true;
            } else {
                hideDetails();
            }
            mClickedIndex = -1;
            invalidate();
        }
        return super.onTouchEvent(event);
    }

    /**
     * 绘制x轴和y轴刻度和线条
     */
    private void drawXyAxisView(Canvas canvas) {
        // 绘制x轴刻度和线条
        for (int i = 0; i < mXTextArray.length; i++) {
            String text = mXTextArray[i];
            canvas.drawLine(mStartCoordinateX + i * mXScaleUnit, mStartCoordinateY, mStartCoordinateX + i * mXScaleUnit, mStartCoordinateY + mYTotalScale - 0.5f * mYScaleUnit, mXyAsiaPaint);
            mXTextPaint.getTextBounds(text, 0, text.length(), mBounds);
            float y = mStartCoordinateY + mYTotalScale + mBounds.height();
            if (i == 0) {
                canvas.drawText(text, mStartCoordinateX - mBounds.width() * 0.5f, y, mXTextPaint);
            } else {
                canvas.drawText(text, mStartCoordinateX + i * mXScaleUnit - mBounds.width() * 0.5f, y, mXTextPaint);
            }
        }
        // 绘制y轴刻度和线条
        for (int i = 0; i < mYTextArray.length; i++) {
            String text = mYTextArray[mYTextArray.length - 1 - i];
            mYTextPaint.getTextBounds(text, 0, text.length(), mBounds);
            canvas.drawText(text, mStartCoordinateX - mXScaleUnit * 0.8f, mStartCoordinateY + mYScaleUnit * (i + 0.5f) + mBounds.height() * 0.5f, mYTextPaint);
            canvas.drawLine(mStartCoordinateX, mStartCoordinateY + (i + 0.5f) * mYScaleUnit, mStartCoordinateX + mXTotalScale, mStartCoordinateY + (i + 0.5f) * mYScaleUnit, mXyAsiaPaint);
        }
    }

    private void drawXyDataView(Canvas canvas) {
        // 获取数据的坐标点，绘制数据线条
        float startCoordinateX = mStartCoordinateX;
        float startCoordinateY = mStartCoordinateY + mYTotalScale;
        for (int i = 0; i < mDataArray.length; i++) {
            mDataCoordinates[i][0] = startCoordinateX + i * mXScaleUnit;
            mDataCoordinates[i][1] = startCoordinateY - (mYScaleUnit * (Float.parseFloat(mDataArray[i]) - Float.parseFloat(mYTextArray[0])) / (Float.parseFloat(mYTextArray[1]) - Float.parseFloat(mYTextArray[0])));
        }
        for (int i = 0; i < mDataCoordinates.length - 1; i++) {
            mDataPaint.setColor(mDataLineColors);
            canvas.drawLine(mDataCoordinates[i][0], mDataCoordinates[i][1], mDataCoordinates[i + 1][0], mDataCoordinates[i + 1][1], mDataPaint);
        }
        // 点击状态下绘制数据点
        if (mClicked && mClickedIndex > -1) {
            mDataPaint.setColor(mDataLineColors);
            canvas.drawCircle(mDataCoordinates[mClickedIndex][0], mDataCoordinates[mClickedIndex][1], mXScaleUnit / 10, mDataPaint);
            mDataPaint.setColor(Color.WHITE);
            canvas.drawCircle(mDataCoordinates[mClickedIndex][0], mDataCoordinates[mClickedIndex][1], mXScaleUnit / 20, mDataPaint);
            mDataPaint.setColor(mDataLineColors);
        }
    }

    private void showDetails(int index) {
        if (mPopup != null) {
            mPopup.dismiss();
        }
        String showText = mDataArray[index] + "%";
        TextView textView = new TextView(getContext());
        textView.setText(showText);
        textView.setTextColor(Color.WHITE);
        textView.setPadding(20, 0, 20, 0);
        textView.setGravity(Gravity.CENTER);
        textView.setBackgroundResource(R.drawable.popup_bg);

        mPopup = new PopupWindow(textView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopup.setBackgroundDrawable(new ColorDrawable(0));
        mPopup.setFocusable(false);
        // 根据坐标点的位置计算弹窗的展示位置
        int xOff = (int) (mDataCoordinates[index][0] - 0.5 * mXScaleUnit);
        int yOff = -(int) (getHeight() - mDataCoordinates[index][1] + 0.75f * mYScaleUnit);
        mPopup.showAsDropDown(this, xOff, yOff);
        mPopup.update();
    }

    private void hideDetails() {
        if (mPopup != null) {
            mPopup.dismiss();
        }
    }

    public void setData(String[] xTextArray, String[] yTextArray, String[] dataArray) {
        this.mXTextArray = xTextArray;
        this.mYTextArray = yTextArray;
        this.mDataArray = dataArray;
        if (xTextArray == null || xTextArray.length <= 0) {
            return;
        }
        if (yTextArray == null || yTextArray.length <= 0) {
            return;
        }
        if (dataArray == null || dataArray.length <= 0) {
            return;
        }
        mDataCoordinates = new float[dataArray.length][2];
        requestLayout();
        postInvalidate();
    }
}
