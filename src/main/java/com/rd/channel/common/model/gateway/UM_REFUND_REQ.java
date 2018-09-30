package com.rd.channel.common.model.gateway;

public class UM_REFUND_REQ extends BaseMsgReq{
	private String tradeNo;//支付平台订单号
	private String refundAmount;//退款金额
	private String refundReason;//退款原因
	private String requestNo;//退款请求流水号
	public UM_REFUND_REQ() {
	}
	public String getTradeNo() {
		return tradeNo;
	}
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	public String getRefundAmount() {
		return refundAmount;
	}
	public void setRefundAmount(String refundAmount) {
		this.refundAmount = refundAmount;
	}
	public String getRefundReason() {
		return refundReason;
	}
	public void setRefundReason(String refundReason) {
		this.refundReason = refundReason;
	}
	public String getRequestNo() {
		return requestNo;
	}
	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}
	
	
}
