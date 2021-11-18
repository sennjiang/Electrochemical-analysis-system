package com.bluedot.framework.simplespring.mvc.mapper;

import com.bluedot.framework.simplespring.util.LogUtil;
import javafx.util.Pair;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;

import java.io.InputStream;
import java.util.*;

/**
 * @author JDsen99
 * @description
 * @createDate 2021/10/2-11:50
 */
public class CommonMapper {


    private Logger logger = LogUtil.getLogger();

    /**
     * Service 映射字典
     */
    public  static Map<String, Pair<Class, String>> methodMapper;

    /**
     * 文件操作类型
     */
    public static Map<String,Integer> fileTypeMapper;

    /**
     * 操作的 角色级别
     */
    public static Map<String,Integer> typeMapper;

    /**
     * 文件上传时的路径 在项目路径下 /uploads/  需在配置文件里配置
     */
    public static Map<String,String> filePathMapper;


    public final static List<String> fileList = new ArrayList<>();

    /**
     * 请求白名单（以 boundary 为 键）
     */
    public static List<String> writeList = new ArrayList<>();

    public static List<String> doLogMapper = new ArrayList<>();


    public void initMapper(Properties contextConfig) {
        logger.debug("start init mapper ... ");
        initFileTypeMapper();
        initTypeMapper();
        initMethodMapper(contextConfig);
        initFileList(contextConfig);
        initFilePathMapper(contextConfig);
        initWhitePathMapper(contextConfig);
        initDoLogMapper();
        logger.debug("end init mapper ... ");
    }

    private void initDoLogMapper() {
        doLogMapper.add("0105");
        doLogMapper.add("0108");
        doLogMapper.add("0109");
        doLogMapper.add("0112");
        doLogMapper.add("0205");
        doLogMapper.add("0206");
        doLogMapper.add("0207");
        doLogMapper.add("0209");
        doLogMapper.add("0210");
        doLogMapper.add("0214");
        doLogMapper.add("0405");
        doLogMapper.add("0406");
        doLogMapper.add("0409");
        doLogMapper.add("0410");
        doLogMapper.add("0411");
        doLogMapper.add("0501");
        doLogMapper.add("0503");
        doLogMapper.add("0504");
        doLogMapper.add("0601");
        doLogMapper.add("0703");
        doLogMapper.add("0704");
        doLogMapper.add("0705");
        doLogMapper.add("0804");
        doLogMapper.add("1002");
        doLogMapper.add("1003");
        doLogMapper.add("1004");
        doLogMapper.add("1006");
        doLogMapper.add("1201");
        doLogMapper.add("1202");
        doLogMapper.add("1301");
        doLogMapper.add("1302");
        doLogMapper.add("1403");
    }

    private void initWhitePathMapper(Properties contextConfig) {
        String whiteList = contextConfig.getProperty("whiteList");
        if (whiteList.contains(",")) {
            String[] split = whiteList.split(",");
            writeList.addAll(Arrays.asList(split));
        }
    }

    private void initFilePathMapper(Properties contextConfig) {
        filePathMapper = new HashMap<>();
        String filePath = contextConfig.getProperty("filePath");
        if (filePath.contains(";")){
            String[] filePaths = filePath.split(";");
            for (String path : filePaths) {
                if (path.contains(",")){
                    String[] str = path.split(",");
                    filePathMapper.put(str[0], "/uploads" + str[1]);
                }
            }

        }
    }

    private void initFileList(Properties contextConfig) {
        String boundary = contextConfig.getProperty("fileBoundary");
        //排除空格
        boundary = boundary.trim();
        //分割 ，
        if (boundary.contains(",")){
            if (boundary.endsWith(",")) boundary = boundary.substring(0,boundary.length()-1);
            String[] split = boundary.split(",");
            fileList.addAll(Arrays.asList(split));
        }
    }

    private void initFileTypeMapper() {
        fileTypeMapper = new HashMap<>();
        fileTypeMapper.put("0201",5);
        fileTypeMapper.put("0203",1);
        fileTypeMapper.put("0205",1);
        fileTypeMapper.put("0206",2);
        fileTypeMapper.put("0207",5);
        fileTypeMapper.put("0209",4);
        fileTypeMapper.put("0210",3);
        fileTypeMapper.put("0214",4);
    }
    private void initTypeMapper() {
        typeMapper = new HashMap<>();
        typeMapper.put("0214", 2);
        typeMapper.put("0404", 2);
        typeMapper.put("0405", 2);
        typeMapper.put("0406", 2);
        typeMapper.put("0409", 2);
        typeMapper.put("0410", 2);
        typeMapper.put("0411", 2);
        typeMapper.put("0412", 3);
        typeMapper.put("0413", 3);
        typeMapper.put("0503", 3);
        typeMapper.put("0504", 3);
        typeMapper.put("0703", 3);
        typeMapper.put("0704", 3);
        typeMapper.put("0705", 3);
        typeMapper.put("0803", 3);
    }

    private void initMethodMapper(Properties contextConfig){
        ClassLoader classLoader = this.getClass().getClassLoader();
        String serviceXmlName = contextConfig.getProperty("serviceMapper.name");
        String serviceXmlPath = contextConfig.getProperty("serviceMapper.path");

        logger.debug("Loading parsingXmlMappings --->name:{} ", serviceXmlName);
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(serviceXmlName);
        Document doc = null;
        try {
            SAXReader reader = new SAXReader();
            methodMapper = new HashMap();
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

                Class<?> clazz = classLoader.loadClass(serviceXmlPath + "." + service1);
                methodMapper.put(number, new Pair<Class, String>(clazz, method));
            }

            logger.debug("parsingXmlMappings had complete ---> name: {}", "xmlMap");

        } catch (DocumentException e) {
            logger.error("service.xml parse error", e);
        } catch (ClassNotFoundException e) {
            logger.error("service.xml load Class error", e);
        }
    }
}
