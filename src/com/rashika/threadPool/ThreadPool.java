package com.rashika.threadPool;

import java.util.ArrayList;
import java.util.List;

import com.rashika.blockingqueue.BlockingQueue;

public class ThreadPool {
	private int poolSize;
	private int maxNumberOfThreads;
	private BlockingQueue<Runnable> blockingQueue;
	private List<PoolThread> poolThreads;
	private ThreadPoolStatus threadPoolStatus;

	public ThreadPool(int poolSize, int maxNumberOfThreads) {
		this.poolSize = poolSize;
		this.maxNumberOfThreads = maxNumberOfThreads;
		this.blockingQueue = new BlockingQueue<Runnable>(this.poolSize);
		this.poolThreads = new ArrayList<PoolThread>();
		this.threadPoolStatus = ThreadPoolStatus.STARTED;
		for (int i = 0; i < this.maxNumberOfThreads; i++) {
			PoolThread poolThread = new PoolThread(blockingQueue);
			poolThreads.add(poolThread);
		}

		for (PoolThread poolThread : poolThreads) {
			poolThread.start();
		}
	}

	public synchronized void addTask(Runnable runnable) {
		if (ThreadPoolStatus.STOPPED.equals(threadPoolStatus)) {
			throw new IllegalStateException();
		}
		blockingQueue.enqueue(runnable);
	}

	public void stopAll() {
		this.threadPoolStatus = ThreadPoolStatus.STOPPED;
		for (PoolThread poolThread : poolThreads) {
			poolThread.stopPoolThread();
		}
	}
}
