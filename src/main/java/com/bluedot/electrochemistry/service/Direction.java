package com.bluedot.electrochemistry.service;

import com.bluedot.electrochemistry.service.exception.IllegalIndexException;
import com.bluedot.electrochemistry.service.search.pages.PageSearchable;

/**
 * @author Sens
 * @Create 2021/12/16 18:58
 */
public abstract class Direction<T> implements Lifecycle{

    protected PageSearchable[] indexs;

    protected int capacity;

    public abstract T get(int index) throws IllegalIndexException;

    @Override
    public void destroy() {
        indexs = null;
    }
}
