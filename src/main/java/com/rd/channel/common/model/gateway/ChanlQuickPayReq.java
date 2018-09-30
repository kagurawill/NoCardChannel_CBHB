package com.rd.channel.common.model.gateway;

public class ChanlQuickPayReq extends ChanlBaseMsg{
	private String transactionId;//商户交易号
	private String orderAmount;	//商户订单金额
	private String productName;	//商品名称  可以为空
	private String productNum;	//商品数量  可以为空
	private String productDesc;	//商品描述  可以为空
	private String orderDesc;//订单描述   可以为空
	private String bgUrl;	//服务器接受支付结果的后台地址  可以为空
	private String buyerIp;//用户在商户平台下单时候的ip地址
	private String payType;	//支付方式 
	
	private String cardType;//卡类型 借记：DC；贷记：CC 
	private String privateFlag;//对公对私  对公：B；对私：C
	private String bankCode; //银行编码
	private String payerName;//付款方名称
	private String payerAcc; //付款方银行卡号
	private String idNumber;//身份证号
	private String phoneNumber;//手机号
	private String expiryDate;//贷记卡有效期   可以为空
	private String cvv2;//CVV2码  可以为空
	private String userSign; //商户网站用户唯一标识  可以为空
	private String ext;//扩展字段  可以为空
	private String feeRate;//费率
	private String settleAging;//清算时效 T0，T1
	private String signData;	//签名
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
	public String getOrderDesc() {
		return orderDesc;
	}
	public void setOrderDesc(String orderDesc) {
		this.orderDesc = orderDesc;
	}
	public String getBgUrl() {
		return bgUrl;
	}
	public void setBgUrl(String bgUrl) {
		this.bgUrl = bgUrl;
	}
	public String getBuyerIp() {
		return buyerIp;
	}
	public void setBuyerIp(String buyerIp) {
		this.buyerIp = buyerIp;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public String getPrivateFlag() {
		return privateFlag;
	}
	public void setPrivateFlag(String privateFlag) {
		this.privateFlag = privateFlag;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public String getPayerName() {
		return payerName;
	}
	public void setPayerName(String payerName) {
		this.payerName = payerName;
	}
	public String getPayerAcc() {
		return payerAcc;
	}
	public void setPayerAcc(String payerAcc) {
		this.payerAcc = payerAcc;
	}
	public String getIdNumber() {
		return idNumber;
	}
	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}
	public String getCvv2() {
		return cvv2;
	}
	public void setCvv2(String cvv2) {
		this.cvv2 = cvv2;
	}
	public String getUserSign() {
		return userSign;
	}
	public void setUserSign(String userSign) {
		this.userSign = userSign;
	}
	public String getExt() {
		return ext;
	}
	public void setExt(String ext) {
		this.ext = ext;
	}
	public String getSignData() {
		return signData;
	}
	public void setSignData(String signData) {
		this.signData = signData;
	}
	public String getFeeRate() {
		return feeRate;
	}
	public void setFeeRate(String feeRate) {
		this.feeRate = feeRate;
	}
	public String getSettleAging() {
		return settleAging;
	}
	public void setSettleAging(String settleAging) {
		this.settleAging = settleAging;
	}
	
}
