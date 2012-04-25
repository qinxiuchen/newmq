package com.elex.newmq.codec;

import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.apache.mina.filter.codec.demux.MessageDecoderAdapter;
import org.apache.mina.filter.codec.demux.MessageDecoderResult;

public class MemcacheDecoder extends MessageDecoderAdapter {

	private CharsetDecoder decoder = Charset.defaultCharset().newDecoder();
	
	public MessageDecoderResult decodable(IoSession session, IoBuffer in) {
		// TODO Auto-generated method stub
		return MessageDecoderResult.OK;
	}

	public MessageDecoderResult decode(IoSession session, IoBuffer in,
			ProtocolDecoderOutput out) throws Exception {
		// TODO Auto-generated method stub
		MemcacheRequestMessage requestMsg = new MemcacheRequestMessage(in.getString(this.decoder));	
		out.write(requestMsg);
		return MessageDecoderResult.OK;
	}
	
}
