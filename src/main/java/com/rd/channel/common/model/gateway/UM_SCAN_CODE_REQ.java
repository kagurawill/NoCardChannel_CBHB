package com.rd.channel.common.model.gateway;

public class UM_SCAN_CODE_REQ extends BaseMsgReq{
	private String tradeNo;//订单号(必填)
	private String totalAmount;//商户订单金额(元)(必填)
	private String body;//交易或商品的描述(必填)
	private String detail;//商品详情(选填)
	private String txnType;//支付方式(必填)
	private String notifyUrl;//通知地址(必填)
	private String currency;//币种(必填)
	
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
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	
	public String getTxnType() {
		return txnType;
	}
	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}
	public String getNotifyUrl() {
		return notifyUrl;
	}
	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	
}
