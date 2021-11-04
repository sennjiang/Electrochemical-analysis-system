package com.bluedot.electrochemistry.service;

import com.bluedot.electrochemistry.dao.base.BaseDao;
import com.bluedot.electrochemistry.dao.base.BaseMapper;
import com.bluedot.electrochemistry.factory.MapperFactory;
import com.bluedot.electrochemistry.pojo.domain.Algorithm;
import com.bluedot.electrochemistry.pojo.vo.AlgorithmView;
import com.bluedot.electrochemistry.service.base.BaseService;
import com.bluedot.electrochemistry.service.callback.ServiceCallback;
import com.bluedot.framework.simplespring.core.annotation.Service;
import com.bluedot.framework.simplespring.inject.annotation.Autowired;
import com.bluedot.framework.simplespring.util.PathUtil;
import org.springframework.util.ResourceUtils;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;
import java.util.Map;

/**
 * 算法业务
 * @author zero
 */
@Service
public class AlgorithmService extends BaseService {

    @Autowired
    MapperFactory mapperFactory;

    /*//项目路径
    private String projectPath = null;*/

    /**算法上传到服务器磁盘的存放目录*/
    private final String uploadPath = null;

    /**算法进行动态编译后存放class文件的位置*/
    private final String compilePath = null;

    /**
     * 加载文件中的数据
     * @author zero
     * @param map 包含algorithmId被加载的算法的id
     * 会将加载完数据的算法实体在函数体内放入到map中去
     */
    private void loadingData(Map<String, Object> map){
        int algorithmId = (Integer) map.get("algorithmId");
        BaseMapper mapper = mapperFactory.createMapper();
        Algorithm algorithm = mapper.getAlgorithmById(algorithmId);
//        new File(algorithm.getUrl());
        //todo 核心代码提取，加载到容器并传给前端

    }

