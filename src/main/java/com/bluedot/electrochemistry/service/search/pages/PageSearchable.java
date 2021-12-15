package com.bluedot.electrochemistry.service.search.pages;

import com.bluedot.electrochemistry.service.search.condition.Conditional;

/**
 * @author Sens
 */
public interface PageSearchable {

    /**/
    void search(Conditional condition, int pageStart, int pageSize);

}
