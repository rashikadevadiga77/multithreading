package com.rashika.blockingqueue;

import java.util.LinkedList;
import java.util.List;

public class BlockingQueue<T> {
	int maxQueueSize = 0;
	List<T> queue = null;	
	private volatile BlockingQueueStatus status;
	public BlockingQueue(int maxQueueSize) {
		this.maxQueueSize = maxQueueSize;
		this.queue = new LinkedList<T>();
		this.status = BlockingQueueStatus.STARTED;
	}
	
	public synchronized void enqueue(T data){
		while(this.status.equals(BlockingQueueStatus.STARTED) && queue.size() == this.maxQueueSize){
			try {
				wait(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		this.queue.add(data);
		//System.out.println("Added " + data + ". Size: " + queue.size());
		notifyAll();
	}
	
	public synchronized T dequeue(){
		while(this.status.equals(BlockingQueueStatus.STARTED) && queue.size() == 0){
			try {
				wait(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		T data = this.queue.remove(0);
		//System.out.println("Removed " + data + ". Size: " + queue.size());
		notifyAll();
		return data;
	}
	
	public synchronized void stopBlockingQueue(){
		this.status = BlockingQueueStatus.STOPPED;
	}
}
