package com.bluedot.electrochemistry.pojo.domain;

import java.sql.Timestamp;

/**
 * @author zero
 * 算法审核实体类
 */
public class AlgorithmSend {
    private Integer alg_send_id;        //算法审核条目的唯一标识
    private Integer alg_id;             //被审核的算法的id
    private Timestamp apply_time;            //算法审核条目生成的时间
    private Integer username;           //申请对算法进行操作的申请者账户
    private Integer classification;     //申请类别、0：添加算法、-1：删除算法、>0：修改算法

    public Integer getAlg_send_id() { return alg_send_id; }
    public void setAlg_send_id(Integer alg_send_id) { this.alg_send_id = alg_send_id; }

    public Integer getAlg_id() { return alg_id; }
    public void setAlg_id(Integer alg_id) { this.alg_id = alg_id; }

    public Timestamp getApply_time() { return apply_time; }
    public void setApply_time(Timestamp apply_time) { this.apply_time = apply_time; }

    public Integer getUsername() { return username; }
    public void setUsername(Integer username) { this.username = username; }

    public Integer getClassification() { return classification; }
    public void setClassification(Integer classification) { this.classification = classification; }

    @Override
    public String toString() {
        return "AlgorithmSend{" +
                "alg_send_id=" + alg_send_id +
                ", alg_id=" + alg_id +
                ", apply_time=" + apply_time +
                ", username=" + username +
                ", classification=" + classification +
                '}';
    }
}
