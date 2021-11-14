package com.bluedot.electrochemistry.service;

import com.bluedot.electrochemistry.dao.base.BaseDao;
import com.bluedot.electrochemistry.dao.base.BaseMapper;
import com.bluedot.electrochemistry.factory.MapperFactory;
import com.bluedot.electrochemistry.pojo.domain.Algorithm;
import com.bluedot.electrochemistry.pojo.domain.AlgorithmSend;
import com.bluedot.electrochemistry.pojo.vo.AlgorithmSendView;
import com.bluedot.electrochemistry.service.base.BaseService;
import com.bluedot.electrochemistry.service.callback.ServiceCallback;
import com.bluedot.framework.simplespring.core.annotation.Service;
import com.bluedot.framework.simplespring.inject.annotation.Autowired;

import java.io.File;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * 算法审核业务
 * @author zero
 */
@Service
public class AlgorithmSendService extends BaseService {

    @Autowired
    private MapperFactory mapperFactory;

    /**
     * 审核算法申请
     * @author zero
     * @param map map中包含的参数：
     *      algorithmSendId算法申请的id
     *      algorithmId被审核的原(旧)算法的id
     *      type：申请的类别，0添加，-1删除，>0修改,
     *            如果type>0，也就是修改操作，当为修改操作时，type代表修改后的算法id
     *      isPass：审核通过与否，0不通过，1通过
     * 审核成功与否的结果会放入到map中去，0失败，1成功
     */
    private void judgementAlgorithmSend(Map map){
        StringBuilder message = new StringBuilder();
        int rulingResult = Integer.parseInt((String) map.get("rulingResult"));
        int algorithmSendId = Integer.parseInt((String) map.get("algorithmSendId"));
        AlgorithmSend algorithmSend = new AlgorithmSend();
        int algorithmId = Integer.parseInt((String) map.get("algorithmId"));
        int sendType = Integer.parseInt((String) map.get("sendType"));
        if(rulingResult == 1) {  //审核通过
            algorithmSend.setAlgId(algorithmId);

            Algorithm algorithm = new Algorithm();
            algorithm.setAlgId(algorithmId);

            if(sendType == 0){  //添加算法操作
                algorithm.setIsUsed(1); //设置启用
                doSimpleModifyTemplate(map, new ServiceCallback<Object>() {
                    @Override
                    public int doDataModifyExecutor(BaseDao baseDao) {
                        int update = baseDao.update(algorithm);
                        if (update!=1){
                            map.put("code",500);
                            message.append("算法添加审核失败 ");
                        }else{
                            map.put("code",200);
                            message.append("算法添加审核成功 ");
                        }
                        return update;
                    }
                });
                //删除算法审核条目
                algorithmSend.setAlgSendId(algorithmSendId);
                deleteAlgorithmSend(map, message, algorithmSend);
            }else if (sendType < 0){    //删除算法操作
                deleteAlgorithm(map, message, algorithmId);
            }else{              // 修改算法操作
                //删除旧算法
                deleteAlgorithm(map, message, sendType);
                //让新算法上架
                Algorithm algorithm_new = new Algorithm();
                algorithm_new.setAlgId(algorithmId);
                algorithm_new.setIsUsed(2); //设置启用
                algorithm_new.setChangeTime(new Timestamp(System.currentTimeMillis()));
                doSimpleModifyTemplate(map, new ServiceCallback<Object>() {
                    @Override
                    public int doDataModifyExecutor(BaseDao baseDao) {
                        int update = baseDao.update(algorithm_new);
                        if(update!=1){
                            map.put("code", 500);
                            message.append("算法修改审核失败 ");
                        }else {
                            map.put("code", 200);
                            message.append("算法修改审核成功 ");
                        }
                        return update;
                    }
                });
                //删除算法审核条目
                algorithmSend.setAlgSendId(algorithmSendId);
                deleteAlgorithmSend(map, message, algorithmSend);
            }
        } else if(rulingResult == 0){   //审核不通过，并且（只要不是删除申请都要删除掉因为申请提案而生成的文件）
            if (sendType>=0)
                deleteAlgorithm(map, message, algorithmId);
            else{
                algorithmSend.setAlgSendId(algorithmSendId);
                deleteAlgorithmSend(map, message, algorithmSend);
            }
        }
    }

    /**
     * 删除算法审核条目
     * @param map 存放必要的信息数据
     * @param message 处理成功与否的信息记录
     * @param algorithmSend 对应的算法实体，用于删除使用
     */
    private void deleteAlgorithmSend(Map map, StringBuilder message, AlgorithmSend algorithmSend) {
        doSimpleModifyTemplate(map, new ServiceCallback<Object>() {
            @Override
            public int doDataModifyExecutor(BaseDao baseDao) {
                int del = baseDao.delete(algorithmSend);
                if (del != 1){
                    map.put("code", 500);
                    message.append("算法审核条目删除失败");
                    map.put("message", message.toString());
                }else{
                    map.put("code", 200);
                    message.append("算法审核条目删除成功");
                    map.put("message", message.toString());
                }
                return del;
            }
        });
    }

