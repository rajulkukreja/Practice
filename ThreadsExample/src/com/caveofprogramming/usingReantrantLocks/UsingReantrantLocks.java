package com.caveofprogramming.usingReantrantLocks;

import java.util.Scanner;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class UsingReantrantLocks {

	public static void main(String[] args) {
		
		ReantrantLocksRunner url = new ReantrantLocksRunner();
		
		Thread t1 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					url.firstThread();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		
		Thread t2 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					url.secondThread();
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
		url.finished();
		System.out.println("All Done");
	}

}

class ReantrantLocksRunner {
	private int count = 0;
	
	private Lock lock = new ReentrantLock();
	private Condition cond = lock.newCondition();
	
	private void increment() {
		for (int i=0;i<100;i++) {
			count++;
		}
	}

	public void firstThread() throws InterruptedException {
		lock.lock();
		System.out.println("Waiting 1st");
		cond.await();
		System.out.println("1st Resumed");
		try {
			increment();
		} 
		finally {
			lock.unlock();
		}
	}

	public void secondThread() throws InterruptedException {
		Thread.sleep(100);
		lock.lock();
		System.out.println("2nd Lock started. Press return key");
		new Scanner(System.in).nextLine();
		cond.signal();
		try {
			increment();
		} 
		finally {
			lock.unlock();
		}
		System.out.println("2nd Done");
	}
	
	public void finished() {
		System.out.println("Value is "+count);
	}
}
