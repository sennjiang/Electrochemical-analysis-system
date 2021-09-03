package com.bluedot.framework.simpespring.util;

import com.google.gson.Gson;
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
                gson = new Gson();
            }
        }
        return gson.toJson(ojb);
    }
}
