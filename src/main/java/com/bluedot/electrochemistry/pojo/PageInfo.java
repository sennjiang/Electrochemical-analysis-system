package com.bluedot.electrochemistry.pojo;

import java.util.List;


/**
 * 分页
 * @param <T> 数据类型
 */
public class PageInfo<T> {
    /**
     * 每页显示数据个数
     */
    private int pageSize;
    /**
     * 页码
     */
    private int pageNo;
    /**
     * 数据总数
     */
    private long totalSize;
    /**
     * 总页数
     */
    private long totalPage;
    /**
     * 查询到的数据
     */
    private List<T> list;

    public PageInfo(int pageSize, int pageNo, long totalSize, long totalPage, List<T> list) {
        this.pageSize = pageSize;
        this.pageNo = pageNo;
        this.totalSize = totalSize;
        this.totalPage = totalPage;
        this.list = list;
    }

    public PageInfo(int pageSize, int pageNo, long totalSize, List<T> list) {
        this.pageSize = pageSize;
        this.pageNo = pageNo;
        this.totalSize = totalSize;
        this.totalPage=totalSize%pageSize==0? totalSize/pageSize:totalSize/pageSize+1;
        this.list = list;
    }

    public PageInfo() {
        super();
    }
    public int getPageSize() {
        return pageSize;
    }
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    public int getPageNumber() {
        return pageNo;
    }
    public void setPageNumber(int pageNo) {
        this.pageNo = pageNo;
    }
    public long getTotalSize() {
        return totalSize;
    }
    public void setTotalSize(long totalSize) {
        this.totalSize = totalSize;
    }
    public long getTotalPage() {
        return totalPage;
    }
    public void setTotalPage(long totalPage) {
        this.totalPage = totalPage;
    }
    public void setTotalPage(long totalSize,int pageSize) {

        this.totalPage = totalSize%pageSize==0? totalSize/pageSize:totalSize/pageSize+1;
    }
    public List<T> getList() {
        return list;
    }
    public void setList(List<T> list) {
        this.list = list;
    }
    @Override
    public String toString() {
        return "PageInfo [pageSize=" + pageSize + ", pageNo=" + pageNo +
                ", totalSize=" + totalSize + ", totalPage=" + totalPage + ", list=" +
                list + "]";
    }

}
