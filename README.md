# Electrochemical-analysis-system
2019届暑假项目，电化学分析系统、一个不错的开始，

### 日志系统
**我们将使用slf4j+log4j2的模式**

使用slf4j可以很好的保证我们的日志系统具有良好的兼容性， 
兼容当前常见几种日志系统，
而使用log4j2而不是log4j是因为Log4j 1.x 在高并发情况下出现死锁导致cpu使用率异常飙升，
而Log4j2.0基于LMAX Disruptor的异步日志在多线程环境下性能会远远优于Log4j 1.x和logback（官方数据是10倍以上）。

### 队列
本项目传递数据将采用队列的形式

队列是自行实现的  **BlockQueue 塞数组队列** 参考于jdk中的BockingArrayQueue

队列实例：

BlockQueue<Data> downBlockQueue;
数据向下传递的队列，由QueueMonitor接收并处理
BlockQueue<Data> upBlockQueue;
数据向上传递的队列，由MQRequestProcessor.Adapter接收并处理返回

#### 数据 Data
Data 实现java.util.Map接口
Data 采用 **门面模式** 将request及其parameter进行封装

#### QueueMonitor 队列监听
QueueMonitor 实现 Runnable 接口 

优势：TODO

说明：TODO

运用：TODO

优化：TODO