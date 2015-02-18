package java_concurrency;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TimedRunPlay {
	private static final int corePoolSize = Runtime.getRuntime().availableProcessors();
	private static final ScheduledExecutorService executeService = Executors.newScheduledThreadPool(corePoolSize);
	
	public static void timedRun(final Runnable r, long timeout, TimeUnit timeUnit) throws Throwable{
		class DedicatedTask implements Runnable{
			Throwable t = null;
			@Override
			public void run() {
				try{
				r.run();
				} catch (Throwable t){
					this.t = t;
				}
			}
			
			public void rethrow() throws Throwable{
				if(t!=null){
					throw t;
				}
			}
			
		}
		
		DedicatedTask t = new DedicatedTask();
		final Thread dedicatedThread = new Thread(t);
		
		executeService.schedule(new Runnable() {
			
			@Override
			public void run() {
				dedicatedThread.interrupt();
				
			}
		}, timeout, timeUnit);
		dedicatedThread.join(timeout);
		t.rethrow();
		
	}
}
