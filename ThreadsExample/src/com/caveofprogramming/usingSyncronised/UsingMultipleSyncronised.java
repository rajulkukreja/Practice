package com.caveofprogramming.usingSyncronised;

import java.util.*;

public class UsingMultipleSyncronised {
	
	public Random random = new Random();
	
	public List<Integer> list1 = new ArrayList<Integer>();
	public List<Integer> list2 = new ArrayList<Integer>();
	
	public synchronized void addList1() {
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		list1.add(random.nextInt(100));
	}
	
	public synchronized void addList2() {
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		list2.add(random.nextInt(100));
	}
	
	public void process() {
		for (int i=0;i<100;i++) {
			addList1();
			addList2();
		}
	}

	public static void main(String[] args) {
		UsingMultipleSyncronised ums = new UsingMultipleSyncronised();
		Thread t1 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				ums.process();
			}
		});
		
		Thread t2 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				ums.process();
			}
		});
		
		long start = System.currentTimeMillis();
		
		t1.start();
		t2.start();
		
		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		long end = System.currentTimeMillis();
		System.out.println("Time taken is " + (end-start));
		System.out.println(ums.list1.size()+" "+ums.list2.size());
	}
}
