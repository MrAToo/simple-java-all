package com.mrdu.simple.juc;


/**
 * 使用volatile关键字解决内存可见性问题
 * flag为共享变量
 */
public class VolatileSimple {
	
	public static void main(String[] args) {
		ThreadA threadA = new ThreadA();
		new Thread(threadA).start();
		
		while(true){
			if(threadA.isFlag()){
				System.out.println("-----------------");
				break;
			}
		}
	}

}


class ThreadA implements Runnable{
	
	private volatile boolean flag = false;

	@Override
	public void run() {
		flag = true;
		System.out.println("ThreadA:"+flag);
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	
}