package com.bluedot.electrochemistry.service;

import com.bluedot.electrochemistry.dao.base.BaseDao;
import com.bluedot.electrochemistry.dao.base.BaseMapper;
import com.bluedot.electrochemistry.factory.MapperFactory;
import com.bluedot.electrochemistry.pojo.domain.File;
import com.bluedot.electrochemistry.service.base.BaseService;
import com.bluedot.electrochemistry.service.callback.ServiceCallback;
import com.bluedot.framework.simplemybatis.session.SqlSessionFactoryBuilder;
import com.bluedot.framework.simplespring.core.annotation.Service;
import com.bluedot.framework.simplespring.inject.annotation.Autowired;
import com.bluedot.framework.simplespring.mvc.monitor.Data;
import javafx.scene.control.TableRow;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
    private void export(Map<String,Object> map) {
        System.out.println("你好啊");
    }

    /**
     * 根据文件编号去查询文件
     *
     * @param map 数据
     */
    private void findFile(Map<String,Object> map) {
    }

    /**
     * 上传文件
     *
     * @param map 数据
     */
    private void uploadFile(Map<String,Object> map) throws Exception {
        HttpServletRequest request = ((Data) map).getRequest();
        String realPath = request.getSession().getServletContext().getRealPath("/uploads");
        java.io.File file = new java.io.File(realPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        List<FileItem> items = upload.parseRequest(request);
        for (FileItem item : items) {
            if (item.isFormField()) {
                //普通表单
            } else {
                String uuid = UUID.randomUUID().toString().replace("-", "");
                String filename = uuid + "_" + item.getName();
                item.write(new java.io.File(realPath, filename));
                item.delete();
            }
        }
    }

    /**
     * 删除文件，文件彻底删除
     *
     * @param map 数据
     */
    private void deleteFile(Map<String,Object> map) {
        doSimpleModifyTemplate(map, new ServiceCallback<Object>() {
            @Override
            public int doDataModifyExecutor(BaseDao baseDao) {
                int fileId = (int) map.get("fileId");
                File file = new File(fileId, null, null, null, null, null, null);
                return baseDao.delete(file);
            }
        });
    }

    /**
     * 移除文件 文件进入回收站
     *
     * @param map 数据
     */
    private void remove(Map<String,Object> map) {
        doSimpleModifyTemplate(map, new ServiceCallback<Object>() {
            @Override
            public int doDataModifyExecutor(BaseDao baseDao) {
                int fileId = (int) map.get("fileId");
                File file = new File(fileId, null, null, null, null, null, (short) 2);
                return baseDao.update(file);
            }
        });
    }

    /**
     * 还原文件，文件从回收站还原
     *
     * @param map 数据
     */
    private void restore(Map<String,Object> map) {
        doSimpleModifyTemplate(map, new ServiceCallback<Object>() {
            @Override
            public int doDataModifyExecutor(BaseDao baseDao) {
                int fileId = (int) map.get("fileId");
                File file = new File(fileId, null, null, null, null, null, (short) 1);
                return baseDao.update(file);
            }
        });
    }

    /**
     * 更新文件
     *
     * @param map 数据
     */
    private void updateFile(Map<String,Object> map) {
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
    private boolean contrast(String fileHash,int username) {
        long l = mapperFactory.createMapper().contrastFile(fileHash, username);
        return l >= 1;
    }

    /**
     * 下载文件
     *
     * @param map 数据
     */
    private void loadingData(Map<String,Object> map) {

    }

    /**
     * 获取文件数据曲线的切点，并将切点信息以数组的形式封装成Map到返回队列中去
     *
     * @param map 内含加载了数据的实体类File
     * @author zero
     */
    private void getTangent(Map<String,Object> map) {

    }

    /**
     * 获取实验报告，并将生成的实验报告文件以流的形式返回到map队列中去
     *
     * @param map 内含file:File实验数据文件；url:String，echarts图像地址
     * @author zero
     */
    private void getReport(Map<String,Object> map) {

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
    private void saveFile(Map<String,Object> map) {

    }

}
