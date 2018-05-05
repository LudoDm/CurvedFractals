package prototypes;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TestThread {
	
	public static void main(String[] args) {
		final ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(0);
		executor.schedule(() -> printHello(), 1, TimeUnit.SECONDS);
	}
	
	
	public static void printHello() {
		System.out.println(("Hello"));
	}

}
