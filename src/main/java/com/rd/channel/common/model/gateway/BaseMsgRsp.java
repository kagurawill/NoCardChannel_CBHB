package com.rd.channel.common.model.gateway;

public class BaseMsgRsp {
	private String code;// SUCCESS/FAIL此字段是通信标识，非交易标识，交易是否成功需要查看retCode来判断
	private String msg;//返回信息，如非空，为错误原因
	private String bizContext;//code为SUCCESS的时候有值
	private String sign;//签名 code为SUCCESS的时候有值
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getBizContext() {
		return bizContext;
	}
	public void setBizContext(String bizContext) {
		this.bizContext = bizContext;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	
	
}
