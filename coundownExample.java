package com.bhama.thread.executors;

import java.util.concurrent.CountDownLatch;

public class coundownExample {

	
	public static void main(String[] args) {
		
		CountDownLatch latch = new CountDownLatch(2);
		
		Thread t = new Thread(new CDRunnable("Thread 1",latch));
		t.start();
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Thread t1 = new Thread(new CDRunnable("Thread 2",latch));
		t1.start();
		
	}
}

class CDRunnable implements Runnable{
	
	private String threadName;
	private CountDownLatch countdownlatch;
	public CDRunnable(String threadName,CountDownLatch latch) {
		this.threadName= threadName;
		this.countdownlatch= latch;
	}
	@Override
	public void run() {
		Thread.currentThread().setName(threadName);
		
		for (int i = 0; i < 50; i++) {
			System.out.println("running at " + i +"  "+ threadName);
		}
		countdownlatch.countDown();
		try {
			System.out.println("waiting for signla for exit" +threadName);
			countdownlatch.await();
			System.out.println("signal released exit with all threads" +threadName);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
