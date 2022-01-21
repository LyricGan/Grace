package com.lyricgan.grace.samples.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 被观察者
 * @author Lyric Gan
 */
public class GraceObservable {
    private final List<GraceObserver> mObserverList = new ArrayList<>();

    public void register(GraceObserver observer) {
        if (observer == null) {
            return;
        }
        synchronized (mObserverList) {
            if (mObserverList.contains(observer)) {
                return;
            }
            mObserverList.add(observer);
        }
    }

    public void unregister(GraceObserver observer) {
        if (observer == null) {
            return;
        }
        synchronized (mObserverList) {
            mObserverList.remove(observer);
        }
    }

    public void unregisterAll() {
        synchronized (mObserverList) {
            mObserverList.clear();
        }
    }

    public void dispatchChanged() {
        synchronized (mObserverList) {
            for (int i = mObserverList.size() - 1; i >= 0; i--) {
                mObserverList.get(i).onChanged();
            }
        }
    }
}
