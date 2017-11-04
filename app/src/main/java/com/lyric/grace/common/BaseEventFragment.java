package com.lyric.grace.common;

import android.os.Bundle;

import org.greenrobot.eventbus.Subscribe;

/**
 * 集成事件通知的基类Fragment
 * @author lyricgan
 * @date 17/11/4 上午11:19
 */
public abstract class BaseEventFragment extends BaseFragment {

    @Override
    public void onPrepareCreate(Bundle savedInstanceState) {
        super.onPrepareCreate(savedInstanceState);
        EventBusUtils.register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBusUtils.unregister(this);
    }

    @Subscribe
    public void onEventMainThread(BaseEvent event) {
        onEventCallback(event);
    }

    protected void onEventCallback(BaseEvent event) {
    }
}
