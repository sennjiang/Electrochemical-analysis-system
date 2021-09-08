package com.bluedot.framework.simplespring.mvc.monitor;

import com.bluedot.electrochemistry.service.base.BaseService;
import com.bluedot.framework.simplespring.core.BeanContainer;
import com.bluedot.framework.simplespring.mvc.processor.RequestProcessor;
import com.bluedot.framework.simplespring.mvc.processor.impl.MQRequestProcessor;
import com.bluedot.framework.simplespring.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.processing.Processor;
import java.io.IOException;

/**
 * @author JDsen99
 * @description
 * @createDate 2021/9/6-17:57
 */
public class QueueMonitor implements Runnable {

    private Logger logger = LoggerFactory.getLogger(Thread.currentThread().getName());

    private BeanContainer beanContainer = BeanContainer.getInstance();

    private static final int MAX_CAPACITY_PRICE = 1000;
    private static final int MAX_SLEEP_TIME_PRICE = 10000;
    private static final int MAX_FREQUENCY_PRICE = 10000;
    private static final int MIN_PARAM_PRICE = 0;

    /**
     * service基础路径
     */
    private String serviceBasePath = "com.bluedot.electrochemistry.service";

    /**
     * 队列容量
     */
    private Integer capacity = 10;

    /**
     * 向下队列 数据队列
     */
    private BlockQueue<Data> downBlockQueue;

    /**
     * 向上队列
     */
    private BlockQueue<Data> upBlockQueue;

    /**
     * 排序阈值 % 默认 75%
     */
    private int orderingThreshold = 75;

    /**
     * 监听器监听队列的频率 单位 毫秒
     */
    private Integer frequency;

    /**
     * 队列为空时 睡眠时间
     */
    private Integer sleepTime;

    /**
     * 是否运行ing
     */
    private Boolean running = false;


    public QueueMonitor(Integer capacity) {
        this(capacity,50,500);
    }

    public QueueMonitor(Integer capacity, Integer frequency, Integer sleepTime) {
        checkParameterValid(capacity,frequency,sleepTime);
        this.capacity = capacity;
        downBlockQueue = new BlockQueue<>(capacity);
        upBlockQueue = new BlockQueue<>(capacity);
        this.frequency = frequency;
        this.sleepTime = sleepTime;
    }

    /**
     * 判断参数是否合法
     * @param capacity
     * @param frequency
     * @param sleepTime
     */
    private void checkParameterValid(Integer capacity, Integer frequency, Integer sleepTime) {
        if (capacity < MIN_PARAM_PRICE
                || frequency < MIN_PARAM_PRICE
                || sleepTime < MIN_PARAM_PRICE ) {
            logger.error("QueueMonitor parameter illegal param < 0 error");
            throw new RuntimeException("QueueMonitor parameter param < 0 error");
        }
        if (capacity > MAX_CAPACITY_PRICE
                || frequency > MAX_FREQUENCY_PRICE
                || sleepTime > MAX_SLEEP_TIME_PRICE) {
            logger.error("QueueMonitor parameter illegal param > limit error");
            throw new RuntimeException("QueueMonitor parameter param > limit error");
        }
    }

    @Override
    public void run() {

        while (running) {
            try {
                Thread.sleep(frequency);
                if (isSort()) {
                    downBlockQueue.sort();
                }
                if (downBlockQueue.size() > 0) {
                    Data data = downBlockQueue.take();
                    if (data != null) {
                        logger.info("开始处理请求---{}", data.getRequest().getPathInfo());
                        String serviceName = data.getService();
                        Class clazz = Thread.currentThread().getContextClassLoader().loadClass(serviceBasePath + "." + serviceName);
                        logger.info(clazz.getName() + clazz);
                        BaseService service = (BaseService) beanContainer.getBean(clazz);
                        service.doService(data);
                        logger.info("处理请求结束---{}", data.getRequest().getPathInfo());
                        upBlockQueue.put(data);
                        Thread.yield();
                    }
                }else {
                    Thread.yield();
                }
            } catch (InterruptedException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取阈值
     * @return
     */
    private double getThreshold() {
        if (orderingThreshold < MIN_PARAM_PRICE || orderingThreshold > 100) {
            logger.error("orderingThreshold illegal : {}", orderingThreshold);
            throw new RuntimeException("orderingThreshold illegal error");
        }
        return ((double) orderingThreshold) / 100L;
    }

    /**
     * 是否需要排序
     * @return
     */
    private Boolean isSort() {
        return (downBlockQueue.size() / capacity) >= getThreshold();
    }

    public void setRunning(Boolean running) {
        this.running = running;
    }

    public BlockQueue<Data> getDownBlockQueue() {
        return downBlockQueue;
    }
    public BlockQueue<Data> getUpBlockQueue() {
        return upBlockQueue;
    }

    public Boolean getRunning() {
        return running;
    }
}
