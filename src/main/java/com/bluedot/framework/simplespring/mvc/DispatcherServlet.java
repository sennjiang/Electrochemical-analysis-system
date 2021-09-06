package com.bluedot.framework.simplespring.mvc;


import com.bluedot.electrochemistry.service.FileService;
import com.bluedot.framework.simplemybatis.session.SqlSessionFactoryBuilder;
import com.bluedot.framework.simplespring.aop.AspectWeaver;
import com.bluedot.framework.simplespring.core.BeanContainer;
import com.bluedot.framework.simplespring.inject.DependencyInject;
import com.bluedot.framework.simplespring.util.LogUtil;
import javafx.util.Pair;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;

import javax.servlet.ServletConfig;
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
    private Properties contextCofig = new Properties();

    /**
     * 请求处理器
     */
    //List<RequestProcessor> PROCESSORS=new ArrayList<>();
    /**
     * 日志
     */
    private final Logger log = LogUtil.getLogger();

    @Override
    public void init(ServletConfig servletConfig) {
        //读取配置文件
        doLoadConfig(servletConfig.getInitParameter("contextConfigLocation"));
        //初始化容器
        BeanContainer beanContainer = BeanContainer.getInstance();
        beanContainer.loadBeans(contextCofig.getProperty("scanPackage"));
        //AOP织入
        new AspectWeaver().doAspectOrientedProgramming();
        //初始化简易mybatis框架，往IoC容器中注入SqlSessionFactory对象
        new SqlSessionFactoryBuilder().build(servletConfig.getInitParameter("contextConfigLocation"));
        //依赖注入
        new DependencyInject().doDependencyInject();
        // xml字典映射 处理
        doParsingXmlMappings("service.xml");

        //初始化请求处理器责任链
//        PROCESSORS.add(new PreRequestProcessor());
//        PROCESSORS.add(new StaticResourceRequestProcessor(servletConfig.getServletContext()));
//        PROCESSORS.add(new JspRequestProcessor(servletConfig.getServletContext()));
//        PROCESSORS.add(new ControllerRequestProcessor());
    }

    public void doParsingXmlMappings(String serviceXmlName) {
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(serviceXmlName);
        Document doc = null;
        Map<String, Pair> xmlMap = null;
        try {
            SAXReader reader = new SAXReader();
            xmlMap = new HashMap();

            doc = reader.read(resourceAsStream);

            Element service = doc.getRootElement();
            Iterator nodes = service.elementIterator();

            String number = null;
            String service1 = null;
            String method = null;

            while (nodes.hasNext()) {
                Element node = (Element) nodes.next();
                //取节点的值
                number = (String) node.element("number").getData();
                service1 = (String) node.element("service").getData();
                method = (String) node.element("method").getData();
                xmlMap.put(number, new Pair(service1, method));
            }

            BeanContainer.getInstance().addBean(Map.class,xmlMap);
        } catch (DocumentException e) {
            log.error("service.xml parse error",e);
            e.printStackTrace();
        }

    }


    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("service","export");
        new FileService().doService(hashMap);
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
            contextCofig.load(is);
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
//        //关闭线程池
//        ResultCache.pool.shutdown();
//        //关闭连接池
//        MyDataSourceImpl.getInstance().close();
//        //注销驱动
//        Enumeration<Driver> drivers = DriverManager.getDrivers();
//        Driver driver = null;
//        while (drivers.hasMoreElements()) {
//            try {
//                driver = drivers.nextElement();
//                DriverManager.deregisterDriver(driver);
//                LogUtil.getLogger().debug("deregister success : driver {}" ,driver);
//            } catch (SQLException e) {
//                LogUtil.getLogger().error("deregister failed : driver {}" ,driver);
//            }
//        }

    }
}
