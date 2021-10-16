package com.bluedot.electrochemistry.service;

import com.bluedot.electrochemistry.dao.base.BaseDao;
import com.bluedot.electrochemistry.dao.base.BaseMapper;
import com.bluedot.electrochemistry.factory.MapperFactory;
import com.bluedot.electrochemistry.pojo.domain.File;
import com.bluedot.electrochemistry.service.base.BaseService;
import com.bluedot.electrochemistry.service.callback.ServiceCallback;
import com.bluedot.framework.simplespring.core.annotation.Service;
import com.bluedot.framework.simplespring.inject.annotation.Autowired;
import jdk.nashorn.internal.ir.annotations.Ignore;

import java.io.*;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @description
 * @createDate 2021/8/25-14:37
 */
@Service
public class FileService extends BaseService {

    @Autowired
    MapperFactory mapperFactory;

    /**
     * 导出文件
     *
     * @param map 数据
     */
    private void export(Map<String, Object> map) {
        //测试1,"a.txt","/qwe",1234567890,100d,(short)1,(short)1
        File file = new File(1, "1.txt", "/user/file", 12345, 1D, "sadf123erd", 1, 1, new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()));
        file.setModifiedTime(new Timestamp(System.currentTimeMillis()));
        file.setProduceTime(new Timestamp(System.currentTimeMillis()));
        List<File> list = new ArrayList<>();

        for (int i = 0; i < 60; i++) {
            list.add(file);
        }

