package mtd.java.concurrency.basic;

public class InterruptExample implements Runnable {

	@Override
	public void run() {
		try {
			Thread.sleep(Long.MAX_VALUE);
		} catch (InterruptedException e) {
			System.out.println("[" + Thread.currentThread().getName()
					+ "] Interrupted by exception!");
		}
		while (!Thread.interrupted()) {
			// do nothing
		}
		System.out.println("[" + Thread.currentThread().getName()
				+ "] Interrupted for the second time.");

	}

}
