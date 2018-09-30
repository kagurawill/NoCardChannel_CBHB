package com.rd.channel.common.model.gateway;

public class ChanlBaseMsg {
	private String version;
	private String merId;
	private String merName;
	private String orderTime;
	private String transCode;
	private String signType;
	private String charse;
	private String reserve1;
	private String reserveJson;
	private String businessContext;
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getMerId() {
		return merId;
	}
	public void setMerId(String merId) {
		this.merId = merId;
	}
	public String getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}
	public String getTransCode() {
		return transCode;
	}
	public void setTransCode(String transCode) {
		this.transCode = transCode;
	}
	public String getSignType() {
		return signType;
	}
	public void setSignType(String signType) {
		this.signType = signType;
	}
	public String getCharse() {
		return charse;
	}
	public void setCharse(String charse) {
		this.charse = charse;
	}
	public String getReserve1() {
		return reserve1;
	}
	public void setReserve1(String reserve1) {
		this.reserve1 = reserve1;
	}
	public String getReserveJson() {
		return reserveJson;
	}
	public void setReserveJson(String reserveJson) {
		this.reserveJson = reserveJson;
	}
	public String getBusinessContext() {
		return businessContext;
	}
	public void setBusinessContext(String businessContext) {
		this.businessContext = businessContext;
	}
	public String getMerName() {
		return merName;
	}
	public void setMerName(String merName) {
		this.merName = merName;
	}
	
}
