package mtd.java.concurrency.concurrencypackage;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class SemaphoreExample implements Runnable {
	private static final Semaphore semaphore = new Semaphore(3, true);
	private static final AtomicInteger counter = new AtomicInteger();
	private static final long endMillis = System.currentTimeMillis() + 10000;

	@Override
	public void run() {
		while (System.currentTimeMillis() < endMillis) {
			try {
				semaphore.acquire();
			} catch (InterruptedException e) {
				System.out.println("[" + Thread.currentThread().getName()
						+ "] Interrupted in acquire().");
			}
			int counterValue = counter.getAndIncrement();
			if (counterValue > 1) {
				throw new IllegalStateException(
						"More than three threads acquired the lock.");
			}
			counter.decrementAndGet();
			semaphore.release();
		}
	}

	public static void main(String[] args) {
		ExecutorService executorService = Executors.newFixedThreadPool(5);
		for (int i = 0; i < 5; i++) {
			executorService.submit(new SemaphoreExample());
		}
		executorService.shutdown();
	}
}
