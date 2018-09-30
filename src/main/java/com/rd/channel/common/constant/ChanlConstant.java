package com.rd.channel.common.constant;


public class ChanlConstant {
	
	/** 编码类型 UTF-8*/
	public final static String CHARSET_UTF8  = "UTF-8";
	/** 编码类型 GBK*/
	public final static String CHARSET_GBK  = "GBK";
	/** 签名方法*/
	public final static String ENCODE_TYPE_MD5  = "MD5";
	/** 成功*/
	public final static String RET_CODE_SUCCESS = "000000";
	/** 原订单不存在,无法发起退款*/
	public final static String RET_CODE_REFNULL = "TS0082";
	/** 请求第三方支付公司,交易超时*/
	public final static String RET_CODE_TIMEOUT = "S00024";
	/** 订单不存在*/
	public final static String RET_CODE_TRANSNULL = "UN0078";
	/** 退款金额不能大于原交易金额*/
	public final static String RET_CODE_REFUNDOVER ="OC0006";
	
	/** 商户信息为空*/
	public final static String RET_CODE_MchNull = "epcc_verfiyMchNull";
	
	/**交易代码：状态查询*/
	public final static String PAY_TYPE_ORDQUERY = "transOrdQuery";
	/**交易代码：下订单接口*/
	public final static String PAY_TYPE_PCINFOQUERY = "pcInfoQuery";
	/**交易代码：退款接口*/
	public final static String PAY_TYPE_REFUND = "refund";
	/**交易代码：商户新增*/
	public final static String PAY_TYPE_MERADD = "MInnerMerchantAdd_0";
	/**交易代码：商户修改*/
	public final static String PAY_TYPE_MERUPD = "MInnerMerchantUpd_0";
	/**交易代码：商户删除*/
	public final static String PAY_TYPE_MERDEL = "MInnerMerchantDel_0";
	
	
	/**交易渠道：微信*/
	public final static String TRANS_CHANNEL_WEINXIN = "tenpay";
	/**交易渠道：支付宝*/
	public final static String TRANS_CHANNEL_ALIPAY = "alipay";

	/** 交易成功*/
	public final static String TRANS_STATUS_00 = "00";
	/** 交易失败*/
	public final static String TRANS_STATUS_01 = "01";
	/** 已部分退款*/
	public final static String TRANS_STATUS_03 = "03";
	/** 已全额退款*/
	public final static String TRANS_STATUS_04 = "04";
	/** 待支付状态*/
	public final static String TRANS_STATUS_08 = "08";
	/** 超时状态*/
	public final static String TRANS_STATUS_99 = "99";
	
}
