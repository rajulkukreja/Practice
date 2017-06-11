package com.caveofprogramming.usingSemaphore;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class UsingSemaphore {

	public static void main(String[] args) {
		ExecutorService es = Executors.newCachedThreadPool();
		
		for (int i=0;i<200;i++) {
			es.submit(new Runnable() {
				
				@Override
				public void run() {
					Connection.getInstance().connect();
				}
			});
		}
		
		es.shutdown();
		
		try {
			es.awaitTermination(1, TimeUnit.DAYS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("All Done");
	}

}

class Connection {
	private static Connection instance = new Connection();
	private int connections = 0;
	Semaphore sem = new Semaphore(10);
	
	public static Connection getInstance() {
		return instance;
	}
	
	public void connect() {
		try {
			sem.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		try {
			doConnect();
		} finally {
			sem.release();
		}
	}

	private void doConnect() {
		synchronized(this) {
			connections++;
		}
		System.out.println("No of connections are "+connections);
		
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		synchronized(this) {
			connections--;
		}
	}
}