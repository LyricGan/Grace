package com.lyricgan.arch;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public abstract class GraceActivity extends AppCompatActivity implements GraceAppListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        onCreatePrepare(savedInstanceState);
        super.onCreate(savedInstanceState);

        Bundle args = getIntent().getExtras();
        if (args != null) {
            onCreateExtras(savedInstanceState, args);
        }
        setContentView(getContentViewId());
        View decorView = getWindow().getDecorView();

        onCreateTitleBar(decorView, savedInstanceState);
        onCreateContentView(decorView, savedInstanceState);
        onCreateData(savedInstanceState);
    }

    protected abstract void onCreateTitleBar(View decorView, Bundle savedInstanceState);
}