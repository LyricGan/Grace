package com.lyric.arch.app;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * base fragment
 *
 * @author lyricgan
 */
public abstract class AppFragment extends Fragment implements AppListener, View.OnClickListener {
    private View mRootView;
    private AppTitleBar titleBar;

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
        if (titleBar == null) {
            titleBar = new AppTitleBar(view);
        }
        onCreateTitleBar(titleBar, savedInstanceState);

        onCreateContentView(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        logMessage("onActivityCreated()");
        onCreateData(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
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

    protected abstract void onCreateTitleBar(AppTitleBar titleBar, Bundle savedInstanceState);

    public View getRootView() {
        return mRootView;
    }

    public <T extends View> T findViewById(int id) {
        Activity activity = getActivity();
        if (activity != null) {
            return activity.findViewById(id);
        }
        return null;
    }

    public void onBackPressed() {
        Activity activity = getActivity();
        if (activity != null) {
            activity.onBackPressed();
        }
    }

    public boolean isActivityFinishing() {
        Activity activity = getActivity();
        return activity == null || activity.isFinishing();
    }

    public void finishActivity() {
        Activity activity = getActivity();
        if (activity == null || activity.isFinishing()) {
            return;
        }
        activity.finish();
    }

    private void logMessage(String message) {
        Log.d(getClass().getName(), message);
    }
}
