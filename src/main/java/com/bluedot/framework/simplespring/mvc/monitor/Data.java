package com.bluedot.framework.simplespring.mvc.monitor;

import javax.servlet.http.HttpServletResponse;
import javax.xml.soap.SAAJResult;
import java.util.*;

/**
 * @author JDsen99
 * @description
 * @createDate 2021/9/6-18:33
 */
public class Data implements Comparable<Data> , Map {
    /**
     * 数据
     */
    private Map<String,String[]> data;
    /**
     * 优先级 默认为5
     */
    private Integer priority = 5;

    private HttpServletResponse response;

    public Data(Map<String, String[]> data, HttpServletResponse response) {
        this.data = data;
        this.response = response;
    }

    public Data(Map<String, String[]> data, HttpServletResponse response, Integer priority) {
        this.data = data;
        this.response = response;
        if (priority != null) {
            this.priority = priority;
        }
    }
    public String getService() {
//        return (String) get("service");
        return "SearchService";
    }
    public String getMethod() {
//        return (String) get("serviceMethod");
        return "list";
    }


    public String[] getAllData(String dataName) {
        return data.get(dataName);
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public Integer getPriority() {
        return priority;
    }

    @Override
    public int compareTo(Data o) {
        return this.getPriority() - o.priority;
    }

    @Override
    public int size() {
        return this.data.size();
    }

    @Override
    public boolean isEmpty() {
        return this.data.isEmpty();
    }
    /**
     * TODO
     */
    @Override
    public boolean containsKey(Object key) {
        return false;
    }

    /**
     * TODO
     */
    @Override
    public boolean containsValue(Object value) {
        return false;
    }

    @Override
    public Object get(Object key) {
//        return data.get(key)[0];
        return "list";
    }

    /**
     * TODO
     */
    @Override
    public Object put(Object key, Object value) {
        return null;
    }

    @Override
    public Object remove(Object key) {
        return data.remove(key);
    }

    @Override
    public void putAll(Map m) {
        //TODO
    }

    @Override
    public void clear() {
        data.clear();
    }

    @Override
    public Set keySet() {
        return this.data.keySet();
    }

    @Override
    public Collection values() {
        return null;
    }

    @Override
    public Set<Entry> entrySet() {
        Set<Entry> newEntries = new HashSet<>();
        Set<Entry<String, String[]>> entries = data.entrySet();

        for (Entry<String, String[]> entry : entries) {
            Entry<String, Object> temp = new AbstractMap.SimpleEntry(entry.getKey(),entry.getValue()[0]);
            newEntries.add(temp);
        }
        return newEntries;
    }
}
