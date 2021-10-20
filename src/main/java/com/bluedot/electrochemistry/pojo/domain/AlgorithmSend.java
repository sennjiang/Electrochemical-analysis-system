package com.bluedot.electrochemistry.pojo.domain;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * @author zero
 * 算法审核实体类
 */
public class AlgorithmSend {
    private Integer algSendId;        //算法审核条目的唯一标识
    private Integer algId;             //被审核的算法的id
    private String applyTime;            //算法审核条目生成的时间
    private Integer username;           //申请对算法进行操作的申请者账户
    private Integer classification;     //申请类别、0：添加算法、-1：删除算法、>0：修改算法

    public Integer getAlgSendId() {
        return algSendId;
    }

    public void setAlgSendId(Integer algSendId) {
        this.algSendId = algSendId;
    }

    public Integer getAlgId() {
        return algId;
    }

    public void setAlgId(Integer algId) {
        this.algId = algId;
    }

    public Timestamp getApplyTime() {
        Timestamp ts = null;
        try {
            ts = Timestamp.valueOf(applyTime);
        } catch (Exception ignored) {
        }
        return ts;
    }

    public void setApplyTime(Timestamp applyTime) {
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.applyTime = sdf.format(applyTime);
    }

    public Integer getUsername() {
        return username;
    }

    public void setUsername(Integer username) {
        this.username = username;
    }

    public Integer getClassification() {
        return classification;
    }

    public void setClassification(Integer classification) {
        this.classification = classification;
    }

    @Override
    public String toString() {
        return "AlgorithmSend{" +
                "algSendId=" + algSendId +
                ", algId=" + algId +
                ", applyTime=" + applyTime +
                ", username=" + username +
                ", classification=" + classification +
                '}';
    }
}
