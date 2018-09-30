package com.rd.channel.service.impl;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.rd.channel.common.constant.ChanlConstant;
import com.rd.channel.common.constant.ReturnCdConstant;
import com.rd.channel.common.constant.ValueConstant;
import com.rd.channel.common.model.chanl.CbhbH5Req;
import com.rd.channel.common.model.chanl.CbhbNotice;
import com.rd.channel.common.model.chanl.CbhbNoticePlain;
import com.rd.channel.common.model.chanl.CbhbPCReq;
import com.rd.channel.common.model.chanl.CbhbQrCodePlainRsp;
import com.rd.channel.common.model.chanl.CbhbQrCodeReq;
import com.rd.channel.common.model.chanl.CbhbQrCodeRsp;
import com.rd.channel.common.model.chanl.CbhbQryPlainRsp;
import com.rd.channel.common.model.chanl.CbhbQryReq;
import com.rd.channel.common.model.chanl.CbhbQryRsp;
import com.rd.channel.common.model.chanl.CbhbRefundReq;
import com.rd.channel.common.model.chanl.CbhbRefundRsp;
import com.rd.channel.common.model.gateway.BaseMsgReq;
import com.rd.channel.common.model.gateway.BaseMsgRsp;
import com.rd.channel.common.model.gateway.ChanlCodePayRsp;
import com.rd.channel.common.model.gateway.ChanlPayQryReq;
import com.rd.channel.common.model.gateway.ChanlPayQryRsp;
import com.rd.channel.common.model.gateway.ChanlPayReq;
import com.rd.channel.common.model.gateway.ChanlPayRsp;
import com.rd.channel.common.model.gateway.UM_NOTICE_REQ;
import com.rd.channel.common.model.gateway.UM_PAY_QRY_REQ;
import com.rd.channel.common.model.gateway.UM_PAY_QRY_RSP;
import com.rd.channel.common.model.gateway.UM_REFUND_QRY_REQ;
import com.rd.channel.common.model.gateway.UM_REFUND_QRY_RSP;
import com.rd.channel.common.model.gateway.UM_REFUND_REQ;
import com.rd.channel.common.model.gateway.UM_REFUND_RSP;
import com.rd.channel.common.model.gateway.UM_SCAN_CODE_REQ;
import com.rd.channel.common.model.gateway.UM_SCAN_CODE_RSP;
import com.rd.channel.common.util.Base64;
import com.rd.channel.common.util.FormatUtil;
import com.rd.channel.common.util.HttpClientUtil;
import com.rd.channel.common.util.MerchantUtil;
import com.rd.channel.common.util.cbhb.HttpClient;
import com.rd.channel.common.util.cbhb.RSAUtil;
import com.rd.channel.service.CbhbPayServiceI;

@Service
public class CbhbPayServiceImpl implements CbhbPayServiceI {
	private static final Logger logger = LoggerFactory
			.getLogger(CbhbPayServiceImpl.class);
	@Value("${channel.merchant.md5Key}")
	private String merchantMd5Key;
	@Value("${channel.cbhb.privateKey}")
	private String privateKey;
	@Value("${channel.cbhb.publicKey}")
	private String publicKey;
	@Value("${channel.cbhb.localNotifyUrl}")
	private String localNotifyUrl;
	@Value("${channel.merchant.notifyUrl}")
	private String notifyUrl;
	@Value("${channel.cbhb.payUrl}")
	private String payUrl;
	@Value("${channel.cbhb.pcPayUrl}")
	private String pcPayUrl;
	@Value("${channel.cbhb.h5PayUrl}")
	private String h5PayUrl;

