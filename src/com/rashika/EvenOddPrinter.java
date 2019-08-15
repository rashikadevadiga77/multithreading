package com.rashika;



/**
 * 
 * @author Rashika Two threads printing numbers
 */

class EvenOddPrintingLock {
	public volatile int counter = 0;
}

class EvenPrinter implements Runnable {
	private EvenOddPrintingLock lock;

	public EvenPrinter(EvenOddPrintingLock lock) {
		this.lock = lock;
	}

	public void run() {
		synchronized (lock) {
			for (int i = 0; i < 100; i++) {
				while (lock.counter % 2 != 0) {
					try {
						lock.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				System.out.print(lock.counter + " ");
				lock.counter++;
				lock.notifyAll();
			}
		}
	}
}

class OddPrinter implements Runnable {
	private EvenOddPrintingLock lock;

	public OddPrinter(EvenOddPrintingLock lock) {
		this.lock = lock;
	}

	public void run() {
		synchronized (lock) {
			for (int i = 0; i < 100; i++) {
				while (lock.counter % 2 == 0) {
					try {
						lock.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				System.out.print(lock.counter + " ");
				lock.counter++;
				lock.notify();
			}
		}
	}
}

public class EvenOddPrinter {
	public static void main(String[] args) {
		EvenOddPrintingLock evenOddPrintingLock = new EvenOddPrintingLock();
		Thread evenThread = new Thread(new EvenPrinter(evenOddPrintingLock));
		Thread oddThread = new Thread(new OddPrinter(evenOddPrintingLock));
		
		evenThread.start();
		oddThread.start();
	}
}
