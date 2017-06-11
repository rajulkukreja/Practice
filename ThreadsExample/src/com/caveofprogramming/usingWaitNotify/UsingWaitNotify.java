package com.caveofprogramming.usingWaitNotify;

import java.util.Scanner;

class WaitNotifyProcessor {
	
	public void producer() throws InterruptedException {
		synchronized(this) {
			System.out.println("Starting producer");
			wait();
			System.out.println("Producer resumed");
		}
	}

	public void consumer() throws InterruptedException {
		Scanner sc =new Scanner(System.in);
		Thread.sleep(100);
		synchronized(this) {
			System.out.println("Waiting for return key");
			sc.nextLine();
			notify();
			Thread.sleep(100);
			System.out.println("Consumer done");
		}
	}
}

public class UsingWaitNotify {

	public static void main(String[] args) {
		WaitNotifyProcessor wnp = new WaitNotifyProcessor();
		
		Thread t1 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					wnp.producer();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		
		Thread t2 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					wnp.consumer();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		
		t1.start();
		t2.start();
		
		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}

