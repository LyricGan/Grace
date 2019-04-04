package com.lyric.support.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextWatcher;
import android.text.util.Linkify;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * @author lyricgan
 * @since 2019/2/25
 */
public class ListViewHolder {
    private Context mContext;
    private View mConvertView;
    private SparseArray<View> mViewArray;
    private Object mAssociatedObject;

    public ListViewHolder(Context context, ViewGroup parent, int layoutId) {
        this.mContext = context;
        this.mConvertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        this.mConvertView.setTag(this);
        this.mViewArray = new SparseArray<>();
    }

    public static ListViewHolder create(Context context, View convertView, ViewGroup parent, int layoutId) {
        if (convertView == null) {
            return new ListViewHolder(context, parent, layoutId);
        } else {
            return (ListViewHolder) convertView.getTag();
        }
    }

    public Context getContext() {
        return mContext;
    }

    public View getConvertView() {
        return mConvertView;
    }

    public <T extends View> T getView(int viewId) {
        View view = mViewArray.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViewArray.put(viewId, view);
        }
        return (T) view;
    }

    public void clear() {
        mViewArray.clear();
    }

    public Object getAssociatedObject() {
        return mAssociatedObject;
    }

    public void setAssociatedObject(Object associatedObject) {
        this.mAssociatedObject = associatedObject;
    }

    public boolean isItemChanged(Object item) {
        boolean itemChanged = (getAssociatedObject() == null || !getAssociatedObject().equals(item));
        setAssociatedObject(item);
        return itemChanged;
    }

    public ListViewHolder setText(int viewId, CharSequence text) {
        TextView textView = getView(viewId);
        if (textView != null) {
            textView.setText(text);
        }
        return this;
    }

    public ListViewHolder setText(int viewId, int resId) {
        TextView textView = getView(viewId);
        if (textView != null) {
            textView.setText(resId);
        }
        return this;
    }

    public ListViewHolder setTextColor(int viewId, int color) {
        TextView textView = getView(viewId);
        if (textView != null) {
            textView.setTextColor(color);
        }
        return this;
    }

    public ListViewHolder setTextColor(int viewId, ColorStateList colorList) {
        TextView textView = getView(viewId);
        if (textView != null) {
            textView.setTextColor(colorList);
        }
        return this;
    }

    public ListViewHolder setTextSize(int viewId, float size) {
        TextView textView = getView(viewId);
        if (textView != null) {
            textView.setTextSize(size);
        }
        return this;
    }

    public ListViewHolder setTextSize(int viewId, int unit, float size) {
        TextView textView = getView(viewId);
        if (textView != null) {
            textView.setTextSize(unit, size);
        }
        return this;
    }

    public ListViewHolder setTextAppearance(int viewId, int resId) {
        TextView textView = getView(viewId);
        if (textView != null) {
            if (Build.VERSION.SDK_INT >= 23) {
                textView.setTextAppearance(resId);
            } else {
                textView.setTextAppearance(textView.getContext(), resId);
            }
        }
        return this;
    }

    public ListViewHolder setTextDrawable(int viewId, int left, int top, int right, int bottom) {
        TextView textView = getView(viewId);
        if (textView != null) {
            textView.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
        }
        return this;
    }

    public ListViewHolder setTextDrawable(int viewId, Drawable left, Drawable top, Drawable right, Drawable bottom) {
        TextView textView = getView(viewId);
        if (textView != null) {
            textView.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
        }
        return this;
    }

    public ListViewHolder setTextDrawablePadding(int viewId, int padding) {
        TextView textView = getView(viewId);
        if (textView != null) {
            textView.setCompoundDrawablePadding(padding);
        }
        return this;
    }

    public ListViewHolder setTextLinkify(int viewId) {
        return setTextLinkify(viewId, Linkify.ALL);
    }

    public ListViewHolder setTextLinkify(int viewId, int mask) {
        TextView textView = getView(viewId);
        if (textView != null) {
            Linkify.addLinks(textView, mask);
        }
        return this;
    }

    public ListViewHolder setTypeface(int viewId, Typeface typeface) {
        TextView textView = getView(viewId);
        if (textView != null) {
            textView.setTypeface(typeface);
            textView.setPaintFlags(textView.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
        }
        return this;
    }

    public ListViewHolder setTypeface(Typeface typeface, int... viewIds) {
        for (int viewId : viewIds) {
            setTypeface(viewId, typeface);
        }
        return this;
    }

    public ListViewHolder setVisibility(int viewId, int visibility) {
        View view = getView(viewId);
        if (view != null) {
            view.setVisibility(visibility);
        }
        return this;
    }

    public ListViewHolder setEnabled(int viewId, boolean enabled) {
        View view = getView(viewId);
        if (view != null) {
            view.setEnabled(enabled);
        }
        return this;
    }

    public ListViewHolder setBackgroundColor(int viewId, int color) {
        View view = getView(viewId);
        if (view != null) {
            view.setBackgroundColor(color);
        }
        return this;
    }

    public ListViewHolder setBackgroundResource(int viewId, int backgroundResource) {
        View view = getView(viewId);
        if (view != null) {
            view.setBackgroundResource(backgroundResource);
        }
        return this;
    }

    public ListViewHolder setBackgroundDrawable(int viewId, Drawable background) {
        View view = getView(viewId);
        if (view != null) {
            if (Build.VERSION.SDK_INT >= 16) {
                view.setBackground(background);
            } else {
                view.setBackgroundDrawable(background);
            }
        }
        return this;
    }

    public ListViewHolder setImageResource(int viewId, int resourceId) {
        ImageView imageView = getView(viewId);
        if (imageView != null) {
            imageView.setImageResource(resourceId);
        }
        return this;
    }

    public ListViewHolder setImageBitmap(int viewId, Bitmap bitmap) {
        ImageView imageView = getView(viewId);
        if (imageView != null) {
            imageView.setImageBitmap(bitmap);
        }
        return this;
    }

    public ListViewHolder setImageDrawable(int viewId, Drawable d) {
        ImageView imageView = getView(viewId);
        if (imageView != null) {
            imageView.setImageDrawable(d);
        }
        return this;
    }

    public ListViewHolder setChecked(int viewId, boolean checked) {
        CompoundButton compoundButton = getView(viewId);
        if (compoundButton != null) {
            compoundButton.setChecked(checked);
        }
        return this;
    }

    public ListViewHolder setAlpha(int viewId, float alpha) {
        View view = getView(viewId);
        if (view != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                view.setAlpha(alpha);
            } else {
                AlphaAnimation alphaAnimation = new AlphaAnimation(alpha, alpha);
                alphaAnimation.setDuration(0);
                alphaAnimation.setFillAfter(true);
                view.startAnimation(alphaAnimation);
            }
        }
        return this;
    }

    public ListViewHolder setProgress(int viewId, int progress) {
        ProgressBar progressBar = getView(viewId);
        if (progressBar != null) {
            progressBar.setProgress(progress);
        }
        return this;
    }

    public ListViewHolder setProgress(int viewId, int progress, int max) {
        ProgressBar progressBar = getView(viewId);
        if (progressBar != null) {
            progressBar.setProgress(progress);
            progressBar.setMax(max);
        }
        return this;
    }

    public ListViewHolder setProgressMax(int viewId, int max) {
        ProgressBar progressBar = getView(viewId);
        if (progressBar != null) {
            progressBar.setMax(max);
        }
        return this;
    }

    public ListViewHolder setRating(int viewId, float rating) {
        RatingBar ratingBar = getView(viewId);
        if (ratingBar != null) {
            ratingBar.setRating(rating);
        }
        return this;
    }

    public ListViewHolder setRating(int viewId, float rating, int max) {
        RatingBar ratingBar = getView(viewId);
        if (ratingBar != null) {
            ratingBar.setRating(rating);
            ratingBar.setMax(max);
        }
        return this;
    }

    public ListViewHolder setTag(int viewId, Object tag) {
        View view = getView(viewId);
        if (view != null) {
            view.setTag(tag);
        }
        return this;
    }

    public ListViewHolder setTag(int viewId, int key, Object tag) {
        View view = getView(viewId);
        if (view != null) {
            view.setTag(key, tag);
        }
        return this;
    }

    public ListViewHolder setOnClickListener(int viewId, View.OnClickListener listener) {
        View view = getView(viewId);
        if (view != null) {
            view.setOnClickListener(listener);
        }
        return this;
    }

    public ListViewHolder setOnLongClickListener(int viewId, View.OnLongClickListener listener) {
        View view = getView(viewId);
        if (view != null) {
            view.setOnLongClickListener(listener);
        }
        return this;
    }

    public ListViewHolder setOnTouchListener(int viewId, View.OnTouchListener listener) {
        View view = getView(viewId);
        if (view != null) {
            view.setOnTouchListener(listener);
        }
        return this;
    }

    public ListViewHolder addTextChangedListener(int viewId, TextWatcher watcher) {
        TextView view = getView(viewId);
        if (view != null) {
            view.addTextChangedListener(watcher);
        }
        return this;
    }

    public ListViewHolder setOnCheckedChangeListener(int viewId, CompoundButton.OnCheckedChangeListener listener) {
        CompoundButton compoundButton = getView(viewId);
        if (compoundButton != null) {
            compoundButton.setOnCheckedChangeListener(listener);
        }
        return this;
    }
}
