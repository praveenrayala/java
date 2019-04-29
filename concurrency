package com.bhama.thread.executors;

import java.util.Iterator;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class Concurrency {

	static ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(200);
	public static void main(String[] args) {
		
		
		for (int i = 1; i <= 200; i++) {
			queue.add(i);
		}
		
		

		CountDownLatch latch = new CountDownLatch(2);
		
		ConcurrentHashMap< String, Integer> countPartialMap = new ConcurrentHashMap<>();
		CyclicBarrier cb = new CyclicBarrier(2,new Runnable() {
			
			@Override
			public void run() {
				Integer totalSum = 0;
				for (Iterator<String> iterator = countPartialMap.keySet().iterator(); iterator.hasNext();) {
					String threadName = (String) iterator.next();
					int partialSum = countPartialMap.get(threadName);
					System.out.println("Partial sum of " + threadName + " is : " + partialSum);
					totalSum =totalSum+partialSum;
					System.out.println("Total sum is :" + totalSum);
				}
				
			}
		});

		Thread t = new Thread(new WorkerRunnable("Thread 1",latch,cb,countPartialMap));
		t.start();
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Thread t1 = new Thread(new WorkerRunnable("Thread 2",latch,cb,countPartialMap));
		t1.start();
	
		
		
	}
}

class WorkerRunnable implements Runnable {

	private String threadName;
	private CountDownLatch countdownlatch;
	private ConcurrentHashMap<String, Integer> countPartialMap;
	private CyclicBarrier cb;
	public WorkerRunnable(String threadName, CountDownLatch latch, CyclicBarrier cb, ConcurrentHashMap<String, Integer> countPartialMap) {
		this.threadName = threadName;
		this.countdownlatch = latch;
		this.countPartialMap = countPartialMap;
		this.cb=cb;
	}

	@Override
	public void run() {
		Thread.currentThread().setName(threadName);
		countdownlatch.countDown();

		try {
			System.out.println("waiting for signla for entry" +threadName);
			countdownlatch.await();
			System.out.println("signal released for start with all threads are ok " +threadName);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int i = 0;
		Integer sum = 0;
		while(!Concurrency.queue.isEmpty()){
			
			try {
				i = Concurrency.queue.take();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("adding " + i +"  threadName " +threadName);
			sum= sum +i;
			
		}
		countPartialMap.put(threadName, sum);
		
		
		try {
			
			System.out.println("waiting for signla for exit" +threadName);
			cb.await();
			System.out.println("signal released exit with all threads" +threadName);
		} catch (InterruptedException | BrokenBarrierException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

}}
