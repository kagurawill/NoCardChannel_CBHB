package com.rd.channel.common.util;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;


/**
 * 下游商户工具类
 * @author rongda
 * 2016-10.31
 *
 */
public class MerchantUtil {
	private static final Logger logger = LoggerFactory.getLogger(MerchantUtil.class);
	public final static String CHARSET = "UTF-8";
	public final static String SIGNTYPE_MD5="MD5";
	public final static String SIGNTYPE_RSA="RSA";
	/**
	 * 亿商城签名
	 * @param sParaTemp
	 * @param key
	 * @param inputCharset
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> yscSign(Map<String, String> sParaTemp,String key, String inputCharset) throws Exception {
        // 除去数组中的空值和签名参数
        Map<String, String> sPara = paraFilter(sParaTemp);
        // 生成签名结果
        String mysign = buildRequestByMD5(sPara, key, inputCharset);
        // 签名结果与签名方式加入请求提交参数组中
        logger.info("[加签]签名结果:" +  mysign);
        sPara.put("sign", mysign.toUpperCase(Locale.US));
        return sPara;
    }
	
	/**
	 * 亿商城验签
	 * @param paramMap
	 * @param key
	 * @param charset
	 * @return
	 * @throws Exception
	 */
	public static boolean yscVerify(Map<String, String> paramMap, String key, String charset) throws Exception {
		String sign = paramMap.get("sign").toLowerCase(Locale.US);
		paramMap.remove("sign");
		if(StringUtils.isBlank(sign)){
			logger.info("验签失败，签名字段不能为空");
			return false;
		}else{
			 Map<String, String> sPara = paraFilter(paramMap);
		     String text = createLinkString(sPara, false , charset);
		     return MD5.verify(text, sign, key, charset);
		}
	}
	
	/**
	 * 签名
	 * @param jsonStr 签名串
	 * @param key 签名密钥
	 * @param signType 签名类型 MD5，RSA
	 * @param inputCharset 编码 默认编码是UTF-8
	 * @return 签名结果
	 * @throws Exception
	 */
	public static String sign(String jsonStr, String key, String signType, String inputCharset) throws Exception{
		String mysign = null;
		if(StringUtils.isBlank(inputCharset)){
			inputCharset = CHARSET;
		}
		if(SIGNTYPE_MD5.equals(signType)){
			mysign = MD5.sign(jsonStr, key, inputCharset);
		}else if(SIGNTYPE_RSA.equals(signType)){
			mysign = RSA.sign(jsonStr, key, inputCharset);
		}else{
			throw new RuntimeException("未知签名方式，丢弃");
		}
		return mysign;
	}
	
	/**
	 * 验签
	 * @param text 签名串
	 * @param sign 签名结果
	 * @param key 签名密钥
	 * @param signType 签名方式
	 * @param inputCharset 编码
	 * @return 验签结果
	 * @throws Exception
	 */
	public static boolean verify(String text,String sign, String key, String signType, String inputCharset) throws Exception {
		boolean isTrue = false;
		if(StringUtils.isBlank(inputCharset)){
			inputCharset = CHARSET;
		}
		if(SIGNTYPE_MD5.equals(signType)){
			isTrue = MD5.verify(text, sign, key, inputCharset);
		}else if(SIGNTYPE_RSA.equals(signType)){
			isTrue = RSA.verify(text, sign, key, inputCharset);
		}else{
			throw new RuntimeException("未知签名方式，丢弃");
		}
		return isTrue;
	}
	
	/**
	 * 验签
	 * @param text 签名串
	 * @param key 签名密钥
	 * @param signType 签名类型 MD5，RSA
	 * @param inputCharset 编码 默认编码是UTF-8
	 * @return
	 * @throws Exception
	 */
	public static boolean verify(String text, String key, String signType, String inputCharset) throws Exception {
		boolean isTrue = false;
		JSONObject rsqStr =  JSON.parseObject(text);
		String sign = rsqStr.getString("signData");
		rsqStr.remove("signData");//验签串不包含signData
		text = rsqStr.toJSONString();
		if(StringUtils.isBlank(inputCharset)){
			inputCharset = CHARSET;
		}
		if(SIGNTYPE_MD5.equals(signType)){
			isTrue = MD5.verify(text, sign, key, inputCharset);
		}else if(SIGNTYPE_RSA.equals(signType)){
			isTrue = RSA.verify(text, sign, key, inputCharset);
		}else{
			throw new RuntimeException("未知签名方式，丢弃");
		}
		return isTrue;
	}
	
	/**
     * 生成要请求给支付平台的参数数组
     * 
     * @param sParaTemp
     *            请求前的参数数组
     * @param key
     *            md5 key
     * @param inputCharset
     *            编码
     * @return 要请求的参数数组
     * @throws Exception
     */
    public static Map<String, String> buildRequestPara(Map<String, String> sParaTemp,String key, String inputCharset) throws Exception {
        // 除去数组中的空值和签名参数
        Map<String, String> sPara = paraFilter(sParaTemp);
        // 生成签名结果
        String mysign = buildRequestByMD5(sPara, key, inputCharset);
        // 签名结果与签名方式加入请求提交参数组中
        logger.info("[加签]签名结果:" +  mysign);
        sPara.put("signData", mysign);
        return sPara;
    }
	
