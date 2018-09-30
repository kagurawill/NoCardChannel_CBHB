package com.rd.channel.common.model.chanl;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CbhbQrCodeReq {

	@JSONField(name="TransId")
	private String TransId;//交易代码
	@JSONField(name="Mch_id")
	private String Mch_id;//商户代码
	@JSONField(name="Mch_name")
	private String Mch_name;//商户名称
	@JSONField(name="Subject")
	private String Subject;//商品名称
	@JSONField(name="MerSeqNo")
	private String MerSeqNo;//交易流水号
	@JSONField(name="TransAmt")
	private String TransAmt;//交易金额
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
	public String getMch_name() {
		return Mch_name;
	}
	public void setMch_name(String mch_name) {
		Mch_name = mch_name;
	}
	public String getSubject() {
		return Subject;
	}
	public void setSubject(String subject) {
		Subject = subject;
	}
	public String getMerSeqNo() {
		return MerSeqNo;
	}
	public void setMerSeqNo(String merSeqNo) {
		MerSeqNo = merSeqNo;
	}
	public String getTransAmt() {
		return TransAmt;
	}
	public void setTransAmt(String transAmt) {
		TransAmt = transAmt;
	}

	
}
