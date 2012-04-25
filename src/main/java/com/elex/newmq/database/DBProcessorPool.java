package com.elex.newmq.database;

import java.util.concurrent.Executor;

import com.elex.newmq.constant.SqlBean;

/**
 * 数据库提交进程池，参照mina的实现
 * @author qxc
 *
 */

public class DBProcessorPool {
	private final Executor executor;
	//默认启动的进程数目
	private static final int DEFAULT_SIZE = Runtime.getRuntime().availableProcessors() + 1;
	//进程池
	private final DBProcessor[] pool;
	
	//初始化进程池的进程数量，如果配置文件没有配置，则使用默认值
	public DBProcessorPool(Executor e){
		this(e, DEFAULT_SIZE);
	}
	/**
	 * 初始化进程池
	 * @param e 进程执行器
	 * @param size 启动的提交进程的数量
	 */
	public DBProcessorPool(Executor e, int size){
		this.executor = e;
		this.pool = new DBProcessor[size];
		for(int i=0; i<pool.length; i++){
			try{
				pool[i] = new DBProcessor(this.executor);
			}catch(Exception ex){}
		}
	}
	/**
	 * 随机为sql执行分配一个进程
	 */
	private DBProcessor getRandomProcessor(){
		int randomIndex = (int)(Math.random()*(this.pool.length));
		return this.pool[randomIndex];
	}
	/**
	 * 增加新的sql语句
	 * @param sql 要执行的sql。
	 */
	public final void add(SqlBean sql){
		DBProcessor processor = this.getRandomProcessor();
		processor.add(sql);
	}
	/**
	 * 查看进程池中的进程是否执行完成
	 * @return
	 */
	public Boolean checkHasComplete(){
		Boolean re = true;
		for(int i=0;i<pool.length; i++){
			if(!pool[i].checkHasComplete()){
				re = false;
				break;
			}
		}
		return re;
	}
}
