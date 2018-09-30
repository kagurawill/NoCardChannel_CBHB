package com.rd.channel.common.model.gateway;

import java.util.HashMap;
import java.util.Map;

/**
 * 扫码支付应答（持卡人被扫）
 * @author caib
 *
 */
public class ChanlCodePayRsp extends ChanlBaseMsg{
	String transactionId;//商户订单号
	String orderAmount;//商户订单金额
	String payType;//支付方式          
	String payAmount;//订单实际支付金额
	String dealId;//支付平台交易序号  
	String dealTime;//支付平台交易时间	
	String transStatus;//订单状态   
	String retCode;//返回码            
	String retRemark;//返回码描述 
	String chanlRetCode;//上游返回码            
	String chanlRetRemark;//上游返回码描述 	
	String signData;//签名  
	
	public ChanlCodePayRsp() {
		super();
	}

	public String getPayType() {
		return payType;
	}


	public void setPayType(String payType) {
		this.payType = payType;
	}


	public String getTransactionId() {
		return transactionId;
	}


	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}


	public String getOrderAmount() {
		return orderAmount;
	}


	public void setOrderAmount(String orderAmount) {
		this.orderAmount = orderAmount;
	}


	public String getPayAmount() {
		return payAmount;
	}


	public void setPayAmount(String payAmount) {
		this.payAmount = payAmount;
	}


	public String getDealId() {
		return dealId;
	}


	public void setDealId(String dealId) {
		this.dealId = dealId;
	}


	public String getDealTime() {
		return dealTime;
	}


	public void setDealTime(String dealTime) {
		this.dealTime = dealTime;
	}


	public String getTransStatus() {
		return transStatus;
	}


	public void setTransStatus(String transStatus) {
		this.transStatus = transStatus;
	}


	public String getRetCode() {
		return retCode;
	}


	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}


	public String getRetRemark() {
		return retRemark;
	}

	public void setRetRemark(String retRemark) {
		this.retRemark = retRemark;
	}
	
	public String getChanlRetCode() {
		return chanlRetCode;
	}

	public void setChanlRetCode(String chanlRetCode) {
		this.chanlRetCode = chanlRetCode;
	}

	public String getChanlRetRemark() {
		return chanlRetRemark;
	}

	public void setChanlRetRemark(String chanlRetRemark) {
		this.chanlRetRemark = chanlRetRemark;
	}

	public String getSignData() {
		return signData;
	}
	public void setSignData(String signData) {
		this.signData = signData;
	}
    /**
     * 民生银行返回结果解析
     * @param result
     * @return
     */
    public static Map<String, String> toMap(String result){
    	Map<String, String> map = new HashMap<String, String>();
    	String[] vars = result.split("&");
    	for(String var:vars){
    		map.put(var.substring(0, var.indexOf("=")), var.substring(var.indexOf("=")+1));
    	}
    	return map;
    }
}
