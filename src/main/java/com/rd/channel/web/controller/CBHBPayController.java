package com.rd.channel.web.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rd.channel.common.constant.ChanlConstant;
import com.rd.channel.common.constant.ReturnCdConstant;
import com.rd.channel.common.constant.ValueConstant;
import com.rd.channel.common.model.chanl.CbhbNotice;
import com.rd.channel.common.model.gateway.BaseMsgReq;
import com.rd.channel.common.model.gateway.BaseMsgRsp;
import com.rd.channel.common.model.gateway.ChanlCodePayRsp;
import com.rd.channel.common.model.gateway.ChanlPayReq;
import com.rd.channel.common.model.gateway.UM_PAY_QRY_RSP;
import com.rd.channel.common.model.gateway.UM_REFUND_QRY_RSP;
import com.rd.channel.common.model.gateway.UM_REFUND_RSP;
import com.rd.channel.common.model.gateway.UM_SCAN_CODE_RSP;
import com.rd.channel.common.util.MerchantUtil;
import com.rd.channel.common.util.TimeUtil;
import com.rd.channel.service.CbhbPayServiceI;
import com.rd.channel.task.DZTask;

@Controller
@RequestMapping("/cbhbPay")
public class CBHBPayController {
	private static final Logger logger = LoggerFactory
			.getLogger(CBHBPayController.class);
	@Value("${channel.merchant.md5Key}")
	private String md5Key;// md5签名密钥
	@Autowired
	private CbhbPayServiceI payService;
	@Autowired
	private DZTask dzTask;;

