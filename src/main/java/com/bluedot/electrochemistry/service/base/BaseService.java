package com.bluedot.electrochemistry.service.base;

import com.bluedot.electrochemistry.pojo.domain.File;
import com.bluedot.electrochemistry.service.FileService;
import com.bluedot.electrochemistry.service.SearchService;
import com.bluedot.electrochemistry.service.callback.ServiceCallback;
import com.bluedot.framework.simplespring.core.BeanContainer;

import java.lang.reflect.Method;
import java.util.Map;

/**
 *
 * @description
 * @createDate 2021/8/25-14:37
 */
public class BaseService {

    private BeanContainer beanContainer = BeanContainer.getInstance();

    /**
     * 查询数据列表的方法
     *
     * @param map             请求参数map
     * @param serviceCallback 具体查询操作的回调接口
     * @param <T>             泛型
     */
    protected <T> void doSimpleQueryListTemplate(Map<String, Object> map, ServiceCallback<T> serviceCallback) {

    }

    /**
     * 操作数据库列表的方法（增删改方法）的模板方法
     *
     * @param map             请求参数map
     * @param serviceCallback 具体查询操作的回调接口
     * @param <T>             泛型
     */
    protected <T> void doSimpleModifyTemplate(Map<String, Object> map, ServiceCallback<T> serviceCallback){

    }

    /**
     * service层的调用接口，所有的具体业务方法都通过该方法进行调用
     * 根据map中的service参数反射调用对应的service方法
     *
     * @param map 包含请求参数和需要执行的service方法参数
     */
    public void doService(Map<String, Object> map) {
        String methodName = (String) map.get("serviceMethod");
        Class<?> clazz = this.getClass();
//        Object obj = beanContainer.getBean(clazz);
        SearchService searchService = new SearchService();
        try {
            Method method = clazz.getDeclaredMethod(methodName, Map.class);
            method.setAccessible(true);
            method.invoke(searchService, map);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("error", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }
}
