package com.lyricgan.grace.samples.util;

import android.app.Activity;

import java.util.Stack;

public class ActivityStackManager {
    private final Stack<Activity> mActivityStack;

    private ActivityStackManager() {
        mActivityStack = new Stack<>();
    }

    private static class Holder {
        private static final ActivityStackManager INSTANCE = new ActivityStackManager();
    }

    public static ActivityStackManager getInstance() {
        return Holder.INSTANCE;
    }

    public void add(Activity activity) {
        mActivityStack.add(activity);
    }

    public void remove(Activity activity) {
        mActivityStack.remove(activity);
    }

    public boolean contains(Activity activity) {
        return mActivityStack.contains(activity);
    }

    public Activity getCurrent() {
        if (mActivityStack.isEmpty()) {
            return null;
        }
        return mActivityStack.lastElement();
    }

    public Activity getPrevious() {
        int size = mActivityStack.size();
        if (size > 1) {
            return mActivityStack.get(size - 1);
        }
        return null;
    }

    public void clear() {
        for (Activity activity : mActivityStack) {
            activity.finish();
        }
        mActivityStack.clear();
    }

    public Activity find(Class<? extends Activity> cls) {
        for (int i = mActivityStack.size() - 1; i >= 0; i--) {
            Activity activity = mActivityStack.get(i);
            if (activity.getClass().equals(cls)) {
                return activity;
            }
        }
        return null;
    }

    public void finish(Class<? extends Activity> cls) {
        Activity activity = find(cls);
        if (activity != null) {
            activity.finish();
            mActivityStack.remove(activity);
        }
    }
}
