package com.caveofprogramming.usingLocks;

import java.util.*;

public class UsingLocks {
	public Random random = new Random();
	
	public Object lock1 = new Object();
	public Object lock2 = new Object();
	
	public List<Integer> list1 =new ArrayList<Integer>();
	public List<Integer> list2 =new ArrayList<Integer>();
	
	public void addlist1() {
		synchronized(lock1) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			list1.add(random.nextInt(100));
		}
	}
	
	public void addList2() {
		synchronized(lock2) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			list2.add(random.nextInt(100));
		}
	}
	
	public void process() {
		for(int i=0;i<100;i++) {
			addlist1();
			addList2();
		}
	}

	public static void main(String[] args) {
		UsingLocks ul = new UsingLocks();
		
		Thread t1 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				ul.process();
			}
		});
		
		Thread t2 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				ul.process();
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
		System.out.println(ul.list1.size()+" "+ul.list2.size());
	}

}
