package com.yuanbaogo.mine.order.model;

public class OrderInfoAddressVO{
	private String country;
	private String province;
	private String city;
	private String name;
	private String mobile;
	private String detail;
	private String defaultAddress;

	public void setCountry(String country){
		this.country = country;
	}

	public String getCountry(){
		return country;
	}

	public void setProvince(String province){
		this.province = province;
	}

	public String getProvince(){
		return province;
	}

	public void setCity(String city){
		this.city = city;
	}

	public String getCity(){
		return city;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setMobile(String mobile){
		this.mobile = mobile;
	}

	public String getMobile(){
		return mobile;
	}

	public void setDetail(String detail){
		this.detail = detail;
	}

	public String getDetail(){
		return detail;
	}

	public void setDefaultAddress(String defaultAddress){
		this.defaultAddress = defaultAddress;
	}

	public Object getDefaultAddress(){
		return defaultAddress;
	}

	@Override
 	public String toString(){
		return 
			"OrderInfoAddressVO{" + 
			"country = '" + country + '\'' + 
			",province = '" + province + '\'' + 
			",city = '" + city + '\'' + 
			",name = '" + name + '\'' + 
			",mobile = '" + mobile + '\'' + 
			",detail = '" + detail + '\'' + 
			",defaultAddress = '" + defaultAddress + '\'' + 
			"}";
		}
}
