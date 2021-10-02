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
数据处理后，按照是否有error 进行选择后置处理器进行处理

### 数据 Data
Data 实现java.util.Map接口
Data 采用 **门面模式** 将request及其parameter进行封装，


### 说明：
1.BlockQueue 的使用 继承与BlockQueue 将其重写，作为线程池的队列对象，待实现排序功能

2.引用java线程池，将每一个请求封装为Adapter(task任务)将其交给线程池并发处理，速度提升，线程池会默认回收task。




使用requestId 来标识唯一request 进行线程提取 解决之前 使用线程名做标识时重复的问题，requestId为线程安全的自增Long

### 优势：
1.将controller与service解耦，在此项目中 service 无直接操作

2.采用多线程，遇到高并发时，将阻塞请求，完成削峰功能，网站不会直接boom

3.采用线程池技术，future异步技术，进一步实现速度优化。


### 运用：
运用 java多线程、线程池、封装。 设计模式：责任链，门面。


### 已实现提升：
1. MQRequestProcessor.Adapter 从实现runnable 接口--->提升为实现callable接口 实现异步操作。

### 配置：
application.properties

## CommonMapper
对DispatcherServlet 进行解耦, 将所有的映射关系，提取到 CommonMapper 提供初始化方法 initMapper() 

