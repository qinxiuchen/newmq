package com.elex.newmq.constant;

/**
 * 定义一些默认的常量
 * @author qxc
 *
 */
public class MQConfig {
	public static int DEFAULT_PORT					= 9999;
	public static String DEFAULT_ADDR				= "127.0.0.1";
	public static int PROCESSOR_NUM					= Runtime.getRuntime().availableProcessors() + 2;
	public static int DB_PROCESSOR_NUM				= Runtime.getRuntime().availableProcessors() + 2;
	public static String DEFAULT_CONFIG_PATH 		= "conf/newmq.xml";
	public static int MAX_DBCONN_SIZE				= 20;
}
