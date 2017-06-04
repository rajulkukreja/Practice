package com.caveofprogramming.usingvolatile;
import java.util.*;

class RunnerVolatile extends Thread {

	// Volatile is used to ensure variable access by Thread from common variable not from Thread cache
	public volatile boolean running = true;
	public void run() {
		while (running) {
			System.out.println("Hello");
			
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void stopThread() {
		running = false;
	}
}

public class UsingVolatile {

	public static void main(String[] args) {
		RunnerVolatile t1 = new RunnerVolatile();
		t1.start();
		
		System.out.println("Type to stop");
		Scanner sc = new Scanner(System.in);
		sc.nextLine();
		
		t1.stopThread();
	}

}
