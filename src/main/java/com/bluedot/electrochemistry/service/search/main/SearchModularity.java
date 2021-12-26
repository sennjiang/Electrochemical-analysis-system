package com.bluedot.electrochemistry.service.search.main;

import com.bluedot.electrochemistry.service.Lifecycle;
import com.bluedot.electrochemistry.service.exception.IllegalIndexException;
import com.bluedot.electrochemistry.service.search.SearchDirection;
import com.bluedot.electrochemistry.service.search.condition.AccountCondition;
import com.bluedot.electrochemistry.service.search.condition.Conditional;
import com.bluedot.electrochemistry.service.search.pages.PageSearchable;

import java.util.List;

/**
 * @author Sens
 * @Create 2021/12/16 18:58
 */
public interface SearchModularity extends Lifecycle {

    List<?> doService(Conditional condition , PageSearchable<?> pageSearchable);
}