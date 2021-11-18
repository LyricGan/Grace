package com.lyricgan.gson;

import com.google.gson.Gson;

/**
 * Gson工具类
 * @author Lyric Gan
 */
public class GsonHelper {
    private Gson mGson;

    private static class Holder {
        private static final GsonHelper INSTANCE = new GsonHelper();
    }

    private GsonHelper() {
    }

    public static GsonHelper getInstance() {
        return Holder.INSTANCE;
    }

    public void setGson(Gson gson) {
        this.mGson = gson;
    }

    public Gson getGson() {
        if (mGson == null) {
            mGson = GsonCompat.build();
        }
        return mGson;
    }
}
