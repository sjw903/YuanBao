package com.yuanbaogo.mine.order.model;

import java.util.List;

public class OrderModel {
	private int total;
	private int size;
	private int page;
	private List<OrderListResponseListItem> rows;

	public void setTotal(int total){
		this.total = total;
	}

	public int getTotal(){
		return total;
	}

	public void setSize(int size){
		this.size = size;
	}

	public int getSize(){
		return size;
	}

	public void setPage(int page){
		this.page = page;
	}

	public int getPage(){
		return page;
	}

	public void setRows(List<OrderListResponseListItem> rows){
		this.rows = rows;
	}

	public List<OrderListResponseListItem> getRows(){
		return rows;
	}

	@Override
	public String toString(){
		return
				"Response{" +
						"total = '" + total + '\'' +
						",size = '" + size + '\'' +
						",page = '" + page + '\'' +
						",rows = '" + rows + '\'' +
						"}";
	}
}