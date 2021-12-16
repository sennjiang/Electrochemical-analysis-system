package com.bluedot.electrochemistry.service.search.pages;

import com.bluedot.electrochemistry.service.search.condition.Conditional;

import java.util.List;

/**
 * @author Sens
 * @Create 2021/12/16 18:58
 */
public interface PageSearchable<E> {

    /**
     * 调用 mapper 做查询
     * @param condition 条件对象 用于生成条件语句
     * @param pageStart 分页开始
     * @param pageSize 分页大小
     */
    List<E> search(Conditional condition, int pageStart, int pageSize);

}
