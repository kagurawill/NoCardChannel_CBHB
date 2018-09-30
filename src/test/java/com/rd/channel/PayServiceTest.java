package com.rd.channel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.rd.channel.common.constant.ValueConstant;
import com.rd.channel.common.model.chanl.CbhbQryReq;
import com.rd.channel.common.model.gateway.BaseMsgReq;
import com.rd.channel.common.model.gateway.BaseMsgRsp;
import com.rd.channel.common.model.gateway.ChanlCodePayReq;
import com.rd.channel.common.model.gateway.ChanlCodePayRsp;
import com.rd.channel.common.model.gateway.ChanlPayQryReq;
import com.rd.channel.common.model.gateway.UM_PAY_QRY_REQ;
import com.rd.channel.common.model.gateway.UM_PAY_QRY_RSP;
import com.rd.channel.common.model.gateway.UM_REFUND_QRY_REQ;
import com.rd.channel.common.model.gateway.UM_REFUND_REQ;
import com.rd.channel.common.model.gateway.UM_SCAN_CODE_REQ;
import com.rd.channel.common.model.gateway.UM_SCAN_CODE_RSP;
import com.rd.channel.common.util.HttpClientUtil;
import com.rd.channel.common.util.MerchantUtil;
import com.rd.channel.common.util.cbhb.HttpClient;
import com.rd.channel.common.util.cbhb.RSAUtil;

public class PayServiceTest {
	private static final Logger logger = LoggerFactory.getLogger(PayServiceTest.class);
	private static String md5Key = "123456";
	private static String merchantId = "010020170908154002";
	private static String merchantName = "广东XX网络科技有限公司";
	private static String submitUrl = "http://localhost:9069/cbhbPay/payProcess";
	@Test
	public void testQrQuery() throws Exception{
		UM_PAY_QRY_REQ req = new UM_PAY_QRY_REQ();
		/** 订单失败*/
		//req.setRefTxnId("QR20170217140955");
		/** 订单成功*/
		//req.setRefTxnId("QR20170904093517");
		/** 订单未支付*/
		//req.setRefTxnId("QR20170904092925");
		req.setTradeNo("QR20170915084651");
		/*签名*/
		String bizContext = JSON.toJSONString(req);
		String sign = MerchantUtil.sign(bizContext, md5Key, MerchantUtil.SIGNTYPE_MD5, "UTF-8");
		/*组装报文*/
		BaseMsgReq merchantBaseMsg = new BaseMsgReq();
		merchantBaseMsg.setVersion("1.0");
		merchantBaseMsg.setOrgId(merchantId);
		merchantBaseMsg.setOrgName(merchantName);
		merchantBaseMsg.setOrderTime(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		merchantBaseMsg.setTxnType(ValueConstant.TXN_TYPE_PAY_QRY);
		merchantBaseMsg.setSignType(MerchantUtil.SIGNTYPE_MD5);
		merchantBaseMsg.setCharset(MerchantUtil.CHARSET);
		merchantBaseMsg.setSign(sign);
		merchantBaseMsg.setBizContext(bizContext);
		
		/*发送报文*/
		@SuppressWarnings("unchecked")
		Map<String,String> requestParams = JSON.parseObject(JSON.toJSONString(merchantBaseMsg), Map.class);
		//String rspStr = HttpClientUtil.doPost(requestParams, "http://localhost:9063/msyhPay/test/");
		String rspStr = HttpClientUtil.doPost(requestParams, submitUrl);
		BaseMsgRsp baseMsg = JSON.parseObject(rspStr, BaseMsgRsp.class);
		logger.info("[扫码支付查询测试]应答报文："+rspStr);
		
		/*解密*/
		String rspMsg = baseMsg.getBizContext();
		logger.info("回应报文："+rspMsg);
		//6.验签
		boolean isTrue = MerchantUtil.verify(rspMsg, baseMsg.getSign(), md5Key, MerchantUtil.SIGNTYPE_MD5, "UTF-8");
		logger.info("[扫码支付查询测试]业务报文："+rspMsg);
		if(isTrue){
			logger.info("[扫码支付查询测试]验签成功");
			UM_PAY_QRY_RSP merchantPayQryRsp = JSON.parseObject(rspMsg, UM_PAY_QRY_RSP.class);
			logger.info("[扫码支付查询测试]code="+baseMsg.getCode());
			logger.info("[扫码支付查询测试]retCode="+merchantPayQryRsp.getRetCode());
			logger.info("[扫码支付查询测试]retMsg="+merchantPayQryRsp.getRetMsg());
			logger.info("[扫码支付查询测试]tradeNo="+merchantPayQryRsp.getTradeNo());
			logger.info("[扫码支付查询测试]dealId="+merchantPayQryRsp.getDealId());
			logger.info("[扫码支付查询测试]upchlRetCode="+merchantPayQryRsp.getUpchlRetCode());
			logger.info("[扫码支付查询测试]upchlRetMsg="+merchantPayQryRsp.getUpchlRetMsg());
		}else{
			logger.info("[扫码支付查询测试]验签失败！");
		}
	}
}
