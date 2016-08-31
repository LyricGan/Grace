package com.lyric.grace.widget.dialog;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import com.lyric.grace.R;

public class LoadingDialog extends AlertDialog {
    private TextView tv_message;

    public LoadingDialog(Context context) {
        super(context);
        View view = View.inflate(getContext(), R.layout.view_dialog_loading, null);
        tv_message = (TextView) view.findViewById(R.id.tv_message);
        setView(view);
    }

    @Override
    public void setMessage(CharSequence message) {
        tv_message.setText(message);
    }
}
