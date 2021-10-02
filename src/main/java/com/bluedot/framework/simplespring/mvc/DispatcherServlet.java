package com.bluedot.framework.simplespring.mvc;

import com.bluedot.framework.simplemybatis.pool.MyDataSourceImpl;
import com.bluedot.framework.simplemybatis.session.SqlSessionFactoryBuilder;
import com.bluedot.framework.simplespring.aop.AspectWeaver;
import com.bluedot.framework.simplespring.core.BeanContainer;
import com.bluedot.framework.simplespring.inject.DependencyInject;
import com.bluedot.framework.simplespring.mvc.mapper.CommonMapper;
import com.bluedot.framework.simplespring.mvc.processor.RequestProcessor;
import com.bluedot.framework.simplespring.mvc.processor.impl.MQRequestProcessor;
import com.bluedot.framework.simplespring.mvc.processor.impl.PreRequestProcessor;
import com.bluedot.framework.simplespring.mvc.processor.impl.StaticResourceRequestProcessor;
import com.bluedot.framework.simplespring.util.LogUtil;
import javafx.util.Pair;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

/**
 * @author JDsen99
 */
@WebServlet(name = "DispatcherServlet", urlPatterns = "/*",
        initParams = {@WebInitParam(name = "contextConfigLocation", value = "application.properties")},
        loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
    /**
     * 保存application.properties配置文件中的内容
     */
    private Properties contextConfig = new Properties();

    /**
     * 请求处理器
     */
    List<RequestProcessor> PROCESSORS = new ArrayList<>();

    /**
     * 日志
     */
    private final Logger log = LogUtil.getLogger();

    @Override
    public void init(ServletConfig servletConfig) {
        //读取配置文件，保存属性到contextConfig
        doLoadConfig(servletConfig.getInitParameter("contextConfigLocation"));
        //初始化容器
        BeanContainer beanContainer = BeanContainer.getInstance();
        beanContainer.loadBeans(contextConfig.getProperty("scanPackage"));
        //AOP织入
        new AspectWeaver().doAspectOrientedProgramming();
        //初始化简易mybatis框架，往IoC容器中注入SqlSessionFactory对象
        new SqlSessionFactoryBuilder().build(servletConfig.getInitParameter("contextConfigLocation"));
        //依赖注入
        new DependencyInject().doDependencyInject();
        //映射初始化
        new CommonMapper().initMapper(contextConfig);

        //初始化请求处理器责任链
        // 预处理的请求处理器
        PROCESSORS.add(new PreRequestProcessor());
        // 静态资源的请求处理器（如果是静态资源让RequestDispatcher自己处理）
        PROCESSORS.add(new StaticResourceRequestProcessor(servletConfig.getServletContext()));
        // 根据业务需要自定义的请求处理器
        PROCESSORS.add(new MQRequestProcessor(contextConfig));
    }




    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        //1.创建责任链对象实例
        RequestProcessorChain requestProcessorChain = new RequestProcessorChain(PROCESSORS.iterator(), request, response);
        //2.通过责任链模式来一次调用请求处理器对请求进行处理
        requestProcessorChain.doRequestProcessorChain();
        //3.对处理结果进行渲染
        requestProcessorChain.doRender();
    }

    /**
     * 加载配置文件
     *
     * @param contextConfigLocation properties配置文件
     */
    private void doLoadConfig(String contextConfigLocation) {
        //直接通过类路径找到框架主配置文件的路径
        //并将配置文件内容读取到properties对象中
        log.info("Loading configLocation--->path:{} ", contextConfigLocation);
        InputStream is = null;
        try {
            is = this.getClass().getClassLoader().getResourceAsStream(contextConfigLocation);
            contextConfig.load(is);
        } catch (IOException e) {
            LogUtil.getLogger().error(e.getMessage());
            throw new RuntimeException(e);
        } finally {
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void destroy() {
        //关闭连接池
        MyDataSourceImpl.getInstance().close();
        //注销驱动
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        Driver driver = null;
        while (drivers.hasMoreElements()) {
            try {
                driver = drivers.nextElement();
                DriverManager.deregisterDriver(driver);
                LogUtil.getLogger().debug("deregister success : driver {}", driver);
            } catch (SQLException e) {
                LogUtil.getLogger().error("deregister failed : driver {}", driver);
            }
        }

    }
}
