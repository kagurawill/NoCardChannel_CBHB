package com.rd.channel.service;

import com.rd.channel.common.model.gateway.BaseMsgReq;
import com.rd.channel.common.model.gateway.BaseMsgRsp;
import com.rd.channel.common.model.gateway.ChanlCodePayRsp;
import com.rd.channel.common.model.gateway.ChanlPayQryRsp;
import com.rd.channel.common.model.gateway.UM_PAY_QRY_RSP;
import com.rd.channel.common.model.gateway.UM_REFUND_QRY_RSP;
import com.rd.channel.common.model.gateway.UM_REFUND_RSP;
import com.rd.channel.common.model.gateway.UM_SCAN_CODE_RSP;

public interface CbhbPayServiceI {

	
	/**
	 * 订单结果异步通知报文
	 * @param cbhbNotice
	 * @return
	 * @throws Exception
	 */
	boolean noticeProcess(String cbhbNotice) throws Exception;
	
	/**
	 * 扫码支付（持卡人被扫）接口
	 * @param merchantBaseMsg
	 * @return
	 * @throws Exception
	 */
	String qrCodePay(BaseMsgReq merchantBaseMsg) throws Exception;
	
	/**
	 * 扫码支付（持卡人被扫）接口回应
	 * @param merchantBaseMsg
	 * @param rsp
	 * @return
	 * @throws Exception
	 */
	String buildQrCodePayRspMsg(BaseMsgRsp merchantBaseMsg, ChanlCodePayRsp rsp) throws Exception;
	
	/**
	 * 扫码支付（持卡人主扫）接口
	 * @param merchantBaseMsg
	 * @return
	 * @throws Exception
	 */
	String qrCodeGenerateOrder(BaseMsgReq merchantBaseMsg) throws Exception;
	
	/**
	 * 扫码支付（持卡人主扫）接口回应
	 * @param merchantBaseMsg
	 * @param rsp
	 * @return
	 * @throws Exception
	 */
	String buildQrCodeGenerateOrderRspMsg(BaseMsgRsp merchantBaseMsg,
			UM_SCAN_CODE_RSP rsp) throws Exception;
	
	/**
	 * 扫码支付查询接口
	 * @param merchantBaseMsg
	 * @return
	 * @throws Exception
	 */
	String qrCodeQuery(BaseMsgReq merchantBaseMsg) throws Exception;
	
	/**
	 * 扫码支付查询接口回应
	 * @param merchantBaseMsg
	 * @param rsp
	 * @return
	 * @throws Exception
	 */
	String buildQrCodeQueryRspMsg(BaseMsgRsp merchantBaseMsg, UM_PAY_QRY_RSP rsp) throws Exception;

	/**
	 * 退款接口
	 * @param merchantBaseMsg
	 * @return
	 * @throws Exception
	 */
	String refund(BaseMsgReq merchantBaseMsg)throws Exception ;
	
	/**
	 * 构造退款应答报文
	 * @param merchantBaseMsg
	 * @param rsp
	 * @return
	 * @throws Exception
	 */
	String buildRefundRspMsg(BaseMsgRsp merchantBaseMsg, UM_REFUND_RSP rsp) throws Exception;
	
	/**
	 * 退款单查询接口
	 * @param merchantBaseMsg
	 * @return
	 * @throws Exception
	 */
	String refundQry(BaseMsgReq merchantBaseMsg)throws Exception ;
	
	/**
	 * 构造退款单查询应答报文
	 * @param merchantBaseMsg
	 * @param rsp
	 * @return
	 * @throws Exception
	 */
	String buildRefundQryRspMsg(BaseMsgRsp merchantBaseMsg, UM_REFUND_QRY_RSP rsp) throws Exception;
	
	/**
	 * 网银支付
	 * @param merchantBaseMsg
	 * @return
	 * @throws Exception
	 */
	String quickPay(BaseMsgReq merchantBaseMsg) throws Exception;
	
	/**
	 * 网银支付H5
	 * @param merchantBaseMsg
	 * @return
	 * @throws Exception
	 */
	String quickPayH5(BaseMsgReq merchantBaseMsg) throws Exception;
	
	/**
	 * 扫码支付查询接口回应
	 * @param merchantBaseMsg
	 * @param rsp
	 * @return
	 * @throws Exception
	 */
	String buildQuickPayH5RspMsg(BaseMsgRsp merchantBaseMsg, UM_SCAN_CODE_RSP rsp) throws Exception;

	/**
	 * 订单查询
	 * @param merchantBaseMsg
	 * @return
	 * @throws Exception
	 */
	String query(BaseMsgReq merchantBaseMsg) throws Exception;
	
	/**
	 * 构造订单查询应答报文
	 * @param merchantBaseMsg
	 * @param rsp
	 * @return
	 * @throws Exception
	 */
	String buildPayQryRspMsg(BaseMsgRsp merchantBaseMsg, ChanlPayQryRsp rsp) throws Exception;

}
