package pers.cocoadel.learning.netty5.http.xmlserver.http;

import io.netty.handler.codec.http.FullHttpResponse;

public class FullHttpXmlResponse {

    private FullHttpResponse response;

    private Object body;

    public FullHttpResponse getResponse() {
        return response;
    }

    public void setResponse(FullHttpResponse response) {
        this.response = response;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }
}
