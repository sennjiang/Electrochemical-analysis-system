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
    private Short classification;       //算法的类别 1：平滑处理、2：滤波处理、3：CV伏安法、4：DPV、5：SWV、6：LSV
    private Timestamp changeTime;           //最新修改时间
    private String url;                 //算法文件路径
    private Short isUsed;               //是否启用 1 ：启用 2 ：未启用
}
