package com.bluedot.electrochemistry.service.callback;

import com.bluedot.electrochemistry.dao.BaseMapper;
import com.bluedot.electrochemistry.dao.bese.BaseDao;

import javax.security.auth.callback.Callback;
import java.util.List;

/**
 *
 * 回调方法，模板方法模式
 * 由于Service的很多的查询的业务逻辑都有相似之处
 * 这里提供一个回调接口来编写查询中非共性的地方
 * 采用默认方法可以避免每次使用都要把方法全部实现
 * @createDate 2021/9/3-16:45
 */
public interface ServiceCallback<T> extends Callback {
        /**
         * 查询数据列表的方法
         * @param baseMapper 查询代理类
         * @param pageStart 数据列表的起点
         * @param pageSize 数据长度
         * @return 数据列表
         */
        default List<T> doListExecutor(BaseMapper baseMapper, int pageStart, int pageSize){
            return null;
        }

        /**
         *
         * 根据查询条件查询数据列表的方法
         * @param baseMapper 查询代理类
         * @param pageStart 数据列表的起点
         * @param pageSize 数据长度
         * @param queryCondition 查询条件
         * @param queryValue 查询条件值
         * @return 数据列表
         */
        default List<T> doListExecutorByQueryCondition(BaseMapper baseMapper, int pageStart, int pageSize,String queryCondition,String queryValue){
            return null;
        }

        /**
         *  查询数据条数的方法
         * @param baseMapper 查询代理类
         * @return 数据条数
         */
        default Long doCountExecutor(BaseMapper baseMapper){
            return null;
        }

        /**
         * 根据查询条件查询数据条数的方法
         * @param baseMapper 查询代理类
         * @param queryCondition 查询条件
         * @param queryValue 查询条件值
         * @return 数据条数
         */
        default Long doCountExecutorByQueryCondition(BaseMapper baseMapper,String queryCondition,String queryValue){
            return null;
        }
        /**
         * 调用增删改方法的模板方法
         * @param baseDao 增删改操作的类
         * @return 影响的行数
         */
        default int doDataModifyExecutor(BaseDao baseDao){ return -1;}
    }


