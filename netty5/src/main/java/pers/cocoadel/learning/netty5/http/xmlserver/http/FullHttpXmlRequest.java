package pers.cocoadel.learning.netty5.http.xmlserver.http;

import io.netty.handler.codec.http.FullHttpRequest;

public class FullHttpXmlRequest {
    private FullHttpRequest request;

    private Object body;

    public FullHttpRequest getRequest() {
        return request;
    }

    public void setRequest(FullHttpRequest request) {
        this.request = request;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }
}
