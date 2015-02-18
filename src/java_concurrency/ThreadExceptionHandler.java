package java_concurrency;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;

public class ThreadExceptionHandler {
	public static void main(String[] args){
		ExecutorService es = Executors.newCachedThreadPool(new ThreadFactory() {
			
			@Override
			public Thread newThread(Runnable r) {
				Thread t = new Thread(r);
				t.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
					
					@Override
					public void uncaughtException(Thread t, Throwable e) {
						// TODO Auto-generated method stub
						System.out.println("this is caught");
					}
				});
				return t;
			}
			
		} );
		/*Future<Integer> future = es.submit(new Callable<Integer>() {

			@Override
			public Integer call() throws Exception {
				// TODO Auto-generated method stub
				System.out.println("yes");
				throw new RuntimeException();
			}
			
		});*/
		
		es.execute(new Runnable() {
			
			@Override
			public void run() {
				throw new RuntimeException();
				
			}
		});
		
		//Thread.sleep(1000);
		System.out.println("yes");
		es.shutdown();
		System.out.println("end");
	}
}
