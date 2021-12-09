package com.yuanbaogo.mine.order.model;

import java.util.List;

public class LikeListModelItem{
	private String englishName;
	private boolean shelfStatus;
	private int minSalePrice;
	private Object description;
	private int minCostPrice;
	private int minLinePrice;
	private Object specialId;
	private int specialType;
	private Object brandDescription;
	private Object specialName;
	private String categoryThreeName;
	private List<SpecificationAggItem> specificationAgg;
	private String goodsName;
	private String categoryTwoName;
	private String categoryOneName;
	private String brandName;
	private int totalSales;
	private String logoUrl;
	private List<Object> keywordList;
	private boolean recycleStatus;
	private Object mainImage;
	private String cargoName;
	private int totalStock;
	private String spuId;
	private String coverImages;

	public void setEnglishName(String englishName){
		this.englishName = englishName;
	}

	public String getEnglishName(){
		return englishName;
	}

	public void setShelfStatus(boolean shelfStatus){
		this.shelfStatus = shelfStatus;
	}

	public boolean isShelfStatus(){
		return shelfStatus;
	}

	public void setMinSalePrice(int minSalePrice){
		this.minSalePrice = minSalePrice;
	}

	public int getMinSalePrice(){
		return minSalePrice;
	}

	public void setDescription(Object description){
		this.description = description;
	}

	public Object getDescription(){
		return description;
	}

	public void setMinCostPrice(int minCostPrice){
		this.minCostPrice = minCostPrice;
	}

	public int getMinCostPrice(){
		return minCostPrice;
	}

	public void setMinLinePrice(int minLinePrice){
		this.minLinePrice = minLinePrice;
	}

	public int getMinLinePrice(){
		return minLinePrice;
	}

	public void setSpecialId(Object specialId){
		this.specialId = specialId;
	}

	public Object getSpecialId(){
		return specialId;
	}

	public void setSpecialType(int specialType){
		this.specialType = specialType;
	}

	public int getSpecialType(){
		return specialType;
	}

	public void setBrandDescription(Object brandDescription){
		this.brandDescription = brandDescription;
	}

	public Object getBrandDescription(){
		return brandDescription;
	}

	public void setSpecialName(Object specialName){
		this.specialName = specialName;
	}

	public Object getSpecialName(){
		return specialName;
	}

	public void setCategoryThreeName(String categoryThreeName){
		this.categoryThreeName = categoryThreeName;
	}

	public String getCategoryThreeName(){
		return categoryThreeName;
	}

	public void setSpecificationAgg(List<SpecificationAggItem> specificationAgg){
		this.specificationAgg = specificationAgg;
	}

	public List<SpecificationAggItem> getSpecificationAgg(){
		return specificationAgg;
	}

	public void setGoodsName(String goodsName){
		this.goodsName = goodsName;
	}

	public String getGoodsName(){
		return goodsName;
	}

	public void setCategoryTwoName(String categoryTwoName){
		this.categoryTwoName = categoryTwoName;
	}

	public String getCategoryTwoName(){
		return categoryTwoName;
	}

	public void setCategoryOneName(String categoryOneName){
		this.categoryOneName = categoryOneName;
	}

	public String getCategoryOneName(){
		return categoryOneName;
	}

	public void setBrandName(String brandName){
		this.brandName = brandName;
	}

	public String getBrandName(){
		return brandName;
	}

	public void setTotalSales(int totalSales){
		this.totalSales = totalSales;
	}

	public int getTotalSales(){
		return totalSales;
	}

	public void setLogoUrl(String logoUrl){
		this.logoUrl = logoUrl;
	}

	public String getLogoUrl(){
		return logoUrl;
	}

	public void setKeywordList(List<Object> keywordList){
		this.keywordList = keywordList;
	}

	public List<Object> getKeywordList(){
		return keywordList;
	}

	public void setRecycleStatus(boolean recycleStatus){
		this.recycleStatus = recycleStatus;
	}

	public boolean isRecycleStatus(){
		return recycleStatus;
	}

	public void setMainImage(Object mainImage){
		this.mainImage = mainImage;
	}

	public Object getMainImage(){
		return mainImage;
	}

	public void setCargoName(String cargoName){
		this.cargoName = cargoName;
	}

	public String getCargoName(){
		return cargoName;
	}

	public void setTotalStock(int totalStock){
		this.totalStock = totalStock;
	}

	public int getTotalStock(){
		return totalStock;
	}

	public void setSpuId(String spuId){
		this.spuId = spuId;
	}

	public String getSpuId(){
		return spuId;
	}

	public void setCoverImages(String coverImages){
		this.coverImages = coverImages;
	}

	public String getCoverImages(){
		return coverImages;
	}

	@Override
 	public String toString(){
		return 
			"LikeListModelItem{" + 
			"englishName = '" + englishName + '\'' + 
			",shelfStatus = '" + shelfStatus + '\'' + 
			",minSalePrice = '" + minSalePrice + '\'' + 
			",description = '" + description + '\'' + 
			",minCostPrice = '" + minCostPrice + '\'' + 
			",minLinePrice = '" + minLinePrice + '\'' + 
			",specialId = '" + specialId + '\'' + 
			",specialType = '" + specialType + '\'' + 
			",brandDescription = '" + brandDescription + '\'' + 
			",specialName = '" + specialName + '\'' + 
			",categoryThreeName = '" + categoryThreeName + '\'' + 
			",specificationAgg = '" + specificationAgg + '\'' + 
			",goodsName = '" + goodsName + '\'' + 
			",categoryTwoName = '" + categoryTwoName + '\'' + 
			",categoryOneName = '" + categoryOneName + '\'' + 
			",brandName = '" + brandName + '\'' + 
			",totalSales = '" + totalSales + '\'' + 
			",logoUrl = '" + logoUrl + '\'' + 
			",keywordList = '" + keywordList + '\'' + 
			",recycleStatus = '" + recycleStatus + '\'' + 
			",mainImage = '" + mainImage + '\'' + 
			",cargoName = '" + cargoName + '\'' + 
			",totalStock = '" + totalStock + '\'' + 
			",spuId = '" + spuId + '\'' + 
			",coverImages = '" + coverImages + '\'' + 
			"}";
		}
}