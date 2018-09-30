package com.rd.channel.common.constant;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class ValueConstant {
	public final static int MAX_ORDER_NO_LENGTH = 16;
	public final static int MAX_ORDER_NO_LENGTH_YS = 32;
	public final static int MAX_ORDER_NO_LENGTH_KFT = 32;
	public final static double MAX_AMOUNT = 10000000;
	public final static double B2C_MINIMAL_AMOUNT = 10.0;
	public final static int MAX_URL_LENGTH = 256;
	public final static int MAX_URL_LENGTH_YS = 500;
	public final static String B2C_P_STATUS_ERROR = "0";
	public final static String VERSION = "1.0";
	public final static String SUCCESS = "SUCCESS";
	public final static String FAIL = "FAIL";
	
	/**交易类型*/
	/**快捷支付（网页跳转，不限卡号）*/
	public static final String TRANS_CODE_T01001 = "T01001";
	/**快捷支付（网页跳转，限制卡号）*/
	public static final String TRANS_CODE_T01002 = "T01002";
	/**快捷支付（API直联，短信认证）*/
	public static final String TRANS_CODE_T01011 = "T01011";
	/**快捷交易确认*/
	public static final String TRANS_CODE_T01012 = "T01012";
	/**订单查询请求（商户->支付平台*/
	public static final String TRANS_CODE_Q01001 = "Q01001";
	/**退款交易*/
	public static final String TRANS_CODE_T01021 = "T01021";
	/**退款交易查询*/
	public static final String TRANS_CODE_Q01021 = "Q01021";
	
	/** 卡bin查询*/
	public static final String TRANS_CODE_Q01031 = "Q01031";
	
	/**扫码支付（持卡人被扫）*/
	public static final String TRANS_CODE_T02001 = "T02001";
	/**扫码支付（持卡人主扫）*/
	public static final String TRANS_CODE_T02002 = "T02002";
	/**APP支付（手机插件支付）*/
	public static final String TRANS_CODE_T02011 = "T02011";
	/**微信公众号支付*/
	public static final String TRANS_CODE_T02021 = "T02021";
	/**商家固定二维码*/
	public static final String TRANS_CODE_T14122 = "T14122";
	/**扫码支付查询*/
	public static final String TRANS_CODE_Q02001 = "Q02001";
	
	/**商户可提现金额查询*/
	public static final String TRANS_CODE_Q02011 ="Q02011";
	/**商户提现请求*/
	public static final String TRANS_CODE_W01001 ="W01001";
	/**商户提现结果查询*/
	public static final String TRANS_CODE_Q02012 ="Q02012";
	/**银行列表查询*/
	public static final String TRANS_CODE_Q16120 ="Q16120";
	/**重新获取验证码*/
	public static final String TRANS_CODE_Q17120 = "Q17120";
	/**交易类型： 扫码支付(持卡人主扫)*/
	public final static String TXN_TYPE_SCAN_CODE = "AG0101";
	/**交易类型： 扫码支付(持卡人被扫)*/
	public final static String TXN_TYPE_PRES_CODE = "AG0102";
	/**交易类型：交易状态查询*/
	public final static String TXN_TYPE_PAY_QRY = "AG0201";
	/**交易类型：退款*/
	public final static String TXN_TYPE_REFUND = "AG0301";
	/**交易类型：退款查询*/
	public final static String TXN_TYPE_REFUND_QRY = "AG0302";
	
	/**未支付*/
	public final static String TRANS_STATUS_UNPAID = "0";//未支付
	/**成功*/
    public final static String TRANS_STATUS_SUCCESS = "1";//成功
    /**失败*/
    public final static String TRANS_STATUS_FAILURE = "2";//失败
    /**待确认*/
    public final static String TRANS_STATUS_COMFIRM = "3"; //待确认
    /**入账成功，出款失败*/
    public final static String TRANS_STATUS_OUTFAILURE = "4"; //入账成功，出账失败
    /**退款中*/
    public final static String TRANS_STATUS_REFUNDING = "5";//退款中
    /**已退款*/
    public final static String TRANS_STATUS_REFUNDED = "6"; //已退款
    /**已撤销*/
    public final static String TRANS_STATUS_CANCELED = "7"; //已撤销
    /**银联快捷支付（不限卡号）*/
    public final static String PAY_TYPE_1001 = "1001";
    /**银行快捷支付（限制卡号）*/
    public final static String PAY_TYPE_1002 = "1002";
    /**银行快捷支付（API直联）*/
    public final static String PAY_TYPE_1003 = "1004";
    /**微信持卡人主扫*/
    public final static String PAY_TYPE_1101 = "1101";
    /**微信持卡人被扫*/
    public final static String PAY_TYPE_1102 = "1102";
    /**微信公众号支付*/
    public final static String PAY_TYPE_1103 = "1103";
    /**微信APP支付*/
    public final static String PAY_TYPE_1104 = "1104";
    /**支付宝持卡人主扫*/
    public final static String PAY_TYPE_1201 = "1201";
    /**支付宝持卡人被扫*/
    public final static String PAY_TYPE_1202 = "1202";
    /**支付宝服务窗支付*/
    public final static String PAY_TYPE_1203 = "1203";
    /**支付宝APP支付*/
    public final static String PAY_TYPE_1204 = "1204";
    /**商户可提现金额查询*/
    public final static String PAY_TYPE_9901 = "9901";
    /**商户提现*/
    public final static String PAY_TYPE_9902 = "9902";
    
    public final static String CONV_TYPE_RETCD = "RETCD";
    
    public final static String NOTICE_RTN_MSG="SUCCESS";
    
    /**渠道号：畅捷*/
    public final static String CHAN_NO_CJ = "10000001";
    /**渠道号：银盛*/
    public final static String CHAN_NO_YS = "10000002";
    /**渠道号：畅捷支付*/
    public final static String CHAN_NO_CJZF = "10000003";
    /**渠道号：欧乐通*/
    public final static String CHAN_NO_OLT = "10000004";
    /**渠道号：摩宝*/
    public final static String CHAN_NO_MB = "10000005";
    /**渠道号：米刷*/
    public final static String CHAN_NO_MS = "10000006";
    /**渠道号：亿商城*/
    public final static String CHAN_NO_YSC = "10000007";
    /**渠道号：通邦*/
    public final static String CHAN_NO_TB = "10000008";
    /**渠道号：快付通*/
    public final static String CHAN_NO_KFT = "10000009";
    /**卡类型：借记*/
    public final static String CARD_TYPE_DC="DC";
    /**卡类型：贷记*/
    public final static String CARD_TYPE_CC="CC";
    /**清算时效 T0*/
    public final static String 	SETTLE_AGING_T0 = "T0";
    /**清算时效 T1*/
    public final static String 	SETTLE_AGING_T1 = "T1";
    /** 1 清算给商户 */
    public final static String T0_SETTLE_TYPE_1 = "1";
    /**2 清算给代理商*/
    public final static String T0_SETTLE_TYPE_2 = "2";
    /**2 清算给平台*/
    public final static String T0_SETTLE_TYPE_3 = "3";
    
    /** 1 清算给商户 */
    public final static String TN_SETTLE_TYPE_1 = "1";
    /**2 清算给代理商*/
    public final static String TN_SETTLE_TYPE_2 = "2";
    
	/*数字100*/
	public static final BigDecimal ONE_HUNDRED = new BigDecimal("100");
	
	/**支付方式*/
	public static Map<String,String> payTypeMap = null;
	static{
		payTypeMap = new HashMap<>();
		payTypeMap.put("1001", "银联快捷支付（不限卡号）");
		payTypeMap.put("1002", "银行快捷支付（限制卡号）");
		payTypeMap.put("1003", "银行快捷支付（API直联）");
		payTypeMap.put("1101", "微信持卡人主扫");
		payTypeMap.put("1102", "微信持卡人被扫");
		payTypeMap.put("1103", "微信公众号支付");
		payTypeMap.put("1104", "微信APP支付");
		payTypeMap.put("1201", "支付宝持卡人主扫");
		payTypeMap.put("1202", "支付宝持卡人被扫");
		payTypeMap.put("1203", "支付宝服务窗支付");
		payTypeMap.put("1204", "支付宝APP支付");
		payTypeMap.put("9901", "商户可提现金额查询");
		payTypeMap.put("9902", "商户提现");
	}
}
