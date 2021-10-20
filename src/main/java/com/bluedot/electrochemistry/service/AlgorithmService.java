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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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
    *  2.算法数据编译处理，这个也还没有做
    * */
    private void addAlgorithm(Map<String, Object> map){
        File file  = (File) map.get("file");
        BufferedReader reader = null;
        try {
            reader = new  BufferedReader(new FileReader(file));
            StringBuffer str = new StringBuffer();
            String temp = "";
            while ((temp = reader.readLine()) != null) {
                str.append(temp);
                //TODO 算法数据编译处理
            }
            System.out.println("file ---------------- " + str);
            //数据库数据添加
            doSimpleModifyTemplate(map, new ServiceCallback<Object>() {
                @Override
                public int doDataModifyExecutor(BaseDao baseDao) {
                    Algorithm algorithm = new Algorithm();
                    algorithm.setAlgorithmName((String) map.get("algorithmName"));
                    algorithm.setClassification((Integer) map.get("classification"));
                    algorithm.setUrl(file.getAbsolutePath());
                    algorithm.setUsername((Integer) map.get("username"));
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
            map.put("code",404);
        }finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    map.put("message",e.getMessage());
                    map.put("code",404);
                }
            }
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
}
