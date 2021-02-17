package com.pers.cocoadel.hystrix;

import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.util.DigestUtils;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;

public class AKSKDemo {

    private static final OkHttpClient OK_HTTP_CLIENT = new OkHttpClient.Builder()
            .callTimeout(Duration.ofSeconds(2))
            .build();

    public static void main(String[] args) throws IOException {
        Map<String, String> map = new HashMap<>(3);
        //timestamp为毫秒数的字符串形式 String.valueOf(LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli())
        String timestamp = String.valueOf(LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli());
        map.put("timestamp",timestamp);  //值应该为毫秒数的字符串形式
        map.put("path", "/http/order/findById");
        map.put("version", "1.0.0");

        List<String> storedKeys = Arrays.stream(map.keySet()
                .toArray(new String[]{}))
                .sorted(Comparator.naturalOrder())
                .collect(Collectors.toList());
        final String sign = storedKeys.stream()
                .map(key -> String.join("", key, map.get(key)))
                .collect(Collectors.joining()).trim()
                .concat("F256701CA81F4FBFAA4400E7062E9CE6");

        String digest =  DigestUtils.md5DigestAsHex(sign.getBytes()).toUpperCase();

        String url = "http://localhost:9195/http/order/findById?id=1";
        Headers headers = new Headers.Builder()
                .add("timestamp",timestamp)
                .add("appKey","12ED6479082D4B818DFCE62A0CDCD836")
//                .add("sign",digest)
                .add("version","1.0.0")
                .build();
        Request request = new Request.Builder()
                .url(url)
                .headers(headers)
                .get()
                .build();

        Response response = OK_HTTP_CLIENT.newCall(request).execute();

        System.out.println(response.body().string());
    }

    private static String getDigest(Map<String, String> map){
        List<String> storedKeys = Arrays.stream(map.keySet()
                .toArray(new String[]{}))
                .sorted(Comparator.naturalOrder())
                .collect(Collectors.toList());
        final String sign = storedKeys.stream()
                .map(key -> String.join("", key, map.get(key)))
                .collect(Collectors.joining()).trim()
                .concat("D004A4357A964C91A3D8BCBF3C5AADDD");
        return DigestUtils.md5DigestAsHex(sign.getBytes()).toUpperCase();
    }


}
