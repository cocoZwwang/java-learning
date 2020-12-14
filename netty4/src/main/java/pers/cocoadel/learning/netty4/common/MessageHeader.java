package pers.cocoadel.learning.netty4.common;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MessageHeader {
    //协议版本
    private Integer version;
    //操作码
    private Integer opCode;
    //会话Id
    private Long streamId;
}
