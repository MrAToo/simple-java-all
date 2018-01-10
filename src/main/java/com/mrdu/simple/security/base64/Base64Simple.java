package com.mrdu.simple.security.base64;

import org.apache.commons.codec.binary.Base64;
import org.junit.Test;

public class Base64Simple {
	
	@Test
	public void base64(){
		
		String string = "Hello World!";
		String encode = encode(string);
		System.out.println(encode);
		String decode = decode(encode);
		System.out.println(decode);
	}

	//编码
	public String encode(String str){
		return Base64.encodeBase64String(str.getBytes());
	}
	
	//解码
	public String decode(String code){
		return new String(Base64.decodeBase64(code));
	}
}
