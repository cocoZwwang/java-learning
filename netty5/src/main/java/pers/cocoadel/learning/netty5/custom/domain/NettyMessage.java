package pers.cocoadel.learning.netty5.custom.domain;

import io.netty.handler.codec.http.FullHttpRequest;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class NettyMessage implements Serializable {
    private static final long serialVersionUID = -8883417370313709604L;
    private Header header;

    private Object body;

    public Header getHeader() {
        return header;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    private NettyMessage(){

    }

    @Override
    public String toString() {
        return "NettyMessage{" +
                "header=" + header +
                ", body=" + body +
                '}';
    }

    public static NettyMessage createNettyMessage() {
        Header header = new Header();
        int crcCode = (0XABEF << 2) + (1 << 1);
        header.setCrcCode(crcCode);
        header.setPriority((byte) 0);
        header.attachment = new HashMap<>();
        NettyMessage nettyMessage = new NettyMessage();
        nettyMessage.header = header;
        return nettyMessage;
    }


    public static class Header implements Serializable {
        private static final long serialVersionUID = -3614739299454500553L;
        private int crcCode;

        private int length;

        private long sessionID;

        private byte type;

        private byte priority;

        private Map<String,Object> attachment;

        private Header(){

        }

        public int getCrcCode() {
            return crcCode;
        }

        public void setCrcCode(int crcCode) {
            this.crcCode = crcCode;
        }

        public int getLength() {
            return length;
        }

        public void setLength(int length) {
            this.length = length;
        }

        public long getSessionID() {
            return sessionID;
        }

        public void setSessionID(long sessionID) {
            this.sessionID = sessionID;
        }

        public byte getType() {
            return type;
        }

        public void setType(byte type) {
            this.type = type;
        }

        public byte getPriority() {
            return priority;
        }

        public void setPriority(byte priority) {
            this.priority = priority;
        }

        public Map<String, Object> getAttachment() {
            return attachment;
        }

        @Override
        public String toString() {
            return "Header{" +
                    "crcCode=" + crcCode +
                    ", length=" + length +
                    ", sessionID=" + sessionID +
                    ", type=" + type +
                    ", priority=" + priority +
                    ", attachment=" + attachment +
                    '}';
        }
    }
}
