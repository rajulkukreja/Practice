package com.caveofprogramming.usingWaitNotifyLock;

import java.util.LinkedList;

class WaitNotifyLockProcessor {
	private LinkedList<Integer> list = new LinkedList<Integer>();
	private final int LIMIT = 10;
	private Object lock = new Object();
	
	public void producer() throws InterruptedException {
		int value =0;
		while(true) {
			synchronized(lock) {
				while(list.size() == LIMIT) {
					lock.wait();
				}
				list.add(value++);
				lock.notify();
			}
		}
	}
	
	public void consumer() throws InterruptedException {
		while(true) {
			synchronized(lock) {
				while(list.size()==0){
					lock.wait();
				}
				int i = list.removeLast();
				System.out.println("Removed value is "+i+" list size is "+list.size());
				lock.notify();
			}
			Thread.sleep(100);
		}
	}
}

public class UsingWaitNotifyLock {

	public static void main(String[] args) {
		WaitNotifyLockProcessor wnlp = new WaitNotifyLockProcessor();
		Thread t1 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					wnlp.producer();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		Thread t2 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					wnlp.consumer();
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
