package com.mrdu.simple.security.rsa;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;

public class RSASign {
	
	private static String msg = "需要加密的字符串";
	public static final String SIGN_ALGORITHMS = "SHA1WithRSA";
	
	public static void main(String[] args) throws Exception {
		Map<String, String> generatorKey = generatorKey();
		String sign = sign(msg, generatorKey.get("privateKey"));
		System.out.println(sign);
		boolean signCheck = signCheck(msg, sign, generatorKey.get("publicKey"));
		System.out.println(signCheck);
	}
	
	
	public static boolean signCheck(String content,String sign,String publicKeyStr) throws Exception{
		X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Base64.decodeBase64(publicKeyStr));
		
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
		MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
		byte[] digest = messageDigest.digest(content.getBytes());
		
		Signature signature = Signature.getInstance(SIGN_ALGORITHMS);
		signature.initVerify(publicKey);
		signature.update(digest);
		return signature.verify(Base64.decodeBase64(sign));
	}
	
	public static String sign(String content,String privateKeyStr) throws Exception{
		PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKeyStr));
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
		
		MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
		byte[] digest = messageDigest.digest(content.getBytes());
		
		Signature signature = Signature.getInstance(SIGN_ALGORITHMS);
		signature.initSign(privateKey);
		signature.update(digest);
		byte[] sign = signature.sign();
		return Base64.encodeBase64String(sign);
	}
	
	/**
	 * 
	 * 生成公钥秘钥
	 */
	public static Map<String, String> generatorKey() throws NoSuchAlgorithmException{
		Map<String, String> map = new HashMap<>();
		//生成秘钥
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
		keyPairGenerator.initialize(2048);
		KeyPair keyPair = keyPairGenerator.generateKeyPair();
		byte[] publicKey = keyPair.getPublic().getEncoded();
		byte[] privateKey = keyPair.getPrivate().getEncoded();
		
		System.out.println("publicKey:"+Base64.encodeBase64String(publicKey));
		System.out.println("privateKey:"+Base64.encodeBase64String(privateKey));
		
		map.put("publicKey", Base64.encodeBase64String(publicKey));
		map.put("privateKey", Base64.encodeBase64String(privateKey));
		return map;
	}

}
