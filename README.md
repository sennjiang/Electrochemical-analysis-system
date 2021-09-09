# Electrochemical-analysis-system
2019届暑假项目，电化学分析系统、一个不错的开始，


## 日志系统
**我们将使用slf4j+log4j2的模式**

使用slf4j可以很好的保证我们的日志系统具有良好的兼容性，
兼容当前常见几种日志系统，
而使用log4j2而不是log4j是因为Log4j 1.x 在高并发情况下出现死锁导致cpu使用率异常飙升，
而Log4j2.0基于LMAX Disruptor的异步日志在多线程环境下性能会远远优于Log4j 1.x和logback（官方数据是10倍以上）。

## 队列
本项目传递数据将采用队列的形式

队列是自行实现的  **BlockQueue 塞数组队列** 参考于jdk中的BockingArrayQueue

队列一经创建，则不可改变其大小

队列实例：

BlockQueue<Data> downBlockQueue;
数据向下传递的队列，由QueueMonitor接收并处理

BlockQueue<Data> upBlockQueue;
数据向上传递的队列，由MQRequestProcessor.Adapter接收并处理返回
数据处理后，选择JsonResultRender 进行处理

### 数据 Data
Data 实现java.util.Map接口
Data 采用 **门面模式** 将request及其parameter进行封装，


### QueueMonitor 队列监听
QueueMonitor 实现 Runnable 接口
监听向下队列,发现有数据就进行处理，处理完成以后，放入向上队列
每次处理数据，默认间隔50ms tomcat底层 每次获取请求 处理间隔时间为50ms 当队列为空时，默认睡眠500ms



队列初始化，将初始化 BlockQueue capacity



### 说明：
BlockQueue 的使用 参考了MQ消息队列，实现了MQ的基本功能，因为BlockQueue集成于项目内部，异步的特点 无法完全体现，且消耗性能，于是使用IOP进行异步操作

### 优势：
1.将controller与service解耦，在此项目中 service 无直接操作

2.采用多线程，遇到高并发时，将阻塞，完成削峰功能，网站不会直接boom


### 运用：
运用 java多线程、封装。 设计模式：责任链，门面。

### 缺点：
目前MQRequestProcessor.Adapter 每个请求一个线程，待使用缓存,多线程的使用，尚未成熟

### 优化：
采用更优秀的NIO模型处理多并发、使用缓存，将类重复使用。

### 配置：
application.properties

monitor
//队列排序阈值 % 默认75%
monitor.thread.orderingThreshold=75

//监听数据队列间隔 ms 默认50ms
monitor.thread.frequency=50

//监听数据队列 没有数据时 默认睡眠时间 500ms
monitor.thread.sleepTime=500