	@Override
	public String qrCodeQuery(BaseMsgReq merchantBaseMsg) throws Exception {
		logger.info("[渤海银行聚合-扫码支付查询]收到来自下游商户[{}]的扫码支付查询请求。",
				merchantBaseMsg.getOrgId());
		BaseMsgRsp baseMsgRsp = new BaseMsgRsp();
		UM_PAY_QRY_RSP rsp = new UM_PAY_QRY_RSP();
		baseMsgRsp.setCode(ValueConstant.SUCCESS);
		UM_PAY_QRY_REQ req = JSON.parseObject(merchantBaseMsg.getBizContext(),
				UM_PAY_QRY_REQ.class);
		rsp.setTradeNo(req.getTradeNo());
		/**
		 * 1 组装请求报文
		 */
		Map<String, String> reqData = new HashMap<String, String>();
		CbhbQryReq cbhbQryReq = new CbhbQryReq();
		cbhbQryReq.setTransId(ChanlConstant.PAY_TYPE_ORDQUERY);
		cbhbQryReq.setMch_id(merchantBaseMsg.getOrgId());
		cbhbQryReq.setMerSeqNo(req.getTradeNo());
		String plain = JSON.toJSONString(cbhbQryReq);
		logger.debug("[渤海银行聚合-扫码支付查询]请求plain报文[{}]", plain);
		/**
		 * 2 数据签名
		 */
		String signed = RSAUtil.sign(plain, privateKey);
		/**
		 * 3 数据加密
		 */
		plain = Base64.encode(plain.getBytes());
		reqData.put("Plain", plain);
		reqData.put("Sign", signed);
		/**
		 * 4 发送数据
		 */
		logger.info("[渤海银行聚合-扫码支付查询]请求报文[{}]", JSON.toJSONString(reqData));
		String rspStr = HttpClient.post(reqData, payUrl,
				ChanlConstant.CHARSET_UTF8);
		/**
		 * 5 处理回应报文
		 */
		logger.info("[渤海银行聚合-扫码支付查询]返回报文[{}]", rspStr);
		if (StringUtils.isBlank(rspStr)) {
			logger.warn("[渤海银行聚合-扫码支付查询]应答报文为空");
			baseMsgRsp.setCode(ValueConstant.FAIL);
			baseMsgRsp.setMsg("系统异常");
			rsp.setUpchlRetCode(ReturnCdConstant.RET_CD_0004);
			rsp.setUpchlRetMsg("应答报文为空");
			rsp.setRetCode(ReturnCdConstant.RET_CD_0004);
			rsp.setRetMsg("系统异常");
			return buildQrCodeQueryRspMsg(baseMsgRsp, rsp);
		}
		/**
		 * 6 验签
		 */
		CbhbQryRsp cbhbQryRsp = JSON.parseObject(rspStr, CbhbQryRsp.class);
		String plainStr = cbhbQryRsp.getPlain();
		if (StringUtils.isNotBlank(plainStr)) {
			plainStr = new String(Base64.decode(cbhbQryRsp.getPlain()));
			boolean flag = RSAUtil.verify(plainStr, publicKey,
					cbhbQryRsp.getSign());
			if (!flag) {
				// 验签失败
				baseMsgRsp.setCode(ValueConstant.FAIL);
				rsp.setUpchlRetCode(ReturnCdConstant.RET_CD_0004);
				rsp.setUpchlRetMsg("应答报文验签失败");
				rsp.setRetCode(ReturnCdConstant.RET_CD_0004);
				rsp.setRetMsg("系统异常");
				return buildQrCodeQueryRspMsg(baseMsgRsp, rsp);
			}
		} else {
			baseMsgRsp.setCode(ValueConstant.FAIL);
			rsp.setRetCode(ReturnCdConstant.RET_CD_0004);
			rsp.setRetMsg("系统异常");
			rsp.setUpchlRetMsg(cbhbQryRsp.getRespMsg());
			return buildQrCodeQueryRspMsg(baseMsgRsp, rsp);
		}
		String errCode = cbhbQryRsp.getRespCode();
		rsp.setUpchlRetCode(errCode);
		rsp.setUpchlRetMsg(cbhbQryRsp.getRespMsg());
		if (ChanlConstant.RET_CODE_SUCCESS.equalsIgnoreCase(errCode)) {
			CbhbQryPlainRsp plainRsp = JSON.parseObject(plainStr,
					CbhbQryPlainRsp.class);
			String status = plainRsp.getTransStatus();
			switch (status) {
			case ChanlConstant.TRANS_STATUS_00:
				logger.info("[渤海银行聚合-扫码支付查询]交易成功");
				rsp.setDealId(plainRsp.getTransSeqNo());
				rsp.setRetCode(ReturnCdConstant.RET_CD_0000);
				rsp.setRetMsg("交易成功");
				break;
			case ChanlConstant.TRANS_STATUS_01:
				logger.info("[渤海银行聚合-扫码支付查询]交易失败");
				// rsp.setDealId(plainRsp.getTransSeqNo());
				rsp.setRetCode(ReturnCdConstant.RET_CD_0003);
				rsp.setRetMsg("交易失败");
				break;
			case ChanlConstant.TRANS_STATUS_03:
				logger.info("[渤海银行聚合-扫码支付查询]交易已部分退款");
				rsp.setDealId(plainRsp.getTransSeqNo());
				rsp.setRetCode(ReturnCdConstant.RET_CD_0024);
				rsp.setRetMsg("交易已部分退款");
				break;
			case ChanlConstant.TRANS_STATUS_04:
				logger.info("[渤海银行聚合-扫码支付查询]交易已全额退款");
				rsp.setDealId(plainRsp.getTransSeqNo());
				rsp.setRetCode(ReturnCdConstant.RET_CD_0025);
				rsp.setRetMsg("交易全额退款");
				break;
			case ChanlConstant.TRANS_STATUS_08:
				logger.info("[渤海银行聚合-扫码支付查询]交易未支付");
				rsp.setDealId(plainRsp.getTransSeqNo());
				rsp.setRetCode(ReturnCdConstant.RET_CD_0031);
				rsp.setRetMsg("交易未支付");
				break;
			case ChanlConstant.TRANS_STATUS_99:
				logger.info("[渤海银行聚合-扫码支付查询]交易超时");
				rsp.setDealId(plainRsp.getTransSeqNo());
				rsp.setRetCode(ReturnCdConstant.RET_CD_0026);
				rsp.setRetMsg("交易超时");
				break;
			default:
				break;
			}
		} else if (ChanlConstant.RET_CODE_TRANSNULL.equalsIgnoreCase(errCode)) {
			logger.info("[渤海银行聚合-扫码支付查询]原订单不存在");
			rsp.setRetCode(ReturnCdConstant.RET_CD_0020);
			rsp.setRetMsg("原订单不存在");
		} else {
			logger.info("[渤海银行聚合-扫码支付查询]其他错误");
			rsp.setRetCode(ReturnCdConstant.RET_CD_0004);
			rsp.setRetMsg("其他错误");
		}
		return buildQrCodeQueryRspMsg(baseMsgRsp, rsp);
	}

	@Override
	public String buildQrCodeQueryRspMsg(BaseMsgRsp merchantBaseMsg,
			UM_PAY_QRY_RSP rsp) throws Exception {
		// 1.签名
		String bizContext = JSON.toJSONString(rsp);
		String sign = MerchantUtil.sign(bizContext, merchantMd5Key,
				MerchantUtil.SIGNTYPE_MD5, ChanlConstant.CHARSET_UTF8);
		merchantBaseMsg.setSign(sign);
		merchantBaseMsg.setBizContext(bizContext);
		return JSON.toJSONString(merchantBaseMsg);
	}

	@Override
	public String qrCodePay(BaseMsgReq merchantBaseMsg) throws Exception {
		BaseMsgRsp baseMsg = new BaseMsgRsp();
		ChanlCodePayRsp rsp = new ChanlCodePayRsp();
		return buildQrCodePayRspMsg(baseMsg, rsp);
	}

	@Override
	public String buildQrCodePayRspMsg(BaseMsgRsp merchantBaseMsg,
			ChanlCodePayRsp rsp) throws Exception {
		// 1.签名
		String bizContext = JSON.toJSONString(rsp);
		String sign = MerchantUtil.sign(bizContext, merchantMd5Key,
				MerchantUtil.SIGNTYPE_MD5, ChanlConstant.CHARSET_UTF8);
		merchantBaseMsg.setSign(sign);
		merchantBaseMsg.setBizContext(bizContext);
		return JSON.toJSONString(merchantBaseMsg);
	}

