package pers.cocoadel.learning.netty4.client.dispatcher;

import pers.cocoadel.learning.netty4.common.ResponseMessage;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RequestPendingCenter {

    private final Map<Long, RequestFuture> map = new ConcurrentHashMap<>();

    public void add(long streamId, RequestFuture requestFuture) {
        map.put(streamId,requestFuture);
    }

    public void set(long streamId, ResponseMessage responseMessage) {
        if (map.containsKey(streamId)) {
            RequestFuture future = map.get(streamId);
            future.setSuccess(responseMessage);
            map.remove(streamId);
        }
    }
}
