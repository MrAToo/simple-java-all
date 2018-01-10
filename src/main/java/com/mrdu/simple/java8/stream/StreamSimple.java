package com.mrdu.simple.java8.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.Test;

public class StreamSimple {
	
	@Test
	public void stream(){
		List<String> list = new ArrayList<>();
		list.add("Java");
		list.add("Mysql");
		list.add("IOS");
		list.add("IOS");
		list.add("oracle");
		list.add("db2");
		list.add("db2");
		list.add("php");
		list.add("php");
		list.add("HTML");
		Stream<String> stream = list.stream();
		//去重打印
		//stream.distinct().forEach(System.out::println);
		//去重后得到新的集合
//		List<String> collect = stream.distinct().collect(Collectors.toList());
//		collect.forEach(System.out :: println);
		//过滤数据
		//stream.filter((str) -> str.indexOf("h")>0).forEach(System.out :: println);
		//处理数据过后过滤
//		List<String> collect = stream.map(str -> str.toUpperCase()).filter((str) -> str.contains("JAVA")).collect(Collectors.toList());
//		collect.forEach(System.out :: println);
		//分页
//		stream.skip(3).limit(5).forEach(System.out :: println);
		//使用MapReduce实现统计
	}

}
