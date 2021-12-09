
package com.yuanbaogo.mine.setting.model;


import java.util.List;

@SuppressWarnings("unused")
public class AddressListBean {

    private Long mPage;
    private List<AddressBean> mAddressBeans;
    private Long mSize;
    private Long mTotal;

    public Long getPage() {
        return mPage;
    }

    public void setPage(Long page) {
        mPage = page;
    }

    public List<AddressBean> getRows() {
        return mAddressBeans;
    }

    public void setRows(List<AddressBean> addressBeans) {
        mAddressBeans = addressBeans;
    }

    public Long getSize() {
        return mSize;
    }

    public void setSize(Long size) {
        mSize = size;
    }

    public Long getTotal() {
        return mTotal;
    }

    public void setTotal(Long total) {
        mTotal = total;
    }

    @Override
    public String toString() {
        return "AddressListBean{" +
                "mPage=" + mPage +
                ", mAddressBeans=" + mAddressBeans +
                ", mSize=" + mSize +
                ", mTotal=" + mTotal +
                '}';
    }
}
