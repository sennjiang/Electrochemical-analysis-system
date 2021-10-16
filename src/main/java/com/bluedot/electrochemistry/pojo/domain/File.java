package com.bluedot.electrochemistry.pojo.domain;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * @author JDsen99
 * @description
 * @createDate 2021/9/3-16:34
 */
public class File {
    /**
     * 文件id
     */
    private Integer id;
    /**
     * 文件名
     */
    private String name;
    /**
     * 文件路径
     */
    private String url;
    /**
     * 文件owner
     */
    private Integer owner;
    /**
     * 文件大小
     */
    private Double size;
    /**
     * 文件hash值
     */
    private String hash;
    /**
     * 文件类型
     */
    private Integer type;
    /**
     * 文件状态
     */
    private Integer status;
    /**
     * 文件创建时间
     */
    private String produceTime;
    /**
     * 文件处理时间
     */
    private String modifiedTime;
    /**
     * 文件数据起始点
     */
    private Double dataStart;
    /**
     * 文件数据结束点
     */
    private Double dataEnd;
    /**
     * 文件数据最小值
     */
    private Double dataBottom;
    /**
     * 文件数据最大值
     */
    private Double dataPeak;
    /**
     * 文件数据精度
     */
    private Double dataPrecision;
    /**
     * 文件数据圈数
     */
    private Double dataCycle;
    /**
     * 文件数据速率
     */
    private Double dataRate;
    /**
     * 文件数据计算结果
     */
    private Double dataResults;

    public File() {
    }

    public File(Integer id, String name, String url, Integer owner, Double size, String hash, Integer type, Integer status, Timestamp produceTime, Timestamp modifiedTime) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.owner = owner;
        this.size = size;
        this.hash = hash;
        this.type = type;
        this.status = status;
//        this.produceTime = produceTime;
//        this.modifiedTime = modifiedTime;
    }

    public File(Integer fileId, Double size, String hash, Timestamp modified_time, Double data_start, Double data_end, Double data_bottom, Double data_peak, Double data_precision, Double data_cycle, Double data_rate, Double data_results) {
        this.id = fileId;
        this.size = size;
        this.hash = hash;
//        this.modifiedTime = modified_time;
        this.dataStart = data_start;
        this.dataEnd = data_end;
        this.dataBottom = data_bottom;
        this.dataPeak = data_peak;
        this.dataPrecision = data_precision;
        this.dataCycle = data_cycle;
        this.dataRate = data_rate;
        this.dataResults = data_results;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getOwner() {
        return owner;
    }

    public void setOwner(Integer owner) {
        this.owner = owner;
    }

    public Double getSize() {
        return size;
    }

    public void setSize(Double size) {
        this.size = size;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Timestamp getProduceTime() {
        Timestamp ts = null;
        try {
            ts = Timestamp.valueOf(produceTime);
        } catch (Exception ignored) {
        }
        return ts;
    }

    public void setProduceTime(Timestamp produceTime) {
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.produceTime = sdf.format(produceTime);
    }

    public Timestamp getModifiedTime() {
        Timestamp ts = null;
        try {
            ts = Timestamp.valueOf(modifiedTime);
        } catch (Exception ignored) {
        }
        return ts;
    }

    public void setModifiedTime(Timestamp modifiedTime) {
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.modifiedTime = sdf.format(modifiedTime);
    }

    public Double getDataStart() {
        return dataStart;
    }

    public void setDataStart(Double dataStart) {
        this.dataStart = dataStart;
    }

    public Double getDataEnd() {
        return dataEnd;
    }

    public void setDataEnd(Double dataEnd) {
        this.dataEnd = dataEnd;
    }

    public Double getDataBottom() {
        return dataBottom;
    }

    public void setDataBottom(Double dataBottom) {
        this.dataBottom = dataBottom;
    }

    public Double getDataPeak() {
        return dataPeak;
    }

    public void setDataPeak(Double dataPeak) {
        this.dataPeak = dataPeak;
    }

    public Double getDataPrecision() {
        return dataPrecision;
    }

    public void setDataPrecision(Double dataPrecision) {
        this.dataPrecision = dataPrecision;
    }

    public Double getDataCycle() {
        return dataCycle;
    }

    public void setDataCycle(Double dataCycle) {
        this.dataCycle = dataCycle;
    }

    public Double getDataRate() {
        return dataRate;
    }

    public void setDataRate(Double dataRate) {
        this.dataRate = dataRate;
    }

    public Double getDataResults() {
        return dataResults;
    }

    public void setDataResults(Double dataResult) {
        this.dataResults = dataResult;
    }
}
