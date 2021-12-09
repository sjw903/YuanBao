package com.yuanbaogo.mine.order.model;

import java.util.List;

public class PayOrderModel {
	private int total;
	private List<OrderListResponseListItem> orderListVOList;

	public void setTotal(int total){
		this.total = total;
	}

	public int getTotal(){
		return total;
	}

	public List<OrderListResponseListItem> getOrderListVOList() {
		return orderListVOList;
	}

	public void setOrderListVOList(List<OrderListResponseListItem> orderListVOList) {
		this.orderListVOList = orderListVOList;
	}

	@Override
 	public String toString(){
		return 
			"Response{" + 
			"total = '" + total + '\'' + 
			",orderListVOList = '" + orderListVOList + '\'' +
			"}";
		}
}