package com.lyricgan.grace.samples.util

import androidx.lifecycle.HasDefaultViewModelProviderFactory
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelLazy
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner

val sharedStoreOwnerMap = HashMap<String, SharedViewModelStoreOwner>()

/**
 * 使用ViewModelLazy实现ViewModel注入，可以感知生命周期，
 * 同一个key标记的ViewModel界面全部销毁的情况下，自动清除ViewModelStore，避免内存泄露
 */
inline fun <reified VM : ViewModel> LifecycleOwner.sharedViewModels(
    key: String, factory: ViewModelProvider.Factory? = null
): Lazy<VM> {
    val storeOwner = sharedStoreOwner(key)
    storeOwner.register(this)
    return ViewModelLazy(VM::class,
        { storeOwner.viewModelStore },
        { factory ?: defaultFactory(this) })
}

fun sharedStoreOwner(key: String): SharedViewModelStoreOwner {
    val storeOwner: SharedViewModelStoreOwner
    if (sharedStoreOwnerMap.keys.contains(key)) {
        storeOwner = sharedStoreOwnerMap[key]!!
    } else {
        storeOwner = SharedViewModelStoreOwner()
        sharedStoreOwnerMap[key] = storeOwner
    }
    return storeOwner
}

fun defaultFactory(owner: LifecycleOwner): ViewModelProvider.Factory =
    if (owner is HasDefaultViewModelProviderFactory)
        owner.defaultViewModelProviderFactory else ViewModelProvider.NewInstanceFactory.instance

class SharedViewModelStoreOwner : ViewModelStoreOwner {
    private val ownerList = mutableListOf<LifecycleOwner>()
    private var viewModelStore: ViewModelStore? = null

    override fun getViewModelStore(): ViewModelStore {
        if (viewModelStore == null) {
            viewModelStore = ViewModelStore()
        }
        return viewModelStore!!
    }

    fun register(owner: LifecycleOwner) {
        if (ownerList.contains(owner)) {
            return
        }
        ownerList.add(owner)
        owner.lifecycle.addObserver(object : LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                if (event == Lifecycle.Event.ON_DESTROY) {
                    owner.lifecycle.removeObserver(this)
                    clear(owner)
                }
            }
        })
    }

    private fun clear(owner: LifecycleOwner) {
        ownerList.remove(owner)
        if (ownerList.isNotEmpty()) {
            return
        }
        sharedStoreOwnerMap.entries.find { it.value == this }
            ?.also {
                viewModelStore?.clear()
                sharedStoreOwnerMap.remove(it.key)
            }
    }
}