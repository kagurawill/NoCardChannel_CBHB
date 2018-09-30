package com.rd.channel.common.model.chanl;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class CbhbMerchantReq {

	@JSONField(name="TransId")
	private String TransId;//交易代码
	@JSONField(name="MerId")
	private String MerId;//商户ID
	@JSONField(name="MerName")
	private String MerName;//商品名称
	@JSONField(name="MerBriefName")
	private String MerBriefName;//商户简称
	@JSONField(name="MerRegCapital")
	private String MerRegCapital;//商户注册资本
	@JSONField(name="MerBusinessLicenseId")
	private String MerBusinessLicenseId;//商户营业执照号
	@JSONField(name="MerTaxRegId")
	private String MerTaxRegId;//商户税务登记号
	@JSONField(name="MerBusinessOpenDate")
	private String MerBusinessOpenDate;//商户开业时间
	@JSONField(name="MerAddr")
	private String MerAddr;//商户办公地址
	@JSONField(name="respURL")
	private String respURL;//商户后台通知地址
	@JSONField(name="MerProperty")
	private String MerProperty;//商户性质
	@JSONField(name="MerCorporationName")
	private String MerCorporationName;//法人代表
	@JSONField(name="MerCorporationPaperType")
	private String MerCorporationPaperType;//法人代表证件类型
	@JSONField(name="MerCorporationPaperId")
	private String MerCorporationPaperId;//法人证件号码
	@JSONField(name="MerSiteName")
	private String MerSiteName;//网站名称
	@JSONField(name="MerDomain")
	private String MerDomain;//网站域名
	@JSONField(name="MerSiteIpPort")
	private String MerSiteIpPort;//网站Ip地址及端口
	@JSONField(name="MerSiteCode")
	private String MerSiteCode;//ICP许可证号
	@JSONField(name="MerWorkScop")
	private String MerWorkScop;//网站经营内容
	@JSONField(name="MerIfVirtualGoods")
	private String MerIfVirtualGoods;//是否有虚拟物品
	@JSONField(name="MerLinkManName")
	private String MerLinkManName;//业务联系人
	@JSONField(name="MerLinkManPhone")
	private String MerLinkManPhone;//业务联系人移动电话
	@JSONField(name="MerLinkManTel")
	private String MerLinkManTel;//业务联系人办公电话
	@JSONField(name="MerLinkManEmail")
	private String MerLinkManEmail;//业务联系人电子邮箱
	@JSONField(name="MerTechLinkName")
	private String MerTechLinkName;//技术联系人
	@JSONField(name="MerTechLinkManPhone")
	private String MerTechLinkManPhone;//技术联系人移动电话
	@JSONField(name="MerTechLinkManTel")
	private String MerTechLinkManTel;//技术联系人办公电话
	@JSONField(name="MerTechLinkManEmai")
	private String MerTechLinkManEmai;//技术联系人电子邮箱
	@JSONField(name="MerFax")
	private String MerFax;//商户传真号码
	//@JSONField(name="MerCorporationName")
	//private String MerCorporationName;//结算账户名字
	@JSONField(name="MerSettAcctNo")
	private String MerSettAcctNo;//结算账户号
	@JSONField(name="MerStatus")
	private String MerStatus;//商户状态
	@JSONField(name="MerLevel")
	private String MerLevel;//商户等级
	@JSONField(name="MerTransType")
	private String MerTransType;//开通支付类型
	@JSONField(name="MER_OPENDATE")
	private String MER_OPENDATE;//商户开通日期
	@JSONField(name="MerServiceType")
	private String MerServiceType;//商户服务类型
	@JSONField(name="MerSpecialLicense")
	private String MerSpecialLicense;//特种经营许可证
	@JSONField(name="MerLicense")
	private String MerLicense;//许可证号
	@JSONField(name="STOREID")
	private String STOREID;//门店号
	@JSONField(name="BOOTHNO")
	private String BOOTHNO;//摊位号
	@JSONField(name="ERPMERCHANTID")
	private String ERPMERCHANTID;//ERP商户号
	@JSONField(name="ProFtAcctNo")
	private String ProFtAcctNo;//分润账号
	@JSONField(name="ProFtAcctName")
	private String ProFtAcctName;//分润账户名称
	@JSONField(name="ProFtAcctNoType")
	private String ProFtAcctNoType;//分润行类型
	@JSONField(name="ProSettAcctBankNo")
	private String ProSettAcctBankNo;//分润行号
	@JSONField(name="ProSuperNo")
	private String ProSuperNo;//分润网银账号
	@JSONField(name="IsBank")
	private String IsBank;//结算行类型
	@JSONField(name="MerSettAcctBankNo")
	private String MerSettAcctBankNo;//结算行大小额行号
	@JSONField(name="SuperNo")
	private String SuperNo;//结算超级网银账号
	@JSONField(name="MerSettType")
	private String MerSettType;//结算类型
	@JSONField(name="MerSettMode")
	private String MerSettMode;//结算方式
	@JSONField(name="MerSettPeriod")
	private String MerSettPeriod;//结算周期
	@JSONField(name="MerFeeSettPeriod")
	private String MerFeeSettPeriod;//手续费结算周期
	@JSONField(name="MerFeeMode")
	private String MerFeeMode;//手续费收取方式
	@JSONField(name="MerFeeAmt")
	private String MerFeeAmt;//手续费金额
	@JSONField(name="IsSecondMer")
	private String IsSecondMer;//是否有父级商户
	@JSONField(name="ParentdMerNo")
	private String ParentdMerNo;//上级商户商户号
	@JSONField(name="MerFeeReturnFlag")
	private String MerFeeReturnFlag;//退货是否退手续费
	@JSONField(name="MerFeeAcctName")
	private String MerFeeAcctName;//手续费账户名称
	@JSONField(name="MerFeeAcctNo")
	private String MerFeeAcctNo;//手续费账户号
	@JSONField(name="MCH_TOKEN")
	private String MCH_TOKEN;//参考号
	public String getTransId() {
		return TransId;
	}
	public void setTransId(String transId) {
		TransId = transId;
	}
	public String getMerId() {
		return MerId;
	}
	public void setMerId(String merId) {
		MerId = merId;
	}
	public String getMerName() {
		return MerName;
	}
	public void setMerName(String merName) {
		MerName = merName;
	}
	public String getMerBriefName() {
		return MerBriefName;
	}
	public void setMerBriefName(String merBriefName) {
		MerBriefName = merBriefName;
	}
	public String getMerRegCapital() {
		return MerRegCapital;
	}
	public void setMerRegCapital(String merRegCapital) {
		MerRegCapital = merRegCapital;
	}
	public String getMerBusinessLicenseId() {
		return MerBusinessLicenseId;
	}
	public void setMerBusinessLicenseId(String merBusinessLicenseId) {
		MerBusinessLicenseId = merBusinessLicenseId;
	}
	public String getMerTaxRegId() {
		return MerTaxRegId;
	}
	public void setMerTaxRegId(String merTaxRegId) {
		MerTaxRegId = merTaxRegId;
	}
	public String getMerBusinessOpenDate() {
		return MerBusinessOpenDate;
	}
	public void setMerBusinessOpenDate(String merBusinessOpenDate) {
		MerBusinessOpenDate = merBusinessOpenDate;
	}
	public String getMerAddr() {
		return MerAddr;
	}
	public void setMerAddr(String merAddr) {
		MerAddr = merAddr;
	}
	public String getRespURL() {
		return respURL;
	}
	public void setRespURL(String respURL) {
		this.respURL = respURL;
	}
	public String getMerProperty() {
		return MerProperty;
	}
	public void setMerProperty(String merProperty) {
		MerProperty = merProperty;
	}
	public String getMerCorporationName() {
		return MerCorporationName;
	}
	public void setMerCorporationName(String merCorporationName) {
		MerCorporationName = merCorporationName;
	}
	public String getMerCorporationPaperType() {
		return MerCorporationPaperType;
	}
	public void setMerCorporationPaperType(String merCorporationPaperType) {
		MerCorporationPaperType = merCorporationPaperType;
	}
	public String getMerCorporationPaperId() {
		return MerCorporationPaperId;
	}
	public void setMerCorporationPaperId(String merCorporationPaperId) {
		MerCorporationPaperId = merCorporationPaperId;
	}
	public String getMerSiteName() {
		return MerSiteName;
	}
	public void setMerSiteName(String merSiteName) {
		MerSiteName = merSiteName;
	}
	public String getMerDomain() {
		return MerDomain;
	}
	public void setMerDomain(String merDomain) {
		MerDomain = merDomain;
	}
	public String getMerSiteIpPort() {
		return MerSiteIpPort;
	}
	public void setMerSiteIpPort(String merSiteIpPort) {
		MerSiteIpPort = merSiteIpPort;
	}
	public String getMerSiteCode() {
		return MerSiteCode;
	}
	public void setMerSiteCode(String merSiteCode) {
		MerSiteCode = merSiteCode;
	}
	public String getMerWorkScop() {
		return MerWorkScop;
	}
	public void setMerWorkScop(String merWorkScop) {
		MerWorkScop = merWorkScop;
	}
	public String getMerIfVirtualGoods() {
		return MerIfVirtualGoods;
	}
	public void setMerIfVirtualGoods(String merIfVirtualGoods) {
		MerIfVirtualGoods = merIfVirtualGoods;
	}
	public String getMerLinkManName() {
		return MerLinkManName;
	}
	public void setMerLinkManName(String merLinkManName) {
		MerLinkManName = merLinkManName;
	}
	public String getMerLinkManPhone() {
		return MerLinkManPhone;
	}
	public void setMerLinkManPhone(String merLinkManPhone) {
		MerLinkManPhone = merLinkManPhone;
	}
	public String getMerLinkManTel() {
		return MerLinkManTel;
	}
	public void setMerLinkManTel(String merLinkManTel) {
		MerLinkManTel = merLinkManTel;
	}
	public String getMerLinkManEmail() {
		return MerLinkManEmail;
	}
	public void setMerLinkManEmail(String merLinkManEmail) {
		MerLinkManEmail = merLinkManEmail;
	}
	public String getMerTechLinkName() {
		return MerTechLinkName;
	}
	public void setMerTechLinkName(String merTechLinkName) {
		MerTechLinkName = merTechLinkName;
	}
	public String getMerTechLinkManPhone() {
		return MerTechLinkManPhone;
	}
	public void setMerTechLinkManPhone(String merTechLinkManPhone) {
		MerTechLinkManPhone = merTechLinkManPhone;
	}
	public String getMerTechLinkManTel() {
		return MerTechLinkManTel;
	}
	public void setMerTechLinkManTel(String merTechLinkManTel) {
		MerTechLinkManTel = merTechLinkManTel;
	}
	public String getMerTechLinkManEmai() {
		return MerTechLinkManEmai;
	}
	public void setMerTechLinkManEmai(String merTechLinkManEmai) {
		MerTechLinkManEmai = merTechLinkManEmai;
	}
	public String getMerFax() {
		return MerFax;
	}
	public void setMerFax(String merFax) {
		MerFax = merFax;
	}
	public String getMerSettAcctNo() {
		return MerSettAcctNo;
	}
	public void setMerSettAcctNo(String merSettAcctNo) {
		MerSettAcctNo = merSettAcctNo;
	}
	public String getMerStatus() {
		return MerStatus;
	}
	public void setMerStatus(String merStatus) {
		MerStatus = merStatus;
	}
	public String getMerLevel() {
		return MerLevel;
	}
	public void setMerLevel(String merLevel) {
		MerLevel = merLevel;
	}
	public String getMerTransType() {
		return MerTransType;
	}
	public void setMerTransType(String merTransType) {
		MerTransType = merTransType;
	}
	public String getMER_OPENDATE() {
		return MER_OPENDATE;
	}
	public void setMER_OPENDATE(String mER_OPENDATE) {
		MER_OPENDATE = mER_OPENDATE;
	}
	public String getMerServiceType() {
		return MerServiceType;
	}
	public void setMerServiceType(String merServiceType) {
		MerServiceType = merServiceType;
	}
	public String getMerSpecialLicense() {
		return MerSpecialLicense;
	}
	public void setMerSpecialLicense(String merSpecialLicense) {
		MerSpecialLicense = merSpecialLicense;
	}
	public String getMerLicense() {
		return MerLicense;
	}
	public void setMerLicense(String merLicense) {
		MerLicense = merLicense;
	}
	public String getSTOREID() {
		return STOREID;
	}
	public void setSTOREID(String sTOREID) {
		STOREID = sTOREID;
	}
	public String getBOOTHNO() {
		return BOOTHNO;
	}
	public void setBOOTHNO(String bOOTHNO) {
		BOOTHNO = bOOTHNO;
	}
	public String getERPMERCHANTID() {
		return ERPMERCHANTID;
	}
	public void setERPMERCHANTID(String eRPMERCHANTID) {
		ERPMERCHANTID = eRPMERCHANTID;
	}
	public String getProFtAcctNo() {
		return ProFtAcctNo;
	}
	public void setProFtAcctNo(String proFtAcctNo) {
		ProFtAcctNo = proFtAcctNo;
	}
	public String getProFtAcctName() {
		return ProFtAcctName;
	}
	public void setProFtAcctName(String proFtAcctName) {
		ProFtAcctName = proFtAcctName;
	}
	public String getProFtAcctNoType() {
		return ProFtAcctNoType;
	}
	public void setProFtAcctNoType(String proFtAcctNoType) {
		ProFtAcctNoType = proFtAcctNoType;
	}
	public String getProSettAcctBankNo() {
		return ProSettAcctBankNo;
	}
	public void setProSettAcctBankNo(String proSettAcctBankNo) {
		ProSettAcctBankNo = proSettAcctBankNo;
	}
	public String getProSuperNo() {
		return ProSuperNo;
	}
	public void setProSuperNo(String proSuperNo) {
		ProSuperNo = proSuperNo;
	}
	public String getIsBank() {
		return IsBank;
	}
	public void setIsBank(String isBank) {
		IsBank = isBank;
	}
	public String getMerSettAcctBankNo() {
		return MerSettAcctBankNo;
	}
	public void setMerSettAcctBankNo(String merSettAcctBankNo) {
		MerSettAcctBankNo = merSettAcctBankNo;
	}
	public String getSuperNo() {
		return SuperNo;
	}
	public void setSuperNo(String superNo) {
		SuperNo = superNo;
	}
	public String getMerSettType() {
		return MerSettType;
	}
	public void setMerSettType(String merSettType) {
		MerSettType = merSettType;
	}
	public String getMerSettMode() {
		return MerSettMode;
	}
	public void setMerSettMode(String merSettMode) {
		MerSettMode = merSettMode;
	}
	public String getMerSettPeriod() {
		return MerSettPeriod;
	}
	public void setMerSettPeriod(String merSettPeriod) {
		MerSettPeriod = merSettPeriod;
	}
	public String getMerFeeSettPeriod() {
		return MerFeeSettPeriod;
	}
	public void setMerFeeSettPeriod(String merFeeSettPeriod) {
		MerFeeSettPeriod = merFeeSettPeriod;
	}
	public String getMerFeeMode() {
		return MerFeeMode;
	}
	public void setMerFeeMode(String merFeeMode) {
		MerFeeMode = merFeeMode;
	}
	public String getMerFeeAmt() {
		return MerFeeAmt;
	}
	public void setMerFeeAmt(String merFeeAmt) {
		MerFeeAmt = merFeeAmt;
	}
	public String getIsSecondMer() {
		return IsSecondMer;
	}
	public void setIsSecondMer(String isSecondMer) {
		IsSecondMer = isSecondMer;
	}
	public String getParentdMerNo() {
		return ParentdMerNo;
	}
	public void setParentdMerNo(String parentdMerNo) {
		ParentdMerNo = parentdMerNo;
	}
	public String getMerFeeReturnFlag() {
		return MerFeeReturnFlag;
	}
	public void setMerFeeReturnFlag(String merFeeReturnFlag) {
		MerFeeReturnFlag = merFeeReturnFlag;
	}
	public String getMerFeeAcctName() {
		return MerFeeAcctName;
	}
	public void setMerFeeAcctName(String merFeeAcctName) {
		MerFeeAcctName = merFeeAcctName;
	}
	public String getMerFeeAcctNo() {
		return MerFeeAcctNo;
	}
	public void setMerFeeAcctNo(String merFeeAcctNo) {
		MerFeeAcctNo = merFeeAcctNo;
	}
	public String getMCH_TOKEN() {
		return MCH_TOKEN;
	}
	public void setMCH_TOKEN(String mCH_TOKEN) {
		MCH_TOKEN = mCH_TOKEN;
	}

}
