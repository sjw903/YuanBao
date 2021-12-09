package com.yuanbaogo.mine.order.model;

import java.util.List;

public class AllOrderModel {
	private int total;
	private List<OrderListResponseListItem> orderListResponseList;

	public void setTotal(int total){
		this.total = total;
	}

	public int getTotal(){
		return total;
	}

	public void setOrderListResponseList(List<OrderListResponseListItem> orderListResponseList){
		this.orderListResponseList = orderListResponseList;
	}

	public List<OrderListResponseListItem> getOrderListResponseList(){
		return orderListResponseList;
	}

	@Override
 	public String toString(){
		return 
			"Response{" + 
			"total = '" + total + '\'' + 
			",orderListResponseList = '" + orderListResponseList + '\'' + 
			"}";
		}
}