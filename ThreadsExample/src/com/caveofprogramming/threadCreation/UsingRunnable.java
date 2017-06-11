package com.caveofprogramming.threadCreation;

class RunnerClass implements Runnable {

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
	
}

public class UsingRunnable {

	public static void main(String[] args) {
		RunnerClass runn = new RunnerClass();
		
		Thread t1 = new Thread(runn);
		t1.setName("1st thread");
		
		Thread t2 = new Thread(runn);
		t2.setName("2nd Thread");
		
		t1.start();
		t2.start();
		
		RunnerClass rc =new RunnerClass();
		rc.run();
	}
}
