package com.lyric.grace.widget.dialog;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import com.lyric.grace.R;

public class LoadingDialog extends AlertDialog {
    private TextView tvMessage;

    public LoadingDialog(Context context) {
        super(context);
        View view = View.inflate(getContext(), R.layout.view_dialog_loading, null);
        tvMessage = (TextView) view.findViewById(R.id.tv_message);
        setView(view);
    }

    @Override
    public void setMessage(CharSequence message) {
        tvMessage.setText(message);
    }
}
