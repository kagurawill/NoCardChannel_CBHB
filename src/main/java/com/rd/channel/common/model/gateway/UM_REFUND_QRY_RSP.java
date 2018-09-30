package com.rd.channel.common.model.gateway;

public class UM_REFUND_QRY_RSP extends BaseMsgReq{
	private String tradeNo;//支付平台订单号
	private String requestNo;//退款请求流水号
	private String dealId;//通道交易流水号
	private String refundAmount;//退款金额
	private String refundTime;//退款支付时间
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
	public String getRequestNo() {
		return requestNo;
	}
	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}
	public String getDealId() {
		return dealId;
	}
	public void setDealId(String dealId) {
		this.dealId = dealId;
	}
	public String getRefundAmount() {
		return refundAmount;
	}
	public void setRefundAmount(String refundAmount) {
		this.refundAmount = refundAmount;
	}
	public String getRefundTime() {
		return refundTime;
	}
	public void setRefundTime(String refundTime) {
		this.refundTime = refundTime;
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
