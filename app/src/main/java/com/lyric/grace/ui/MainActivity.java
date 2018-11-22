package com.lyric.grace.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.lyric.grace.R;
import com.lyric.utils.DisplayUtils;

public class MainActivity extends Activity implements View.OnClickListener {
    private TextView tvMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvMessage = findViewById(R.id.tv_message);

        findViewById(R.id.btn_display).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_display:
                showDisplayString();
                break;
        }
    }

    private void showDisplayString() {
        String displayString = DisplayUtils.toDisplayString();

        tvMessage.setText(displayString);
    }
}
