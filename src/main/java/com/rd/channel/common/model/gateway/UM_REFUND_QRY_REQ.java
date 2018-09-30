package com.rd.channel.common.model.gateway;

public class UM_REFUND_QRY_REQ {
	private String tradeNo;//支付平台订单号
	private String requestNo;//退款请求流水号
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
	
}
