package com.rd.channel.common.model.chanl;

public class CbhbQryRsp {
	
	private String RespCode;//交易状态
	private String RespMsg;//描述信息
	private String Plain;
	private String Sign;
	public String getRespCode() {
		return RespCode;
	}
	public void setRespCode(String respCode) {
		RespCode = respCode;
	}
	public String getRespMsg() {
		return RespMsg;
	}
	public void setRespMsg(String respMsg) {
		RespMsg = respMsg;
	}
	public String getPlain() {
		return Plain;
	}
	public void setPlain(String plain) {
		Plain = plain;
	}
	public String getSign() {
		return Sign;
	}
	public void setSign(String sign) {
		Sign = sign;
	}
	
}
