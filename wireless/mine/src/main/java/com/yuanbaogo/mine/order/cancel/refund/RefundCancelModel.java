package com.yuanbaogo.mine.order.cancel.refund;

public class RefundCancelModel {
	private long finishTime;
	private int afterSalePrice;
	private String des;
	private String afterSalesId;
	private String orderId;
	private long createTime;
	private int salesStatus;
	private String closeReason;

	public void setFinishTime(long finishTime){
		this.finishTime = finishTime;
	}

	public long getFinishTime(){
		return finishTime;
	}

	public void setAfterSalePrice(int afterSalePrice){
		this.afterSalePrice = afterSalePrice;
	}

	public int getAfterSalePrice(){
		return afterSalePrice;
	}

	public void setDes(String des){
		this.des = des;
	}

	public String getDes(){
		return des;
	}

	public void setAfterSalesId(String afterSalesId){
		this.afterSalesId = afterSalesId;
	}

	public String getAfterSalesId(){
		return afterSalesId;
	}

	public void setOrderId(String orderId){
		this.orderId = orderId;
	}

	public String getOrderId(){
		return orderId;
	}

	public void setCreateTime(long createTime){
		this.createTime = createTime;
	}

	public long getCreateTime(){
		return createTime;
	}

	public void setSalesStatus(int salesStatus){
		this.salesStatus = salesStatus;
	}

	public int getSalesStatus(){
		return salesStatus;
	}

	public void setCloseReason(String closeReason){
		this.closeReason = closeReason;
	}

	public String getCloseReason(){
		return closeReason;
	}

	@Override
 	public String toString(){
		return 
			"Response{" + 
			"finishTime = '" + finishTime + '\'' + 
			",afterSalePrice = '" + afterSalePrice + '\'' + 
			",des = '" + des + '\'' + 
			",afterSalesId = '" + afterSalesId + '\'' + 
			",orderId = '" + orderId + '\'' + 
			",createTime = '" + createTime + '\'' + 
			",salesStatus = '" + salesStatus + '\'' + 
			",closeReason = '" + closeReason + '\'' + 
			"}";
		}
}
