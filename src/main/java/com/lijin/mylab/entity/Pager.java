package com.lijin.mylab.entity;

import java.util.Collections;
import java.util.List;

/**
 * 分页类
 */
public class Pager<T> {

    private int pageNum = 1;        // 当前页
    private int pageSize = 10;      // 每页记录条数
    private int total = 0;          // 总记录数
    private List<T> resultLst;      // 分页记录列表

    public Pager(int pageNum, int pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    public Pager(int pageNum, int pageSize, int total) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.total = total;
    }

    public Pager(int pageNum, int pageSize, int total, List<T> resultLst) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.total = total;
        this.resultLst = resultLst;
    }

    /**
     * 获取数据库起始记录索引
     * @return
     */
    public int getStartNum() {
        return (pageNum - 1) * pageSize;
    }

    public int getStartIdx() {
        return (pageNum - 1) * pageSize + 1;
    }

    public int getEndIdx() {
        return ((pageNum * pageSize) > total) ? total : (pageNum * pageSize);
    }

    public void setResultLst(List<T> resultLst) {
        this.resultLst = resultLst;
    }

    public int getPageNum() {
        return pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getTotal() {
        return total;
    }

    public List<T> getResultLst() {
        return resultLst == null ? Collections.emptyList() : resultLst;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
