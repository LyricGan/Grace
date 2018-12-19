package com.lyric.grace.base;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lyricgan
 */
public class DataApi {
    private static final String API_URL = "http://v.juhe.cn/toutiao/index";

    private DataApi() {
    }

    private static final class Holder {
        private static final DataApi INSTANCE = new DataApi();
    }

    public static DataApi getInstance() {
        return Holder.INSTANCE;
    }

    private Map<String, String> buildDefaultParams() {
        Map<String, String> params = new HashMap<>();
        params.put("device", "android");
        params.put("key", "f909a4cf8e87f8553c95f6d4989d1559");// 聚合数据APP KEY
        return params;
    }

    /**
     * 请求资讯数据
     * @param keys 类型：top(头条，默认),shehui(社会),guonei(国内),guoji(国际),yule(娱乐),tiyu(体育)junshi(军事),keji(科技),caijing(财经),shishang(时尚)
     */
    public void queryNews(String keys) {
        Map<String, String> params = buildDefaultParams();
        params.put("type", keys);
    }
}
