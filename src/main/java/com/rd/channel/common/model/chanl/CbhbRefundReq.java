package com.rd.channel.common.model.chanl;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class CbhbRefundReq {

	//@JsonProperty
	@JSONField(name="TransId")
	private String TransId;//交易代码
	@JSONField(name="Mch_id")
	private String Mch_id;//一级商户号
	@JSONField(name="Sub_mch_id")
	private String Sub_mch_id;//二级商户号
	@JSONField(name="OrgMerSeqNo")
	private String OrgMerSeqNo;//原商户流水号
	@JSONField(name="MerSeqNo")
	private String MerSeqNo;//商户交易流水号
	@JSONField(name="TransAmount")
	private String TransAmount;//交易总金额
	@JSONField(name="Subject")
	private String Subject;//商品名称
	@JSONField(name="MerTransDate")
	private String MerTransDate;//交易日期
	@JSONField(name="MerTransDateTime")
	private String MerTransDateTime;//交易时间
	@JSONField(name="CashierId")
	private String CashierId;//收银员
	@JSONField(name="MerchantUrl")
	private String MerchantUrl;//商户ＵＲＬ
	@JSONField(name="MerFrontURL")
	private String MerFrontURL;//商户ＵＲＬ
	public String getTransId() {
		return TransId;
	}
	public void setTransId(String transId) {
		TransId = transId;
	}
	public String getMch_id() {
		return Mch_id;
	}
	public void setMch_id(String mch_id) {
		Mch_id = mch_id;
	}
	public String getSub_mch_id() {
		return Sub_mch_id;
	}
	public void setSub_mch_id(String sub_mch_id) {
		Sub_mch_id = sub_mch_id;
	}
	public String getOrgMerSeqNo() {
		return OrgMerSeqNo;
	}
	public void setOrgMerSeqNo(String orgMerSeqNo) {
		OrgMerSeqNo = orgMerSeqNo;
	}
	public String getMerSeqNo() {
		return MerSeqNo;
	}
	public void setMerSeqNo(String merSeqNo) {
		MerSeqNo = merSeqNo;
	}
	public String getTransAmount() {
		return TransAmount;
	}
	public void setTransAmount(String transAmount) {
		TransAmount = transAmount;
	}
	public String getSubject() {
		return Subject;
	}
	public void setSubject(String subject) {
		Subject = subject;
	}
	public String getMerTransDate() {
		return MerTransDate;
	}
	public void setMerTransDate(String merTransDate) {
		MerTransDate = merTransDate;
	}
	public String getMerTransDateTime() {
		return MerTransDateTime;
	}
	public void setMerTransDateTime(String merTransDateTime) {
		MerTransDateTime = merTransDateTime;
	}
	public String getCashierId() {
		return CashierId;
	}
	public void setCashierId(String cashierId) {
		CashierId = cashierId;
	}
	public String getMerchantUrl() {
		return MerchantUrl;
	}
	public void setMerchantUrl(String merchantUrl) {
		MerchantUrl = merchantUrl;
	}
	public String getMerFrontURL() {
		return MerFrontURL;
	}
	public void setMerFrontURL(String merFrontURL) {
		MerFrontURL = merFrontURL;
	}


}
