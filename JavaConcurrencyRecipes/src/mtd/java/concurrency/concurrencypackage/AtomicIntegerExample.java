package mtd.java.concurrency.concurrencypackage;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerExample implements Runnable {

	private final static AtomicInteger counter = new AtomicInteger();

	@Override
	public void run() {
		for (int i = 0; i < 10; i++) {
			int currentValue = counter.getAndIncrement();
			if (currentValue == 42) {
				System.out.println("[" + Thread.currentThread().getName()
						+ "]: " + currentValue);
			}
		}
	}

	public static void main(String args[]) {
		int cores = Runtime.getRuntime().availableProcessors();
		System.out.println("Number of cores: " + cores);
		
		ExecutorService executorService = Executors.newFixedThreadPool(5);
		for (int i = 0; i < 5; i++) {
			executorService.submit(new AtomicIntegerExample());
		}
		executorService.shutdown();
	}
}
