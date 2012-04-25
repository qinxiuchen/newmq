package com.elex.newmq.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.dbcp.BasicDataSource;

import com.elex.newmq.constant.MQConfig;

/**
 * 数据库连接管理类，为数据库提交服务提供空闲的数据库连接。仅支持mysql
 * @author qxc
 *
 */

public class DataSourceManager  implements IService{

	public ConcurrentHashMap<String,BasicDataSource> dataSourceMap = new ConcurrentHashMap<String,BasicDataSource>();
	private final String className = "com.mysql.jdbc.Driver";
	/**
	 * @param key 数据库的标识
	 * @return connection 返回该数据库的一个空闲连接
	 * @throws SQLException 
	 */
	public Connection getConnection(String key) throws SQLException{
		if(key == null){
			//throw new IllegalException("");
		}
		return this.dataSourceMap.get(key).getConnection();
	}
	public void init(HierarchicalConfiguration configure) {
		
		for(HierarchicalConfiguration dbserver: (List<HierarchicalConfiguration>)configure.configurationsAt("dbserver")){
			BasicDataSource bds = new BasicDataSource();
			String tableFlag = dbserver.getString("tableflag");
			bds.setDriverClassName(className);
			bds.setUrl(dbserver.getString("uri"));
			bds.setUsername(dbserver.getString("user"));
			bds.setPassword(dbserver.getString("password", ""));
			bds.setInitialSize(dbserver.getInt("initialsize", 1));
			bds.setMaxActive(dbserver.getInt("maxactive", MQConfig.MAX_DBCONN_SIZE));
			bds.setMaxIdle(dbserver.getInt("maxidle", MQConfig.MAX_DBCONN_SIZE));
			dataSourceMap.put(tableFlag, bds);
		}

	}
	public void start() {
		// TODO Auto-generated method stub
		
	}
	public void stop() {
		// TODO Auto-generated method stub
		
	}
	public void reload() {
		// TODO Auto-generated method stub
		
	}
}
