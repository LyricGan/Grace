package com.lyricgan.grace.samples.app

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentActivity

/**
 * Activity生命周期监听
 * @author Lyric Gan
 */
class PageActivityCallbacks : Application.ActivityLifecycleCallbacks {
    private val fragmentCallbacks by lazy { PageFragmentCallbacks() }
    private var foregroundActivityCount = 0

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        log("onActivityCreated-$activity")
        ActivityStack.instance.add(activity)
        registerFragmentLifecycleCallbacks(activity)
    }

    override fun onActivityStarted(activity: Activity) {
        log("onActivityStarted-$activity")
        if (foregroundActivityCount++ == 0) {
            onForegroundBegin()
        }
    }

    override fun onActivityResumed(activity: Activity) {
        log("onActivityResumed-$activity")
        ActivityStack.instance.resume(activity)
    }

    override fun onActivityPaused(activity: Activity) {
        log("onActivityPaused-$activity")
    }

    override fun onActivityStopped(activity: Activity) {
        log("onActivityStopped-$activity")
        ActivityStack.instance.stop(activity)
        if (--foregroundActivityCount == 0) {
            onForegroundEnd()
        }
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
    }

    override fun onActivityDestroyed(activity: Activity) {
        log("onActivityDestroyed-$activity")
        ActivityStack.instance.remove(activity)
        unregisterFragmentLifecycleCallbacks(activity)
    }

    private fun registerFragmentLifecycleCallbacks(activity: Activity) {
        if (activity is FragmentActivity) {
            activity.supportFragmentManager.registerFragmentLifecycleCallbacks(fragmentCallbacks, true)
        }
    }

    private fun unregisterFragmentLifecycleCallbacks(activity: Activity) {
        if (activity is FragmentActivity) {
            activity.supportFragmentManager.unregisterFragmentLifecycleCallbacks(fragmentCallbacks)
        }
    }

    private fun log(msg: String) {
        Log.d("PageActivityCallbacks", msg)
    }

    private fun onForegroundBegin() {
    }

    private fun onForegroundEnd() {
    }
}