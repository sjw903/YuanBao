package com.yuanbaogo.mine.order.model;

import java.util.List;

public class SpecificationAggItem{
	private long specId;
	private boolean numerical;
	private List<ValueAggListItem> valueAggList;
	private String specName;
	private boolean hasCustomSpec;
	private long spuId;
	private boolean hasImage;
	private long id;
	private boolean required;
	private boolean isGeneralSpec;

	public void setSpecId(long specId){
		this.specId = specId;
	}

	public long getSpecId(){
		return specId;
	}

	public void setNumerical(boolean numerical){
		this.numerical = numerical;
	}

	public boolean isNumerical(){
		return numerical;
	}

	public void setValueAggList(List<ValueAggListItem> valueAggList){
		this.valueAggList = valueAggList;
	}

	public List<ValueAggListItem> getValueAggList(){
		return valueAggList;
	}

	public void setSpecName(String specName){
		this.specName = specName;
	}

	public String getSpecName(){
		return specName;
	}

	public void setHasCustomSpec(boolean hasCustomSpec){
		this.hasCustomSpec = hasCustomSpec;
	}

	public boolean isHasCustomSpec(){
		return hasCustomSpec;
	}

	public void setSpuId(long spuId){
		this.spuId = spuId;
	}

	public long getSpuId(){
		return spuId;
	}

	public void setHasImage(boolean hasImage){
		this.hasImage = hasImage;
	}

	public boolean isHasImage(){
		return hasImage;
	}

	public void setId(long id){
		this.id = id;
	}

	public long getId(){
		return id;
	}

	public void setRequired(boolean required){
		this.required = required;
	}

	public boolean isRequired(){
		return required;
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
			"SpecificationAggItem{" + 
			"specId = '" + specId + '\'' + 
			",numerical = '" + numerical + '\'' + 
			",valueAggList = '" + valueAggList + '\'' + 
			",specName = '" + specName + '\'' + 
			",hasCustomSpec = '" + hasCustomSpec + '\'' + 
			",spuId = '" + spuId + '\'' + 
			",hasImage = '" + hasImage + '\'' + 
			",id = '" + id + '\'' + 
			",required = '" + required + '\'' + 
			",isGeneralSpec = '" + isGeneralSpec + '\'' + 
			"}";
		}
}