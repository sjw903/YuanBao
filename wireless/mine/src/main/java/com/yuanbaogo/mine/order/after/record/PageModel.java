package com.yuanbaogo.mine.order.after.record;

public class PageModel{
	private int size;
	private int page;

	public PageModel(int size, int page) {
		this.size = size;
		this.page = page;
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

	@Override
 	public String toString(){
		return 
			"PageModel{" + 
			"size = '" + size + '\'' + 
			",page = '" + page + '\'' + 
			"}";
		}

}