	@RequestMapping(value = "/payProcess")
	public ModelAndView payProcess(BaseMsgReq merchantBaseMsg,
			HttpServletResponse response) throws Exception {
		logger.info("[渤海银行聚合-报文统一入口点]收到网关请求报文");
		logger.info("[渤海银行聚合-报文统一入口点]请求报文：{}",
				JSON.toJSONString(merchantBaseMsg));
		String errorCode = null;
		String errorMsg = null;
		String businessContext = merchantBaseMsg.getBizContext();
		/**
		 * 1.验签
		 */
		try {
			boolean flag = MerchantUtil.verify(businessContext, merchantBaseMsg.getSign(), md5Key, merchantBaseMsg.getSignType(), merchantBaseMsg.getCharset());
			if (!flag) {
				logger.info("[渤海银行聚合-报文统一入口点]业务报文验签失败！");
				errorCode = ReturnCdConstant.RET_CD_0006;
				errorMsg = "业务报文验签失败";
				return processError(merchantBaseMsg, errorCode, errorMsg,
						response);
			}
		} catch (Exception e) {
			logger.error("[渤海银行聚合-报文统一入口点]业务报文验签失败！", e);
			errorCode = ReturnCdConstant.RET_CD_0006;
			errorMsg = "业务报文验签失败";
			return processError(merchantBaseMsg, errorCode, errorMsg, response);
		}
		/**
		 * 2.根据交易类型分发处理请求报文
		 */
		String transCode = merchantBaseMsg.getTxnType();
		String processRsp = null;
		String merId = merchantBaseMsg.getOrgId();
		if (ValueConstant.TXN_TYPE_PRES_CODE.equals(transCode)) {
			/** 扫码支付（持卡人被扫） */
			logger.info("[渤海银行聚合-报文统一入口点]收到商户[{}]的扫码支付（持卡人被扫）[{}]请求。", merId,
					transCode);
			try {
				processRsp = payService.qrCodePay(merchantBaseMsg);
			} catch (Exception e) {
				logger.error("[交易网关统一入口点]处理扫码支付（持卡人被扫）请求时异常！", e);
				errorCode = ReturnCdConstant.RET_CD_0004;
				errorMsg = "处理扫码支付（持卡人被扫）请求时异常";
				return processError(merchantBaseMsg, errorCode, errorMsg,
						response);
			}
		} else if (ValueConstant.TXN_TYPE_SCAN_CODE.equals(transCode)) {
			/** 扫码支付（持卡人主扫） */
			logger.info("[渤海银行聚合-报文统一入口点]收到商户[{}]的扫码支付（持卡人主扫）[{}]请求。", merId,
					transCode);
			try {
				processRsp = payService.quickPayH5(merchantBaseMsg);
			} catch (Exception e) {
				logger.error("[交易网关统一入口点]处理扫码支付（持卡人主扫）请求时异常！", e);
				errorCode = ReturnCdConstant.RET_CD_0004;
				errorMsg = "处理扫码支付（持卡人主扫）请求时异常";
				return processError(merchantBaseMsg, errorCode, errorMsg,
						response);
			}
		} else if (ValueConstant.TXN_TYPE_PAY_QRY.equals(transCode)) {
			/** 扫码支付查询 */
			logger.info("[渤海银行聚合-报文统一入口点]收到商户[{}]的扫码支付查询[{}]请求。", merId,
					transCode);
			try {
				processRsp = payService.qrCodeQuery(merchantBaseMsg);
			} catch (Exception e) {
				logger.error("[交易网关统一入口点]处理扫码支付查询请求时异常！", e);
				errorCode = ReturnCdConstant.RET_CD_0004;
				errorMsg = "处理扫码支付查询请求时异常";
				return processError(merchantBaseMsg, errorCode, errorMsg,
						response);
			}
		} else if (ValueConstant.TXN_TYPE_REFUND.equals(transCode)) {
			/** 订单退款申请 */
			logger.info("[渤海银行聚合-报文统一入口点]收到商户[{}]的订单退款申请[{}]请求。", merId,
					transCode);
			try {
				processRsp = payService.refund(merchantBaseMsg);
			} catch (Exception e) {
				logger.error("[交易网关统一入口点]处理订单退款申请请求时异常！", e);
				errorCode = ReturnCdConstant.RET_CD_0004;
				errorMsg = "处理订单退款申请请求时异常";
				return processError(merchantBaseMsg, errorCode, errorMsg,
						response);
			}
		} else if (ValueConstant.TXN_TYPE_REFUND_QRY.equals(transCode)) {
			/** 订单退款查询 */
			logger.info("[渤海银行聚合-报文统一入口点]收到商户[{}]的订单退款查询[{}]请求。", merId,
					transCode);
			try {
				processRsp = payService.refundQry(merchantBaseMsg);
			} catch (Exception e) {
				logger.error("[交易网关统一入口点]处理订单退款查询请求时异常！", e);
				errorCode = ReturnCdConstant.RET_CD_0004;
				errorMsg = "处理订单退款查询请求时异常";
				return processError(merchantBaseMsg, errorCode, errorMsg,
						response);
			}
		} else if (ValueConstant.TRANS_CODE_T01001.equals(transCode)) {
			/** 网银支付（PC页面跳转） */
			logger.info("[渤海银行聚合-报文统一入口点]收到商户[{}]的网银支付申请[{}]请求。", merId,
					transCode);
			try {
				String url = payService.quickPay(merchantBaseMsg);
				response.sendRedirect(url);
				return null;
			} catch (Exception e) {
				logger.error("[交易网关统一入口点]处理网银支付请求时异常！", e);
				errorCode = ReturnCdConstant.RET_CD_0004;
				errorMsg = "处理网银支付请求时异常";
				return processError(merchantBaseMsg, errorCode, errorMsg,
						response);
			}
		}/* else if (ValueConstant.TRANS_CODE_T01002.equals(transCode)) {
			*//** 网银支付（H5页面跳转） *//*
			logger.info("[渤海银行聚合-报文统一入口点]收到商户[{}]的网银支付申请[{}]请求。", merId,
					transCode);
			try {
				response.sendRedirect(payService.quickPayH5(merchantBaseMsg));
				return null;
			} catch (Exception e) {
				logger.error("[交易网关统一入口点]处理网银支付请求时异常！", e);
				errorCode = ReturnCdConstant.RET_CD_0004;
				errorMsg = "处理网银支付请求时异常";
				return processError(merchantBaseMsg, errorCode, errorMsg,
						response);
			}
		} */else if (ValueConstant.TRANS_CODE_Q01001.equals(transCode)) {
			/** 订单查询请求 */
			logger.info("[渤海银行聚合-报文统一入口点]收到商户[{}]的订单查询[{}]请求。", merId,
					transCode);
			try {
				processRsp = payService.query(merchantBaseMsg);
			} catch (Exception e) {
				logger.error("[交易网关统一入口点]处理订单查询请求时异常！", e);
				errorCode = ReturnCdConstant.RET_CD_0004;
				errorMsg = "处理订单查询请求时异常";
				return processError(merchantBaseMsg, errorCode, errorMsg,
						response);
			}
		} else {
			/** 未知的交易类型 */
			logger.error("[渤海银行聚合-交易网关统一入口点]未知的交易类型[{}]", transCode);
			errorCode = ReturnCdConstant.RET_CD_0006;
			errorMsg = "不支持[" + transCode + "]类型的交易请求";
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("retCode", errorCode);
			jsonObject.put("retMsg", errorMsg);
		}
		if (StringUtils.isNotBlank(processRsp)) {
			response.getWriter().write(processRsp);
			response.getWriter().flush();
		}
		return null;
	}