    /**
     * 验签
     * @param paramMap 参数数组
     * @param key md5 key
     * @param charset 编码
     * @return
     * @throws Exception
     */
	public static boolean verify(Map<String, String> paramMap, String key, String charset) throws Exception {
		String sign = paramMap.get("signData");
		if(StringUtils.isBlank(sign)){
			logger.info("验签失败，签名字段不能为空");
			return false;
		}else{
			 Map<String, String> sPara = paraFilter(paramMap);
		     String text = createLinkString(sPara, false , charset);
		     return MD5.verify(text, sign, key, charset);
		}
	}
	
	
	 /**
     * 除去数组中的空值和签名参数
     *
     * @param sArray
     *            签名参数组
     * @return 去掉空值与签名参数后的新签名参数组
     */
    public static Map<String, String> paraFilter(Map<String, String> sArray) {
        Map<String, String> result = new HashMap<String, String>();
        if (sArray == null || sArray.size() <= 0) {
            return result;
        }
        DecimalFormat formater = new DecimalFormat("###0.00");
        for (Entry<String, String> set : sArray.entrySet()) {
        	String finalValue = null;
        	Object value = sArray.get(set.getKey());
            if(value instanceof BigDecimal){
				finalValue = formater.format(value);
			}else {
				finalValue = (String) value;
			}
            if (value == null || value.equals("")
					||  set.getKey().equalsIgnoreCase("signData")) {
                continue;
            }
            result.put(set.getKey(), finalValue);
        }
        return result;
    }
    
    
    /**
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     *
     * @param params
     *            需要排序并参与字符拼接的参数组
     * @param encode
     *            是否需要urlEncode
     * @return 拼接后字符串
     */
    public static String createLinkString(Map<String, String> params, boolean encode, String charset) {
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);
        StringBuffer prestr = new StringBuffer();
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);
            if (encode) {
                try {
                    value = URLEncoder.encode(value, charset);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
                prestr.append(key).append("=").append(value);
            } else {
            	prestr.append(key).append("=").append(value).append("&");
            }
        }
        return prestr.toString();
    }
    
    /**
     * 生成MD5签名结果
     *
     * @param sPara
     *            要签名的数组
     * @return 签名结果字符串
     */
    private static String buildRequestByMD5(Map<String, String> sPara, String key, String inputCharset) throws Exception {
        String prestr = createLinkString(sPara, false , inputCharset); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
        String mysign = MD5.sign(prestr, key, inputCharset);
        return mysign;
    }
    
    /**
     * RSA加密：指定加密数据编码
     * @param src 源数据
     * @param publicKey 加密密钥
     * @param charset 数据编码  不指定编码默认编码是UTF-8
     * @return 加密后的字符串
     * @throws Exception
     */
    public static String encryptDataByRSA(String src,String publicKey,String charset) throws Exception{
    	try {
    		if(StringUtils.isBlank(charset)){
    			charset = CHARSET;
    		}
    		byte[] bytes = RSA.encryptByPublicKey(src.getBytes(charset), publicKey);
    		return Base64.encodeBase64String(bytes);
		} catch (Exception e) {
			logger.error("RSA加密出错",e);
			throw e;
		}
    }
    
    
    /**
     * RSA解密：指定编码
     * @param src 加密数据
     * @param privateKey 解密密钥
     * @param charset 指定的字符集  不指定默认编码是UTF-8
     * @return 解密后的数据
     * @throws Exception
     */
    public static String decryptDataByRSA(String src,String privateKey,String charset) throws Exception{
    	try {
    		if(StringUtils.isBlank(charset)){
    			charset = CHARSET;
    		}
    		byte[] bytes = Base64.decodeBase64(src);
    		byte[] decryptData = RSA.decryptByPrivateKey(bytes, privateKey);
    		return new String(decryptData,charset);
		} catch (Exception e) {
			logger.error("RSA解密出错",e);
			throw e;
		}
    }
    
    /**
     * AES加密
     * @param src 加密数据
     * @param aesKey （BASE64编码）
     * @param charset 编码，默认UTF-8
     * @return 加密后数据 （BASE64编码）
     * @throws Exception 
     */
    public static String encryptDataByAES(String src, String aesKey, String charset) throws Exception{
    	try{
    		if(StringUtils.isBlank(charset)){
    			charset = CHARSET;
    		}
    		byte[] bytes = AES.encrypt(src.getBytes(CHARSET), aesKey);
    		return Base64.encodeBase64String(bytes);
    	}catch(Exception e){
    		logger.error("AES加密出错",e);
    		throw e;
    	}
    }
    
    /**
     * AES解密
     * @param src 密文（BASE64编码）
     * @param aesKey 密钥 （BASE64编码）
     * @param charset 编码，默认UTF-8
     * @return 解密后数据
     * @throws Exception 
     */
    public static String decryptDataByAES(String src,String aesKey, String charset) throws Exception{
    	try{
    		if(StringUtils.isBlank(charset)){
    			charset = CHARSET;
    		}
    		byte[] srcByte = Base64.decodeBase64(src);
        	byte[] bytes =AES.decrypt(srcByte, aesKey);
        	return new String(bytes, charset);
    	}catch(Exception e){
    		logger.error("AES解密出错",e);
    		throw e;
    	}
    }
    
    public  static String getUrlStr(Map<String,String> transMap){
		//组织需要加密的字符串
		StringBuffer transStr = new StringBuffer();
		int flag=0;
		for(Entry<String,String> set:transMap.entrySet()) 
		{
			if((transMap.size()-1)==flag){
				transStr.append(set.getKey()).append("=").append(transMap.get(set.getKey()));
			}else{
				transStr.append(set.getKey()).append("=").append(transMap.get(set.getKey())).append("&");
			}
			flag++;
		} 
		return 	transStr.toString();
	}
}
