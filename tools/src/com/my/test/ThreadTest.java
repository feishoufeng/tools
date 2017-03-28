package com.my.test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ThreadTest {
	public static void main(String[] args) {
		thread1 th1 = new thread1();
		thread2 th2 = new thread2();
		thread3 th3 = new thread3();
		th1.start();
		th2.start();
		th3.run();
	}
}

class thread1 extends Thread {
	public void run() {
		try {
			sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mainThread.print("thread1");
	}
}

class thread2 extends Thread {
	public void run() {
		mainThread.print("thread2");
	}
}

class thread3 implements Runnable {

	@Override
	public void run() {
		// TODO Auto-generated method stub
		mainThread.print("thread3");
	}

}

class mainThread {
	public synchronized static void print(String threadName) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
		System.out.println(sdf.format(new Date()) + "   " + threadName);
	}
}
