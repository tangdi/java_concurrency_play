package java_concurrency;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerWithExecutorService {
	private final int NThreads = Runtime.getRuntime().availableProcessors();
	private final ExecutorService executorService = Executors.newFixedThreadPool(NThreads);
	
	public void serve(final Runnable app) throws IOException{
		ServerSocket serverSocket = new ServerSocket(80);
		while(true){
			
			Socket socket = serverSocket.accept();
			executorService.submit(new Runnable() {
				
				@Override
				public void run() {
					try{
					app.run();
					}catch(Exception e){
						System.out.println(e);
					}
				}
			});
		}
	}
}
