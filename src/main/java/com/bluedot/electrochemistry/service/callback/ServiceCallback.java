package com.bluedot.electrochemistry.service.callback;

import com.bluedot.electrochemistry.dao.base.BaseMapper;
import com.bluedot.electrochemistry.dao.base.BaseDao;

import javax.security.auth.callback.Callback;
import java.util.List;

/**
 * 回调方法，模板方法模式
 * 由于Service的很多的查询的业务逻辑都有相似之处
 * 这里提供一个回调接口来编写查询中非共性的地方
 * 采用默认方法可以避免每次使用都要把方法全部实现
 * @createDate 2021/9/3-16:45
 * @updateDate 2021年9月8日21:08:30
 */
public interface ServiceCallback<T> extends Callback {
        /**
         * 根据下拉菜单的选择查询列表的方法
         * @param baseMapper 查询代理类
         * @param pageStart 数据列表的起点
         * @param pageSize 数据长度
         * @param dropdownType1 下拉菜单的选项类型1
         * @param dropdownType2 下拉菜单的选项类型2
         * @param dropdownValue1 下拉菜单的选项值1
         * @param dropdownValue2 下拉菜单的选项值2
         * @param queryCondition 查询条件
         * @return 数据列表
         */
        default List<T> doListExecutor(BaseMapper baseMapper, int pageStart, int pageSize,String dropdownType1,String dropdownType2, int dropdownValue1,int dropdownValue2,String queryCondition){
            return null;
        }

        /**
         * 根据查询条件查询数据条数的方法
         * @param baseMapper 查询代理类
         * @param queryCondition 查询条件
         * @param dropdownType1 下拉菜单的选项类型1
         * @param dropdownType2 下拉菜单的选项类型2
         * @param dropdownValue1 下拉菜单的选项值1
         * @param dropdownValue2 下拉菜单的选项值2
         * @return 数据条数
         */
        default Long doCountExecutor(BaseMapper baseMapper,String dropdownType1,String dropdownType2, int dropdownValue1,int dropdownValue2,String queryCondition){ return null; }

    /**
     * 调用增删改方法的模板方法
     *
     * @param baseDao 增删改操作的类
     * @return 影响的行数
     */
    default int doDataModifyExecutor(BaseDao baseDao) {
        return -1;
    }
}


