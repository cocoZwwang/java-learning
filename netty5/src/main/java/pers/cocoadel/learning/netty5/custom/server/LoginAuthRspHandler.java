package pers.cocoadel.learning.netty5.custom.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import pers.cocoadel.learning.netty5.custom.domain.NettyMessage;
import pers.cocoadel.learning.netty5.custom.domain.NettyMessageType;
import java.util.HashSet;
import java.util.Set;

/**
 * 登录请求响应
 */
public class LoginAuthRspHandler extends SimpleChannelInboundHandler<NettyMessage> {

    private final Set<String> whiteIps = new HashSet<>();
    private final Set<String> loginIps = new HashSet<>();

    public LoginAuthRspHandler(){
        whiteIps.add("127.0.0.1");
        whiteIps.add("localhost");
    }

    private String getIp(ChannelHandlerContext ctx){
        String s = ctx.channel().remoteAddress().toString();
        if( s.startsWith("/")){
            s = s.substring(1, s.length() - 1);
        }
        String[] ss = s.split(":");
        return ss[0];
    }

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, NettyMessage msg) throws Exception {
        NettyMessage.Header header = msg.getHeader();
        if (header != null && header.getType() == NettyMessageType.LOGIN_REQ.code) {
            String ip = getIp(ctx);
            System.out.println("receive the login req from client,ip:" + ip);
            //白名单过滤
            if(!whiteIps.contains(ip)){
                System.out.printf("ip:%s-不在白名单范围内\n",ip);
                sendRsp(ctx, 1);
                return;
            }
            //不允许重复登录
            if(loginIps.contains(ip)){
                System.out.printf("ip%s-已经登录，不允许重复登录\n",ip);
                sendRsp(ctx,  1);
            }
            loginIps.add(ip);
            sendRsp(ctx, 0);
        }else{
            ctx.fireChannelRead(msg);
        }
    }

    private void sendRsp(ChannelHandlerContext ctx, int res) {
        NettyMessage nettyMessage = NettyMessage.createNettyMessage();
        NettyMessage.Header header = nettyMessage.getHeader();
        header.setType((byte) NettyMessageType.LOGIN_RSP.code);
        Byte body = Byte.parseByte("" + res);
        nettyMessage.setBody(body);
        ctx.writeAndFlush(nettyMessage);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        String ip = ctx.channel().remoteAddress().toString();
        loginIps.remove(ip);
        //清除登录缓存
        ctx.close();
    }

}
