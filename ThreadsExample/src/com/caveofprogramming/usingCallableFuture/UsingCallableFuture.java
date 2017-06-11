package com.caveofprogramming.usingCallableFuture;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class UsingCallableFuture {

	public static void main(String[] args) {
		ExecutorService es = Executors.newCachedThreadPool();
		
		Future<Integer> future = es.submit(new Callable<Integer>() {

			@Override
			public Integer call() throws Exception {
				Random r = new Random();
				int duration = r.nextInt(1000);
				System.out.println("Starting..");
				Thread.sleep(duration);
				System.out.println("Done..");
				return duration;
			}
		});
		
		es.shutdown();
		
		try {
			es.awaitTermination(1, TimeUnit.DAYS);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		try {
			System.out.println("Duration is "+future.get());
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}

}
