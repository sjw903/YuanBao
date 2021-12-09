package com.yuanbaogo.mine.live.model;

import java.util.List;

public class LiveAmountBean {

    private int size;
    private int page;
    private int total;
    private List<LiveAmountItem> rows;

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<LiveAmountItem> getRows() {
        return rows;
    }

    public void setRows(List<LiveAmountItem> rows) {
        this.rows = rows;
    }
}
