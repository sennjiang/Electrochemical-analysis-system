package com.bluedot.framework.simplespring.mvc.monitor;

import java.util.Arrays;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author JDsen99
 * @description 枷锁 阻塞数组队列
 * @createDate 2021/9/6-18:25
 */
public class BlockQueue<E> {

    private Object[] items;
    private int count = 0;

    final ReentrantLock lock;

    private final Condition notEmpty;

    /**
     * Condition for waiting puts
     */
    private final Condition notFull;

    /**
     * 监听器监听队列的频率 单位 毫秒
     *
     * @param frequency
     */
    private int frequency;

    private int putIndex;
    private int takeIndex;

    public BlockQueue(int capacity) {
        this(capacity, 50);
    }

    public BlockQueue(Integer capacity, int frequency) {
        if (capacity <= 0)
            throw new IllegalArgumentException();
        this.items = new Object[capacity];
        lock = new ReentrantLock(false);
        notEmpty = lock.newCondition();
        notFull = lock.newCondition();
        this.frequency = frequency;
    }

    public void sort() {
        Arrays.sort(items);
    }

    /**
     * 添加元素
     * @param e items
     * @return 成功 ture 失败 false
     */
    public boolean offer(E e) {
        checkNotNull(e);
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            if (count == items.length) {
                return false;
            } else {
                enqueue(e);
                return true;
            }
        } finally {
            lock.unlock();
        }
    }

    /**
     * 添加元素 已满则等待
     * @param e
     * @throws InterruptedException
     */
    public void put(E e) throws InterruptedException {
        checkNotNull(e);
        final ReentrantLock lock = this.lock;
        lock.lockInterruptibly();
        try {
            while (count == items.length)
                notFull.await();
            enqueue(e);
        } finally {
            lock.unlock();
        }
    }

    /**
     * 获取元素 队列已空则等待
     * @return
     * @throws InterruptedException
     */
    public E take() throws InterruptedException {
        final ReentrantLock lock = this.lock;
        lock.lockInterruptibly();
        try {
            while (count == 0)
                notEmpty.await();
            return dequeue();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 插入元素
     * @param x
     */
    private void enqueue(E x) {
        // assert lock.getHoldCount() == 1;
        // assert items[putIndex] == null;
        final Object[] items = this.items;
        items[putIndex] = x;
        if (++putIndex == items.length)
            putIndex = 0;
        count++;
        notEmpty.signal();
    }

    /**
     * 取出元素
     * @return
     */
    private E dequeue() {
        // assert lock.getHoldCount() == 1;
        // assert items[takeIndex] != null;
        final Object[] items = this.items;
        @SuppressWarnings("unchecked")
        E x = (E) items[takeIndex];
        items[takeIndex] = null;
        if (++takeIndex == items.length)
            takeIndex = 0;
        count--;
        notFull.signal();
        return x;
    }

    /**
     * 判断是否为空
     *
     * @param e
     */
    private void checkNotNull(E e) {
        if (e == null || "".equals(e)) {
            throw new NullPointerException();
        }
    }

    /**
     * 队列大小
     * @return
     */
    public int size() {
        return this.count;
    }
}
