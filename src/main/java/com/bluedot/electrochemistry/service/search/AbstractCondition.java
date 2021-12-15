package com.bluedot.electrochemistry.service.search;

import com.bluedot.electrochemistry.service.search.condition.Conditional;

/**
 * @author Sens
 * @Date 2021/12/15 17:09
 */
public abstract class AbstractCondition implements Conditional {
    /**
     * 搜索框内容
     */
    protected String searchContent;

}
