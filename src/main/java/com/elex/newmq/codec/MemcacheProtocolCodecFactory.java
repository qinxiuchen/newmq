package com.elex.newmq.codec;

import org.apache.mina.filter.codec.demux.DemuxingProtocolCodecFactory;

public class MemcacheProtocolCodecFactory extends DemuxingProtocolCodecFactory{

	public MemcacheProtocolCodecFactory(){
		//增加消息解码器
		super.addMessageDecoder(MemcacheDecoder.class);
		//增加消息编码器
		super.addMessageEncoder(MemcacheResponseMessage.class, MemcacheEncoder.class);
	}
}
