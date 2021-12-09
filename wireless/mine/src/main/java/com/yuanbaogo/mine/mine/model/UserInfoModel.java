package com.yuanbaogo.mine.mine.model;

import java.util.List;

public class UserInfoModel {
	private int prePayCount;
	private int preEvaluateCount;
	private List<OrderListItem> orderList;
	private int preReceiveCount;
	private int afterSaleCount;

	public int getPrePayCount(){
		return prePayCount;
	}

	public int getPreEvaluateCount(){
		return preEvaluateCount;
	}

	public List<OrderListItem> getOrderList(){
		return orderList;
	}

	public int getPreReceiveCount(){
		return preReceiveCount;
	}

	public void setPrePayCount(int prePayCount) {
		this.prePayCount = prePayCount;
	}

	public void setPreEvaluateCount(int preEvaluateCount) {
		this.preEvaluateCount = preEvaluateCount;
	}

	public void setOrderList(List<OrderListItem> orderList) {
		this.orderList = orderList;
	}

	public void setPreReceiveCount(int preReceiveCount) {
		this.preReceiveCount = preReceiveCount;
	}

	public int getAfterSaleCount() {
		return afterSaleCount;
	}

	public void setAfterSaleCount(int afterSaleCount) {
		this.afterSaleCount = afterSaleCount;
	}

	@Override
	public String toString() {
		return "UserInfoModel{" +
				"prePayCount=" + prePayCount +
				", preEvaluateCount=" + preEvaluateCount +
				", orderList=" + orderList +
				", preReceiveCount=" + preReceiveCount +
				", afterSaleCount=" + afterSaleCount +
				'}';
	}
}