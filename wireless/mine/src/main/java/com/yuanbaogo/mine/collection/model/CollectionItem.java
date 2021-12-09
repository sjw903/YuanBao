package com.yuanbaogo.mine.collection.model;

import java.util.List;

public class CollectionItem {
	private int salePrice;
	private int linePrice;
	private boolean isShelf;
	private int sales;
	private int goodsType;
	private String imgUrl;
	private List<String> keywordList;
	private int createBy;
	private String areaId;
	private long createTime;
	private String spuId;
	private String skuId;
	private String id;
	private String goodsName;

	public void setSalePrice(int salePrice){
		this.salePrice = salePrice;
	}

	public int getSalePrice(){
		return salePrice;
	}

	public void setLinePrice(int linePrice){
		this.linePrice = linePrice;
	}

	public int getLinePrice(){
		return linePrice;
	}

	public void setIsShelf(boolean isShelf){
		this.isShelf = isShelf;
	}

	public boolean isIsShelf(){
		return isShelf;
	}

	public void setSales(int sales){
		this.sales = sales;
	}

	public int getSales(){
		return sales;
	}

	public void setGoodsType(int goodsType){
		this.goodsType = goodsType;
	}

	public int getGoodsType(){
		return goodsType;
	}

	public void setImgUrl(String imgUrl){
		this.imgUrl = imgUrl;
	}

	public String getImgUrl(){
		return imgUrl;
	}

	public void setKeywordList(List<String> keywordList){
		this.keywordList = keywordList;
	}

	public List<String> getKeywordList(){
		return keywordList;
	}

	public void setCreateBy(int createBy){
		this.createBy = createBy;
	}

	public int getCreateBy(){
		return createBy;
	}

	public void setAreaId(String areaId){
		this.areaId = areaId;
	}

	public String getAreaId(){
		return areaId;
	}

	public void setCreateTime(long createTime){
		this.createTime = createTime;
	}

	public long getCreateTime(){
		return createTime;
	}

	public void setSpuId(String spuId){
		this.spuId = spuId;
	}

	public String getSpuId(){
		return spuId;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setGoodsName(String goodsName){
		this.goodsName = goodsName;
	}

	public String getGoodsName(){
		return goodsName;
	}

	public String getSkuId() {
		return skuId;
	}

	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}

	@Override
 	public String toString(){
		return 
			"RowsItem{" + 
			"salePrice = '" + salePrice + '\'' + 
			",linePrice = '" + linePrice + '\'' + 
			",isShelf = '" + isShelf + '\'' + 
			",sales = '" + sales + '\'' + 
			",goodsType = '" + goodsType + '\'' + 
			",imgUrl = '" + imgUrl + '\'' + 
			",keywordList = '" + keywordList + '\'' + 
			",createBy = '" + createBy + '\'' + 
			",areaId = '" + areaId + '\'' + 
			",createTime = '" + createTime + '\'' + 
			",spuId = '" + spuId + '\'' + 
			",id = '" + id + '\'' + 
			",goodsName = '" + goodsName + '\'' + 
			"}";
		}
}