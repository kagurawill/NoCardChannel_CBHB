package com.rd.channel.common.model.gateway;

public class ChanlQuickPayRsp extends ChanlBaseMsg{
	private String transactionId;//商户交易号
	private String transStatus;//处理结果
	private String retCode;//返回码
	private String retRemark;//返回码描述
	private String chanlRetCode;//上游应答码
	private String chanlRetRemark;//上游应答码描述
	private String dealId;//支付平台交易序号
	private String signData;//签名
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
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
	public String getDealId() {
		return dealId;
	}
	public void setDealId(String dealId) {
		this.dealId = dealId;
	}
	
}