	@Override
	public String qrCodeGenerateOrder(BaseMsgReq merchantBaseMsg)
			throws Exception {
		logger.info("[渤海银行聚合-扫码支付(持卡人主扫)]收到来自下游商户[{}]的扫码支付请求。",
				merchantBaseMsg.getOrgId());
		BaseMsgRsp baseMsgRsp = new BaseMsgRsp();
		UM_SCAN_CODE_RSP rsp = new UM_SCAN_CODE_RSP();
		baseMsgRsp.setCode(ValueConstant.SUCCESS);
		UM_SCAN_CODE_REQ req = JSON.parseObject(
				merchantBaseMsg.getBizContext(), UM_SCAN_CODE_REQ.class);
		rsp.setTradeNo(req.getTradeNo());
		/**
		 * 1 组装请求报文
		 */
		Map<String, String> reqData = new HashMap<String, String>();
		CbhbQrCodeReq cbhbQrCodeReq = new CbhbQrCodeReq();
		cbhbQrCodeReq.setTransId(ChanlConstant.PAY_TYPE_PCINFOQUERY);
		cbhbQrCodeReq.setMch_id(merchantBaseMsg.getOrgId());
		cbhbQrCodeReq.setMch_name(merchantBaseMsg.getOrgName());
		cbhbQrCodeReq.setSubject(req.getBody());
		cbhbQrCodeReq.setMerSeqNo(req.getTradeNo());
		cbhbQrCodeReq.setTransAmt(req.getTotalAmount());
		String plain = JSON.toJSONString(cbhbQrCodeReq);
		logger.info("[渤海银行聚合-扫码支付(持卡人主扫)]请求数据报文[{}]", plain);
		/**
		 * 2 数据签名
		 */
		String signed = RSAUtil.sign(plain, privateKey);
		/**
		 * 3 数据加密
		 */
		plain = Base64.encode(plain.getBytes());
		reqData.put("Plain", plain);
		reqData.put("Sign", signed);
		/**
		 * 4 发送数据
		 */
		logger.info("[渤海银行聚合-扫码支付(持卡人主扫)]请求报文[{}]", JSON.toJSONString(reqData));
		String rspStr = HttpClient.post(reqData, payUrl,
				ChanlConstant.CHARSET_UTF8);
		/**
		 * 5 处理回应报文
		 */
		logger.info("[渤海银行聚合-扫码支付(持卡人主扫)]返回报文[{}]", rspStr);
		if (StringUtils.isBlank(rspStr)) {
			logger.warn("[渤海银行聚合-扫码支付(持卡人主扫)]应答报文为空");
			baseMsgRsp.setCode(ValueConstant.FAIL);
			baseMsgRsp.setMsg("系统异常");
			rsp.setUpchlRetCode(ReturnCdConstant.RET_CD_0004);
			rsp.setUpchlRetMsg("应答报文为空");
			rsp.setRetCode(ReturnCdConstant.RET_CD_0004);
			rsp.setRetMsg("获取订单二维码失败");
			return buildQrCodeGenerateOrderRspMsg(baseMsgRsp, rsp);
		}
		/**
		 * 6 验签
		 */
		CbhbQrCodeRsp cbhbQrCodeRsp = JSON.parseObject(rspStr,
				CbhbQrCodeRsp.class);
		String plainStr = cbhbQrCodeRsp.getPlain();
		if (StringUtils.isNotBlank(plainStr)) {
			plainStr = new String(Base64.decode(plainStr));
			boolean flag = RSAUtil.verify(plainStr, publicKey,
					cbhbQrCodeRsp.getSign());
			if (!flag) {
				// 验签失败
				baseMsgRsp.setCode(ValueConstant.FAIL);
				baseMsgRsp.setMsg("系统异常");
				rsp.setUpchlRetCode(ReturnCdConstant.RET_CD_0004);
				rsp.setUpchlRetMsg("应答报文验签失败");
				rsp.setRetCode(ReturnCdConstant.RET_CD_0004);
				rsp.setRetMsg("获取订单二维码失败");
				return buildQrCodeGenerateOrderRspMsg(baseMsgRsp, rsp);
			}
		} else {
			baseMsgRsp.setCode(ValueConstant.FAIL);
			rsp.setRetCode(ReturnCdConstant.RET_CD_0004);
			rsp.setRetMsg("系统异常");
			rsp.setUpchlRetCode(cbhbQrCodeRsp.getRespCode());
			rsp.setUpchlRetMsg(cbhbQrCodeRsp.getRespMsg());
			return buildQrCodeGenerateOrderRspMsg(baseMsgRsp, rsp);
		}
		String errCode = cbhbQrCodeRsp.getRespCode();
		rsp.setUpchlRetCode(errCode);
		rsp.setUpchlRetMsg(cbhbQrCodeRsp.getRespMsg());
		if (ChanlConstant.RET_CODE_SUCCESS.equalsIgnoreCase(errCode)) {
			CbhbQrCodePlainRsp plainRsp = JSON.parseObject(plainStr,
					CbhbQrCodePlainRsp.class);
			logger.info("[渤海银行聚合-扫码支付(持卡人主扫)]获取订单二维码成功");
			rsp.setQrCode(plainRsp.getQrcode());
			rsp.setRetCode(ReturnCdConstant.RET_CD_0031);
			rsp.setRetMsg("获取订单二维码成功");
		} else {
			logger.warn("[渤海银行聚合-扫码支付(持卡人主扫)]获取订单二维码失败");
			rsp.setRetCode(ReturnCdConstant.RET_CD_0003);
			rsp.setRetMsg("获取订单二维码失败");
			return buildQrCodeGenerateOrderRspMsg(baseMsgRsp, rsp);
		}
		return buildQrCodeGenerateOrderRspMsg(baseMsgRsp, rsp);
	}

	@Override
	public String buildQrCodeGenerateOrderRspMsg(BaseMsgRsp merchantBaseMsg,
			UM_SCAN_CODE_RSP rsp) throws Exception {
		// 1.签名
		String bizContext = JSON.toJSONString(rsp);
		String sign = MerchantUtil.sign(bizContext, merchantMd5Key,
				MerchantUtil.SIGNTYPE_MD5, ChanlConstant.CHARSET_UTF8);
		merchantBaseMsg.setSign(sign);
		merchantBaseMsg.setBizContext(bizContext);
		return JSON.toJSONString(merchantBaseMsg);
	}

