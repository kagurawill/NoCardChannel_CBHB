package com.rd.channel.common.model.gateway;

public class TCOS_DZ_REQ extends BaseMsgReq{
	private String msgId; //请求流水号
	private String txnType; //业务种类
	private String fileName; //文件名
	private String filePath; //文件路径
	private String totCnt; //总笔数
	public String getMsgId() {
		return msgId;
	}
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	public String getTxnType() {
		return txnType;
	}
	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getTotCnt() {
		return totCnt;
	}
	public void setTotCnt(String totCnt) {
		this.totCnt = totCnt;
	}

	
}
