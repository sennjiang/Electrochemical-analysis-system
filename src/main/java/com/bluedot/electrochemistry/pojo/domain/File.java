package com.bluedot.electrochemistry.pojo.domain;

import java.sql.Timestamp;

/**
 * @author JDsen99
 * @description
 * @createDate 2021/9/3-16:34
 */
public class File {
    private int id;
    private String name;
    private String url;
    private int owner;
    private double size;
    private String hash;
    private short type;
    private short status;
    private Timestamp produceTime;
    private Timestamp modifiedTime;
    private double dataStart;
    private double dataEnd;
    private double dataBottom;
    private double dataPeak;
    private double dataPrecision;
    private double dataCycle;
    private double dataRate;
    private double dataResult;

    public File() {
    }

    public File(int id, String name, String url, int owner, double size, String hash, short type, short status, Timestamp produceTime, Timestamp modifiedTime, double dataStart, double dataEnd, double dataBottom, double dataPeak, double dataPrecision, double dataCycle, double dataRate, double dataResult) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.owner = owner;
        this.size = size;
        this.hash = hash;
        this.type = type;
        this.status = status;
        this.produceTime = produceTime;
        this.modifiedTime = modifiedTime;
        this.dataStart = dataStart;
        this.dataEnd = dataEnd;
        this.dataBottom = dataBottom;
        this.dataPeak = dataPeak;
        this.dataPrecision = dataPrecision;
        this.dataCycle = dataCycle;
        this.dataRate = dataRate;
        this.dataResult = dataResult;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public short getType() {
        return type;
    }

    public void setType(short type) {
        this.type = type;
    }

    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
    }

    public Timestamp getProduceTime() {
        return produceTime;
    }

    public void setProduceTime(Timestamp produceTime) {
        this.produceTime = produceTime;
    }

    public Timestamp getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Timestamp modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public double getDataStart() {
        return dataStart;
    }

    public void setDataStart(double dataStart) {
        this.dataStart = dataStart;
    }

    public double getDataEnd() {
        return dataEnd;
    }

    public void setDataEnd(double dataEnd) {
        this.dataEnd = dataEnd;
    }

    public double getDataBottom() {
        return dataBottom;
    }

    public void setDataBottom(double dataBottom) {
        this.dataBottom = dataBottom;
    }

    public double getDataPeak() {
        return dataPeak;
    }

    public void setDataPeak(double dataPeak) {
        this.dataPeak = dataPeak;
    }

    public double getDataPrecision() {
        return dataPrecision;
    }

    public void setDataPrecision(double dataPrecision) {
        this.dataPrecision = dataPrecision;
    }

    public double getDataCycle() {
        return dataCycle;
    }

    public void setDataCycle(double dataCycle) {
        this.dataCycle = dataCycle;
    }

    public double getDataRate() {
        return dataRate;
    }

    public void setDataRate(double dataRate) {
        this.dataRate = dataRate;
    }

    public double getDataResult() {
        return dataResult;
    }

    public void setDataResult(double dataResult) {
        this.dataResult = dataResult;
    }
}