	@Override
	public String refund(BaseMsgReq merchantBaseMsg) throws Exception {
		logger.info("[渤海银行聚合-退款]收到来自下游商户[{}]的扫码退款请求。",
				merchantBaseMsg.getOrgId());
		BaseMsgRsp baseMsgRsp = new BaseMsgRsp();
		UM_REFUND_RSP rsp = new UM_REFUND_RSP();
		baseMsgRsp.setCode(ValueConstant.SUCCESS);
		UM_REFUND_REQ req = JSON.parseObject(merchantBaseMsg.getBizContext(),
				UM_REFUND_REQ.class);
		rsp.setTradeNo(req.getRequestNo());
		/**
		 * 1 组装请求报文
		 */
		Map<String, String> reqData = new HashMap<String, String>();
		CbhbRefundReq cbhbRefundReq = new CbhbRefundReq();
		cbhbRefundReq.setTransId(ChanlConstant.PAY_TYPE_REFUND);
		cbhbRefundReq.setMch_id(merchantBaseMsg.getOrgId());
		cbhbRefundReq.setSub_mch_id(merchantBaseMsg.getOrgId());
		cbhbRefundReq.setOrgMerSeqNo(req.getTradeNo());
		cbhbRefundReq.setSubject(req.getRefundReason());
		cbhbRefundReq.setMerSeqNo(req.getRequestNo());
		cbhbRefundReq
				.setTransAmount(FormatUtil.yuanToFen(req.getRefundAmount()));
		cbhbRefundReq.setMerTransDate("");
		cbhbRefundReq.setCashierId("123456");
		String plain = JSON.toJSONString(cbhbRefundReq);
		logger.debug("[渤海银行聚合-退款]请求plain报文[{}]", plain);
		/**
		 * 2 数据签名
		 */
		String signed = RSAUtil.sign(plain, privateKey);
		/**
		 * 3 数据加密
		 */
		plain = Base64.encode(plain.getBytes());
		reqData.put("Plain", plain);
		reqData.put("Sign", signed);
		/**
		 * 4 发送数据
		 */
		logger.info("[渤海银行聚合-退款]请求报文[{}]", JSON.toJSONString(reqData));
		String rspStr = HttpClient.post(reqData, payUrl,
				ChanlConstant.CHARSET_UTF8);
		/**
		 * 5 处理回应报文
		 */
		logger.info("[渤海银行聚合-退款]返回报文[{}]", rspStr);
		if (StringUtils.isBlank(rspStr)) {
			logger.warn("[渤海银行聚合-退款]应答报文为空");
			rsp.setRetCode(ReturnCdConstant.RET_CD_0004);
			rsp.setRetMsg("应答报文为空，请发查询");
			return buildRefundRspMsg(baseMsgRsp, rsp);
		}
		/**
		 * 6 验签
		 */
		CbhbRefundRsp cbhbRefundRsp = JSON.parseObject(rspStr,
				CbhbRefundRsp.class);
		String plainStr = cbhbRefundRsp.getPlain();
		if (StringUtils.isNotBlank(plainStr)) {
			plainStr = new String(Base64.decode(plainStr));
			boolean flag = RSAUtil.verify(plainStr, publicKey,
					cbhbRefundRsp.getSign());
			if (!flag) {
				// 验签失败
				rsp.setUpchlRetCode(ReturnCdConstant.RET_CD_0004);
				rsp.setUpchlRetMsg("应答报文验签失败");
				rsp.setRetCode(ReturnCdConstant.RET_CD_0004);
				rsp.setRetMsg("系统异常");
				return buildRefundRspMsg(baseMsgRsp, rsp);
			}
		} else {
			rsp.setRetCode(ReturnCdConstant.RET_CD_0004);
			rsp.setRetMsg("系统异常");
			rsp.setUpchlRetCode(cbhbRefundRsp.getRespCode());
			rsp.setUpchlRetMsg(cbhbRefundRsp.getRespMsg());
			return buildRefundRspMsg(baseMsgRsp, rsp);
		}
		rsp.setUpchlRetCode(cbhbRefundRsp.getRespCode());
		rsp.setUpchlRetMsg(cbhbRefundRsp.getRespMsg());
		String errCode = cbhbRefundRsp.getRespCode();
		if (ChanlConstant.RET_CODE_SUCCESS.equalsIgnoreCase(errCode)) {
			logger.info("[渤海银行聚合-退款]退款成功");
			rsp.setRetCode(ReturnCdConstant.RET_CD_0000);
			rsp.setRetMsg("退款成功");
		} else if (ChanlConstant.RET_CODE_REFNULL.equalsIgnoreCase(errCode)) {
			logger.warn("[渤海银行聚合-退款]原订单不存在,无法发起退款");
			rsp.setRetCode(ReturnCdConstant.RET_CD_0020);
			rsp.setRetMsg("原订单不存在,无法发起退款");
		} else if (ChanlConstant.RET_CODE_TIMEOUT.equalsIgnoreCase(errCode)) {
			logger.warn("[渤海银行聚合-退款]请求第三方支付公司,交易超时");
			rsp.setRetCode(ReturnCdConstant.RET_CD_0030);
			rsp.setRetMsg("请求第三方支付公司,交易超时");
		} else if (ChanlConstant.RET_CODE_REFUNDOVER.equalsIgnoreCase(errCode)) {
			logger.warn("[渤海银行聚合-退款]退货金额超过支付可退金额");
			rsp.setRetCode(ReturnCdConstant.RET_CD_0026);
			rsp.setRetMsg("退款金额大于支付金额");
		} else {
			logger.warn("[渤海银行聚合-退款]其他错误");
			rsp.setRetCode(ReturnCdConstant.RET_CD_0004);
			rsp.setRetMsg("其他错误");
		}
		return buildRefundRspMsg(baseMsgRsp, rsp);
	}

	@Override
	public String buildRefundRspMsg(BaseMsgRsp merchantBaseMsg,
			UM_REFUND_RSP rsp) throws Exception {
		// 1.签名
		String bizContext = JSON.toJSONString(rsp);
		String sign = MerchantUtil.sign(bizContext, merchantMd5Key,
				MerchantUtil.SIGNTYPE_MD5, ChanlConstant.CHARSET_UTF8);
		merchantBaseMsg.setSign(sign);
		merchantBaseMsg.setBizContext(bizContext);
		return JSON.toJSONString(merchantBaseMsg);
	}

