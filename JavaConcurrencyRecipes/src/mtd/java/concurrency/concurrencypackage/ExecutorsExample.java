package mtd.java.concurrency.concurrencypackage;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * An example on how to use Executors.newFixedThreadPool() and Callable<V>.
 * **/
public class ExecutorsExample implements Callable<Integer> {
	private static Random random = new Random();

	@Override
	public Integer call() throws Exception {
		Thread.sleep(1000);
		return random.nextInt(100);
	}

	public static void main(String argsp[]) throws InterruptedException,
			ExecutionException {
		ExecutorService executorService = Executors.newFixedThreadPool(5);
		Future<Integer>[] futures = new Future[5];

		for (int i = 0; i < futures.length; i++) {
			futures[i] = executorService.submit(new ExecutorsExample());
		}
		for (int i = 0; i < futures.length; i++) {
			int retVal = futures[i].get();
			System.out.println(retVal);
		}
		executorService.shutdown();
	}

}
