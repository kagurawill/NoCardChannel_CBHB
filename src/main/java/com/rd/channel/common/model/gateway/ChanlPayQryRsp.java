package com.rd.channel.common.model.gateway;

public class ChanlPayQryRsp extends ChanlBaseMsg{
	String transactionId;//商户订单号    
	String refTxnId;//原商户订单号
	String qryRetCode;//查询应答码
	String qryRetRemark;//查询应答码描述 	
	String orderAmount;//商户订单金额  
	String transStatus;//订单状态      
	String retCode;//返回码            
	String retRemark;//返回码描述   
	private String chanlRetCode;//上游应答码
	private String chanlRetRemark;//上游应答码描述
	String signData;//签名  
	public String getQryRetCode() {
		return qryRetCode;
	}
	public void setQryRetCode(String qryRetCode) {
		this.qryRetCode = qryRetCode;
	}
	public String getQryRetRemark() {
		return qryRetRemark;
	}
	public void setQryRetRemark(String qryRetRemark) {
		this.qryRetRemark = qryRetRemark;
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
	public String getRefTxnId() {
		return refTxnId;
	}
	public void setRefTxnId(String refTxnId) {
		this.refTxnId = refTxnId;
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