        map.put("code", 200);
        map.put("message", "文件列表加载完成");
        map.put("data", list);
        map.put("length", list.size());

    }

    /**
     * 查询文件
     *
     * @param map 数据集合
     */
    private void listFiles(Map<String, Object> map) {
        try {
            String str = (String) map.get("username");
            int username = Integer.parseInt(str);
            Integer pageStart = Integer.parseInt((String) map.get("page"));
            Integer pageSize = Integer.parseInt((String) map.get("limit"));
            short type = Short.parseShort((String) map.get("type"));
            short status = Short.parseShort((String) map.get("status"));

            BaseMapper mapper = mapperFactory.createMapper();

            List<File> files = mapper.listFiles(type, status, username, (pageStart - 1) * pageSize, pageSize);
            Long size = mapper.countFiles(type, status, username);
            map.put("data", files);
            map.put("code", 200);
            map.put("message", "文件列表加载完成");
            map.put("length", size);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", 401);
            map.put("message", "文件列表加载失败");
        }

    }

    /**
     * 查询文件
     *
     * @param map 数据集合
     */
    private void searchFiles(Map<String, Object> map) {
        try {
            String str = (String) map.get("username");
            int username = Integer.parseInt(str);
            Integer pageStart = Integer.parseInt((String) map.get("page"));
            Integer pageSize = Integer.parseInt((String) map.get("limit"));
            short type = Short.parseShort((String) map.get("type"));
            if (type == 1) {

            }
            String title = (String) map.get("title");
            BaseMapper mapper = mapperFactory.createMapper();
//            List<File> files = mapper.listFiles(kind, status, username, (pageStart - 1) * pageSize, pageSize);
//            Long size = mapper.countFilesBySearch(kind, status, username,title,timeCondition,time);
//            map.put("data", files);
            map.put("code", 200);
            map.put("message", "文件列表加载完成");
//            map.put("length", size);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", 401);
            map.put("message", "文件列表加载失败");
        }

    }
        /**
     * 根据文件编号去查询文件
     *
     * @param map 数据
     */
    private void findFile(Map<String, Object> map) {
        try {
            Integer fileId = (Integer) map.get("fileId");
            Integer pageStart = (Integer) map.get("pageStart");
            Integer pageSize = (Integer) map.get("pageSize");
            BaseMapper mapper = mapperFactory.createMapper();
//            File file = mapper.getFileById(fileId);
//            map.put("data",file);
        } catch (Exception e) {
            map.put("message", e.getMessage());
            map.put("code", 404);
        }
    }

    /**
     * 上传文件
     *
     * @param map 数据
     */
    private void uploadFile(Map<String, Object> map) {
        java.io.File file = (java.io.File) map.get("file");
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            StringBuffer str = new StringBuffer();
            String temp = "";
            while ((temp = reader.readLine()) != null) {
                str.append(temp).append("\n");
                //TODO 文件数据处理
            }
            System.out.println("file ---------------- " + str);
        } catch (Exception e) {
            map.put("message", e.getMessage());
            map.put("code", 401);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    map.put("message", e.getMessage());
                    map.put("code", 401);
                }
            }
        }
    }

    /**
     * 删除文件，文件彻底删除
     *
     * @param map 数据
     */
    private void deleteFile(Map<String, Object> map) {
        doSimpleModifyTemplate(map, new ServiceCallback<Object>() {
            @Override
            public int doDataModifyExecutor(BaseDao baseDao) {
                Integer fileId = Integer.parseInt((String) map.get("fileId"));
                File file = new File();
                file.setId(fileId);
                return baseDao.delete(file);
            }
        });
    }

    /**
     * 移除文件 文件进入回收站
     *
     * @param map 数据
     */
    private void remove(Map<String, Object> map) {
        doSimpleModifyTemplate(map, new ServiceCallback<Object>() {
            @Override
            public int doDataModifyExecutor(BaseDao baseDao) {
                int fileId = (int) map.get("fileId");
                File file = new File(fileId, null, null, null, null, null, null, 2, null, null);
                return baseDao.update(file);
            }
        });
    }

    /**
     * 还原文件，文件从回收站还原
     *
     * @param map 数据
     */
    private void restore(Map<String, Object> map) {
        doSimpleModifyTemplate(map, new ServiceCallback<Object>() {
            @Override
            public int doDataModifyExecutor(BaseDao baseDao) {
                int fileId = (int) map.get("fileId");
                File file = new File();
                file.setId(fileId);
                file.setStatus(1);
                return baseDao.update(file);
            }
        });
    }

    /**
     * 更新文件
     *
     * @param map 数据
     */
    private void updateFile(Map<String, Object> map) {
        doSimpleModifyTemplate(map, new ServiceCallback<Object>() {
            @Override
            public int doDataModifyExecutor(BaseDao baseDao) {
                int fileId = (int) map.get("fileId");
                double size = 1;
                Timestamp modified_time = (Timestamp) map.get("modified_time");
                String hash = null;
                double data_start = (double) map.get("data_start");
                double data_end = (double) map.get("data_end");
                double data_bottom = (double) map.get("data_bottom");
                double data_peak = (double) map.get("data_peak");
                double data_precision = (double) map.get("data_precision");
                double data_cycle = (double) map.get("data_cycle");
                double data_rate = (double) map.get("data_rate");
                double data_results = (double) map.get("data_results");
                File file = new File(fileId, size, hash, modified_time, data_start, data_end, data_bottom, data_peak, data_precision, data_cycle, data_rate, data_results);
                return baseDao.update(file);
            }
        });


    }

    /**
     * 比较文件hash值
     *
     * @param fileHash 文件hash值
     */
    private boolean contrast(String fileHash, int username) {
//        long l = mapperFactory.createMapper().contrastFile(fileHash, username);
        return 2 >= 1;
    }

    /**
     * 下载文件
     *
     * @param map 数据
     */
    private void loadingData(Map<String, Object> map) {

    }

    /**
     * 获取文件数据曲线的切点，并将切点信息以数组的形式封装成Map到返回队列中去
     *
     * @param map 内含加载了数据的实体类File
     * @author zero
     */
    private void getTangent(Map<String, Object> map) {

    }

    /**
     * 获取实验报告，并将生成的实验报告文件以流的形式返回到map队列中去
     *
     * @param map 内含file:File实验数据文件；url:String，echarts图像地址
     * @author zero
     */
    private void getReport(Map<String, Object> map) {

    }

    /**
     * 保存文件，并将添加成功与否的状态值放入到map队列中去
     *
     * @param map 内含参数：
     *            username：int，账号；
     *            type：int,文件类型：0CV,1PDV,2SWV,3LSV；
     *            fileName：String,文件名；
     *            xDataArr[]：double[],x轴数据组；
     *            yDataArr[]：double[]，y轴数据组
     * @author zero
     */
    private void saveFile(Map<String, Object> map) {

    }

}
