package com.lyricgan.widget;

import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import android.view.View;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;

/**
 * ViewHolder工具类
 * @author Lyric Gan
 */
public class ViewHolderUtils {

    @SuppressWarnings("unchecked")
    public static <T extends View> T get(View convertView, int id) {
        if (convertView == null) {
            return null;
        }
        SparseArray<View> viewHolder = (SparseArray<View>) convertView.getTag();
        if (viewHolder == null) {
            viewHolder = new SparseArray<>();
            convertView.setTag(viewHolder);
        }
        View childView = viewHolder.get(id);
        if (childView == null) {
            childView = convertView.findViewById(id);
            viewHolder.put(id, childView);
        }
        return (T) childView;
    }

    public static void setVisibility(View convertView, int id, int visibility) {
        View view = get(convertView, id);
        if (view == null) {
            return;
        }
        view.setVisibility(visibility);
    }

    public static void setEnabled(View convertView, int id, boolean enabled) {
        View view = get(convertView, id);
        if (view == null) {
            return;
        }
        view.setEnabled(enabled);
    }

    public static void setAlpha(View convertView, int id, float alpha) {
        View view = get(convertView, id);
        if (view == null) {
            return;
        }
        view.setAlpha(alpha);
    }

    public static void setBackgroundResource(View convertView, int id, int resId) {
        View view = get(convertView, id);
        if (view == null) {
            return;
        }
        view.setBackgroundResource(resId);
    }

    public static void setBackgroundColor(View convertView, int id, int color) {
        View view = get(convertView, id);
        if (view == null) {
            return;
        }
        view.setBackgroundColor(color);
    }

    public static void setChecked(View convertView, int id, boolean checked) {
        View view = get(convertView, id);
        if (view instanceof Checkable) {
            ((Checkable) view).setChecked(checked);
        }
    }

    public static void setTag(View convertView, int id, Object tag) {
        View view = get(convertView, id);
        if (view == null) {
            return;
        }
        view.setTag(tag);
    }

    public static void setText(View convertView, int id, int textId) {
        TextView textView = get(convertView, id);
        if (textView == null) {
            return;
        }
        textView.setText(textId);
    }

    public static void setText(View convertView, int id, CharSequence text) {
        TextView textView = get(convertView, id);
        if (textView == null) {
            return;
        }
        textView.setText(text);
    }

    public static void setTextColor(View convertView, int id, int color) {
        TextView textView = get(convertView, id);
        if (textView == null) {
            return;
        }
        textView.setTextColor(color);
    }

    public static void setTextColor(View convertView, int id, ColorStateList colorStateList) {
        TextView textView = get(convertView, id);
        if (textView == null) {
            return;
        }
        textView.setTextColor(colorStateList);
    }

    public static void setTextSize(View convertView, int id, float size) {
        TextView textView = get(convertView, id);
        if (textView == null) {
            return;
        }
        textView.setTextSize(size);
    }

    public static void setTextSize(View convertView, int id, int unit, float size) {
        TextView textView = get(convertView, id);
        if (textView == null) {
            return;
        }
        textView.setTextSize(unit, size);
    }

    public void setTextDrawable(View convertView, int id, @DrawableRes int left, @DrawableRes int top, @DrawableRes int right, @DrawableRes int bottom) {
        TextView textView = get(convertView, id);
        if (textView == null) {
            return;
        }
        textView.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
    }

    public void setTextDrawable(View convertView, int id, @Nullable Drawable left, @Nullable Drawable top, @Nullable Drawable right, @Nullable Drawable bottom) {
        TextView textView = get(convertView, id);
        if (textView == null) {
            return;
        }
        textView.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
    }

    public void setTextDrawablePadding(View convertView, int id, int padding) {
        TextView textView = get(convertView, id);
        if (textView == null) {
            return;
        }
        textView.setCompoundDrawablePadding(padding);
    }

    public void setTextTypeface(View convertView, int id, Typeface typeface) {
        TextView textView = get(convertView, id);
        if (textView == null) {
            return;
        }
        textView.setTypeface(typeface);
        textView.setPaintFlags(textView.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
    }

    public static void setImageResource(View convertView, int id, int resId) {
        ImageView imageView = get(convertView, id);
        if (imageView == null) {
            return;
        }
        imageView.setImageResource(resId);
    }

    public static void setImageBitmap(View convertView, int id, Bitmap bitmap) {
        ImageView imageView = get(convertView, id);
        if (imageView == null) {
            return;
        }
        imageView.setImageBitmap(bitmap);
    }

    public static void setImageDrawable(View convertView, int id, Drawable drawable) {
        ImageView imageView = get(convertView, id);
        if (imageView == null) {
            return;
        }
        imageView.setImageDrawable(drawable);
    }

    public static void setOnClickListener(View convertView, int id, View.OnClickListener listener) {
        View view = get(convertView, id);
        if (view == null) {
            return;
        }
        view.setOnClickListener(listener);
    }

    public static void setOnLongClickListener(View convertView, int id, View.OnLongClickListener listener) {
        View view = get(convertView, id);
        if (view == null) {
            return;
        }
        view.setOnLongClickListener(listener);
    }

    public static void setOnTouchListener(View convertView, int id, View.OnTouchListener listener) {
        View view = get(convertView, id);
        if (view == null) {
            return;
        }
        view.setOnTouchListener(listener);
    }

    public static void setOnCheckedChangeListener(View convertView, int id, CompoundButton.OnCheckedChangeListener listener) {
        View view = get(convertView, id);
        if (view instanceof CompoundButton) {
            ((CompoundButton) view).setOnCheckedChangeListener(listener);
        }
    }

    public static void setOnCheckedChangeListener(View convertView, int id, RadioGroup.OnCheckedChangeListener listener) {
        View view = get(convertView, id);
        if (view instanceof RadioGroup) {
            ((RadioGroup) view).setOnCheckedChangeListener(listener);
        }
    }
}
