package com.elex.newmq.store;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class QItem {
	
	public static ByteBuffer pack(String value){
		ByteBuffer buffer = ByteBuffer.allocate(value.length());
		buffer.order(ByteOrder.LITTLE_ENDIAN);
		buffer.putInt(value.length());
		buffer.put(value.getBytes());
		buffer.flip();
		return buffer;
	}

}
