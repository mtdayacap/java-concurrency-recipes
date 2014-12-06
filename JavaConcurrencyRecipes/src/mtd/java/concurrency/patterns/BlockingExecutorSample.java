package mtd.java.concurrency.patterns;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class BlockingExecutorSample {

	public static void main(String[] args) {
		ThreadPoolExecutor blockingExec = new BlockingExecutor(2, 10);
		for (int i = 0; i < 20; i++) {
			blockingExec.execute(new Runnable() {

				@Override
				public void run() {
					try {
						System.out.println("Sleeping "
								+ Thread.currentThread().getId());
						Thread.sleep(1000);
						System.out.println("Wake up "
								+ Thread.currentThread().getId());
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});
		}
		blockingExec.shutdown();
		
		while(!blockingExec.isTerminated()){
			
		}
		System.out.println("Done with Blocking Executor");
		
		ThreadPoolExecutor threadPoolExec = new ThreadPoolExecutor(2, 2, 0L,
				TimeUnit.MILLISECONDS, new LinkedBlockingDeque<Runnable>(10),
				new RejectedExecutionHandler() {

					@Override
					public void rejectedExecution(Runnable r,
							ThreadPoolExecutor executor) {
						System.out.println("Rejected task!");
					}
				});
		
		for (int i = 0; i < 20; i++) {
			threadPoolExec.execute(new Runnable() {

				@Override
				public void run() {
					try {
						System.out.println("Sleeping "
								+ Thread.currentThread().getId());
						Thread.sleep(1000);
						System.out.println("Wake up "
								+ Thread.currentThread().getId());
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});
		}
		threadPoolExec.shutdown();
	}
}
