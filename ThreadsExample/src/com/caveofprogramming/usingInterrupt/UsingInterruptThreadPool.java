package com.caveofprogramming.usingInterrupt;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class UsingInterruptThreadPool {

	public static void main(String[] args) {
		ExecutorService es = Executors.newCachedThreadPool();
		
		Future<?> future = es.submit(new Callable<Void>() {

			@Override
			public Void call() throws Exception {
				int i=0;
				while(true) {
					if(Thread.currentThread().isInterrupted()) {
						System.out.println("Interrupted");
						break;
					}
					System.out.println("Hello" +i);
					i++;
				}
				return null;
			}
		});
		
		es.shutdown();
		
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		es.shutdownNow();
//		future.cancel(true);
		
		try {
			es.awaitTermination(1, TimeUnit.DAYS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("All done");
		
	}

}
;