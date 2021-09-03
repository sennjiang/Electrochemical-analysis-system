# Electrochemical-analysis-system
2019界暑假项目，电化学分析系统、一个不错的开始，

### 日志系统
**我们将使用slf4j+log4j2的模式**

使用slf4j可以很好的保证我们的日志系统具有良好的兼容性， 
兼容当前常见几种日志系统，
而使用log4j2而不是log4j是因为Log4j 1.x 在高并发情况下出现死锁导致cpu使用率异常飙升，
而Log4j2.0基于LMAX Disruptor的异步日志在多线程环境下性能会远远优于Log4j 1.x和logback（官方数据是10倍以上）。