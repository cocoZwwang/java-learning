package pers.cocoadel.uncles.comm;

import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.ToString;

import java.nio.charset.StandardCharsets;

@ToString
@Data
public class Greet {
    public static final String meet = "遇见";
    public static final String rq0 = "吃了没，你呐？";
    public static final String rq1 = "你这，嘛去？";
    public static final String rq2 = "有空家里坐坐啊！";
    public static final String rs0 = "刚吃。";
    public static final String rs1 = "嗨，没事儿溜溜弯儿。";
    public static final String rs2 = "回头去给老太太请安！";

    private Long streamId;

    private String message;

    public void decode(ByteBuf buf) {
        streamId = buf.readLong();
        byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);
        message = new String(bytes, StandardCharsets.UTF_8);
    }

    public void encode(ByteBuf out) {
        out.writeLong(streamId);
        out.writeBytes(message.getBytes(StandardCharsets.UTF_8));
    }
}
