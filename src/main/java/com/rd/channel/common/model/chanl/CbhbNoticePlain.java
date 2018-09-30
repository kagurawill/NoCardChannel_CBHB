package com.rd.channel.common.model.chanl;

public class CbhbNoticePlain {
	private String RespCode;//交易状态
	private String RespMsg;//描述信息
	private String TransSeqNo;//平台交易流水
	private String MerSeqNo;//商户流水号
	private String Mch_id;//一级商户号
	private String Sub_mch_id;//二级商户号
	private String TransAmount;//交易金额
	private String ClearDate;//银行日期
	private String TransDateTim;//交易时间
	private String TransStatus;//交易处理状态
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
	public String getTransSeqNo() {
		return TransSeqNo;
	}
	public void setTransSeqNo(String transSeqNo) {
		TransSeqNo = transSeqNo;
	}
	public String getMerSeqNo() {
		return MerSeqNo;
	}
	public void setMerSeqNo(String merSeqNo) {
		MerSeqNo = merSeqNo;
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
	public String getTransAmount() {
		return TransAmount;
	}
	public void setTransAmount(String transAmount) {
		TransAmount = transAmount;
	}
	public String getClearDate() {
		return ClearDate;
	}
	public void setClearDate(String clearDate) {
		ClearDate = clearDate;
	}
	public String getTransDateTim() {
		return TransDateTim;
	}
	public void setTransDateTim(String transDateTim) {
		TransDateTim = transDateTim;
	}
	public String getTransStatus() {
		return TransStatus;
	}
	public void setTransStatus(String transStatus) {
		TransStatus = transStatus;
	}
	
}
