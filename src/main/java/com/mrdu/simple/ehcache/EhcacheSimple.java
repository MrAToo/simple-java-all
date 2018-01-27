package com.mrdu.simple.ehcache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class EhcacheSimple {
	
	
	public static void main(String[] args) {
		//创建缓存管理器
		CacheManager cacheManager = CacheManager.create("./src/main/resources/ehcache.xml");
		//创建缓存实例
		Cache cache = cacheManager.getCache("helloworld");
		//创建元素
		Element element = new Element("key", "value");
		//缓存元素
		cache.put(element);
		//从缓存中获取元素
		Element element2 = cache.get("key");
		System.out.println(element2.getObjectValue());
	}

}
