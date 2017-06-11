package com.caveofprogramming.usingInterrupt;

public class UsingInterrupt {

	public static void main(String[] args) {
		Thread t1 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				int i=0;
				while(true) {
					if(Thread.currentThread().isInterrupted()) {
						System.out.println("Interrupted");
						break;
					}
					System.out.println("Hello" +i);
					i++;
				}
			}
		});
		
		System.out.println("Starting thread");
		t1.start();
		
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		t1.interrupt();
		try {
			t1.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("All done");
	}

}
