package com.lyric.arch.widget.recycler;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
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
public class RecyclerViewHolder extends RecyclerView.ViewHolder {
    private Context mContext;
    private View mConvertView;
    private SparseArray<View> mViews;

    public RecyclerViewHolder(Context context, View itemView) {
        super(itemView);
        this.mContext = context;
        this.mConvertView = itemView;
        this.mViews = new SparseArray<>();
    }

    public static RecyclerViewHolder createViewHolder(Context context, View itemView) {
        return new RecyclerViewHolder(context, itemView);
    }

    public static RecyclerViewHolder createViewHolder(Context context, ViewGroup parent, int layoutId) {
        View itemView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        return new RecyclerViewHolder(context, itemView);
    }

    public Context getContext() {
        return mContext;
    }

    public View getConvertView() {
        return mConvertView;
    }

    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public RecyclerViewHolder setText(int viewId, CharSequence text) {
        TextView textView = getView(viewId);
        if (textView != null) {
            textView.setText(text);
        }
        return this;
    }

    public RecyclerViewHolder setText(int viewId, int resId) {
        TextView textView = getView(viewId);
        if (textView != null) {
            textView.setText(resId);
        }
        return this;
    }

    public RecyclerViewHolder setTextColor(int viewId, int color) {
        TextView textView = getView(viewId);
        if (textView != null) {
            textView.setTextColor(color);
        }
        return this;
    }

    public RecyclerViewHolder setTextColor(int viewId, ColorStateList colorList) {
        TextView textView = getView(viewId);
        if (textView != null) {
            textView.setTextColor(colorList);
        }
        return this;
    }

    public RecyclerViewHolder setTextSize(int viewId, float size) {
        TextView textView = getView(viewId);
        if (textView != null) {
            textView.setTextSize(size);
        }
        return this;
    }

    public RecyclerViewHolder setTextSize(int viewId, int unit, float size) {
        TextView textView = getView(viewId);
        if (textView != null) {
            textView.setTextSize(unit, size);
        }
        return this;
    }