    /**
     * 添加算法
     * @author zero
     * @param map map中包含参数：
     *      file 算法文件
     *      algorithm被添加的算法实体的所需数据：
     *              algorithmName
     *              username
     *              classification
     *      username当前修改的用户的账号
     * 添加成功与否的结果会放入到map中去，0失败，1成功
     */
    /*TODO
       1.事务处理还没有做,数据库中的数据要和文件上传做事务同步提交
    * */
    private void addAlgorithm(Map<String, Object> map){
        File oldFile  = (File) map.get("file");
        File uploadPath = new File(getUploadPath());
        File newFile = new File(uploadPath, (String) map.get("fileName"));
        if (!uploadPath.exists()) { uploadPath.mkdirs(); }
        if (!newFile.exists()) {
            try {
                newFile.createNewFile();
            } catch (IOException e) {
                map.put("code", 500);
                map.put("message", "文件创建失败");
                oldFile.delete();
                return;
            }
        }
        BufferedReader reader = null;
        BufferedWriter writer = null;
        try {
            reader = new BufferedReader(new FileReader(oldFile));
            writer = new BufferedWriter(new FileWriter(newFile));
            StringBuffer str = new StringBuffer();
            String temp = "";
            while ((temp = reader.readLine()) != null) {
                str.append(temp);
                writer.write(temp);
                writer.newLine();
                writer.flush();
            }
            System.out.println("file ---------------- " + str);
            //数据库数据添加
            doSimpleModifyTemplate(map, new ServiceCallback<Object>() {
                @Override
                public int doDataModifyExecutor(BaseDao baseDao) {
                    Algorithm algorithm = new Algorithm();
                    //删除文件的后缀.java
                    StringBuffer sb = new StringBuffer((String) map.get("fileName"));
                    sb.delete(sb.length()-5,sb.length());
                    algorithm.setAlgorithmName(sb.toString());
                    algorithm.setClassification(Integer.parseInt((String) map.get("classification")));
                    algorithm.setUrl(newFile.toString());
                    algorithm.setUsername(Integer.parseInt((String) map.get("username")));
                    int insert = baseDao.insert(algorithm);
                    if (insert != 1) {
                        map.put("code", 500);
                        map.put("message", "添加算法到数据库失败");
                    } else {
                        map.put("code", 200);
                        map.put("message", "添加算法成功");
                    }
                    return insert;
                }
            });
        }catch (Exception e) {
            map.put("message",e.getMessage());
            map.put("code",500);
        }finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    map.put("message",e.getMessage());
                    map.put("code",500);
                }
            }
            if(writer != null) {
                try {
                    writer.close();
                }catch (IOException e) {
                    map.put("message",e.getMessage());
                    map.put("code",500);
                }
            }
        }
        if(oldFile.exists()){
            oldFile.delete();
        }
    }

    /**
     * 删除算法
     * @author zero
     * @param map map中包含参数：
     *      algorithmId被删除的算法的id
     *      username当前修改的用户的账号
     * 删除成功与否的结果会放入到map中去，0失败，1成功
     */
    /*TODO
       1.事务处理还没有做,数据库中的数据要和文件删除做事务同步提交
    * */
    private void deleteAlgorithm(Map<String, Object> map){
        try {
            doSimpleModifyTemplate(map, new ServiceCallback<Object>() {
                @Override
                public int doDataModifyExecutor(BaseDao baseDao) {
                    Integer algorithmId = (Integer) map.get("algorithmId");
                    Algorithm alg = mapperFactory.createMapper().getAlgorithmById(algorithmId);
                    boolean delete = new File(alg.getUrl()).delete();
                    int del = -1;
                    if (delete) {
                        del = baseDao.delete(alg);
                        if (del != 1) {
                            map.put("code", 500);
                            map.put("message", "删除算法失败");
                        } else {
                            map.put("code", 200);
                            map.put("message", "删除算法成功");
                        }
                    }
                    return del;
                }
            });
        }catch (Exception e){
            map.put("code", 500);
            map.put("message", "删除算法失败");
        }
    }

    /**
     * 查询所有算法列表
     * @author zero
     * @param map map中包含参数：
     *      page --> pageStart
     *      limit --> pageSize
     * 查询结果会以list列表的形式放回到map中去
     */
    private void listAlgorithms(Map<String,Object> map){
        BaseMapper mapper = mapperFactory.createMapper();
        Integer pageStart = Integer.parseInt((String) map.get("page"));
        Integer pageSize = Integer.parseInt((String) map.get("limit"));
        List<AlgorithmView> algs = mapper.getAlgorithms((pageStart - 1) * pageSize,pageSize);
        Long size = mapper.getAlgorithmsCount();
        if(algs!=null){
            map.put("message", "算法列表查询成功");
            map.put("code", 200);
            map.put("data",algs);
            map.put("length", size);
        }else{
            map.put("code", 500);
            map.put("message", "算法列表查询失败");
        }
    }

    /**
     * 根据条件查询所有符合要求的算法集合
     * @param map 包含查询条件 queryCondition
     */
    private void searchAlgorithms(Map<String, Object> map) {
        try {
            BaseMapper mapper = mapperFactory.createMapper();
            Integer pageStart = Integer.parseInt((String) map.get("page"));
            Integer pageSize = Integer.parseInt((String) map.get("limit"));
            String queryCondition = (String) map.get("title");
            Long size = null;
            List<AlgorithmView> algorithms = mapper.getAlgorithmsByQueryCondition("%"+queryCondition+"%", "%"+queryCondition+"%", (pageStart - 1) * pageSize, pageSize);
            size = mapper.getAlgorithmsCountByQueryCondition("%"+queryCondition+"%", "%"+queryCondition+"%");

            map.put("data", algorithms);
            map.put("length", size);
            map.put("code", 200);
            map.put("message", "算法列表搜索完成");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", 500);
            map.put("message", "算法列表搜索失败："+e.getMessage());
        }
    }

    /**
     * 动态编译运行算法文件
     */
    private void compileAndRun(Map<String, Object> map) {
        int algorithmId = Integer.parseInt( (String) map.get("algorithmId"));
        BaseMapper mapper = mapperFactory.createMapper();
        Algorithm algorithm = mapper.getAlgorithmById(algorithmId);
        //动态编译
        try {
            JavaCompiler javac = ToolProvider.getSystemJavaCompiler();
            File file = new File(getCompilePath());
            if (!file.exists()) file.mkdirs();
            int status = javac.run(null, null, null,"-d", getCompilePath(),algorithm.getUrl());
            if (status != 0) {
                map.put("code", 500);
                map.put("message", "算法编译失败!");
                System.out.println("算法编译失败!");
            } else {  //动态执行
                //获取url对象
                URL url = file.toURI().toURL();
                //创建url类加载器
                URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{url});
                Class clz = urlClassLoader.loadClass(algorithm.getAlgorithmName());
                Object obj = clz.newInstance();
                Method method = clz.getDeclaredMethod("start");
                method.invoke(obj);
                map.put("code", 200);
                map.put("message", "算法编译执行成功!");
                System.out.println("算法编译执行成功!");
            }
        }catch (Exception e){
            map.put("code", 500);
            map.put("message", "算法编译或运行失败:"+e.getMessage());
            System.out.println("算法编译或运行失败:"+e.getMessage());
        }
    }

    /**
     * 测试方法
     */
    private void test(Map<String, Object> map){
        System.out.println(PathUtil.getAppPath()+"/com/bluedot/electrochemistry/uploadAlgorithms");
        System.out.println("----------------------");
        System.out.println(this.getClass().getResource("/").getPath());
        System.out.println("----------------------");
        System.out.println(this.getClass().getClassLoader().getResource(""));
    }

    /**
     * 获取编译路径
     * @return 编译路径
     */
    private String getCompilePath(){
        if(this.compilePath != null) return this.compilePath;
        else {
            StringBuffer sb = new StringBuffer(this.getClass().getResource("/").getPath());
            //去除'target/Electrochemical-Analysis-System/WEB-INF/classes/'
            sb.delete(sb.length()-55,sb.length());
            //去除初始的'/'
            sb.delete(0,1);
            sb.append("uploads/uploadAlgorithmClasses");
            return sb.toString();
        }
    }

    /**
     * 获取上传路径
     * @return 上传路径
     */
    private String getUploadPath(){
        if(this.uploadPath != null) return this.uploadPath;
        else {
            StringBuffer sb = new StringBuffer(this.getClass().getResource("/").getPath());
            //去除'target/Electrochemical-Analysis-System/WEB-INF/classes/'
            sb.delete(sb.length()-55,sb.length());
            //去除初始的'/'
            sb.delete(0,1);
            sb.append("uploads/uploadAlgorithms");
            return sb.toString();
        }
    }
}
