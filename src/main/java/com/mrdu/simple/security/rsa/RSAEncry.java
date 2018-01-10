package com.mrdu.simple.security.rsa;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;

public class RSAEncry {
	
	private static String msg = "需要加密的字符串";
	
	public static void main(String[] args) throws Exception {
		Map<String, String> generatorKey = generatorKey();
		String encode = encode(generatorKey.get("publicKey"), msg);
		System.out.println(encode);
		String decode = decode(generatorKey.get("privateKey"), encode);
		System.out.println(decode);
	}
	
	
	/**
	 * 解密 返回原字符串
	 * @param privateKeyStr
	 * @param encodeStr
	 * @return
	 * @throws Exception 
	 */
	public static String decode(String privateKeyStr,String encodeStr) throws Exception{
		PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKeyStr));
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
		
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		byte[] doFinal = cipher.doFinal(Base64.decodeBase64(encodeStr));
		return new String(doFinal);
	}

	/**
	 * 加密 返回加密后的字符串
	 * @param publicKeyStr 公钥Base64编码后的字符串
	 * @param msg 需要加密的消息
	 * @return
	 * @throws Exception 
	 */
	public static String encode(String publicKeyStr,String msg) throws Exception{
		X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Base64.decodeBase64(publicKeyStr));
		
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
		
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		byte[] doFinal = cipher.doFinal(msg.getBytes());
		return Base64.encodeBase64String(doFinal);
	}
	
	
	/**
	 * 
	 * 生成公钥秘钥
	 */
	public static Map<String, String> generatorKey() throws NoSuchAlgorithmException{
		Map<String, String> map = new HashMap<>();
		//生成秘钥
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
		//初始化秘钥生成长度,必须是64的整数倍
		keyPairGenerator.initialize(2048);
		//生成KeyPair,KeyPair用于生成公钥和私钥
		KeyPair keyPair = keyPairGenerator.generateKeyPair();
		byte[] publicKey = keyPair.getPublic().getEncoded();
		byte[] privateKey = keyPair.getPrivate().getEncoded();
		
		System.out.println("publicKey Base64:"+Base64.encodeBase64String(publicKey));
		System.out.println("privateKey Base64:"+Base64.encodeBase64String(privateKey));
		
		map.put("publicKey", Base64.encodeBase64String(publicKey));
		map.put("privateKey", Base64.encodeBase64String(privateKey));
		return map;
	}
}