	@Override
	public String refundQry(BaseMsgReq merchantBaseMsg) throws Exception {
		logger.info("[渤海银行聚合-退款单查询]收到来自下游商户[{}]的退款单查询请求。",
				merchantBaseMsg.getOrgId());
		BaseMsgRsp baseMsgRsp = new BaseMsgRsp();
		UM_REFUND_QRY_RSP rsp = new UM_REFUND_QRY_RSP();
		baseMsgRsp.setCode(ValueConstant.SUCCESS);
		UM_REFUND_QRY_REQ req = JSON.parseObject(
				merchantBaseMsg.getBizContext(), UM_REFUND_QRY_REQ.class);
		rsp.setRequestNo(req.getRequestNo());
		rsp.setTradeNo(rsp.getTradeNo());
		/**
		 * 1 组装请求报文
		 */
		Map<String, String> reqData = new HashMap<String, String>();
		CbhbQryReq cbhbQryReq = new CbhbQryReq();
		cbhbQryReq.setTransId(ChanlConstant.PAY_TYPE_ORDQUERY);
		cbhbQryReq.setMch_id(merchantBaseMsg.getOrgId());
		cbhbQryReq.setMerSeqNo(req.getTradeNo());
		String plain = JSON.toJSONString(cbhbQryReq);
		/**
		 * 2 数据签名
		 */
		String signed = RSAUtil.sign(plain, privateKey);
		/**
		 * 3 数据加密
		 */
		plain = Base64.encode(plain.getBytes());
		reqData.put("Plain", plain);
		reqData.put("Sign", signed);
		/**
		 * 4 发送数据
		 */
		logger.info("[渤海银行聚合-退款单查询]请求报文[{}]", JSON.toJSONString(reqData));
		String rspStr = HttpClient.post(reqData, payUrl,
				ChanlConstant.CHARSET_UTF8);
		/**
		 * 5 处理回应报文
		 */
		logger.info("[渤海银行聚合-退款单查询]返回报文[{}]", rspStr);
		if (StringUtils.isBlank(rspStr)) {
			logger.warn("[渤海银行聚合-退款单查询]应答报文为空");
			baseMsgRsp.setCode(ValueConstant.FAIL);
			baseMsgRsp.setMsg("系统异常");
			rsp.setUpchlRetCode(ReturnCdConstant.RET_CD_0004);
			rsp.setUpchlRetMsg("应答报文为空");
			rsp.setRetCode(ReturnCdConstant.RET_CD_0004);
			rsp.setRetMsg("系统异常");
			return buildRefundQryRspMsg(baseMsgRsp, rsp);
		}
		/**
		 * 6 验签
		 */
		CbhbQryRsp cbhbQryRsp = JSON.parseObject(rspStr, CbhbQryRsp.class);
		String plainStr = cbhbQryRsp.getPlain();
		if (StringUtils.isNotBlank(plainStr)) {
			plainStr = new String(Base64.decode(cbhbQryRsp.getPlain()));
			boolean flag = RSAUtil.verify(plainStr, publicKey,
					cbhbQryRsp.getSign());
			if (!flag) {
				// 验签失败
				logger.warn("[渤海银行聚合-退款单查询]应答报文验签失败");
				baseMsgRsp.setCode(ValueConstant.FAIL);
				rsp.setUpchlRetCode(ReturnCdConstant.RET_CD_0004);
				rsp.setUpchlRetMsg("应答报文验签失败");
				rsp.setRetCode(ReturnCdConstant.RET_CD_0004);
				rsp.setRetMsg("系统异常");
				return buildRefundQryRspMsg(baseMsgRsp, rsp);
			}
		} else {
			baseMsgRsp.setCode(ValueConstant.FAIL);
			rsp.setRetCode(ReturnCdConstant.RET_CD_0004);
			rsp.setRetMsg("系统异常");
			rsp.setUpchlRetMsg(cbhbQryRsp.getRespMsg());
			return buildRefundQryRspMsg(baseMsgRsp, rsp);
		}
		String errCode = cbhbQryRsp.getRespCode();
		rsp.setUpchlRetCode(errCode);
		rsp.setUpchlRetMsg(cbhbQryRsp.getRespMsg());
		if (ChanlConstant.RET_CODE_SUCCESS.equalsIgnoreCase(errCode)) {
			CbhbQryPlainRsp plainRsp = JSON.parseObject(plainStr,
					CbhbQryPlainRsp.class);
			String status = plainRsp.getTransStatus();
			switch (status) {
			case ChanlConstant.TRANS_STATUS_00:
				logger.info("[渤海银行聚合-退款单查询]交易成功");
				rsp.setDealId(plainRsp.getTransSeqNo());
				rsp.setRetCode(ReturnCdConstant.RET_CD_0000);
				rsp.setRetMsg("交易成功");
				break;
			case ChanlConstant.TRANS_STATUS_01:
				logger.info("[渤海银行聚合-退款单查询]交易失败");
				// rsp.setDealId(plainRsp.getTransSeqNo());
				rsp.setRetCode(ReturnCdConstant.RET_CD_0003);
				rsp.setRetMsg("交易失败");
				break;
			case ChanlConstant.TRANS_STATUS_03:
				logger.info("[渤海银行聚合-退款单查询]交易已部分退款");
				rsp.setDealId(plainRsp.getTransSeqNo());
				rsp.setRetCode(ReturnCdConstant.RET_CD_0024);
				rsp.setRetMsg("交易已部分退款");
				break;
			case ChanlConstant.TRANS_STATUS_04:
				logger.info("[渤海银行聚合-退款单查询]交易已全额退款");
				rsp.setDealId(plainRsp.getTransSeqNo());
				rsp.setRetCode(ReturnCdConstant.RET_CD_0025);
				rsp.setRetMsg("交易全额退款");
				break;
			case ChanlConstant.TRANS_STATUS_08:
				logger.info("[渤海银行聚合-退款单查询]交易未支付");
				rsp.setDealId(plainRsp.getTransSeqNo());
				rsp.setRetCode(ReturnCdConstant.RET_CD_0031);
				rsp.setRetMsg("交易未支付");
				break;
			case ChanlConstant.TRANS_STATUS_99:
				logger.info("[渤海银行聚合-退款单查询]交易超时");
				rsp.setDealId(plainRsp.getTransSeqNo());
				rsp.setRetCode(ReturnCdConstant.RET_CD_0026);
				rsp.setRetMsg("交易超时");
				break;
			default:
				break;
			}
		} else if (ChanlConstant.RET_CODE_TRANSNULL.equalsIgnoreCase(errCode)) {
			logger.info("[渤海银行聚合-退款单查询]原订单不存在");
			rsp.setRetCode(ReturnCdConstant.RET_CD_0020);
			rsp.setRetMsg("原订单不存在");
		} else {
			logger.info("[渤海银行聚合-退款单查询]其他错误");
			rsp.setRetCode(ReturnCdConstant.RET_CD_0004);
			rsp.setRetMsg("其他错误");
		}
		return buildRefundQryRspMsg(baseMsgRsp, null);
	}

