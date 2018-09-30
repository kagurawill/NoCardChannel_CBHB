package com.rd.channel.common.model.gateway;
/**
 * 扫码支付查询请求
 * @author caib
 *
 */
public class ChanlPayQryReq extends ChanlBaseMsg{
	private String transactionId;//商户订单号
	private String refTxnId;//原商户订单号
	private String signData;//签名
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	
	public String getRefTxnId() {
		return refTxnId;
	}
	public void setRefTxnId(String refTxnId) {
		this.refTxnId = refTxnId;
	}
	public String getSignData() {
		return signData;
	}
	public void setSignData(String signData) {
		this.signData = signData;
	}
	

}
