package com.rashika.threadPool;

import com.rashika.blockingqueue.BlockingQueue;

public class PoolThread extends Thread {
	private BlockingQueue<Runnable> jobList = null;
	private ThreadPoolStatus threadPoolStatus;

	public PoolThread(BlockingQueue<Runnable> jobList) {
		this.jobList = jobList;
		this.threadPoolStatus = ThreadPoolStatus.STARTED;
	}

	@Override
	public void run() {
		while (!ThreadPoolStatus.STOPPED.equals(this.threadPoolStatus)) {
			Runnable runnable = this.jobList.dequeue();
			runnable.run();
		}
		System.out.println("Stopped Pool");
	}

	public void stopPoolThread() {
		this.threadPoolStatus = ThreadPoolStatus.STOPPED;
		this.jobList.stopBlockingQueue();	
	}
}
