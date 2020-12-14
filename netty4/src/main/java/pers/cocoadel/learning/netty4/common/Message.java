package pers.cocoadel.learning.netty4.common;

import io.netty.buffer.ByteBuf;
import lombok.Data;
import pers.cocoadel.learning.netty4.util.JsonUtil;

@Data
public abstract class Message<T extends MessageBody> {

    private MessageHeader messageHeader;

    private T messageBody;

    public T getMessageBody(){
        return messageBody;
    }

    public void decode(ByteBuf msg){
        messageHeader = new MessageHeader();
        messageHeader.setVersion(msg.readInt());
        messageHeader.setOpCode(msg.readInt());
        messageHeader.setStreamId(msg.readLong());

        int len = msg.readableBytes();
        byte[] bytes = new byte[len];
        msg.readBytes(bytes);

        messageBody = JsonUtil.toObject(new String(bytes), getMessageBodyClassByOpCode(messageHeader.getOpCode()));
    }

    public void encoder(ByteBuf byteBuf){
        byteBuf.writeInt(messageHeader.getVersion());
        byteBuf.writeInt(messageHeader.getOpCode());
        byteBuf.writeLong(messageHeader.getStreamId());
        byteBuf.writeBytes(JsonUtil.toJson(messageBody).getBytes());
    }

    protected abstract Class<? extends T> getMessageBodyClassByOpCode(int code);

    public MessageHeader getMessageHeader() {
        return messageHeader;
    }

    public void setMessageHeader(MessageHeader messageHeader) {
        this.messageHeader = messageHeader;
    }

    public void setMessageBody(T messageBody) {
        this.messageBody = messageBody;
    }

    @Override
    public String toString() {
        return "Message{" +
                "messageHeader=" + messageHeader.toString() +
                ", messageBody=" + messageBody.toString() +
                '}';
    }
}
