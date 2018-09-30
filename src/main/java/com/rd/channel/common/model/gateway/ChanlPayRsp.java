package com.rd.channel.common.model.gateway;

public class ChanlPayRsp extends ChanlBaseMsg{
	private String payType;	//支付方式		 
	private String transactionId;//商户订单号
	private String orderAmount;//商户订单金额
	private String cur;//币种
	private String dealId;//支付平台交易序号
	private String dealTime;//支付平台交易时间
	private String payAmount;//订单实际支付金额
	private String transStatus;//订单状态
	private String retCode;//应答码
	private String retRemark;//应答码描述
	private String chanlRetCode;//上游应答码
	private String chanlRetRemark;//上游应答码描述
	private String signData;//签名
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(String orderAmount) {
		this.orderAmount = orderAmount;
	}
	public String getCur() {
		return cur;
	}
	public void setCur(String cur) {
		this.cur = cur;
	}
	public String getDealId() {
		return dealId;
	}
	public void setDealId(String dealId) {
		this.dealId = dealId;
	}
	public String getDealTime() {
		return dealTime;
	}
	public void setDealTime(String dealTime) {
		this.dealTime = dealTime;
	}
	public String getPayAmount() {
		return payAmount;
	}
	public void setPayAmount(String payAmount) {
		this.payAmount = payAmount;
	}
	public String getTransStatus() {
		return transStatus;
	}
	public void setTransStatus(String transStatus) {
		this.transStatus = transStatus;
	}
	public String getRetCode() {
		return retCode;
	}
	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}
	public String getRetRemark() {
		return retRemark;
	}
	public void setRetRemark(String retRemark) {
		this.retRemark = retRemark;
	}
	public String getSignData() {
		return signData;
	}
	public void setSignData(String signData) {
		this.signData = signData;
	}
	public String getChanlRetCode() {
		return chanlRetCode;
	}
	public void setChanlRetCode(String chanlRetCode) {
		this.chanlRetCode = chanlRetCode;
	}
	public String getChanlRetRemark() {
		return chanlRetRemark;
	}
	public void setChanlRetRemark(String chanlRetRemark) {
		this.chanlRetRemark = chanlRetRemark;
	}
	

}
