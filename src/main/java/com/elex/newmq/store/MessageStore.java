package com.elex.newmq.store;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;

import com.elex.newmq.share.ShareVariable;

public class MessageStore {
	
	private FileChannel writer;
	private File file;
	private byte[] buffer = new byte[4];
	private ByteBuffer byteBuffer = ByteBuffer.wrap(buffer);
	
	public void init() throws FileNotFoundException{
		file = new File("logs/newmqdata.log");
		writer = (new FileOutputStream(file, true)).getChannel();
	}
	
	public Boolean addValue(String value) throws IOException{
		ByteBuffer buffer = ByteBuffer.allocate(value.length() + 4);
		buffer.order(ByteOrder.LITTLE_ENDIAN);
		buffer.putInt(value.length());
		buffer.put(value.getBytes());
		buffer.flip();
		int re = writer.write(buffer);
		if(re <= 0)
			throw new IOException("write error");
		//重置curFileOffset
	    ShareVariable.curFileOffset.getAndAdd(4 + value.length());
		return true;
	}
	
	public FileChannel getWriter(){
		return this.writer;
	}
	/**
	 * 从持久化文件中恢复数据
	 * @return
	 */
	public void replay(){
		
	}
	/**
	 * 从持久化文件中取出单条数据
	 * @param in 文件对应的filechannel
	 * @return 返回json格式的字符串
	 * @throws IOException
	 */
	private String readItem(FileChannel in) throws IOException{
		//首先获取数据段的长度，根据长度去取得对应数据。
		byteBuffer.rewind();
	    byteBuffer.limit(4);
	    int x = 0;
	    do{
	    	x = in.read(byteBuffer);
	    }while(byteBuffer.position() < byteBuffer.limit() && x >=0);
	    if( x < 0 ){
	    	throw new IOException("Unexpected EOF");
	    }
	    byteBuffer.rewind();
	    int dataLength = byteBuffer.getInt();
	    //根据数据长度去取得对应的数据段
	    byte[] data = new byte[dataLength];
	    ByteBuffer dataBuffer = ByteBuffer.wrap(data);
	    x = 0;
	    do{
	    	x = in.read(dataBuffer);
	    }while(dataBuffer.position() < dataBuffer.limit() && x >=0);
	    //重置curFileOffset
	    ShareVariable.curFileOffset.getAndAdd(4 + dataLength);
	    
		return data.toString();
	}
	//将缓存中的数据提交到硬盘中
	public void fsync() throws IOException{
		this.writer.force(false);
	}
	//是否满足fsync的条件
	public Boolean canFsync(){
		return true;
	}
}
