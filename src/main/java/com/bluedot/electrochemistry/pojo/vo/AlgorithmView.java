package com.bluedot.electrochemistry.pojo.vo;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * @author zero
 * @description 算法视图实体类
 * @createDate 2021年9月20日10:34:37
 */
public class AlgorithmView {
    //算法的创建者的昵称
    private String nickname;

    //算法名称
    private String algorithmName;

    //算法在服务器的url地址
    private String url;

    //算法的创建时间
    private String createdTime;

    //算法的类型所属
    private Integer classification;

    //算法的修改时间
    private String changeTime;

    //算法是否启用, 1启用，2未启用
    private Integer isUsed;

    //算法的id，算法的唯一标识
    private Integer algId;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAlgorithmName() {
        return algorithmName;
    }

    public void setAlgorithmName(String algorithmName) {
        this.algorithmName = algorithmName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Timestamp getCreatedTime() {
        Timestamp ts = null;
        try {
            ts = Timestamp.valueOf(createdTime);
        } catch (Exception ignored) {
        }
        return ts;
    }

    public void setCreatedTime(Timestamp createdTime) {
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.createdTime = sdf.format(createdTime);
    }

    public Integer getClassification() {
        return classification;
    }

    public void setClassification(Integer classification) {
        this.classification = classification;
    }

    public Timestamp getChangeTime() {
        Timestamp ts = null;
        try {
            ts = Timestamp.valueOf(changeTime);
        } catch (Exception ignored) {
        }
        return ts;
    }

    public void setChangeTime(Timestamp changeTime) {
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.changeTime = sdf.format(changeTime);
    }

    public Integer getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(Integer isUsed) {
        this.isUsed = isUsed;
    }

    public Integer getAlgId() {
        return algId;
    }

    public void setAlgId(Integer algId) {
        this.algId = algId;
    }

    @Override
    public String toString() {
        return "AlgorithmView{" +
                "nickname='" + nickname + '\'' +
                ", algorithmName='" + algorithmName + '\'' +
                ", url='" + url + '\'' +
                ", createdTime=" + createdTime +
                ", classification=" + classification +
                ", changeTime=" + changeTime +
                ", isUsed=" + isUsed +
                ", algId=" + algId +
                '}';
    }
}