	/**
	 * 构造异常回应
	 * 
	 * @param merchantBaseMsg
	 * @param errorCode
	 * @param errorMsg
	 * @param response
	 * @param merchant
	 * @return
	 * @throws Exception
	 */
	private ModelAndView processError(BaseMsgReq merchantBaseMsg,
			String errorCode, String errorMsg, HttpServletResponse response)
			throws Exception {
		String transCode = merchantBaseMsg.getTxnType();
		String processRsp = new String();
		if (ValueConstant.TXN_TYPE_PRES_CODE.equals(transCode)) {
			/** 扫码支付（持卡人被扫）请求 */
			BaseMsgRsp baseMsgRsp = new BaseMsgRsp();
			ChanlCodePayRsp rsp = new ChanlCodePayRsp();
			baseMsgRsp.setCode(ValueConstant.SUCCESS);
			rsp.setRetCode(errorCode);
			rsp.setRetRemark(errorMsg);
			rsp.setTransStatus(ValueConstant.TRANS_STATUS_FAILURE);
			payService.buildQrCodePayRspMsg(baseMsgRsp, rsp);
		} else if (ValueConstant.TXN_TYPE_SCAN_CODE.equals(transCode)) {
			/** 扫码支付（持卡人主扫）请求 */
			BaseMsgRsp baseMsgRsp = new BaseMsgRsp();
			UM_SCAN_CODE_RSP rsp = new UM_SCAN_CODE_RSP();
			baseMsgRsp.setCode(ValueConstant.SUCCESS);
			rsp.setRetCode(errorCode);
			rsp.setRetMsg(errorMsg);
			payService.buildQrCodeGenerateOrderRspMsg(baseMsgRsp, rsp);
		} else if (ValueConstant.TXN_TYPE_PAY_QRY.equals(transCode)) {
			/** 扫码支付查询请求 */
			BaseMsgRsp baseMsgRsp = new BaseMsgRsp();
			UM_PAY_QRY_RSP rsp = new UM_PAY_QRY_RSP();
			baseMsgRsp.setCode(ValueConstant.SUCCESS);
			rsp.setRetCode(errorCode);
			rsp.setRetMsg(errorMsg);
			payService.buildQrCodeQueryRspMsg(baseMsgRsp, rsp);
		} else if (ValueConstant.TXN_TYPE_REFUND.equals(transCode)) {
			/** 退款请求 */
			BaseMsgRsp baseMsgRsp = new BaseMsgRsp();
			UM_REFUND_RSP rsp = new UM_REFUND_RSP();
			baseMsgRsp.setCode(ValueConstant.SUCCESS);
			rsp.setRetCode(errorCode);
			rsp.setRetMsg(errorMsg);
			payService.buildRefundRspMsg(baseMsgRsp, rsp);
		} else if (ValueConstant.TXN_TYPE_REFUND_QRY.equals(transCode)) {
			/** 退款查询请求 */
			BaseMsgRsp baseMsgRsp = new BaseMsgRsp();
			UM_REFUND_QRY_RSP rsp = new UM_REFUND_QRY_RSP();
			baseMsgRsp.setCode(ValueConstant.SUCCESS);
			rsp.setRetCode(errorCode);
			rsp.setRetMsg(errorMsg);
			payService.buildRefundQryRspMsg(baseMsgRsp, rsp);
		} else {
			BaseMsgRsp baseMsgRsp = new BaseMsgRsp();
			UM_REFUND_QRY_RSP rsp = new UM_REFUND_QRY_RSP();
			baseMsgRsp.setCode(ValueConstant.FAIL);
			payService.buildRefundQryRspMsg(baseMsgRsp, rsp);
		}
		if (StringUtils.isNotBlank(processRsp)) {
			response.getWriter().write(processRsp);
			response.getWriter().flush();
		}
		return null;
	}

