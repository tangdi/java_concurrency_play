package java_concurrency;

public class PreserveThreadInterruptStatus {
	public static void main(String[] args) throws InterruptedException {
		Thread t = new Thread( new Runnable() {
			
			@Override
			public void run() {
				System.out.println("before:" + Thread.currentThread().isInterrupted());
				Thread.currentThread().interrupt();
				System.out.println("middle:" + Thread.currentThread().isInterrupted());
				try{
					Thread.sleep(1);
				} catch(InterruptedException e){
					System.out.println("after:" + Thread.currentThread().isInterrupted());
					Thread.currentThread().interrupt();
					System.out.println("after reinterrupt:" + Thread.currentThread().isInterrupted());
				}
				
			}
		});
		
		t.start();
		t.join();
	}
}