    public RecyclerViewHolder setTextAppearance(int viewId, int resId) {
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

    public RecyclerViewHolder setTextDrawable(int viewId, int left, int top, int right, int bottom) {
        TextView textView = getView(viewId);
        if (textView != null) {
            textView.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
        }
        return this;
    }

    public RecyclerViewHolder setTextDrawable(int viewId, Drawable left, Drawable top, Drawable right, Drawable bottom) {
        TextView textView = getView(viewId);
        if (textView != null) {
            textView.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
        }
        return this;
    }

    public RecyclerViewHolder setTextDrawablePadding(int viewId, int padding) {
        TextView textView = getView(viewId);
        if (textView != null) {
            textView.setCompoundDrawablePadding(padding);
        }
        return this;
    }

    public RecyclerViewHolder setTextLinkify(int viewId) {
        return setTextLinkify(viewId, Linkify.ALL);
    }

    public RecyclerViewHolder setTextLinkify(int viewId, int mask) {
        TextView textView = getView(viewId);
        if (textView != null) {
            Linkify.addLinks(textView, mask);
        }
        return this;
    }

    public RecyclerViewHolder setTypeface(int viewId, Typeface typeface) {
        TextView textView = getView(viewId);
        if (textView != null) {
            textView.setTypeface(typeface);
            textView.setPaintFlags(textView.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
        }
        return this;
    }

    public RecyclerViewHolder setTypeface(Typeface typeface, int... viewIds) {
        for (int viewId : viewIds) {
            setTypeface(viewId, typeface);
        }
        return this;
    }

    public RecyclerViewHolder setVisibility(int viewId, int visibility) {
        View view = getView(viewId);
        if (view != null) {
            view.setVisibility(visibility);
        }
        return this;
    }

    public RecyclerViewHolder setEnabled(int viewId, boolean enabled) {
        View view = getView(viewId);
        if (view != null) {
            view.setEnabled(enabled);
        }
        return this;
    }

    public RecyclerViewHolder setBackgroundColor(int viewId, int color) {
        View view = getView(viewId);
        if (view != null) {
            view.setBackgroundColor(color);
        }
        return this;
    }

    public RecyclerViewHolder setBackgroundResource(int viewId, int backgroundResource) {
        View view = getView(viewId);
        if (view != null) {
            view.setBackgroundResource(backgroundResource);
        }
        return this;
    }

    public RecyclerViewHolder setBackgroundDrawable(int viewId, Drawable background) {
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

    public RecyclerViewHolder setImageResource(int viewId, int resourceId) {
        ImageView imageView = getView(viewId);
        if (imageView != null) {
            imageView.setImageResource(resourceId);
        }
        return this;
    }

    public RecyclerViewHolder setImageBitmap(int viewId, Bitmap bitmap) {
        ImageView imageView = getView(viewId);
        if (imageView != null) {
            imageView.setImageBitmap(bitmap);
        }
        return this;
    }

    public RecyclerViewHolder setImageDrawable(int viewId, Drawable d) {
        ImageView imageView = getView(viewId);
        if (imageView != null) {
            imageView.setImageDrawable(d);
        }
        return this;
    }

    public RecyclerViewHolder setChecked(int viewId, boolean checked) {
        CompoundButton compoundButton = getView(viewId);
        if (compoundButton != null) {
            compoundButton.setChecked(checked);
        }
        return this;
    }

    public RecyclerViewHolder setAlpha(int viewId, float alpha) {
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

    public RecyclerViewHolder setProgress(int viewId, int progress) {
        ProgressBar progressBar = getView(viewId);
        if (progressBar != null) {
            progressBar.setProgress(progress);
        }
        return this;
    }

    public RecyclerViewHolder setProgress(int viewId, int progress, int max) {
        ProgressBar progressBar = getView(viewId);
        if (progressBar != null) {
            progressBar.setProgress(progress);
            progressBar.setMax(max);
        }
        return this;
    }

    public RecyclerViewHolder setProgressMax(int viewId, int max) {
        ProgressBar progressBar = getView(viewId);
        if (progressBar != null) {
            progressBar.setMax(max);
        }
        return this;
    }

    public RecyclerViewHolder setRating(int viewId, float rating) {
        RatingBar ratingBar = getView(viewId);
        if (ratingBar != null) {
            ratingBar.setRating(rating);
        }
        return this;
    }

    public RecyclerViewHolder setRating(int viewId, float rating, int max) {
        RatingBar ratingBar = getView(viewId);
        if (ratingBar != null) {
            ratingBar.setRating(rating);
            ratingBar.setMax(max);
        }
        return this;
    }

    public RecyclerViewHolder setTag(int viewId, Object tag) {
        View view = getView(viewId);
        if (view != null) {
            view.setTag(tag);
        }
        return this;
    }

    public RecyclerViewHolder setTag(int viewId, int key, Object tag) {
        View view = getView(viewId);
        if (view != null) {
            view.setTag(key, tag);
        }
        return this;
    }

    public RecyclerViewHolder setOnClickListener(int viewId, View.OnClickListener listener) {
        View view = getView(viewId);
        if (view != null) {
            view.setOnClickListener(listener);
        }
        return this;
    }

    public RecyclerViewHolder setOnLongClickListener(int viewId, View.OnLongClickListener listener) {
        View view = getView(viewId);
        if (view != null) {
            view.setOnLongClickListener(listener);
        }
        return this;
    }

    public RecyclerViewHolder setOnTouchListener(int viewId, View.OnTouchListener listener) {
        View view = getView(viewId);
        if (view != null) {
            view.setOnTouchListener(listener);
        }
        return this;
    }

    public RecyclerViewHolder addTextChangedListener(int viewId, TextWatcher watcher) {
        TextView view = getView(viewId);
        if (view != null) {
            view.addTextChangedListener(watcher);
        }
        return this;
    }

    public RecyclerViewHolder setOnCheckedChangeListener(int viewId, CompoundButton.OnCheckedChangeListener listener) {
        CompoundButton compoundButton = getView(viewId);
        if (compoundButton != null) {
            compoundButton.setOnCheckedChangeListener(listener);
        }
        return this;
    }
}
