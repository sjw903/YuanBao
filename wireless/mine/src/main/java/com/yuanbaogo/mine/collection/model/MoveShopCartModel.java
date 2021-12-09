package com.yuanbaogo.mine.collection.model;

public class MoveShopCartModel{
	private String skuName;
	private int createBy;
	private int goodsCount;
	private int areaId;
	private int cartType;
	private String spuId;
	private int createGoodsPrice;
	private String createGoodsName;
	private String goodsImgUrl;
	private String skuId;
	private int goodsType;

	public MoveShopCartModel() {
	}

	public MoveShopCartModel(String skuName, int createBy, int goodsCount, int areaId, int cartType, String spuId, int createGoodsPrice, String createGoodsName, String goodsImgUrl, String skuId, int goodsType) {
		this.skuName = skuName;
		this.createBy = createBy;
		this.goodsCount = goodsCount;
		this.areaId = areaId;
		this.cartType = cartType;
		this.spuId = spuId;
		this.createGoodsPrice = createGoodsPrice;
		this.createGoodsName = createGoodsName;
		this.goodsImgUrl = goodsImgUrl;
		this.skuId = skuId;
		this.goodsType = goodsType;
	}

	public void setSkuName(String skuName){
		this.skuName = skuName;
	}

	public String getSkuName(){
		return skuName;
	}

	public void setCreateBy(int createBy){
		this.createBy = createBy;
	}

	public int getCreateBy(){
		return createBy;
	}

	public void setGoodsCount(int goodsCount){
		this.goodsCount = goodsCount;
	}

	public int getGoodsCount(){
		return goodsCount;
	}

	public void setAreaId(int areaId){
		this.areaId = areaId;
	}

	public int getAreaId(){
		return areaId;
	}

	public void setCartType(int cartType){
		this.cartType = cartType;
	}

	public int getCartType(){
		return cartType;
	}

	public void setSpuId(String spuId){
		this.spuId = spuId;
	}

	public String getSpuId(){
		return spuId;
	}

	public void setCreateGoodsPrice(int createGoodsPrice){
		this.createGoodsPrice = createGoodsPrice;
	}

	public int getCreateGoodsPrice(){
		return createGoodsPrice;
	}

	public void setCreateGoodsName(String createGoodsName){
		this.createGoodsName = createGoodsName;
	}

	public String getCreateGoodsName(){
		return createGoodsName;
	}

	public void setGoodsImgUrl(String goodsImgUrl){
		this.goodsImgUrl = goodsImgUrl;
	}

	public String getGoodsImgUrl(){
		return goodsImgUrl;
	}

	public void setSkuId(String skuId){
		this.skuId = skuId;
	}

	public String getSkuId(){
		return skuId;
	}

	public void setGoodsType(int goodsType){
		this.goodsType = goodsType;
	}

	public int getGoodsType(){
		return goodsType;
	}

	@Override
 	public String toString(){
		return 
			"MoveShopCartModel{" + 
			"skuName = '" + skuName + '\'' + 
			",createBy = '" + createBy + '\'' + 
			",goodsCount = '" + goodsCount + '\'' + 
			",areaId = '" + areaId + '\'' + 
			",cartType = '" + cartType + '\'' + 
			",spuId = '" + spuId + '\'' + 
			",createGoodsPrice = '" + createGoodsPrice + '\'' + 
			",createGoodsName = '" + createGoodsName + '\'' + 
			",goodsImgUrl = '" + goodsImgUrl + '\'' + 
			",skuId = '" + skuId + '\'' + 
			",goodsType = '" + goodsType + '\'' + 
			"}";
		}
}
