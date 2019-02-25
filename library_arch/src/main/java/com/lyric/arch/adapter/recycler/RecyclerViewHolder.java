package com.lyric.arch.adapter.recycler;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.util.Linkify;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * @author lyricgan
 * @since 2019/2/25
 */
public class RecyclerViewHolder extends RecyclerView.ViewHolder {
    private SparseArray<View> mViews;
    private Context mContext;
    private View mConvertView;

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

    public RecyclerViewHolder setText(int viewId, String text) {
        TextView textView = getView(viewId);
        textView.setText(text);
        return this;
    }

    public RecyclerViewHolder setTextColor(int viewId, int textColor) {
        TextView textView = getView(viewId);
        textView.setTextColor(textColor);
        return this;
    }

    public RecyclerViewHolder setTextLinkify(int viewId) {
        TextView textView = getView(viewId);
        Linkify.addLinks(textView, Linkify.ALL);
        return this;
    }

    public RecyclerViewHolder setTextTypeface(Typeface typeface, int... viewIds) {
        for (int viewId : viewIds) {
            TextView textView = getView(viewId);
            textView.setTypeface(typeface);
            textView.setPaintFlags(textView.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
        }
        return this;
    }

    public RecyclerViewHolder setImageResource(int viewId, int resId) {
        ImageView imageView = getView(viewId);
        imageView.setImageResource(resId);
        return this;
    }

    public RecyclerViewHolder setImageBitmap(int viewId, Bitmap bitmap) {
        ImageView imageView = getView(viewId);
        imageView.setImageBitmap(bitmap);
        return this;
    }

    public RecyclerViewHolder setImageDrawable(int viewId, Drawable drawable) {
        ImageView imageView = getView(viewId);
        imageView.setImageDrawable(drawable);
        return this;
    }

    public RecyclerViewHolder setProgress(int viewId, int progress) {
        ProgressBar progressBar = getView(viewId);
        progressBar.setProgress(progress);
        return this;
    }

    public RecyclerViewHolder setProgress(int viewId, int progress, int max) {
        ProgressBar progressBar = getView(viewId);
        progressBar.setProgress(progress);
        progressBar.setMax(max);
        return this;
    }

    public RecyclerViewHolder setMax(int viewId, int max) {
        ProgressBar progressBar = getView(viewId);
        progressBar.setMax(max);
        return this;
    }

    public RecyclerViewHolder setRating(int viewId, float rating) {
        RatingBar ratingBar = getView(viewId);
        ratingBar.setRating(rating);
        return this;
    }

    public RecyclerViewHolder setRating(int viewId, float rating, int max) {
        RatingBar ratingBar = getView(viewId);
        ratingBar.setRating(rating);
        ratingBar.setMax(max);
        return this;
    }

    public RecyclerViewHolder setBackgroundColor(int viewId, int color) {
        View view = getView(viewId);
        view.setBackgroundColor(color);
        return this;
    }

    public RecyclerViewHolder setBackgroundResource(int viewId, int resId) {
        View view = getView(viewId);
        view.setBackgroundResource(resId);
        return this;
    }

    public RecyclerViewHolder setAlpha(int viewId, float value) {
        View view = getView(viewId);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            view.setAlpha(value);
        } else {
            AlphaAnimation alpha = new AlphaAnimation(value, value);
            alpha.setDuration(0);
            alpha.setFillAfter(true);
            view.startAnimation(alpha);
        }
        return this;
    }

    public RecyclerViewHolder setVisibility(int viewId, int visibility) {
        View view = getView(viewId);
        view.setVisibility(visibility);
        return this;
    }

    public RecyclerViewHolder setTag(int viewId, Object tag) {
        View view = getView(viewId);
        view.setTag(tag);
        return this;
    }

    public RecyclerViewHolder setTag(int viewId, int key, Object tag) {
        View view = getView(viewId);
        view.setTag(key, tag);
        return this;
    }

    public RecyclerViewHolder setChecked(int viewId, boolean checked) {
        Checkable checkable = getView(viewId);
        checkable.setChecked(checked);
        return this;
    }

    public RecyclerViewHolder setOnClickListener(int viewId, View.OnClickListener listener) {
        View view = getView(viewId);
        view.setOnClickListener(listener);
        return this;
    }

    public RecyclerViewHolder setOnLongClickListener(int viewId, View.OnLongClickListener listener) {
        View view = getView(viewId);
        view.setOnLongClickListener(listener);
        return this;
    }

    public RecyclerViewHolder setOnTouchListener(int viewId, View.OnTouchListener listener) {
        View view = getView(viewId);
        view.setOnTouchListener(listener);
        return this;
    }
}
