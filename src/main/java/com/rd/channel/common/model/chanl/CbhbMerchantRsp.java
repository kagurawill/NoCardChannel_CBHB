package com.rd.channel.common.model.chanl;


public class CbhbMerchantRsp {

	private String TransId;//交易代码
	private String MerchantId;//商户编号
	private String RespCode;//交易状态
	private String RespMesssage;//描述信息
	public String getTransId() {
		return TransId;
	}
	public void setTransId(String transId) {
		TransId = transId;
	}
	public String getMerchantId() {
		return MerchantId;
	}
	public void setMerchantId(String merchantId) {
		MerchantId = merchantId;
	}
	public String getRespCode() {
		return RespCode;
	}
	public void setRespCode(String respCode) {
		RespCode = respCode;
	}
	public String getRespMesssage() {
		return RespMesssage;
	}
	public void setRespMesssage(String respMesssage) {
		RespMesssage = respMesssage;
	}
	
}
