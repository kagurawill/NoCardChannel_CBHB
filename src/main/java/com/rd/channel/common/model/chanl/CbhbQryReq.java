package com.rd.channel.common.model.chanl;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class CbhbQryReq {

	//@JsonProperty
	@JSONField(name="TransId")
	private String TransId;//交易代码
	@JSONField(name="MerSeqNo")
	private String MerSeqNo;//商户交易流水号
	@JSONField(name="Mch_id")
	private String Mch_id;
	@JsonIgnore
	public String getTransId() {
		return TransId;
	}
	@JsonIgnore
	public void setTransId(String transId) {
		TransId = transId;
	}
	@JsonIgnore
	public String getMerSeqNo() {
		return MerSeqNo;
	}
	@JsonIgnore
	public void setMerSeqNo(String merSeqNo) {
		MerSeqNo = merSeqNo;
	}
	public String getMch_id() {
		return Mch_id;
	}
	public void setMch_id(String mch_id) {
		Mch_id = mch_id;
	}
	
	
}
