package com.bluedot.electrochemistry.service.base;

import com.bluedot.electrochemistry.dao.base.BaseMapper;
import com.bluedot.electrochemistry.factory.MapperFactory;
import com.bluedot.electrochemistry.pojo.PageInfo;
import com.bluedot.electrochemistry.dao.base.BaseDao;
import com.bluedot.electrochemistry.service.callback.ServiceCallback;
import com.bluedot.framework.simplespring.core.BeanContainer;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 *
 * @description
 * @createDate 2021/8/25-14:37
 */
public class BaseService extends HttpServlet {

    private BeanContainer beanContainer = BeanContainer.getInstance();

    /**
     * 查询数据列表的方法
     * @param map             请求参数map
     * @param serviceCallback 具体查询操作的回调接口
     * @param <T>             泛型
     */
    protected <T> void doSimpleQueryListTemplate(Map<String, Object> map, ServiceCallback<T> serviceCallback) {
        BaseMapper baseMapper =((MapperFactory) beanContainer.getBean(MapperFactory.class)).createMapper();
        int pageNo = (int) map.get("pageNo");
        int page = (int) map.get("page");
        int pageSize = (int) map.get("pageSize");
        String dropdownType1 = (String) map.get("dropdownType1");
        int dropdownValue1 = (int) map.get("dropdownValue1");
        String dropdownType2 = (String) map.get("dropdownType2");
        int dropdownValue2 = (int) map.get("dropdownValue2");
        String queryCondition = (String) map.get("queryCondition");
        List<T> list;
        Long totalSize;
        try {
            list = serviceCallback.doListExecutor(baseMapper, page, pageSize, dropdownType1, dropdownType2, dropdownValue1, dropdownValue2, queryCondition);
            totalSize = serviceCallback.doCountExecutor(baseMapper, dropdownType1, dropdownType2, dropdownValue1, dropdownValue2, queryCondition);

            //页面大小、页码、数据总数、数据列表，注意顺序
            PageInfo<T> pageInfo = new PageInfo<>(pageSize, pageNo, totalSize, list);
            //单独计算总页数
            pageInfo.setTotalPage(totalSize, pageSize);
            map.put("pageInfo", pageInfo);
        } catch (Exception e) {
            map.put("error", "数据查询出错");
        }
    }

    /**
     * 操作数据库列表的方法（增删改方法）的模板方法
     *
     * @param map             请求参数map
     * @param serviceCallback 具体查询操作的回调接口
     * @param <T>             泛型
     */
    protected <T> void doSimpleModifyTemplate(Map<String, Object> map, ServiceCallback<T> serviceCallback){
        try {
            BaseDao baseDao = (BaseDao) beanContainer.getBean(BaseDao.class);
            System.out.println(baseDao);
            int affectedRows = serviceCallback.doDataModifyExecutor(baseDao);
            System.out.println(map);
            if (affectedRows == 0) {
                map.put("error", "数据库信息操作失败！受影响的行数为0");
            }else {
                map.put("code", 200);
                map.put("message", "执行成功");
            }
        }catch (Exception e) {
            map.put("code", 500);
            map.put("message", "执行失败");
        }

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
        Object obj = beanContainer.getBean(clazz);
        //SearchService searchService = new SearchService();
        try {
            Method method = clazz.getDeclaredMethod(methodName, Map.class);
            method.setAccessible(true);
            method.invoke(obj, map);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("error", e.getMessage());
        }
    }
}
