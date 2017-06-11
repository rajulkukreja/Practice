package com.caveofprogramming.usingThreadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class Processor extends Thread {
	private int id;
	
	Processor(int id) {
		this.id = id;
	}
	
	public void run() {
		System.out.println("Started Thread "+id);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Completed Thread "+id);
	}
}

public class UsingThreadPool {

	public static void main(String[] args) {
		ExecutorService es = Executors.newFixedThreadPool(2);
		
		for(int i=0;i<5;i++) {
			es.submit(new Processor(i));
		}
		
		es.shutdown();
		System.out.println("All tasks submitted");
		try {
			es.awaitTermination(1, TimeUnit.DAYS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("All tasks completed");
	}

}
