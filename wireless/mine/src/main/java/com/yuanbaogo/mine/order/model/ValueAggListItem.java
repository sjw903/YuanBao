package com.yuanbaogo.mine.order.model;

public class ValueAggListItem{
	private long specId;
	private String specValue;
	private String imageUrl;
	private int specIndex;
	private long spuId;
	private Object relationId;
	private long id;
	private boolean isGeneralSpec;

	public void setSpecId(long specId){
		this.specId = specId;
	}

	public long getSpecId(){
		return specId;
	}

	public void setSpecValue(String specValue){
		this.specValue = specValue;
	}

	public String getSpecValue(){
		return specValue;
	}

	public void setImageUrl(String imageUrl){
		this.imageUrl = imageUrl;
	}

	public String getImageUrl(){
		return imageUrl;
	}

	public void setSpecIndex(int specIndex){
		this.specIndex = specIndex;
	}

	public int getSpecIndex(){
		return specIndex;
	}

	public void setSpuId(long spuId){
		this.spuId = spuId;
	}

	public long getSpuId(){
		return spuId;
	}

	public void setRelationId(Object relationId){
		this.relationId = relationId;
	}

	public Object getRelationId(){
		return relationId;
	}

	public void setId(long id){
		this.id = id;
	}

	public long getId(){
		return id;
	}

	public void setIsGeneralSpec(boolean isGeneralSpec){
		this.isGeneralSpec = isGeneralSpec;
	}

	public boolean isIsGeneralSpec(){
		return isGeneralSpec;
	}

	@Override
 	public String toString(){
		return 
			"ValueAggListItem{" + 
			"specId = '" + specId + '\'' + 
			",specValue = '" + specValue + '\'' + 
			",imageUrl = '" + imageUrl + '\'' + 
			",specIndex = '" + specIndex + '\'' + 
			",spuId = '" + spuId + '\'' + 
			",relationId = '" + relationId + '\'' + 
			",id = '" + id + '\'' + 
			",isGeneralSpec = '" + isGeneralSpec + '\'' + 
			"}";
		}
}
