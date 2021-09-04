package com.bluedot.electrochemistry.pojo.domain;

import java.util.Date;

/**
 * @author zero
 * 算法实体类
 */
public class Algorithm {
    private Integer algID;              //算法实体的唯一标识
    private String algorithm_name;      //算法的名称
    private Integer username;           //算法上传者的账户
    private Date created_time;          //算法生成的时间
    private Short classification;       //算法的类别 1：平滑处理、2：滤波处理、3：CV伏安法、4：DPV、5：SWV、6：LSV
    private Date change_time;           //最新修改时间
    private String url;                 //算法文件路径
    private Short isUsed;               //是否启用 1 ：启用 2 ：未启用

    public Integer getAlgID() { return algID; }
    public void setAlgID(Integer algID) { this.algID = algID; }

    public String getAlgorithm_name() { return algorithm_name; }
    public void setAlgorithm_name(String algorithm_name) { this.algorithm_name = algorithm_name; }

    public Integer getUsername() { return username; }
    public void setUsername(Integer username) { this.username = username; }

    public Date getCreated_time() { return created_time; }
    public void setCreated_time(Date created_time) { this.created_time = created_time; }

    public Short getClassification() { return classification; }
    public void setClassification(Short classification) { this.classification = classification; }

    public Date getChange_time() { return change_time; }
    public void setChange_time(Date change_time) { this.change_time = change_time; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public Short getIsUsed() { return isUsed; }
    public void setIsUsed(Short isUsed) { this.isUsed = isUsed; }

    @Override
    public String toString() {
        return "Algorithm{" +
                "algID=" + algID +
                ", algorithm_name='" + algorithm_name + '\'' +
                ", username=" + username +
                ", created_time=" + created_time +
                ", classification=" + classification +
                ", change_time=" + change_time +
                ", url='" + url + '\'' +
                ", isUsed=" + isUsed +
                '}';
    }
}
