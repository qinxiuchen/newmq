package com.elex.newmq.service;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.configuration.HierarchicalConfiguration;

import com.elex.newmq.store.MessageStore;
import com.elex.newmq.store.PeriodicSyncTask;

/**
 * 持久化服务
 * @author qxc
 *
 */

public class PersistenceService implements IService {

	private MessageStore messageStore;
	private PeriodicSyncTask fsyncTask;
	
	public PersistenceService(){
		
	}

	public void addItem(String value) throws IOException{
		this.messageStore.addValue(value);
	}
	
	public void init(HierarchicalConfiguration configure) {

		this.messageStore = new MessageStore();
		try {
			this.messageStore.init();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.fsyncTask = new PeriodicSyncTask(5,5,this.messageStore);
	}

	public void start() {

		this.fsyncTask.start();
	}

	public void stop() throws IOException {

		this.fsyncTask.stop();
	}

	public void reload() {
		// TODO Auto-generated method stub

	}

}
