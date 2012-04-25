package com.elex.newmq.codec;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.apache.mina.filter.codec.demux.MessageEncoder;

public class MemcacheEncoder implements MessageEncoder {

	public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception {
		// TODO Auto-generated method stub
		MemcacheResponseMessage responseMsg = (MemcacheResponseMessage)message;

		IoBuffer buf = IoBuffer.allocate(responseMsg.getSize());
		buf.setAutoExpand(true);

        buf.put(responseMsg.getBytes());
        buf.flip();
        out.write(buf);
	}

}