	/**
	 * 订单结果异步通知处理
	 * 
	 * @param chanlNotice
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/NotifyUrl")
	public void noticeFromCBHB(CbhbNotice cbhbNotice,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		BufferedReader in = new BufferedReader(new InputStreamReader(
				request.getInputStream()));
		StringBuilder sb = new StringBuilder();
		String line = null;
		while ((line = in.readLine()) != null) {
			sb.append(line);
		}
		payService.noticeProcess(sb.toString());
		/*Map<String, String> params = new TreeMap<String, String>();

		// 取出所有参数是为了验证签名
		Enumeration<String> parameterNames = request.getParameterNames();

		while (parameterNames.hasMoreElements()) {
			String parameterName = parameterNames.nextElement();

			params.put(parameterName, request.getParameter(parameterName));
		}
		logger.info("模拟下游接收[{}]", JSON.toJSONString(params));
		logger.info("[渤海银行聚合-订单结果异步通知]接收到订单结果异步通知报文");
		boolean isTrue = payService.noticeProcess(cbhbNotice);
		if (isTrue) {
			PrintWriter out = response.getWriter();
			out.print("notify_success");
			out.flush();
			out.close();
		}*/
	}

	@RequestMapping(value = "/result")
	public ModelAndView result(String status, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("[异步通知-公众号支付]结果页面（页面跳转）");
		Map<String, String> params = new TreeMap<String, String>();
		if (StringUtils.isBlank(status)) {
			params.put("retCode", ReturnCdConstant.RET_CD_0004);
			params.put("retMsg", "其他错误");
		}
		switch (status) {
		case ChanlConstant.TRANS_STATUS_00:
			params.put("retCode", ReturnCdConstant.RET_CD_0000);
			params.put("retMsg", "交易完成");
			break;
		case ChanlConstant.TRANS_STATUS_01:
			params.put("retCode", ReturnCdConstant.RET_CD_0030);
			params.put("retMsg", "待支付");
			break;
		default:
			params.put("retCode", ReturnCdConstant.RET_CD_0004);
			params.put("retMsg", "其他错误");
			break;
		}
		return new ModelAndView("/result", params);
	}

	@ResponseBody
	@RequestMapping(value = "/dz")
	public String dz(HttpServletRequest request) throws Exception {
		logger.info("发起对账");
		String dateStr = request.getParameter("date");
		Date date = new SimpleDateFormat("yyyyMMdd").parse(dateStr);
		dzTask.downloadBill(TimeUtil.getSpecifiedDayBefore(date, "yyyy-MM-dd"),
				"gzip");
		return "已发起对账";
	}

	@ResponseBody
	@RequestMapping(value = "/test")
	public void test(HttpServletRequest request) throws Exception {
		logger.info("模拟下游接收");
		Map<String, String> params = new TreeMap<String, String>();

		// 取出所有参数是为了验证签名
		Enumeration<String> parameterNames = request.getParameterNames();

		while (parameterNames.hasMoreElements()) {
			String parameterName = parameterNames.nextElement();

			params.put(parameterName, request.getParameter(parameterName));
		}
		logger.info("模拟下游接收[{}]", JSON.toJSONString(params));
	}

	//@RequestMapping(value = "/errTest")
	public ModelAndView errTest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("[异步通知-模拟公众号支付]错误页面（页面跳转）");
		return new ModelAndView("/error", null);
	}

	// 网银支付模拟发起，直接发往前置
	@RequestMapping(value = "/testWYFep")
	public ModelAndView testWYFep(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("[异步通知-模拟收银台发起]模拟报文");
		ChanlPayReq req = new ChanlPayReq();
		req.setTransactionId(request.getParameter("id"));
		req.setOrderAmount("0.01");
		req.setPayType(ValueConstant.PAY_TYPE_1002);
		req.setBankAccountType("DC");
		req.setPayBank("1004");
		req.setProductDesc("test测试");
		req.setOrderDesc("ord测试");
		req.setProductName("pro测试");
		req.setPageUrl("http://183.60.125.17:19805/gcPay/resultPage");
		/* 签名 */
		String bizContext = JSON.toJSONString(req);
		String sign = MerchantUtil.sign(bizContext, md5Key,
				MerchantUtil.SIGNTYPE_MD5, "UTF-8");
		/* 组装报文 */
		BaseMsgReq merchantBaseMsg = new BaseMsgReq();
		merchantBaseMsg.setVersion("1.0");
		merchantBaseMsg.setOrgId("010020170908154002");
		merchantBaseMsg.setOrgName("广东紫银网络科技有限公司");
		merchantBaseMsg.setOrderTime(new SimpleDateFormat("yyyyMMddHHmmss")
				.format(new Date()));
		merchantBaseMsg.setTxnType(ValueConstant.TRANS_CODE_T01002);
		merchantBaseMsg.setSignType(MerchantUtil.SIGNTYPE_MD5);
		merchantBaseMsg.setSign(sign);
		merchantBaseMsg.setCharset(MerchantUtil.CHARSET);
		merchantBaseMsg.setBizContext(bizContext);
		// 4.发送报文
		@SuppressWarnings("unchecked")
		Map<String, String> requestParams = JSON.parseObject(
				JSON.toJSONString(merchantBaseMsg), Map.class);
		requestParams.put("submitUrl",
				"http://192.168.1.199:9069/cbhbPay/payProcess");
		// requestParams.put("submitUrl",
		// "http://183.60.125.17:19805/gcPay/payProcess");
		// http://wt.joinxx.cn/gcPay/payProcess
		// requestParams.put("submitUrl",
		// "http://wt.joinxx.cn/gcPay/payProcess");
		return new ModelAndView("/testPub", requestParams);
	}
}
