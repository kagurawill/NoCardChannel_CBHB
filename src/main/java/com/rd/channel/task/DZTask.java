package com.rd.channel.task;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.rd.channel.common.util.TimeUtil;

//@Component
@Service
public class DZTask {
	//private static final Logger logger = LoggerFactory.getLogger(DZTask.class);
	/*@Value("${channel.pingan.openKey}")
	private String orgOpenKey; //解密算法
	@Value("${channel.pingan.openId}")
	private String orgOpenId; //
	@Value("${channel.pingan.orgPayUrl}")
	private String orgPayUrl; //
	@Value("${channel.pingan.privateKey}")
	private String orgPrivateKey; //
	@Value("${channel.pingan.filePath}")
	private String filePath; */
	
	public void dz(){
		downloadBill(TimeUtil.getSpecifiedDayBefore(new Date(), "yyyy-MM-dd"), "gzip");
	}
	
	public void downloadBill(String day, String tarType) {
		//TCOS_DZ_REQ req = new TCOS_DZ_REQ();
		//BaseMsg baseMsg = new BaseMsg();
	}
}
