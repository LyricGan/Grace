package com.lyric.grace.common;

import android.app.Activity;

import java.util.Stack;

/**
 * Activity管理类：用于Activity管理和应用程序退出
 * @author lyricgan
 * @date 2017/11/3 9:56
 */
public class ActivityHelper {
	private static Stack<Activity> mActivityStack;

	private ActivityHelper() {
        if (mActivityStack == null) {
            mActivityStack = new Stack<>();
        }
	}

    private static final class Holder {
        private static final ActivityHelper mInstance = new ActivityHelper();
    }

	public static ActivityHelper getInstance() {
		return Holder.mInstance;
	}

	public void add(Activity activity) {
		mActivityStack.add(activity);
	}

    public void remove(Activity activity) {
        mActivityStack.remove(activity);
    }

	public Activity last() {
		return mActivityStack.lastElement();
	}

	public Activity find(Class<?> cls) {
		Activity activity = null;
		for (Activity element : mActivityStack) {
			if (element.getClass().equals(cls)) {
				activity = element;
				break;
			}
		}
		return activity;
	}

	public void finish(Activity activity) {
		if (activity != null) {
			mActivityStack.remove(activity);
			activity.finish();
		}
	}

	public void finish(Class<? extends Activity> cls) {
		for (Activity activity : mActivityStack) {
			if (activity.getClass().equals(cls)) {
                finish(activity);
			}
		}
	}
}
