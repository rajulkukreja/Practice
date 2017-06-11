package com.caveofprogramming.usingCountDownLatches;

import java.util.concurrent.*;

class LatchProcessor implements Runnable {
	CountDownLatch latch;
	int id;
	
	public LatchProcessor(int i,CountDownLatch latch) {
		this.latch = latch;
		this.id = i;
	}
	
	@Override
	public void run() {
		System.out.println("Started Thread "+id);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Completed Thread "+id);
		latch.countDown();
	}
	
}

public class UsingCountdownLatches {

	public static void main(String[] args) {
		CountDownLatch latch = new CountDownLatch(6);
		
		ExecutorService executor = Executors.newFixedThreadPool(3);
		for(int i=0;i<6;i++) {
			executor.submit(new LatchProcessor(i,latch));
		}
		
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Completed");
	}

}
