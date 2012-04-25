package com.elex.newmq.share;

import java.util.concurrent.atomic.AtomicInteger;

import com.elex.newmq.constant.DBServiceStatus;
import com.elex.newmq.service.DataSourceManager;

/**
 * 定义一些额程序公用变量
 * @author qxc
 *
 */
public class ShareVariable {
	//数据库服务的当前状态
	public static DBServiceStatus dbServiceStatus = DBServiceStatus.idle;
	//持久化文件指针当前的位置
	public static AtomicInteger curFileOffset = new AtomicInteger(0);
	public static DataSourceManager dataSource = new DataSourceManager();
}
