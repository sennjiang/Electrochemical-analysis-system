package com.bluedot.electrochemistry.factory;

import com.bluedot.framework.simplespring.core.annotation.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xxbb
 */
@Repository
public class MapInitFactory {
    private static final String PAGE_SIZE="pageSize";
    private static final String PAGE_NO="pageNo";
    /**
     * 创建存储分页参数的map，为controller提供map对象
     * @param pageNo 页码
     * @param pageSize 页面大小
     * @return map
     */
    public static Map<String,Object> createServiceMapForPageParameters(Integer pageNo, Integer pageSize){
        //当正确传入分页参数时
        if (pageNo == null || pageSize == null) {
            pageNo = 1;
            pageSize = 10;
        }
        int pageStart=(pageNo-1)*pageSize;
        Map<String,Object> serviceMap=new HashMap<>(16);
        serviceMap.put("pageNo",pageNo);
        serviceMap.put("pageStart",pageStart);
        serviceMap.put("pageSize",pageSize);
        return serviceMap;
    }

    /**
     * 从serviceMap中解析存储分页参数的map
     * @param serviceMap 请求参数集合
     */
    public static void createServiceMapForPageParameters(Map<String,Object> serviceMap){
        //当正确传入分页参数时
        int pageNo;
        int pageSize;
        if(serviceMap.get(PAGE_SIZE)==null||serviceMap.get(PAGE_NO)==null){
            pageNo=1;
            pageSize=10;
        }else{
            pageNo= Integer.parseInt((String) serviceMap.get(PAGE_NO));
            pageSize= Integer.parseInt((String) serviceMap.get(PAGE_SIZE));
        }


        int pageStart=(pageNo-1)*pageSize;
        serviceMap.put("pageNo",pageNo);
        serviceMap.put("pageStart",pageStart);
        serviceMap.put("pageSize",pageSize);
    }
}
