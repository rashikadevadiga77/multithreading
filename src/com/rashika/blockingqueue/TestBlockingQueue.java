package com.rashika.blockingqueue;

public class TestBlockingQueue {
	public static void main(String[] args) {
		final BlockingQueue<Integer> blockingQueue = new BlockingQueue<Integer>(7);
		Thread thread1 = new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 1; i <= 11; i++) {
					blockingQueue.enqueue(i);
				}
			}
		});
		
		Thread thread2 = new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 1; i <= 11; i++) {
					blockingQueue.dequeue();
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		
		thread2.start();
		thread1.start();
	}
}
