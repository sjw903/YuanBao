package com.yuanbaogo.mine.order.after.model;

import java.util.List;

public class RefundModel {
	private int total;
	private int size;
	private int page;
	private List<AfterSalesModel> rows;

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

	public void setRows(List<AfterSalesModel> rows){
		this.rows = rows;
	}

	public List<AfterSalesModel> getRows(){
		return rows;
	}

	@Override
 	public String toString(){
		return 
			"RefundRecordModel{" + 
			"total = '" + total + '\'' + 
			",size = '" + size + '\'' + 
			",page = '" + page + '\'' + 
			",rows = '" + rows + '\'' + 
			"}";
		}
}