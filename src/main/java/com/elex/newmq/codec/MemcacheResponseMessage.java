package com.elex.newmq.codec;

import com.elex.newmq.constant.MemcacheConstant;

public class MemcacheResponseMessage {

	//返回信息
	private String info;
	
	public MemcacheResponseMessage(String info){
		this.info = info;
		this.info += MemcacheConstant.END;
	}
	//获得iobuffer的数据源
	public byte[] getBytes(){
		return this.info.getBytes();
	}
	//获取iobuffer需要分配的空间大小
	public int getSize(){
		return this.info.length();
	}
	public String getInfo(){
		return this.info;
	}
}
