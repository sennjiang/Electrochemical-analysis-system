package com.bluedot.electrochemistry.service.search;

import com.bluedot.electrochemistry.service.Direction;
import com.bluedot.electrochemistry.service.exception.IllegalIndexException;
import com.bluedot.electrochemistry.service.search.pages.AccountPage;
import com.bluedot.electrochemistry.service.search.pages.PageSearchable;

import java.util.List;

/**
 * @author Sens
 * @Create 2021/12/16 18:58
 */
public class SearchDirection extends Direction<PageSearchable> {

    @Override
    public PageSearchable get(int index) throws IllegalIndexException {
        if (index < 0 || index >= capacity) throw new IllegalIndexException("非法索引异常");
        return indexs[index];
    }

    @Override
    public void init() {
        this.capacity = 1;
        indexs = new PageSearchable[1];
        indexs[0] = new AccountPage();
    }
}
