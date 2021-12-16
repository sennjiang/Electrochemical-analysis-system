package com.bluedot.electrochemistry.service.search.condition;

/**
 * @author Sens
 * @Create 2021/12/16 18:58
 */
public interface Conditional {

    /**
     *  获取 sql语句 where 后的 的条件 除 limit
     * @return sql语句
     */
    String decodeCondition();

    /**
     *  验证条件 防止sql注入
     * @return sql语句
     */
    boolean checkCondition();
}
