package com.yuanbaogo.mine.order.after.logistics;

public class LogisticsCompanyModel{
	private int code;
	private String name;

	public void setCode(int code){
		this.code = code;
	}

	public int getCode(){
		return code;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	@Override
 	public String toString(){
		return 
			"LogisticsCompanyModel{" + 
			"code = '" + code + '\'' + 
			",name = '" + name + '\'' + 
			"}";
		}
}
