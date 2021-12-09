package com.yuanbaogo.mine.setting.about;

public class ApkInfoModel{
	private String publishTime;
	private boolean deleted;
	private boolean forcedUpgrade;
	private String createTime;
	private boolean isNeedUpgrade;
	private String updateUrl;
	private String updateTime;
	private String terminal;
	private String descriptions;
	private String versionNumber;

	public void setPublishTime(String publishTime){
		this.publishTime = publishTime;
	}

	public String getPublishTime(){
		return publishTime;
	}

	public void setDeleted(boolean deleted){
		this.deleted = deleted;
	}

	public boolean isDeleted(){
		return deleted;
	}

	public void setForcedUpgrade(boolean forcedUpgrade){
		this.forcedUpgrade = forcedUpgrade;
	}

	public boolean isForcedUpgrade(){
		return forcedUpgrade;
	}

	public void setCreateTime(String createTime){
		this.createTime = createTime;
	}

	public String getCreateTime(){
		return createTime;
	}

	public void setIsNeedUpgrade(boolean isNeedUpgrade){
		this.isNeedUpgrade = isNeedUpgrade;
	}

	public boolean isIsNeedUpgrade(){
		return isNeedUpgrade;
	}

	public void setUpdateUrl(String updateUrl){
		this.updateUrl = updateUrl;
	}

	public String getUpdateUrl(){
		return updateUrl;
	}

	public void setUpdateTime(String updateTime){
		this.updateTime = updateTime;
	}

	public String getUpdateTime(){
		return updateTime;
	}

	public void setTerminal(String terminal){
		this.terminal = terminal;
	}

	public String getTerminal(){
		return terminal;
	}

	public void setDescriptions(String descriptions){
		this.descriptions = descriptions;
	}

	public String getDescriptions(){
		return descriptions;
	}

	public void setVersionNumber(String versionNumber){
		this.versionNumber = versionNumber;
	}

	public String getVersionNumber(){
		return versionNumber;
	}

	@Override
 	public String toString(){
		return 
			"ApkInfoModel{" + 
			"publishTime = '" + publishTime + '\'' + 
			",deleted = '" + deleted + '\'' + 
			",forcedUpgrade = '" + forcedUpgrade + '\'' + 
			",createTime = '" + createTime + '\'' + 
			",isNeedUpgrade = '" + isNeedUpgrade + '\'' + 
			",updateUrl = '" + updateUrl + '\'' + 
			",updateTime = '" + updateTime + '\'' + 
			",terminal = '" + terminal + '\'' + 
			",descriptions = '" + descriptions + '\'' + 
			",versionNumber = '" + versionNumber + '\'' + 
			"}";
		}
}
