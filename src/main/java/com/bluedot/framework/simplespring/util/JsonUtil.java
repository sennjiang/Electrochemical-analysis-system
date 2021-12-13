package com.bluedot.framework.simplespring.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Map;

/**
 * @author JDsen99
 * @description 解析成json对象
 * @createDate 2021/8/27-17:04
 */
public class JsonUtil {
    private static volatile Gson gson = null;

    private JsonUtil() {
    }

    /**
     * 准确的多线程 的 单例模式
     *
     * @param ojb 数据
     * @return String json
     */
    public static String toJson(Object ojb) {
        if (null == gson) { //为了避免开销 先检查是否为空
            synchronized (JsonUtil.class) {
                if (null == gson) {
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    gson = gsonBuilder.create();
                }
            }
        }
        //剔除 service 因为service是Class对象 不需要输出Json
        //TODO 使用更优秀的方式 或设计模式
        ((Map) ojb).remove("service");
        ((Map) ojb).remove("file");
        return gson.toJson(ojb);
    }
}
