package com.elex.newmq.constant;

public class SqlBean {

	//数据库表的标识，根据它去取对应的数据库连接
	public String tableFlag;
	//要执行的sql语句
	public String sql;
	
	public SqlBean(String tableFlag, String sql){
		this.tableFlag = tableFlag;
		this.sql = sql;
	}
	
}
