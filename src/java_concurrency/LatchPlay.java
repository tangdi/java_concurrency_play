package java_concurrency;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class LatchPlay {
	public void profilingTask(int nThreads, final Runnable runnable) throws InterruptedException{
		final CountDownLatch startLatch = new CountDownLatch(1);
		final CountDownLatch endLatch = new CountDownLatch(nThreads);
		for( int i = 0; i< nThreads; i++){
			Thread t = new Thread(){
				public void run() {
					try{
						startLatch.await();
						runnable.run();
					}catch(InterruptedException e){
						Thread.currentThread().interrupt();
					}finally{
						endLatch.countDown();
					}
				}
			};
			t.start();
		}
		
		Long start = System.nanoTime();
		startLatch.countDown();
		endLatch.await();
		Long end = System.nanoTime();
		System.out.println(end - start);
	}
	
	public static void main(String[] args) throws InterruptedException {
		final AtomicInteger counter = new AtomicInteger(0);
		LatchPlay play = new LatchPlay();
		play.profilingTask(4, new Runnable() {
			
			@Override
			public void run() {
				System.out.println("this is :" + counter.incrementAndGet());
				
			}
		});
	}
}
