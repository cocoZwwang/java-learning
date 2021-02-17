package com.pers.cocoadel.hystrix;

import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixObservableCommand;
import com.netflix.hystrix.exception.HystrixBadRequestException;
import okhttp3.*;
import rx.Observable;
import rx.Subscriber;

import java.io.IOException;
import java.time.Duration;

public class FindByIdCommand extends HystrixObservableCommand<Response> {
    private static final OkHttpClient OK_HTTP_CLIENT = new OkHttpClient.Builder()
            .callTimeout(Duration.ofSeconds(2))
            .build();

    protected FindByIdCommand(String group) {
        super(HystrixCommandGroupKey.Factory.asKey(group));
    }


    @Override
    protected Observable<Response> construct() {
        return Observable.create(subscriber -> {
            try {
                Response response = doRequest();
                subscriber.onNext(response);
            } catch (Exception e) {
                subscriber.onError(e);
            }
        });
    }

    private Response doRequest() {
        String url = "http://localhost:9197/http/order/findById?id=1";
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        try {
            return OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new HystrixBadRequestException(e.getMessage());
        }
    }

    @Override
    protected Observable<Response> resumeWithFallback() {
        return Observable.create(subscriber -> doFallback());
    }

    private void doFallback(){
        System.out.println("do fallback!!");
    }

    public static void main(String[] args) throws IOException {
        FindByIdCommand command = new FindByIdCommand("order");
        command.toObservable().subscribe(new Subscriber<Response>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println(throwable.getMessage());
            }

            @Override
            public void onNext(Response response) {
                if (response.isSuccessful()) {
                    try {
                        System.out.println(response.body().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        System.in.read();
    }
}
