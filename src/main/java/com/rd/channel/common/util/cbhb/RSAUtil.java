package com.rd.channel.common.util.cbhb;

import java.io.UnsupportedEncodingException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;


public class RSAUtil {
	private static final Logger logger = LoggerFactory.getLogger(RSAUtil.class);
	public static final int MAX_ENCRYPT_BLOCK = 117;
	public static final int MAX_DECRYPT_BLOCK = 128;
	public static final String KEY_ALGORTHM = "RSA";//
	public static final String SIGNATURE_ALGORITHM = "MD5withRSA";

	/**
	 * 用私钥对信息生成数字签名
	 */
	public static String sign(String data, String privateKey) {
		try {
			byte[] dataBytes = new BASE64Decoder().decodeBuffer(data);
			byte[] keyBytes = new BASE64Decoder().decodeBuffer(privateKey);
			PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(
					keyBytes);
			KeyFactory key = KeyFactory.getInstance(KEY_ALGORTHM);
			PrivateKey privateKey2 = key.generatePrivate(pkcs8EncodedKeySpec);
			// 用私钥对信息生成数字签名
			Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
			signature.initSign(privateKey2);
			signature.update(dataBytes);
			return new BASE64Encoder().encode(signature.sign());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

    /**
     * @param content
     * @param charset
     * @return
     * @throws SignatureException
     * @throws UnsupportedEncodingException
     */
    private static byte[] getContentBytes(String content, String charset) {
        if (charset == null || "".equals(charset)) {
            return content.getBytes();
        }
        try {
            return content.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("签名过程中出现错误。指定的编码集不对,您目前指定的编码集是:" + charset);
        }
    }
    
	/**
	 * 校验数字签名
	 */
	public static boolean verify(String data, String publicKey, String sign) {
		try {
			byte[] dataBytes = new BASE64Decoder().decodeBuffer(data);
			byte[] keyBytes = new BASE64Decoder().decodeBuffer(publicKey);
			X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(
					keyBytes);
			KeyFactory key = KeyFactory.getInstance(KEY_ALGORTHM);
			PublicKey publicKey2 = key.generatePublic(x509EncodedKeySpec);
			Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
			signature.initVerify(publicKey2);
			signature.update(dataBytes);

			return signature.verify(new BASE64Decoder().decodeBuffer(sign));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * 产生密钥对
	 */
	public static void getKeyPairs() {
		KeyPairGenerator keyPairGenerator;
		try {
			keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORTHM);
			keyPairGenerator.initialize(1024);
			KeyPair keyPair = keyPairGenerator.generateKeyPair();
			// 公钥
			RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
			logger.info("公钥：[{}]", new BASE64Encoder().encode(publicKey.getEncoded()));
			// 私钥
			RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
			logger.info("私钥：[{}]", new BASE64Encoder().encode(privateKey.getEncoded()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}