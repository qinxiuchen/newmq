package com.elex.newmq;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.elex.newmq.constant.SqlBean;
import com.elex.newmq.service.DBService;
import com.elex.newmq.service.DataSourceManager;

public class TestDB {

	/**
	 * @param args
	 * @throws SQLException 
	 */
	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
//		DataSourceManager ds= new DataSourceManager();
//		ds.init();
//		Connection conn = null;
//        Statement stmt = null;
//        ResultSet rset = null; 
//		conn = ds.getConnection("user_account");
//		stmt = conn.createStatement();
//        rset = stmt.executeQuery("select * from hcdb.user_account");
//        int cols = rset.getMetaData().getColumnCount();
//        while(rset.next()) {
//            for(int i=1;i<=cols;i++) {
//                System.out.print(rset.getString(i));
//                System.out.print("|");
//            }
//            System.out.println("");
//        }
//		DBService ds = new DBService();
//		ds.init();
//		SqlBean sql = new SqlBean("user_account", "select * from hcdb.user_account");
//		ds.add(sql);
	}

}
