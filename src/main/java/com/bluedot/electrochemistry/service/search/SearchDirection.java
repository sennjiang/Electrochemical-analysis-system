package com.bluedot.electrochemistry.service.search;

import com.bluedot.electrochemistry.service.Direction;
import com.bluedot.electrochemistry.service.search.pages.AccountPage;
import com.bluedot.electrochemistry.service.search.pages.PageSearchable;

public class SearchDirection extends Direction<PageSearchable> {
    @Override
    protected PageSearchable get(int index) {
        return null;
    }

    @Override
    public void init() {
        index = new PageSearchable[1];
        index[0] = new AccountPage();
    }
}
