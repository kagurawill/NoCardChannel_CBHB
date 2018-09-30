package com.rd.channel.common.model.gateway;

public class UM_SCAN_CODE_RSP extends BaseMsgReq{
	private String tradeNo;//商户订单号(必填)
	private String qrCode;//当前预下单请求生成的二维码码串
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
	public String getQrCode() {
		return qrCode;
	}
	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
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
