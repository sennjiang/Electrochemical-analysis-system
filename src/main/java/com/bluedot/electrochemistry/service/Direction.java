package com.bluedot.electrochemistry.service;

public abstract class Direction<T> implements Lifecycle{

    protected T[] index;

    protected abstract T get(int index);

    @Override
    public void destroy() {
        index = null;
    }
}
