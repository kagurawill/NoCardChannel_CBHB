package com.rd.channel.common.util;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class AES {
	/**
	 * 密钥算法
	 */
	private final static String KEY_ALGORITHM = "AES";
	/**
	 * <p>
	 * Cipher转换格式：算法/模式/填充、算法
	 * </p>
	 * <p>
	 * 可选值：
	 * </p>
	 * <p>
	 * 算法:AES
	 * </p>
	 * <p>
	 * 模式:CBC，CFB，ECB，OFB，PCBC
	 * </p>
	 * <p>
	 * 填充:NoPadding，PKCS5Padding，ISO10126Padding
	 * </p>
	 */
	private final static String CIPHER_ALGORITHNM = "AES";
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String getAutoCreateAESKey() throws Exception{  
		KeyGenerator kg = KeyGenerator.getInstance(CIPHER_ALGORITHNM);
		kg.init(128);//要生成多少位，只需要修改这里即可128, 192或256  
        SecretKey sk = kg.generateKey();  
        return Base64.encodeBase64String(sk.getEncoded());
	}
	
	
	
	/**
	 * 加密
	 * @param data
	 * @param aesKey
	 * @return
	 */
	public static byte[] encrypt(byte[] data,String aesKey) throws Exception {
		byte[] encryptBytes = Base64.decodeBase64(aesKey);
		SecretKeySpec key = new SecretKeySpec(encryptBytes, KEY_ALGORITHM);  
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHNM);// 创建密码器    使用AES 算法 加密，默认模式  AES/ECB 
        cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化   
        return cipher.doFinal(data);
	}
	
	/**
	 * AES解密
	 * @param data 密文
	 * @param aesKey 密钥 (BASE64编码)
	 * @return
	 * @throws Exception 
	 */
	public static byte[] decrypt(byte[] data, String aesKey) throws Exception {
		byte[] encryptBytes = Base64.decodeBase64(aesKey);
        SecretKeySpec key = new SecretKeySpec(encryptBytes, KEY_ALGORITHM);              
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHNM);// 创建密码器   
        cipher.init(Cipher.DECRYPT_MODE, key);// 初始化   
        return cipher.doFinal(data);
	}
}
