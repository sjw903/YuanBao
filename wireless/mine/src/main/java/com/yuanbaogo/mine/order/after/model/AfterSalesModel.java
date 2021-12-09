package com.yuanbaogo.mine.order.after.model;

import com.yuanbaogo.mine.order.model.GoodsVOListItem;

import java.util.Arrays;
import java.util.List;

public class AfterSalesModel {
	private String orderId;
	private String afterSalesId;
	private int salesType;
	private Long afterSalePrice;
	private int goodsCount;
	private int salesStatus;
	private String reason;
	private Long createTime;
	private String expressCompany;
	private String[] urlList;
	private boolean closeStatus;
	private Object closeReason;
	private String expressNumber;
	private String consignee;
	private String consigneeMobile;
	private String consigneeAddress;
	private int goodsType;
	private List<GoodsVOListItem> goodsVoList;
	private boolean expressNumberFlag;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getAfterSalesId() {
		return afterSalesId;
	}

	public void setAfterSalesId(String afterSalesId) {
		this.afterSalesId = afterSalesId;
	}

	public int getSalesType() {
		return salesType;
	}

	public void setSalesType(int salesType) {
		this.salesType = salesType;
	}

	public Long getAfterSalePrice() {
		return afterSalePrice;
	}

	public void setAfterSalePrice(Long afterSalePrice) {
		this.afterSalePrice = afterSalePrice;
	}

	public int getGoodsCount() {
		return goodsCount;
	}

	public void setGoodsCount(int goodsCount) {
		this.goodsCount = goodsCount;
	}

	public int getSalesStatus() {
		return salesStatus;
	}

	public void setSalesStatus(int salesStatus) {
		this.salesStatus = salesStatus;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public String getExpressCompany() {
		return expressCompany;
	}

	public void setExpressCompany(String expressCompany) {
		this.expressCompany = expressCompany;
	}

	public String[] getUrlList() {
		return urlList;
	}

	public void setUrlList(String[] urlList) {
		this.urlList = urlList;
	}

	public boolean isCloseStatus() {
		return closeStatus;
	}

	public void setCloseStatus(boolean closeStatus) {
		this.closeStatus = closeStatus;
	}

	public Object getCloseReason() {
		return closeReason;
	}

	public void setCloseReason(Object closeReason) {
		this.closeReason = closeReason;
	}

	public String getExpressNumber() {
		return expressNumber;
	}

	public void setExpressNumber(String expressNumber) {
		this.expressNumber = expressNumber;
	}

	public String getConsignee() {
		return consignee;
	}

	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}

	public String getConsigneeMobile() {
		return consigneeMobile;
	}

	public void setConsigneeMobile(String consigneeMobile) {
		this.consigneeMobile = consigneeMobile;
	}

	public String getConsigneeAddress() {
		return consigneeAddress;
	}

	public void setConsigneeAddress(String consigneeAddress) {
		this.consigneeAddress = consigneeAddress;
	}

	public int getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(int goodsType) {
		this.goodsType = goodsType;
	}

	public List<GoodsVOListItem> getGoodsVoList() {
		return goodsVoList;
	}

	public void setGoodsVoList(List<GoodsVOListItem> goodsVoList) {
		this.goodsVoList = goodsVoList;
	}

	public boolean isExpressNumberFlag() {
		return expressNumberFlag;
	}

	public void setExpressNumberFlag(boolean expressNumberFlag) {
		this.expressNumberFlag = expressNumberFlag;
	}

	@Override
	public String toString() {
		return "AfterSalesModel{" +
				"orderId='" + orderId + '\'' +
				", afterSalesId='" + afterSalesId + '\'' +
				", salesType=" + salesType +
				", afterSalePrice=" + afterSalePrice +
				", goodsCount=" + goodsCount +
				", salesStatus=" + salesStatus +
				", reason='" + reason + '\'' +
				", createTime=" + createTime +
				", expressCompany='" + expressCompany + '\'' +
				", urlList=" + Arrays.toString(urlList) +
				", closeStatus=" + closeStatus +
				", closeReason=" + closeReason +
				", expressNumber='" + expressNumber + '\'' +
				", consignee='" + consignee + '\'' +
				", consigneeMobile='" + consigneeMobile + '\'' +
				", consigneeAddress='" + consigneeAddress + '\'' +
				", goodsType=" + goodsType +
				", goodsVoList=" + goodsVoList +
				", expressNumberFlag=" + expressNumberFlag +
				'}';
	}
}