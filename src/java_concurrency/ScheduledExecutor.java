package java_concurrency;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ScheduledExecutor {
	private final ScheduledExecutorService timer = Executors.newScheduledThreadPool(2);
	
	public void addTimedTask(Runnable runnable){
		timer.scheduleWithFixedDelay(runnable, 5, 5, TimeUnit.SECONDS);
	}
	
	public static void main(String[] args) {
		ScheduledExecutor se = new ScheduledExecutor();
		se.addTimedTask(new Runnable() {
			
			@Override
			public void run() {
				System.out.println(" 1st");
				
			}
		});
		
	//	se.addTimedTask(runnable);
		se.timer.shutdown();
	}
}
