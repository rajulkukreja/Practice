package com.caveofprogramming.stopDeadlocks;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class StopDeadlocks {

	public static void main(String[] args) {
		DeadlockRunner dr = new DeadlockRunner();
		
		Thread t1 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					dr.firstThread();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		
		Thread t2 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					dr.secondThread();
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
		dr.finish();	
	}

}

class DeadlockRunner {
	Account acnt1 = new Account();
	Account acnt2 = new Account();
	
	Lock lock1 = new ReentrantLock();
	Lock lock2 = new ReentrantLock();
	
	private void acquireLock() throws InterruptedException {
		boolean firstLock = false;
		boolean secondLock = false;
		
		while (true) {
			
			try {
				firstLock = lock1.tryLock();
				secondLock = lock2.tryLock();
			} finally {
				if (firstLock && secondLock) {
					return;
				}
				
				if (firstLock) {
					lock1.unlock();
				}
				
				if (secondLock) {
					lock2.unlock();
				}
			}
			
			Thread.sleep(1);
			
		}
		
	}
	
	private void releaseLock() {
		lock1.unlock();
		lock2.unlock();
	}

	public void firstThread() throws InterruptedException {
		Random r = new Random();
		
		for (int i=0;i<1000;i++) {
			acquireLock();
			try {
				Account.transfer(acnt1,acnt2,r.nextInt(100));
			} finally {
				releaseLock();
			}
		}
	}

	public void secondThread() throws InterruptedException {
		Random r = new Random();
		
		for (int i=0;i<1000;i++) {
			acquireLock();
			try {
				Account.transfer(acnt2,acnt1,r.nextInt(100));
			} finally {
				releaseLock();
			}
		}
	}
	
	public void finish() {
		System.out.println("Acnt 1 balance is "+acnt1.getBalance());
		System.out.println("Acnt 2 balance is "+acnt2.getBalance());
		System.out.println("Total balance is "+(acnt1.getBalance()+acnt2.getBalance()));
	}
	
}

class Account {
	private int balance=10000;
	
	public int getBalance() {
		return balance;
	}
	
	public void deposit(int amount) {
		balance += amount;
	}
	
	public void withdraw(int amount) {
		balance -= amount;
	}
	
	public static void transfer(Account acct1, Account acct2, int amount) {
		acct1.withdraw(amount);
		acct2.deposit(amount);
	}
}
