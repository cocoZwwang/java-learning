package pers.cocoadel.learning.netty5.custom.domain;

public enum NettyMessageType {
    /**
     * 0：业务请求消息
     */
    BUSINESS_REQ(0),
    /**
     * 1：业务响应消息
     */
    Business_rsp(1),
    /**
     * 2：业务ONE WAY消息（即是请求消息也是响应消息）
     */
    ONE_WAY(2),
    /**
     * 3：握手请求消息
     */
    LOGIN_REQ(3),
    /**
     * 4：握手响应消息
     */
    LOGIN_RSP(4),
    /**
     * 5：心跳请求消息
     */
    HEART_BEAT_REQ(5),
    /**
     * 6：心跳响应消息
     */
    HEART_BEAT_RSP(6);
    public final int code;

    NettyMessageType(int code) {
        this.code = code;
    }

    public NettyMessageType getNettyMessageType(int code){
        for (NettyMessageType type : values()) {
            if (code == type.code) {
                return type;
            }
        }
        return null;
    }
}
