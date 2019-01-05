package com.caveofprogramming.threadCreation;

public class UsingRunnableAnonymousBlock {

	public static void main(String[] args) {
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				for(int i=0;i<10;i++) {
					System.out.println("Value is "+i+ " Thread name is "+Thread.currentThread().getName());
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		
		t1.start();
		
		
		Thread t2 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				System.out.println("abcde");
			}
		});
		t2.start();
	}

}
