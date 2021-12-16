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
public abstract class SearchModularity implements Lifecycle {

    private SearchDirection direction;

    public void doService() {
        Conditional conditional = new AccountCondition();
        PageSearchable pageSearchable = null;

        try {
            pageSearchable = direction.get(0);
        } catch (IllegalIndexException e) {
            //TODO
            e.printStackTrace();
        }

        List list = pageSearchable.search(conditional, 0, 10);
    }


    @Override
    public void destroy() {
        direction.destroy();
    }
}
