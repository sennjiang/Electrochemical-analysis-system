package com.bluedot.electrochemistry.pojo.domain;

import java.sql.Timestamp;

/**
 * @author zero
 * 算法实体类
 */
public class Algorithm {
    private Integer algId;              //算法实体的唯一标识
    private String algorithmName;      //算法的名称
    private Integer username;           //算法上传者的账户
    private Timestamp createdTime;          //算法生成的时间
    private Integer classification;       //算法的类别 1：平滑处理、2：滤波处理、3：CV伏安法、4：DPV、5：SWV、6：LSV
    private Timestamp changeTime;           //最新修改时间
    private String url;                 //算法文件路径
    private Integer isUsed;               //是否启用 1 ：启用 2 ：未启用

    public Integer getAlgId() {
        return algId;
    }

    public void setAlgId(Integer algId) {
        this.algId = algId;
    }

    public String getAlgorithmName() {
        return algorithmName;
    }

    public void setAlgorithmName(String algorithmName) {
        this.algorithmName = algorithmName;
    }

    public Integer getUsername() {
        return username;
    }

    public void setUsername(Integer username) {
        this.username = username;
    }

    public Timestamp getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Timestamp createdTime) {
        this.createdTime = createdTime;
    }

    public Integer getClassification() {
        return classification;
    }

    public void setClassification(Integer classification) {
        this.classification = classification;
    }

    public Timestamp getChangeTime() {
        return changeTime;
    }

    public void setChangeTime(Timestamp changeTime) {
        this.changeTime = changeTime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(Integer isUsed) {
        this.isUsed = isUsed;
    }

    @Override
    public String toString() {
        return "Algorithm{" +
                "algId=" + algId +
                ", algorithmName='" + algorithmName + '\'' +
                ", username=" + username +
                ", createdTime=" + createdTime +
                ", classification=" + classification +
                ", changeTime=" + changeTime +
                ", url='" + url + '\'' +
                ", isUsed=" + isUsed +
                '}';
    }
}