	@Override
	public String buildRefundQryRspMsg(BaseMsgRsp merchantBaseMsg,
			UM_REFUND_QRY_RSP rsp) throws Exception {
		// 1.签名
		String bizContext = JSON.toJSONString(rsp);
		String sign = MerchantUtil.sign(bizContext, merchantMd5Key,
				MerchantUtil.SIGNTYPE_MD5, ChanlConstant.CHARSET_UTF8);
		merchantBaseMsg.setSign(sign);
		merchantBaseMsg.setBizContext(bizContext);
		return JSON.toJSONString(merchantBaseMsg);
	}

	@Override
	public boolean noticeProcess(String notice) throws Exception {
		logger.info("[渤海银行聚合-支付结果异步通知]处理订单结果异步通知报文{}", JSON.toJSON(notice));
		if (StringUtils.isBlank(notice)) {
			logger.info("[渤海银行聚合-支付结果异步通知]订单结果异步通知报文为空，丢弃！");
			return false;
		}
		BaseMsgRsp baseMsgRsp = new BaseMsgRsp();
		UM_NOTICE_REQ rsp = new UM_NOTICE_REQ();
		/**
		 * 1 验签
		 */
		CbhbNotice cbhbNotice = JSON.parseObject(notice, CbhbNotice.class);
		String plainStr = cbhbNotice.getPlain();
		if (StringUtils.isNotBlank(plainStr)) {
			plainStr = new String(Base64.decode(plainStr));
			boolean flag = RSAUtil.verify(plainStr, publicKey,
					cbhbNotice.getSign());
			if (!flag) {
				// 验签失败
				logger.info("[渤海银行聚合-支付结果异步通知]验签失败，丢弃！");
				return false;
			}
		} else {
			logger.info("[渤海银行聚合-支付结果异步通知]报文异常，丢弃！");
			return false;
		}
		rsp.setUpchlRetCode(ReturnCdConstant.RET_CD_0000);
		rsp.setUpchlRetMsg("异步通知");
		CbhbNoticePlain plainRsp = JSON.parseObject(plainStr,
				CbhbNoticePlain.class);
		rsp.setTradeNo(plainRsp.getMerSeqNo());
		String status = plainRsp.getTransStatus();
		switch (status) {
		case ChanlConstant.TRANS_STATUS_00:
			logger.info("[渤海银行聚合-支付结果异步通知]交易成功");
			rsp.setDealId(plainRsp.getTransSeqNo());
			rsp.setTotalAmount(plainRsp.getTransAmount());
			rsp.setPayTime(plainRsp.getTransDateTim());
			rsp.setRetCode(ReturnCdConstant.RET_CD_0000);
			rsp.setRetMsg("交易成功");
			break;
		case ChanlConstant.TRANS_STATUS_01:
			logger.info("[渤海银行聚合-支付结果异步通知]交易失败");
			// rsp.setDealId(plainRsp.getTransSeqNo());
			// rsp.setTotalAmount(plainRsp.getTransAmount());
			// rsp.setPayTime(plainRsp.getTransDateTim());
			rsp.setRetCode(ReturnCdConstant.RET_CD_0003);
			rsp.setRetMsg("交易失败");
			break;
		case ChanlConstant.TRANS_STATUS_03:
			logger.info("[渤海银行聚合-支付结果异步通知]交易已部分退款");
			rsp.setDealId(plainRsp.getTransSeqNo());
			rsp.setTotalAmount(plainRsp.getTransAmount());
			rsp.setPayTime(plainRsp.getTransDateTim());
			rsp.setRetCode(ReturnCdConstant.RET_CD_0024);
			rsp.setRetMsg("交易已部分退款");
			break;
		case ChanlConstant.TRANS_STATUS_04:
			logger.info("[渤海银行聚合-支付结果异步通知]交易已全额退款");
			rsp.setDealId(plainRsp.getTransSeqNo());
			rsp.setTotalAmount(plainRsp.getTransAmount());
			rsp.setPayTime(plainRsp.getTransDateTim());
			rsp.setRetCode(ReturnCdConstant.RET_CD_0025);
			rsp.setRetMsg("交易全额退款");
			break;
		case ChanlConstant.TRANS_STATUS_08:
			logger.info("[渤海银行聚合-支付结果异步通知]交易未支付");
			rsp.setDealId(plainRsp.getTransSeqNo());
			rsp.setTotalAmount(plainRsp.getTransAmount());
			rsp.setPayTime(plainRsp.getTransDateTim());
			rsp.setRetCode(ReturnCdConstant.RET_CD_0031);
			rsp.setRetMsg("交易未支付");
			break;
		case ChanlConstant.TRANS_STATUS_99:
			logger.info("[渤海银行聚合-支付结果异步通知]交易超时");
			rsp.setDealId(plainRsp.getTransSeqNo());
			rsp.setRetCode(ReturnCdConstant.RET_CD_0026);
			rsp.setRetMsg("交易超时");
			break;
		default:
			break;
		}
		String bizContext = JSON.toJSONString(rsp);
		String sign = MerchantUtil.sign(bizContext, merchantMd5Key,
				MerchantUtil.SIGNTYPE_MD5, ChanlConstant.CHARSET_UTF8);
		baseMsgRsp.setCode(ValueConstant.SUCCESS);
		baseMsgRsp.setSign(sign);
		baseMsgRsp.setBizContext(bizContext);
		@SuppressWarnings("unchecked")
		Map<String, String> requestParams = JSON.parseObject(
				JSON.toJSONString(baseMsgRsp), Map.class);
		logger.info("下游通知地址：{}", notifyUrl);
		logger.debug("下游通知报文：{}", JSON.toJSONString(requestParams));
		HttpClientUtil.doPost(requestParams, notifyUrl);
		return true;
	}

