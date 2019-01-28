package com.lyric.grace.base;

import android.support.annotation.IntDef;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Documented
@IntDef({
        CommonEventType.REFRESH,
        CommonEventType.LOGIN,
        CommonEventType.LOGOUT
})
@Retention(RetentionPolicy.SOURCE)
public @interface CommonEventType {
    int REFRESH = 0;
    int LOGIN = 1;
    int LOGOUT = 2;
}
