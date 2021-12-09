package com.yuanbaogo.mine.mine.model;

public class OrderListItem{
	private String id;
	private String orderId;
	private String scanDate;
	private String expressNo;
	private String type;
	private String des;
	private int createBy;
	private String createTime;
	private String updateTime;
	private String imgUrl;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getScanDate() {
		return scanDate;
	}

	public void setScanDate(String scanDate) {
		this.scanDate = scanDate;
	}

	public String getExpressNo() {
		return expressNo;
	}

	public void setExpressNo(String expressNo) {
		this.expressNo = expressNo;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}

	public int getCreateBy() {
		return createBy;
	}

	public void setCreateBy(int createBy) {
		this.createBy = createBy;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	@Override
	public String toString() {
		return "OrderListItem{" +
				"id='" + id + '\'' +
				", orderId='" + orderId + '\'' +
				", scanDate='" + scanDate + '\'' +
				", expressNo='" + expressNo + '\'' +
				", type='" + type + '\'' +
				", des='" + des + '\'' +
				", createBy=" + createBy +
				", createTime='" + createTime + '\'' +
				", updateTime='" + updateTime + '\'' +
				", imgUrl='" + imgUrl + '\'' +
				'}';
	}
}