	@Override
	public String quickPay(BaseMsgReq merchantBaseMsg) throws Exception {
		logger.info("[渤海银行聚合-网银支付]收到来自下游商户[" + merchantBaseMsg.getOrgId()
				+ "]的网银支付请求。");
		String submitUrl = new String();
		ChanlPayRsp rsp = new ChanlPayRsp();
		ChanlPayReq req = JSON.parseObject(merchantBaseMsg.getBizContext(),
				ChanlPayReq.class);
		rsp.setTransactionId(req.getTransactionId());
		/**
		 * 1 组装请求报文
		 */
		CbhbPCReq cbhbPCReq = new CbhbPCReq();
		cbhbPCReq.setMch_id(merchantBaseMsg.getOrgId());
		cbhbPCReq.setMch_name(merchantBaseMsg.getOrgName());
		cbhbPCReq.setMerSeqNo(req.getTransactionId());
		cbhbPCReq.setSubject(req.getOrderDesc());
		cbhbPCReq.setTransAmt(req.getOrderAmount());
		// cbhbPCReq.setMerURL(localNotifyUrl);
		String plain = JSON.toJSONString(cbhbPCReq);
		logger.debug("[渤海银行聚合-网银支付]请求plain报文[{}]", plain);
		/**
		 * 2 数据签名
		 */
		String signed = RSAUtil.sign(plain, privateKey);
		/**
		 * 3 数据加密
		 */
		plain = Base64.encode(plain.getBytes());
		/**
		 * 2.发送请求报文
		 */
		submitUrl = pcPayUrl + "?Plain=" + plain + "&Sign="
				+ URLEncoder.encode(signed, ChanlConstant.CHARSET_UTF8);
		logger.info("[渤海银行聚合-网银支付]请求报文[{}]", submitUrl);
		// 发起请求
		return submitUrl;
	}

	@Override
	public String quickPayH5(BaseMsgReq merchantBaseMsg) throws Exception {
		logger.info("[渤海银行聚合-移动端收银台]收到来自下游商户[{}]的网银支付请求。",
				merchantBaseMsg.getOrgId());
		String submitUrl = new String();
		BaseMsgRsp baseMsgRsp = new BaseMsgRsp();
		UM_SCAN_CODE_RSP rsp = new UM_SCAN_CODE_RSP();
		UM_SCAN_CODE_REQ req = JSON.parseObject(
				merchantBaseMsg.getBizContext(), UM_SCAN_CODE_REQ.class);
		rsp.setTradeNo(req.getTradeNo());
		/**
		 * 1 组装请求报文
		 */
		CbhbH5Req cbhbH5Req = new CbhbH5Req();
		cbhbH5Req.setMch_id(merchantBaseMsg.getOrgId());
		cbhbH5Req.setMch_name(merchantBaseMsg.getOrgName());
		cbhbH5Req.setMerSeqNo(req.getTradeNo());
		cbhbH5Req.setSubject(req.getBody());
		cbhbH5Req.setTransAmt(req.getTotalAmount());
		String plain = JSON.toJSONString(cbhbH5Req);
		logger.info("[渤海银行聚合-移动端收银台]请求plain报文[{}]", plain);
		/**
		 * 2 数据签名
		 */
		String signed = RSAUtil.sign(plain, privateKey);
		/**
		 * 3 数据加密
		 */
		plain = Base64.encode(plain.getBytes());
		/**
		 * 2.发送请求报文
		 */
		submitUrl = h5PayUrl + "?Plain=" + plain + "&Sign="
				+ URLEncoder.encode(signed, ChanlConstant.CHARSET_UTF8);
		logger.info("[渤海银行聚合-移动端收银台]请求报文[{}]", submitUrl);
		// 发起请求
		logger.info("[渤海银行聚合-移动端收银台]获取订单二维码成功");
		rsp.setUpchlRetCode(ReturnCdConstant.RET_CD_0000);
		rsp.setUpchlRetMsg("获取订单二维码成功");
		rsp.setQrCode(submitUrl);
		rsp.setRetCode(ReturnCdConstant.RET_CD_0031);
		rsp.setRetMsg("获取订单二维码成功");
		return buildQrCodeGenerateOrderRspMsg(baseMsgRsp, rsp);
	}

	@Override
	public String buildQuickPayH5RspMsg(BaseMsgRsp merchantBaseMsg,
			UM_SCAN_CODE_RSP rsp) throws Exception {
		// 1.签名
		String bizContext = JSON.toJSONString(rsp);
		String sign = MerchantUtil.sign(bizContext, merchantMd5Key,
				MerchantUtil.SIGNTYPE_MD5, ChanlConstant.CHARSET_UTF8);
		merchantBaseMsg.setSign(sign);
		merchantBaseMsg.setBizContext(bizContext);
		return JSON.toJSONString(merchantBaseMsg);
	}

