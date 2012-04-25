package com.elex.newmq.service;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import com.elex.newmq.codec.MemcacheProtocolCodecFactory;
import com.elex.newmq.constant.MQConfig;
import com.elex.newmq.handler.MemcacheHandler;

/**
 * 基于mina构建的tcp server服务器，用于接收连接来的mc请求
 * @author qxc
 *
 */

public class RemotingServer implements IService{

	private NioSocketAcceptor acceptor;
	//绑定的ip地址
	private String bindAddr;
	//监听的端口
	private int port;
	private int processorNum;
	
	public void init(HierarchicalConfiguration configure) {
		//默认绑定的ip地址
		this.bindAddr = configure.getString("bindaddr", MQConfig.DEFAULT_ADDR);
		//从配置文件中读取监听的端口
		this.port = configure.getInt("bindport", MQConfig.DEFAULT_PORT);
		//从配置文件中读取启动的线程数目,默认为cpu数目+2
		this.processorNum = configure.getInt("processornum", MQConfig.PROCESSOR_NUM);
		// Create an acceptor
    	acceptor = new NioSocketAcceptor(this.processorNum);
    	 // Create a protocolFilter
    	acceptor.getFilterChain().addLast("protocolFilter",
    			new ProtocolCodecFilter(new MemcacheProtocolCodecFactory()));
    	//set handler     	
        acceptor.setHandler(new MemcacheHandler());
	}

	public void start() throws IOException{
		acceptor.bind(new InetSocketAddress(bindAddr, port));
		System.out.println("Server now listening on port " + port);
	}

	public void stop() {
		// TODO Auto-generated method stub
		
	}

	public void reload() {
		// TODO Auto-generated method stub
		
	}

}
