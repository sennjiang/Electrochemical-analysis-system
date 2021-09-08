package com.bluedot.framework.simplespring.util;

import com.bluedot.framework.simplespring.mvc.monitor.Data;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author JDsen99
 * @description 解析成json对象
 * @createDate 2021/8/27-17:04
 */
public class JsonUtil {
    private static Gson gson;
    public static String getJson(Object ojb) {
        if (gson == null) {
            synchronized (JsonUtil.class) {
                gson = new GsonBuilder().create();
            }
        }
        return gson.toJson(ojb);
    }
}