	@Override
	public String query(BaseMsgReq merchantBaseMsg) throws Exception {
		logger.info("[渤海银行聚合-订单查询]收到来自下游商户[{}]的订单查询请求。",
				merchantBaseMsg.getOrgId());
		BaseMsgRsp baseMsgRsp = new BaseMsgRsp();
		ChanlPayQryRsp rsp = new ChanlPayQryRsp();
		baseMsgRsp.setCode(ValueConstant.SUCCESS);
		ChanlPayQryReq req = JSON.parseObject(merchantBaseMsg.getBizContext(),
				ChanlPayQryReq.class);
		rsp.setTransactionId(req.getTransactionId());
		rsp.setRefTxnId(req.getRefTxnId());
		/**
		 * 1 组装请求报文
		 */
		Map<String, String> reqData = new HashMap<String, String>();
		CbhbQryReq cbhbQryReq = new CbhbQryReq();
		cbhbQryReq.setTransId(ChanlConstant.PAY_TYPE_ORDQUERY);
		cbhbQryReq.setMerSeqNo(req.getRefTxnId());
		String plain = JSON.toJSONString(cbhbQryReq);
		/**
		 * 2 数据签名
		 */
		String signed = RSAUtil.sign(plain, privateKey);
		/**
		 * 3 数据加密
		 */
		plain = Base64.encode(plain.getBytes());
		reqData.put("Plain", plain);
		reqData.put("Sign", signed);
		/**
		 * 4 发送数据
		 */
		logger.info("[渤海银行聚合-订单查询]请求报文[{}]", JSON.toJSONString(reqData));
		String rspStr = HttpClient.post(reqData, payUrl,
				ChanlConstant.CHARSET_UTF8);
		/**
		 * 5 处理回应报文
		 */
		logger.info("[渤海银行聚合-订单查询]返回报文[{}]", rspStr);
		if (StringUtils.isBlank(rspStr)) {
			logger.warn("[渤海银行聚合-订单查询]应答报文为空");
			baseMsgRsp.setCode(ValueConstant.FAIL);
			baseMsgRsp.setMsg("系统异常");
			rsp.setQryRetCode(ReturnCdConstant.RET_CD_0000);
			rsp.setQryRetRemark("查询成功");
			rsp.setRetCode(ReturnCdConstant.RET_CD_0004);
			rsp.setRetRemark("应答报文为空");
			rsp.setTransStatus(ValueConstant.TRANS_STATUS_COMFIRM);
			return buildPayQryRspMsg(baseMsgRsp, rsp);
		}
		CbhbQryRsp cbhbQryRsp = JSON.parseObject(rspStr, CbhbQryRsp.class);
		String errCode = cbhbQryRsp.getRespCode();
		rsp.setQryRetCode(ReturnCdConstant.RET_CD_0000);
		rsp.setQryRetRemark("查询成功");
		rsp.setChanlRetCode(errCode);
		rsp.setChanlRetRemark(cbhbQryRsp.getRespMsg());
		if (ChanlConstant.RET_CODE_SUCCESS.equalsIgnoreCase(errCode)) {
			String status = "";// cbhbQryRsp.getTransStatus();
			switch (status) {
			case ChanlConstant.TRANS_STATUS_00:
				logger.info("[渤海银行聚合-订单查询]交易成功");
				// rsp.setDealId(data.get("original_ord_no").toString());
				rsp.setRetCode(ReturnCdConstant.RET_CD_0000);
				rsp.setRetRemark("交易成功");
				rsp.setTransStatus(ValueConstant.TRANS_STATUS_SUCCESS);
				break;
			case ChanlConstant.TRANS_STATUS_01:
				logger.info("[渤海银行聚合-订单查询]交易失败");
				// rsp.setDealId(data.get("original_ord_no").toString());
				rsp.setRetCode(ReturnCdConstant.RET_CD_0003);
				rsp.setRetRemark("交易失败");
				rsp.setTransStatus(ValueConstant.TRANS_STATUS_FAILURE);
				break;
			case ChanlConstant.TRANS_STATUS_03:
				logger.info("[渤海银行聚合-订单查询]交易已部分退款");
				// rsp.setDealId(data.get("original_ord_no").toString());
				rsp.setRetCode(ReturnCdConstant.RET_CD_0024);
				rsp.setRetRemark("交易已部分退款");
				rsp.setTransStatus(ValueConstant.TRANS_STATUS_SUCCESS);
				break;
			case ChanlConstant.TRANS_STATUS_04:
				logger.info("[渤海银行聚合-订单查询]交易已全额退款");
				// rsp.setDealId(data.get("original_ord_no").toString());
				rsp.setRetCode(ReturnCdConstant.RET_CD_0025);
				rsp.setRetRemark("交易全额退款");
				rsp.setTransStatus(ValueConstant.TRANS_STATUS_SUCCESS);
				break;
			case ChanlConstant.TRANS_STATUS_08:
				logger.info("[渤海银行聚合-订单查询]交易未支付");
				// rsp.setDealId(data.get("original_ord_no").toString());
				rsp.setRetCode(ReturnCdConstant.RET_CD_0031);
				rsp.setRetRemark("交易未支付");
				rsp.setTransStatus(ValueConstant.TRANS_STATUS_UNPAID);
				break;
			case ChanlConstant.TRANS_STATUS_99:
				logger.info("[渤海银行聚合-订单查询]交易超时");
				// rsp.setDealId(data.get("original_ord_no").toString());
				rsp.setRetCode(ReturnCdConstant.RET_CD_0030);
				rsp.setRetRemark("交易超时");
				rsp.setTransStatus(ValueConstant.TRANS_STATUS_COMFIRM);
				break;
			default:
				break;
			}
		} else if ("".equalsIgnoreCase(errCode)) {
			logger.info("[渤海银行聚合-订单查询]原订单不存在");
			rsp.setRetCode(ReturnCdConstant.RET_CD_0020);
			rsp.setRetRemark("原订单不存在");
			rsp.setTransStatus(ValueConstant.TRANS_STATUS_COMFIRM);
		} else {
			logger.info("[渤海银行聚合-订单查询]其他错误");
			rsp.setRetCode(ReturnCdConstant.RET_CD_0004);
			rsp.setRetRemark("其他错误");
			rsp.setTransStatus(ValueConstant.TRANS_STATUS_COMFIRM);
		}
		return buildPayQryRspMsg(baseMsgRsp, rsp);
	}

	@Override
	public String buildPayQryRspMsg(BaseMsgRsp merchantBaseMsg,
			ChanlPayQryRsp rsp) throws Exception {
		// 1.签名
		String jsonStr = JSON.toJSONString(rsp);
		String businessContext = MerchantUtil.sign(jsonStr, merchantMd5Key,
				MerchantUtil.SIGNTYPE_MD5, ChanlConstant.CHARSET_UTF8);
		merchantBaseMsg.setBizContext(businessContext);
		return JSON.toJSONString(merchantBaseMsg);
	}

}
