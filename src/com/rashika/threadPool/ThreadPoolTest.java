package com.rashika.threadPool;

class Task implements Runnable {
	int count;

	public Task(int count) {
		this.count = count;
	}

	@Override
	public void run() {
		try {
			System.out.println("Executing task: " + count);
			Thread.sleep(500);
			System.out.println("Executed task: " + count);
		} catch (InterruptedException e) {
			System.out.println("Exception while executing task: " + count + ". Exception: ");
			e.printStackTrace();
		}
	}
}

public class ThreadPoolTest {
	public static void main(String[] args) {
		final ThreadPool threadPool = new ThreadPool(11, 7);
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {				
				for (int i = 0; i < 100; i++) {
					threadPool.addTask(new Task(i+1));
				}	
			}
		});
		thread.setName("Starter thread");
		thread.start();
		try {
			Thread.sleep(1000);
			System.out.println("Stopping Thread");
			threadPool.stopAll();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
