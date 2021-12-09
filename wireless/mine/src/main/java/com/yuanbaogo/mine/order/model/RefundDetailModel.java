package com.yuanbaogo.mine.order.model;

public class RefundDetailModel {
	private int salesType;
	private String reason;
	private int amount;
	private String logisticsId;
	private int afterSalesId;
	private String settlementRemark;
	private int approveCode;
	private int orderId;
	private String companyName;
	private int settlementCode;
	private String approveRemark;
	private int paymentCode;
	private String settlementTime;
	private String describe;
	private int salesStatus;
	private String applyTime;
	private String approveTime;
	private String paymentTime;

	public void setSalesType(int salesType){
		this.salesType = salesType;
	}

	public int getSalesType(){
		return salesType;
	}

	public void setReason(String reason){
		this.reason = reason;
	}

	public String getReason(){
		return reason;
	}

	public void setAmount(int amount){
		this.amount = amount;
	}

	public int getAmount(){
		return amount;
	}

	public void setLogisticsId(String logisticsId){
		this.logisticsId = logisticsId;
	}

	public String getLogisticsId(){
		return logisticsId;
	}

	public void setAfterSalesId(int afterSalesId){
		this.afterSalesId = afterSalesId;
	}

	public int getAfterSalesId(){
		return afterSalesId;
	}

	public void setSettlementRemark(String settlementRemark){
		this.settlementRemark = settlementRemark;
	}

	public String getSettlementRemark(){
		return settlementRemark;
	}

	public void setApproveCode(int approveCode){
		this.approveCode = approveCode;
	}

	public int getApproveCode(){
		return approveCode;
	}

	public void setOrderId(int orderId){
		this.orderId = orderId;
	}

	public int getOrderId(){
		return orderId;
	}

	public void setCompanyName(String companyName){
		this.companyName = companyName;
	}

	public String getCompanyName(){
		return companyName;
	}

	public void setSettlementCode(int settlementCode){
		this.settlementCode = settlementCode;
	}

	public int getSettlementCode(){
		return settlementCode;
	}

	public void setApproveRemark(String approveRemark){
		this.approveRemark = approveRemark;
	}

	public String getApproveRemark(){
		return approveRemark;
	}

	public void setPaymentCode(int paymentCode){
		this.paymentCode = paymentCode;
	}

	public int getPaymentCode(){
		return paymentCode;
	}

	public void setSettlementTime(String settlementTime){
		this.settlementTime = settlementTime;
	}

	public String getSettlementTime(){
		return settlementTime;
	}

	public void setDescribe(String describe){
		this.describe = describe;
	}

	public String getDescribe(){
		return describe;
	}

	public void setSalesStatus(int salesStatus){
		this.salesStatus = salesStatus;
	}

	public int getSalesStatus(){
		return salesStatus;
	}

	public void setApplyTime(String applyTime){
		this.applyTime = applyTime;
	}

	public String getApplyTime(){
		return applyTime;
	}

	public void setApproveTime(String approveTime){
		this.approveTime = approveTime;
	}

	public String getApproveTime(){
		return approveTime;
	}

	public void setPaymentTime(String paymentTime){
		this.paymentTime = paymentTime;
	}

	public String getPaymentTime(){
		return paymentTime;
	}
}
