package com.elex.newmq.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import com.elex.newmq.constant.SqlBean;
import com.elex.newmq.share.ShareVariable;

/**
 * 数据库提交进程
 * @author qxc
 *
 */

public class DBProcessor {
	//进程池执行器
	private final Executor executor;
	private final AtomicReference<Processor> processorRef = new AtomicReference<Processor>();
	//sql队列
	private final Queue<SqlBean> newSqls = new ConcurrentLinkedQueue<SqlBean>();
	//当前需要执行的sql数量
	private final AtomicInteger sqlNums = new AtomicInteger(0); 
	
	private Connection conn = null;
	private Statement stmt = null;
	private ResultSet rset = null;
	
	public DBProcessor(Executor e){
		this.executor = e;
	}

	/**
	 * 启动该进程，如果processor未初始化，则初始化之
	 */
	private void startupProcessor() {
        Processor processor = processorRef.get();

        if (processor == null) {
            processor = new Processor();
            if (processorRef.compareAndSet(null, processor)) {
                executor.execute(processor);
            }
        }
    }
	/**
	 * 为当前进程增加一个新的sql
	 * @param newsql
	 */
	public final void add(SqlBean newsql){
		this.newSqls.add(newsql);
		this.sqlNums.getAndIncrement();
		this.startupProcessor();
	}
	/**
	 * 获取当前进程执行队列中的sql数量
	 * @return int sqlnum
	 */
	public int getCurrentSqlNums(){
		return this.sqlNums.get();
	}
	/**
	 * 判断当前进程是否执行完成
	 * @return
	 */
	public Boolean checkHasComplete(){
		return this.sqlNums.get() == 0 ? true : false;
	}
	
	/**
	 * sql执行类
	 * @author qxc
	 */
	private class Processor implements Runnable {
		public void run() {
			assert (processorRef.get() == this);
			while(true){
				SqlBean newsql = newSqls.poll();
				if(newsql != null){	
					try {
						conn = ShareVariable.dataSource.getConnection(newsql.tableFlag);
						stmt = conn.createStatement();
				        rset = stmt.executeQuery(newsql.sql);
				        int cols = rset.getMetaData().getColumnCount();
				        while(rset.next()) {
				            for(int i=1;i<=cols;i++) {
				                System.out.print(rset.getString(i));
				                System.out.print("|");
				            }
				            System.out.println("");
				        }
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}else{
					try {
						this.wait(5000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}

}