    /**
     * 删除算法文件，并进行数据库的相关记录的删除操作
     * @param map 包含相关参数
     * @param message 信息记录，用于相关操作后的信息反馈
     * @param algorithmId 被删除的算法的id
     */
    private void deleteAlgorithm(Map map, StringBuilder message, int algorithmId) {
        doSimpleModifyTemplate(map, new ServiceCallback<Object>() {
            @Override
            public int doDataModifyExecutor(BaseDao baseDao) {
                Algorithm algorithm = mapperFactory.createMapper().getAlgorithmById(algorithmId);
                boolean del = new File(algorithm.getUrl()).delete();
                int delete = -1;
                if (del) {
                    delete = baseDao.delete(algorithm);
                    if (delete != 1) {
                        map.put("code", 500);
                        message.append("算法删除审核失败 ");
                        map.put("message", message.toString());
                    } else {
                        map.put("code", 200);
                        message.append("算法删除审核成功 ");
                        map.put("message", message.toString());
                    }
                } else {
                    map.put("code", 500);
                    message.append("算法文件删除失败 ");
                    map.put("message", message.toString());
                }
                return delete;
            }
        });
    }

    /**
     * 添加算法申请记录
     * @author zero
     * @param map map中包含的参数：
     *            algorithmId被审核的原(旧)算法的id
     *            type：申请的类别，0添加，-1删除，>0修改
     *            username：算法申请的发起者的账号
     *       注：算法申请的发起时间以添加到数据库的时间为准，由添加数据库表时默认生成，所以不在传递的参数计量之内
     */
    private void addAlgorithmSend(Map<String, Object> map){
        Integer algorithmId = Integer.parseInt((String) map.get("algorithmId"));
        Integer classification = Integer.parseInt((String) map.get("type"));
        Integer username = Integer.parseInt((String) map.get("username"));
        AlgorithmSend algSend = new AlgorithmSend();
        algSend.setClassification(classification);
        algSend.setAlgId(algorithmId);
        algSend.setUsername(username);
        doSimpleModifyTemplate(map, new ServiceCallback<AlgorithmSend>() {
            @Override
            public int doDataModifyExecutor(BaseDao baseDao) {
                int insert = baseDao.insert(algSend);
                if (insert!=1){
                    map.put("code", 500);
                    map.put("message", "算法申请添加失败");
                }else{
                    map.put("code",200);
                    map.put("message", "算法申请添加成功");
                }
                return insert;
            }
        });
    }

    /**
     * 查询所有算法申请
     * @author zero
     * @param map map 用来存放查好的算法申请集合
     */
    private void listAlgorithmSends(Map<String, Object> map){
        try {
            BaseMapper mapper = mapperFactory.createMapper();
            Integer pageStart = Integer.parseInt((String) map.get("page"));
            Integer pageSize = Integer.parseInt((String) map.get("limit"));
            List<AlgorithmSendView> algorithmSends = mapper.getAlgorithmSends((pageStart - 1) * pageSize,pageSize);
            Long size = mapper.getAlgorithmSendsCount();
            map.put("data", algorithmSends);
            map.put("code", "200");
            map.put("message", "查询算法申请列表成功");
            map.put("length", size);
        }catch (Exception e){
            map.put("code", "500");
            map.put("message", "查询算法申请列表失败");
        }
    }

    /**
     * 根据条件查询所有符合要求的算法申请集合
     * @param map 包含查询条件 queryCondition
     */
    private void searchAlgorithmSends(Map<String, Object> map) {
        try {
            BaseMapper mapper = mapperFactory.createMapper();
            Integer pageStart = Integer.parseInt((String) map.get("page"));
            Integer pageSize = Integer.parseInt((String) map.get("limit"));
            String queryCondition = (String) map.get("title");
            Long size = null;
            List<AlgorithmSendView> algSds = mapper.getAlgorithmSendsByQueryCondition("%"+queryCondition+"%", "%"+queryCondition+"%", (pageStart - 1) * pageSize, pageSize);
            size = mapper.getAlgorithmSendsCountByQueryCondition("%"+queryCondition+"%", "%"+queryCondition+"%");

            map.put("data", algSds);
            map.put("length", size);
            map.put("code", 200);
            map.put("message", "算法申请列表搜索完成");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", 500);
            map.put("message", "算法申请列表搜索失败："+e.getMessage());
        }
    }
}
