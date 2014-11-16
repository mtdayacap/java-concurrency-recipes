package mtd.java.concurrency.concurrencypackage;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * By using CountDownLatch, all threads that were spawned will wait for the latch counter to
 * be zero before exiting the thread.
 * **/
public class CountDownLatchExample implements Runnable {
	private static final int NUMBER_OF_THREADS = 5;
	private static final Random random = new Random(System.currentTimeMillis());
	private static final CountDownLatch latch = new CountDownLatch(NUMBER_OF_THREADS);
	
	@Override
	public void run() {
        try {
        	int randomSleepTime = random.nextInt(20000);
        	System.out.println("[" + Thread.currentThread().getName() + "] Sleeping for " + randomSleepTime);
			Thread.sleep(randomSleepTime);
			latch.countDown();
            System.out.println("[" + Thread.currentThread().getName() + "] Waiting for latch.");
            latch.await();
            System.out.println("[" + Thread.currentThread().getName() + "] Finished.");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void main(String args[]) { 
		ExecutorService executorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
		for (int i = 0; i < NUMBER_OF_THREADS; i++) {
			executorService.submit(new CountDownLatchExample());
		}
		executorService.shutdown();
	}
}
