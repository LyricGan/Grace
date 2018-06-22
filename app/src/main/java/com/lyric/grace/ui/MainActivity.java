package com.lyric.grace.ui;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lyric.grace.R;
import com.lyric.grace.data.DataApi;
import com.lyric.grace.network.ResponseCallback;
import com.lyric.grace.network.ResponseError;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        final TextView tvMessage = (TextView) findViewById(R.id.tv_message);
        tvMessage.setMovementMethod(ScrollingMovementMethod.getInstance());

        findViewById(R.id.btn_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);

                final String keys = "top";
                DataApi.getInstance().queryNews(keys, new ResponseCallback<String>() {
                    @Override
                    public void onSuccess(String response) {
                        progressBar.setVisibility(View.GONE);

                        tvMessage.setText(response);
                    }

                    @Override
                    public void onFailed(ResponseError error) {
                        progressBar.setVisibility(View.GONE);
                        tvMessage.setText(error.toString());
                    }
                }).load();
            }
        });
    }
}
