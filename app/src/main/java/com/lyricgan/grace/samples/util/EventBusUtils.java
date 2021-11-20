package com.lyricgan.grace.samples.util;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.meta.SubscriberInfoIndex;

/**
 * EventBus工具类，https://github.com/greenrobot/EventBus
 * @author Lyric Gan
 */
public class EventBusUtils {

    private EventBusUtils() {
    }

    /**
     * 初始化操作，建立索引提升查找效率，只能初始化一次
     */
    public static void init(SubscriberInfoIndex index) {
        try {
            EventBus.builder().addIndex(index).installDefaultEventBus();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void register(Object subscriber) {
        if (!isRegistered(subscriber)) {
            EventBus.getDefault().register(subscriber);
        }
    }

    public static void unregister(Object subscriber) {
        if (isRegistered(subscriber)) {
            EventBus.getDefault().unregister(subscriber);
        }
    }

    public static boolean isRegistered(Object subscriber) {
        return EventBus.getDefault().isRegistered(subscriber);
    }

    public static void post(Object event) {
        EventBus.getDefault().post(event);
    }

    public static void postSticky(Object event) {
        EventBus.getDefault().postSticky(event);
    }

    public static void cancelEventDelivery(Object event) {
        EventBus.getDefault().cancelEventDelivery(event);
    }

    public static <T> T getStickyEvent(Class<T> eventType) {
        return EventBus.getDefault().getStickyEvent(eventType);
    }

    public static void removeStickyEvent(Object event) {
        EventBus.getDefault().removeStickyEvent(event);
    }

    public static <T> T removeStickyEvent(Class<T> eventType) {
        return EventBus.getDefault().removeStickyEvent(eventType);
    }

    public static void removeAllStickyEvents() {
        EventBus.getDefault().removeAllStickyEvents();
    }

    public static boolean hasSubscriberForEvent(Class<?> eventClass) {
        return EventBus.getDefault().hasSubscriberForEvent(eventClass);
    }
}
