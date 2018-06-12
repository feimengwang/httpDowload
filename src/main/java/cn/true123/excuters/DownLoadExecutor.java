package cn.true123.excuters;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class DownLoadExecutor {
	private static DownLoadExecutor executor;
	private ExecutorService service;
	public static DownLoadExecutor instance() {
		if (null == executor) {
			synchronized (DownLoadExecutor.class) {
				executor = new DownLoadExecutor();
			}
		}
		return executor;
	}

	private DownLoadExecutor() {

	}

	public DownLoadExecutor newFixedThreadPool() {
		service = new ThreadPoolExecutor(10, 50,
				0L, TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<Runnable>()) ;
		return this;
	}


	public void execute(Runnable t){
		service.execute(t);
	}
	public void shutdown(){
		service.shutdown();
	}
}
