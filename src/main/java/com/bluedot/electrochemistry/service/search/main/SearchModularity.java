package com.bluedot.electrochemistry.service.search.main;

import com.bluedot.electrochemistry.service.Lifecycle;
import com.bluedot.electrochemistry.service.search.SearchDirection;

/**
 * @author Sens
 */
public abstract class SearchModularity implements Lifecycle {

    private SearchDirection direction;

    @Override
    public void destroy() {
        direction.destroy();
    }
}
