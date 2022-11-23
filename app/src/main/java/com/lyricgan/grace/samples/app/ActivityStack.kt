package com.lyricgan.grace.samples.app

import android.app.Activity
import java.lang.ref.WeakReference
import java.util.Stack

class ActivityStack private constructor() {
    private val stack: Stack<Activity> = Stack()
    private var resumeActivity: WeakReference<Activity>? = null

    val current: Activity?
        get() = if (stack.isEmpty()) {
            null
        } else stack.lastElement()

    val previous: Activity?
        get() {
            val size = stack.size
            return if (size > 1) {
                stack[size - 1]
            } else null
        }

    companion object {
        val instance = ActivityStack()
    }

    fun add(activity: Activity) {
        stack.add(activity)
    }

    fun remove(activity: Activity) {
        stack.remove(activity)
    }

    fun clear() {
        stack.clear()
    }

    fun contains(activity: Activity): Boolean {
        return stack.contains(activity)
    }

    fun find(cls: Class<out Activity?>): Activity? {
        for (i in stack.indices.reversed()) {
            val activity = stack[i]
            if (activity.javaClass == cls) {
                return activity
            }
        }
        return null
    }

    fun finish(cls: Class<out Activity?>) {
        val activity = find(cls)
        if (activity != null) {
            activity.finish()
            remove(activity)
        }
    }

    fun resume(activity: Activity) {
        resumeActivity = WeakReference(activity)
    }

    fun resumeActivity(): Activity? {
        return resumeActivity!!.get()
    }

    fun stop(activity: Activity) {
        if (resumeActivity != null && resumeActivity!!.get() === activity) {
            resumeActivity!!.clear()
            resumeActivity = null
        }
    }
}