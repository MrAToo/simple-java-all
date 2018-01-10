package com.mrdu.simple.jvm.arg;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JvmArgSimple {
	
	public static void main(String[] args) {  
		systemProp();
		getDisk().forEach(System.out::println);
    } 

	
	// 获取文件系统使用率  
    public static List<Map<String, Long>> getDisk() {  
  
        // 操作系统  
        List<Map<String, Long>> list = new ArrayList<Map<String, Long>>();  
  
        for (char c = 'A'; c <= 'Z'; c++) {  
            String dirName = c + ":/";  
            File win = new File(dirName);  
            if (win.exists()) {  
                long total = (long) win.getTotalSpace();  
                long free = (long) win.getFreeSpace();
                Map<String, Long> map = new HashMap<>();
                map.put("total", total/1024/1024/1024);
                map.put("free", free/1024/1024/1024);
//                Double compare = (Double) (1 - free * 1.0 / total) * 100;  
//                String str = c + ":盘已使用" + compare.intValue() + "%";  
                list.add(map);  
            }  
        }  
        return list;  
    } 
    
    
    public static void systemProp(){
    	String[] keys = new String[] { "java.version", "java.vendor",  
                "java.vendor.url", "java.home",  
                "java.vm.specification.version",  
                "java.vm.specification.vendor", "java.vm.specification.name",  
                "java.vm.version", "java.vm.vendor", "java.vm.name",  
                "java.specification.version", "java.specification.vendor",  
                "java.specification.name", "java.class.version",  
                "java.class.path", "java.library.path", "java.io.tmpdir",  
                "java.compiler", "java.ext.dirs", "os.name", "os.arch",  
                "os.version", "file.separator", "path.separator",  
                "line.separator", "user.name", "user.home", "user.dir" };  
  
        int size = keys.length;  
  
        for (int i = 0; i < size; i++) {  
            System.out.println(keys[i] + ": " + System.getProperty(keys[i]));  
        }
    }

}
