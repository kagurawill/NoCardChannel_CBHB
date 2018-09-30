package com.rd.channel.common.model.gateway;

public class UM_PAY_QRY_REQ extends BaseMsgReq{
	private String tradeNo;//订单号(必填)

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	
}
