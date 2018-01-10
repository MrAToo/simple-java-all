package com.mrdu.simple.java8.lambda;

import org.junit.Test;

public class LambdaSimple {
	
	@Test
	public void lambda(){
		String string = hello("张三",(name)->{
			return "Hello:"+name;
		});
		System.out.println(string);
	}

	public String hello(String name,IFunInterface funInterface) {
		return funInterface.say(name);
	}

}
