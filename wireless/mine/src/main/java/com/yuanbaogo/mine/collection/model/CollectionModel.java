package com.yuanbaogo.mine.collection.model;

import java.util.List;

public class CollectionModel{
	private int total;
	private int size;
	private int page;
	private List<CollectionItem> rows;

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

	public void setRows(List<CollectionItem> rows){
		this.rows = rows;
	}

	public List<CollectionItem> getRows(){
		return rows;
	}

	@Override
 	public String toString(){
		return 
			"CollectionModel{" + 
			"total = '" + total + '\'' + 
			",size = '" + size + '\'' + 
			",page = '" + page + '\'' + 
			",rows = '" + rows + '\'' + 
			"}";
		}
}