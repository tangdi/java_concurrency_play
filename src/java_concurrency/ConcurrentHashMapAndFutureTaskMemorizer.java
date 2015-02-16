package java_concurrency;

import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class ConcurrentHashMapAndFutureTaskMemorizer<A, V> implements Computable<A, V> {
	private final ConcurrentHashMap<A, Future<V>> cache = new ConcurrentHashMap<A, Future<V>>();
	private final Computable<A, V> computer;
	
	public ConcurrentHashMapAndFutureTaskMemorizer(Computable<A, V> computer) {
		
		this.computer = computer;
	}
	
	@Override
	public V compute(final A arg) throws InterruptedException {
		
		Future<V> f = cache.get(arg);
		
		if(f == null){
			FutureTask<V> ft = new FutureTask<V>(
					new Callable<V>() {				 						
						@Override
						public V call() throws InterruptedException {
							return computer.compute(arg);			
						}
					}
				);
			f = cache.putIfAbsent(arg, (Future<V>) ft);
			if(f == null){
				f = ft;
				ft.run();
			}
		}
		
		try{
			return f.get();
		}catch(CancellationException e){
			cache.remove(arg);
		}catch(ExecutionException e){
			Thread.currentThread().interrupt();
		}
		return null;
	}
	
}
