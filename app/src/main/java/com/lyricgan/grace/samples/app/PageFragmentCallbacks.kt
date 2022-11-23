package com.lyricgan.grace.samples.app

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

/**
 * Fragment生命周期监听
 * @author Lyric Gan
 */
class PageFragmentCallbacks : FragmentManager.FragmentLifecycleCallbacks() {

    override fun onFragmentViewCreated(
        fm: FragmentManager,
        f: Fragment,
        v: View,
        savedInstanceState: Bundle?
    ) {
        super.onFragmentViewCreated(fm, f, v, savedInstanceState)
        log("onFragmentViewCreated-$f")
    }

    override fun onFragmentStarted(fm: FragmentManager, f: Fragment) {
        super.onFragmentStarted(fm, f)
        log("onFragmentStarted-$f")
    }

    override fun onFragmentResumed(fm: FragmentManager, f: Fragment) {
        super.onFragmentResumed(fm, f)
        log("onFragmentResumed-$f")
    }

    override fun onFragmentPaused(fm: FragmentManager, f: Fragment) {
        super.onFragmentPaused(fm, f)
        log("onFragmentPaused-$f")
    }

    override fun onFragmentStopped(fm: FragmentManager, f: Fragment) {
        super.onFragmentStopped(fm, f)
        log("onFragmentStopped-$f")
    }

    override fun onFragmentViewDestroyed(fm: FragmentManager, f: Fragment) {
        super.onFragmentViewDestroyed(fm, f)
        log("onFragmentViewDestroyed-$f")
    }

    override fun onFragmentDestroyed(fm: FragmentManager, f: Fragment) {
        super.onFragmentDestroyed(fm, f)
        log("onFragmentDestroyed-$f")
    }

    private fun log(msg: String) {
        Log.d("PageFragmentCallbacks", msg)
    }
}