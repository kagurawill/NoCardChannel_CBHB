package com.rd.channel.common.model.gateway;
/**
 * 快捷支付实体类（网页跳转）
 * @author caib
 * 2017-02-08
 */
public class ChanlPayReq extends ChanlBaseMsg{
	private String pageUrl;//支付完成后跳转到商户的页面
	private String bgUrl;//服务器接受支付结果的后台地址
	private String transactionId;//商户订单号
	private String orderAmount;//商户订单金额
	private String orderDesc;//订单描述
	private String timeout;//timeout
	private String cur;//cur
	private String productName;//商品名称
	private String productNum;//商品数量
	private String productDesc;//商品描述
	private String payType;//支付方式
	private String payBank;//银行代码
	private String bankAccountType;//付款方银行账户类型
	private String payerAcc;//付款账号
	private String feeRate;//费率
	
	private String settleAging;//清算时效 T0，T1
	private String payeeBankName;//出款银行名称
	private String payeeAcc;//出款银行卡号
	private String payeeIdNum;//出款身份证号
	private String payeeName;//出款银行卡户名
	private String signData;//签名
	
	public String getPageUrl() {
		return pageUrl;
	}
	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}
	public String getBgUrl() {
		return bgUrl;
	}
	public void setBgUrl(String bgUrl) {
		this.bgUrl = bgUrl;
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
	public String getOrderDesc() {
		return orderDesc;
	}
	public void setOrderDesc(String orderDesc) {
		this.orderDesc = orderDesc;
	}
	public String getTimeout() {
		return timeout;
	}
	public void setTimeout(String timeout) {
		this.timeout = timeout;
	}
	public String getCur() {
		return cur;
	}
	public void setCur(String cur) {
		this.cur = cur;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductNum() {
		return productNum;
	}
	public void setProductNum(String productNum) {
		this.productNum = productNum;
	}
	public String getProductDesc() {
		return productDesc;
	}
	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	
	public String getPayBank() {
		return payBank;
	}
	public void setPayBank(String payBank) {
		this.payBank = payBank;
	}
	public String getBankAccountType() {
		return bankAccountType;
	}
	public void setBankAccountType(String bankAccountType) {
		this.bankAccountType = bankAccountType;
	}
	public String getSignData() {
		return signData;
	}
	public void setSignData(String signData) {
		this.signData = signData;
	}
	public String getSettleAging() {
		return settleAging;
	}
	public void setSettleAging(String settleAging) {
		this.settleAging = settleAging;
	}
	public String getPayeeBankName() {
		return payeeBankName;
	}
	public void setPayeeBankName(String payeeBankName) {
		this.payeeBankName = payeeBankName;
	}
	public String getPayeeAcc() {
		return payeeAcc;
	}
	public void setPayeeAcc(String payeeAcc) {
		this.payeeAcc = payeeAcc;
	}
	public String getPayeeIdNum() {
		return payeeIdNum;
	}
	public void setPayeeIdNum(String payeeIdNum) {
		this.payeeIdNum = payeeIdNum;
	}
	public String getPayeeName() {
		return payeeName;
	}
	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
	}
	public String getPayerAcc() {
		return payerAcc;
	}
	public void setPayerAcc(String payerAcc) {
		this.payerAcc = payerAcc;
	}
	public String getFeeRate() {
		return feeRate;
	}
	public void setFeeRate(String feeRate) {
		this.feeRate = feeRate;
	}
	
}
