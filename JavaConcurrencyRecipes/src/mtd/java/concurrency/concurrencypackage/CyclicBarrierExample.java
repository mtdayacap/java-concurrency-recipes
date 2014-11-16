package mtd.java.concurrency.concurrencypackage;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class CyclicBarrierExample implements Runnable {
	private static final int NUMBER_OF_THREADS = 5;
	private static final Random RANDOM = new Random(System.currentTimeMillis());
	private static final AtomicInteger COUNTER = new AtomicInteger();
	private static final CyclicBarrier BARRIER = new CyclicBarrier(NUMBER_OF_THREADS, new Runnable() {
		
		@Override
		public void run() {
			COUNTER.getAndIncrement();
		}
	});
	
	@Override
	public void run() {
		while(COUNTER.get() < 3) { 
            try {
            	int randomSleepTime = RANDOM.nextInt(10000);
            	System.out.println("[" + Thread.currentThread().getName() + "] Sleeping for " + randomSleepTime);
				Thread.sleep(randomSleepTime);
				System.out.println("[" + Thread.currentThread().getName() + "] Waiting for barrier.");
				BARRIER.await();
			} catch (InterruptedException | BrokenBarrierException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String args[]) { 
		ExecutorService executorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
		for (int i = 0; i < NUMBER_OF_THREADS; i++) {
			executorService.submit(new CyclicBarrierExample());
		}
		executorService.shutdown();
	}
}
