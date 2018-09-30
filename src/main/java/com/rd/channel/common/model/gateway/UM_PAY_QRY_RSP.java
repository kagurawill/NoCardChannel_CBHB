package com.rd.channel.common.model.gateway;

public class UM_PAY_QRY_RSP extends BaseMsgReq{
	private String tradeNo;//订单号(必填)
	private String dealId;//通道交易流水号
	private String totalAmount;//商户订单金额(元)(必填)
	private String payTime;//交易支付时间
	private String retCode;//业务返回码
	private String retMsg;//业务返回码描述
	private String upchlRetCode;//通道返回码
	private String upchlRetMsg;//通道返回码描述
	public String getTradeNo() {
		return tradeNo;
	}
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	public String getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getPayTime() {
		return payTime;
	}
	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}
	public String getDealId() {
		return dealId;
	}
	public void setDealId(String dealId) {
		this.dealId = dealId;
	}
	public String getRetCode() {
		return retCode;
	}
	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}
	public String getRetMsg() {
		return retMsg;
	}
	public void setRetMsg(String retMsg) {
		this.retMsg = retMsg;
	}
	public String getUpchlRetCode() {
		return upchlRetCode;
	}
	public void setUpchlRetCode(String upchlRetCode) {
		this.upchlRetCode = upchlRetCode;
	}
	public String getUpchlRetMsg() {
		return upchlRetMsg;
	}
	public void setUpchlRetMsg(String upchlRetMsg) {
		this.upchlRetMsg = upchlRetMsg;
	}

}
