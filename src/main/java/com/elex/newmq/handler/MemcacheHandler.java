package com.elex.newmq.handler;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

import com.elex.newmq.codec.MemcacheRequestMessage;
import com.elex.newmq.codec.MemcacheResponseMessage;
import com.elex.newmq.constant.MemcacheCommand;
import com.elex.newmq.constant.MemcacheConstant;

public class MemcacheHandler extends IoHandlerAdapter {
	
	//接收到消息后的处理
	public void messageReceived(IoSession session, Object message) {
		MemcacheRequestMessage requestMsg = (MemcacheRequestMessage)message;
		MemcacheResponseMessage rep = null;
//		String re = "VALUE test 0 5\r\nhello\r\n";
//		MemcacheResponseMessage rep = new MemcacheResponseMessage(re);
//		session.write(rep);
		String command = requestMsg.getlines()[0];
		switch(MemcacheCommand.getCommand(command)){
		case get:
			break;
		case set:
			rep = new MemcacheResponseMessage(MemcacheConstant.STORED);
			break;
		case stats:
			break;
		case add:
			break;
		case delete:
			break;
		}
		session.write(rep);
	}
}
