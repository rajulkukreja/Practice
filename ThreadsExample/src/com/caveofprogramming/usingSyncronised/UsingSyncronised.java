package com.caveofprogramming.usingSyncronised;

public class UsingSyncronised {
	
	private int count = 0;
	
	public synchronized void increment() {
		count++;
	}
	
	public static void main(String[] args) {
		UsingSyncronised us = new UsingSyncronised();
		us.doWork();
	}

	private void doWork() {
		Thread t1 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				for(int i=0;i<100;i++){
					increment();
				}
			}
		});
		
		
		Thread t2 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				for(int i=0;i<100;i++){
					increment();
				}
			}
		});
		
		t1.start();
		t2.start();
		System.out.println("Count is "+count);
		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Count is "+count);
	}

}
