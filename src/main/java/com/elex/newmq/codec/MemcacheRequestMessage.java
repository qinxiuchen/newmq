package com.elex.newmq.codec;

import com.elex.newmq.constant.MemcacheConstant;

public class MemcacheRequestMessage {
	
	//data
	private String[] lines;
	
	public MemcacheRequestMessage(String data){
		//¹ýÂËµô\r\n
		String temp = data.substring(0, data.length()-2);
		//data
		this.lines = temp.split(" |" + MemcacheConstant.SEP);
	}
	
	public String[] getlines(){
		return this.lines;
	}

}
