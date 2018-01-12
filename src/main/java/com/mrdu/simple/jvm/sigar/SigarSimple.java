package com.mrdu.simple.jvm.sigar;

import java.text.DecimalFormat;

import org.bouncycastle.asn1.crmf.PKIPublicationInfo;
import org.hyperic.sigar.Cpu;
import org.hyperic.sigar.FileSystem;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;


/**
 * 使用Sigar获取系统信息
 * 1.将对应系统的so或者dll文件放到JAVA_HOME/bin下
 * 2.添加jar包或者maven依赖
 */
public class SigarSimple {
	
	
	public static void main(String[] args) {
		memory();
		cpu();
		disk();
	}
	
	
	/**
	 * 获取内存信息
	 */
	public static void memory(){
		Sigar sigar = new Sigar();
		try {
			Mem mem = sigar.getMem();
			long total = mem.getTotal();
			long free = mem.getFree();
			DecimalFormat decimalFormat = new DecimalFormat("#######.##");
			String totalMem = decimalFormat.format((double)total/1024/1024/1024)+"G";
			String freeMem = decimalFormat.format((double)free/1024/1024/1024)+"G";
			String usageRate = decimalFormat.format(((double)free/(double)total)*100.0)+"%";
			System.out.println("总内存:"+totalMem);
			System.out.println("使用内存:"+freeMem);
			System.out.println("使用率:"+usageRate);
		} catch (SigarException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 获取CPU信息
	 */
	public static void cpu(){
		Sigar sigar = new Sigar();
		try {
			Cpu cpu = sigar.getCpu();
			long total = cpu.getTotal();
			long idle = cpu.getIdle();
			System.out.println(total);
			System.out.println(idle);
		} catch (SigarException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取磁盘信息
	 */
	public static void disk(){
		Sigar sigar = new Sigar();
		try {
			FileSystem[] list = sigar.getFileSystemList();
			for(FileSystem fs:list){
				String dirName = fs.getDirName();
				System.out.println(dirName);
			}
		} catch (SigarException e) {
			e.printStackTrace();
		}
	}

}
