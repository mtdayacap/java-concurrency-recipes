package mtd.java.concurrency.deadlock;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class SynchronizedAndWait {

	private Queue<Integer> queue = new ConcurrentLinkedQueue<Integer>();

	public Integer getNextIntConditionsSynchronized() {
		Integer retVal = null;

		synchronized (queue) {
			while (queue.isEmpty()) {
				try {
					queue.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
		synchronized (queue) {
			retVal = queue.poll();
			if (retVal == null) {
				System.out.println("["+Thread.currentThread().getName()+"]: retVal is null");
				throw new IllegalStateException();
			}
		}

		return retVal;
	}

	public Integer getINextInt() {
		Integer retVal = null;
		synchronized (queue) {
			try {
				queue.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			retVal = queue.poll();
		}
		return retVal;
	}

	public synchronized void putInt(Integer integer) {
		synchronized (queue) {
			queue.add(integer);
			queue.notify();
		}
	}

	public static void main(String[] args) throws InterruptedException {
		final SynchronizedAndWait queue = new SynchronizedAndWait();
		Thread thread1 = new Thread(new Runnable() {

			@Override
			public void run() {
				for (int i = 0; i < 10; i++) {
					queue.putInt(i);
				}
			}
		});

		Thread thread2 = new Thread(new Runnable() {

			@Override
			public void run() {
				for (int i = 0; i < 10000; i++) {
					Integer integer = queue.getNextIntConditionsSynchronized();
					System.out.println("Next int: " + integer);
				}
			}
		});
		thread1.start();
		thread2.start();
		thread1.join();
		thread2.join();
	}

}
