package mtd.java.concurrency.concurrencypackage;

import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MapComparison implements Runnable {
	private static Random random = new Random(System.currentTimeMillis());
	private static Map<Integer, String> map;

	public static void main(String args[]) throws InterruptedException {
		runPerfTest(new Hashtable<Integer, String>());
		runPerfTest(Collections.synchronizedMap(new HashMap<Integer, String>()));
		runPerfTest(new ConcurrentHashMap<Integer, String>());
		runPerfTest(new ConcurrentSkipListMap<Integer, String>());
	}

	private static void runPerfTest(Map<Integer, String> map)
			throws InterruptedException {
		MapComparison.map = map;
		fillMap(map);
		ExecutorService executorService = Executors.newFixedThreadPool(10);
		long startMillis = System.currentTimeMillis();
		for (int i = 0; i < 10; i++) {
			executorService.submit(new MapComparison());
		}
		executorService.shutdown();
		executorService.awaitTermination(1, TimeUnit.SECONDS);
		System.out.println(map.getClass().getName() + " took "
				+ (System.currentTimeMillis() - startMillis));

	}

	private static void fillMap(Map<Integer, String> map) {
		for (int i = 0; i < 100; i++) {
			map.put(i, String.valueOf(i));
		}
	}

	@Override
	public void run() {
		for (int i = 0; i < 100000; i++) {
			int randomInt = random.nextInt(100);
			map.get(randomInt);
			randomInt = random.nextInt(100);
			map.put(randomInt, String.valueOf(randomInt));
		}
	}

}
