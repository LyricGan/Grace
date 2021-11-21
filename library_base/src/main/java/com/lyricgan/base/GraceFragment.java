package com.lyricgan.base;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public abstract class GraceFragment extends Fragment implements GraceAppListener {
    private View mRootView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        onCreatePrepare(savedInstanceState);
        super.onCreate(savedInstanceState);
        logMessage("onCreate()");
        Bundle args = getArguments();
        if (args != null) {
            onCreateExtras(savedInstanceState, args);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        logMessage("onCreateView()");
        return inflater.inflate(getContentViewId(), null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        logMessage("onViewCreated()");
        this.mRootView = view;

        onCreateContentView(view, savedInstanceState);

        onCreateData(savedInstanceState);
    }

    @Override
    public void onInflate(@NonNull Context context, @NonNull AttributeSet attrs, Bundle savedInstanceState) {
        super.onInflate(context, attrs, savedInstanceState);
        logMessage("onInflate()");
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        logMessage("onAttach()");
    }

    @Override
    public void onStart() {
        super.onStart();
        logMessage("onStart()");
    }

    @Override
    public void onResume() {
        super.onResume();
        logMessage("onResume()");
    }

    @Override
    public void onPause() {
        super.onPause();
        logMessage("onPause()");
    }

    @Override
    public void onStop() {
        super.onStop();
        logMessage("onStop()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        logMessage("onDestroy()");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        logMessage("onDetach()");
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        logMessage("onSaveInstanceState()");
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        logMessage("onHiddenChanged(),hidden:" + hidden);
    }

    public View getRootView() {
        return mRootView;
    }

    private void logMessage(String message) {
        Log.d(getClass().getName(), message);
    }
}
