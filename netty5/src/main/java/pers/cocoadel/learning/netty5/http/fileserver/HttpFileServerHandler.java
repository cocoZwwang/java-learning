package pers.cocoadel.learning.netty5.http.fileserver;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.stream.ChunkedFile;
import io.netty.util.CharsetUtil;

import javax.activation.MimetypesFileTypeMap;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.regex.Pattern;

import static io.netty.handler.codec.http.HttpHeaders.Names.LOCATION;
import static io.netty.handler.codec.http.HttpResponseStatus.FOUND;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

public class HttpFileServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private static final Pattern ALLOWED_FILE_NAME = Pattern.compile("[A-Za-z0-9][-_A-Za-z0-9\\.]*");
    private static final Pattern INSECURE_URI = Pattern.compile(".*[<>&\"].*");

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if (ctx.channel().isActive()) {
            sendError(ctx, HttpResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        //判断request解码是否成功
        if (!request.getDecoderResult().isSuccess()) {
            sendError(ctx, HttpResponseStatus.BAD_REQUEST);
            return;
        }
        //判断Method是否是GET
        if (request.getMethod() != HttpMethod.GET) {
            sendError(ctx, HttpResponseStatus.METHOD_NOT_ALLOWED);
            return;
        }
        //获取uri
        String uri = request.getUri();
        //拼接当前工程目录
        String path = sanitizeUri(uri);

        //文件路径为null？文件隐藏？ 文件不存在？
        if (path == null) {
            sendError(ctx, HttpResponseStatus.FORBIDDEN);
            return;
        }
        File file = new File(path);
        if (file.isHidden() || !file.exists()) {
            sendError(ctx, HttpResponseStatus.NO_CONTENT);
            return;
        }

        //如果文件是目录返回目录连接
        if (file.isDirectory()) {
            if (uri.endsWith("/")) {
                sendListing(ctx, file, uri);
            } else {
                sendRedirect(ctx, uri + "/");
            }
            return;
        }

        //不是文件返回错误
        if (!file.isFile()) {
            sendError(ctx, HttpResponseStatus.FORBIDDEN);
            return;
        }

        //通过RandomAccessFile打开文件
        RandomAccessFile randomAccessFile = null;
        try {
            randomAccessFile = new RandomAccessFile(file, "r");
        } catch (FileNotFoundException e) {
            sendError(ctx, HttpResponseStatus.NOT_FOUND);
            return;
        }
        //读取文件长度
        long length = randomAccessFile.length();
        //设置rsp返回头，内容长度、内容类型
        //这里使用DefaultFullHttpResponse不行，为什么呢？
        HttpResponse response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
        MimetypesFileTypeMap mimetypesFileTypeMap = new MimetypesFileTypeMap();
        response.headers().add(HttpHeaders.Names.CONTENT_TYPE, mimetypesFileTypeMap.getContentType(file.getPath()));
        response.headers().add(HttpHeaders.Names.CONTENT_LENGTH, length);
        //写入rsp
        if (HttpHeaders.isKeepAlive(request)) {
            response.headers().add(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);

        }
        ctx.write(response);
        //通过ChunkedFile写入缓冲区
        ChannelFuture sendFuture = ctx.write(new ChunkedFile(randomAccessFile, 0, length, 8192),
                ctx.newProgressivePromise());
        //添加监听器
        sendFuture.addListener(new ChannelProgressiveFutureListener() {
            @Override
            public void operationProgressed(ChannelProgressiveFuture future, long progress, long total) throws Exception {
                if (total < 0) {
                    System.err.println("Transfer Progress: " + progress);
                } else {
                    System.err.println("Transfer Progress: " + progress / total);
                }
            }

            @Override
            public void operationComplete(ChannelProgressiveFuture future) throws Exception {
                System.out.printf("文件[%s]传输完毕\n", path);
            }
        });
        //写入LastContent的空体消息
        ChannelFuture cf = ctx.write(LastHttpContent.EMPTY_LAST_CONTENT);
        if (!HttpHeaders.isKeepAlive(request)) {
            cf.addListener(ChannelFutureListener.CLOSE);
        }
        ctx.flush();
    }

    private static void sendError(ChannelHandlerContext ctx, HttpResponseStatus status) {
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status);
        response.headers().add("Content-Type", "text/html;charset=utf-8");
        response.headers().add("Content-Length", response.content().readableBytes());
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

    private String sanitizeUri(String uri) {
        //对URI进行编码
        try {
            uri = URLDecoder.decode(uri, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            try {
                uri = URLDecoder.decode(uri, "ISO-88591-1");
            } catch (UnsupportedEncodingException unsupportedEncodingException) {
                throw new Error();
            }
        }
        //转换成文件路径
        String path = uri.replace('/', File.separatorChar);
        //判断文件路径是否合法
        if (path.contains(File.separator + ".") || path.contains("." + File.separator) ||
                path.startsWith(".") || path.endsWith(".") || INSECURE_URI.matcher(path).matches()) {
            return null;
        }
        //返回绝对路劲
        return System.getProperty("user.dir") + File.separator + path;
    }

    private static void sendRedirect(ChannelHandlerContext ctx, String newUri) {
        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, FOUND);
        response.headers().set(LOCATION, newUri);
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

    private static void sendListing(ChannelHandlerContext ctx, File file, String uri) {
        //拼接HTML的代码
        File[] files = file.listFiles();
        StringBuilder sb = new StringBuilder("<html>");
        sb.append("<body>");
        sb.append("<h2>");
        sb.append(file.getAbsolutePath());
        sb.append("</h2>");
        sb.append("<ul>");
        sb.append("<li>");
        sb.append("<a href=\"\">..");
        sb.append("</a>");
        sb.append("<li>");
        if (files != null) {
            for (File f : files) {
                if (!f.exists() || f.isHidden()) {
                    continue;
                }
                String fName = f.getName();
                if (!ALLOWED_FILE_NAME.matcher(fName).matches()) {
                    continue;
                }
                sb.append("<li>");
                sb.append(String.format("<a href=\"%s\">%s", fName, fName));
                sb.append("</a>");
                sb.append("<li>");
            }
        }
        sb.append("</ul>");
        sb.append("</body>");
        sb.append("</html>");
        //bytebuf
        ByteBuf byteBuf = Unpooled.wrappedBuffer(sb.toString().getBytes(CharsetUtil.UTF_8));
        //构建Rsp
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, byteBuf);
        //设置contentType
        response.headers().add("Content-Type", "text/html;charset=utf-8");
        response.headers().add("Content-Length", response.content().readableBytes());
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }
}
