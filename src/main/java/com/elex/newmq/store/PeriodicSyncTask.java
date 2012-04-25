package com.elex.newmq.store;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
/**
 * 定时将file channel缓存中的数据dump到硬盘中
 * @author qxc
 *
 */
public class PeriodicSyncTask implements Runnable {

	private ScheduledFuture Handler;
	private MessageStore store;
	private int initialDelay;
	private int Duration;
	
	public PeriodicSyncTask(int initialDelay, int Duration, MessageStore store){
		this.initialDelay = initialDelay;
		this.Duration = Duration;
		this.store = store;
	}
	public void start(){
		this.Handler = Executors.newScheduledThreadPool(1).
		scheduleAtFixedRate(this, initialDelay, Duration, TimeUnit.SECONDS);
	}
	public void run() {
		// TODO Auto-generated method stub
		try {
			if(this.store.canFsync()) this.store.fsync();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void stop() throws IOException{
		Boolean re = this.Handler.cancel(true);
		if(re == false)
			throw new IOException("close fsync server fail!");
		this.store.fsync();
	}
}
