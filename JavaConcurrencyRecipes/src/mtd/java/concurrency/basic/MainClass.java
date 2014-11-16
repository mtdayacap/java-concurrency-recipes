package mtd.java.concurrency.basic;

import java.lang.Thread.State;

public class MainClass {

	public static void main(String[] args) throws InterruptedException {
		interruptSample();
	}

	private static void getCurrentThreadInfo() {
		Thread currThread = Thread.currentThread();
		long id = currThread.getId();
		String name = currThread.getName();
		int priority = currThread.getPriority();
		State state = currThread.getState();
		String threadGroupName = currThread.getThreadGroup().getName();
		System.out.println("id=" + id + "; name=" + name + "; priority="
				+ priority + "; state=" + state + "; threadGroupName="
				+ threadGroupName);
	}

	private static void interruptSample() throws InterruptedException {
		Thread myThread = new Thread(new InterruptExample(), "myThread");
		myThread.start();

		System.out.println("[" + Thread.currentThread().getName()
				+ "] Sleeping in main thread for 5s...");
		Thread.sleep(5000);

		System.out.println("[" + Thread.currentThread().getName()
				+ "] Interrupting myThread");
		myThread.interrupt();

		System.out.println("[" + Thread.currentThread().getName()
				+ "] Sleeping in main thread for 5s...");
		Thread.sleep(5000);

		System.out.println("[" + Thread.currentThread().getName()
				+ "] Interrupting myThread");
		myThread.interrupt();

	}

}
