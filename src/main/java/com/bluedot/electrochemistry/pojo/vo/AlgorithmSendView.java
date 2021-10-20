package com.bluedot.electrochemistry.pojo.vo;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * @author zero
 * @description 算法申请视图实体类
 * @createDate 2021年9月20日10:03:47
 */
public class AlgorithmSendView {
    //算法申请者的昵称
    private String senderNickname;

    //申请类别、0：添加算法、-1：删除算法、>0：修改算法
    // (如果是修改算法类型，则该成员变量也将成为新算法实体的id)
    private Integer sendType;

    //算法审核条目生成的时间
    private String applyTime;

    //算法审核条目的唯一标识
    private Integer algSendId;

    //算法创建者的昵称
    private String creatorNickname;

    //算法名称
    private String algorithmName;

    //算法创建者的用户名
    private Integer algCreateUsername;

    /*//原算法的地址
    private String url;*/

    //原算法的id标识
    private Integer algId;

    public String getSenderNickname() {
        return senderNickname;
    }

    public void setSenderNickname(String senderNickname) {
        this.senderNickname = senderNickname;
    }

    public Integer getSendType() {
        return sendType;
    }

    public void setSendType(Integer sendType) {
        this.sendType = sendType;
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

    public Integer getAlgSendId() {
        return algSendId;
    }

    public void setAlgSendId(Integer algSendId) {
        this.algSendId = algSendId;
    }

    public String getCreatorNickname() {
        return creatorNickname;
    }

    public void setCreatorNickname(String creatorNickname) {
        this.creatorNickname = creatorNickname;
    }

    public String getAlgorithmName() {
        return algorithmName;
    }

    public void setAlgorithmName(String algorithmName) {
        this.algorithmName = algorithmName;
    }

    public Integer getAlgCreateUsername() {
        return algCreateUsername;
    }

    public void setAlgCreateUsername(Integer algCreateUsername) {
        this.algCreateUsername = algCreateUsername;
    }

    public Integer getAlgId() {
        return algId;
    }

    public void setAlgId(Integer algId) {
        this.algId = algId;
    }

    @Override
    public String toString() {
        return "AlgorithmSendView{" +
                "senderNickname='" + senderNickname + '\'' +
                ", sendType=" + sendType +
                ", applyTime=" + applyTime +
                ", algSendId=" + algSendId +
                ", creatorNickname='" + creatorNickname + '\'' +
                ", algorithmName='" + algorithmName + '\'' +
                ", algCreateUsername=" + algCreateUsername +
                ", algId=" + algId +
                '}';
    }
}
