package mtd.java.concurrency.patterns;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class BlockingExecutor extends ThreadPoolExecutor {

	private Semaphore semaphore;

	/**
	 * Creates a BlockingExecutor which will block and prevent further
	 * submission to the pool when the specified queue size has been reached.
	 * 
	 * @param poolSize the number of the threads in the pool
	 * @param queueSize the size of the queue
	 * */
	public BlockingExecutor(final int poolSize, final int queueSize) {
		super(poolSize, poolSize, 0L, TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<Runnable>());

		semaphore = new Semaphore(queueSize + poolSize);
	}

	@Override
	public <T> Future<T> submit(Callable<T> task) {
		// TODO Auto-generated method stub
		return super.submit(task);
	}

	/**
	 * Executes the given task
	 * This method will block when the semaphore has no permits
	 * i.e. When the queue has reached its capacity
	 * */
	@Override
	public void execute(Runnable command) {
		boolean acquired = false;
		do {
			try {
				semaphore.acquire();
				acquired = true;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} while (!acquired);

		try {
			super.execute(command);
		} catch (RejectedExecutionException e) {
			semaphore.release();
			System.out.println("Rejected!");
			throw e;
		}
	}

	/**
	 * Method invoked upon the completion of the given Runnable,
	 * by the Thread that executed the task.
	 * Releases a semaphore permit.
	 */
	@Override
	protected void afterExecute(Runnable r, Throwable t) {
		super.afterExecute(r, t);
		semaphore.release();
	}

}
