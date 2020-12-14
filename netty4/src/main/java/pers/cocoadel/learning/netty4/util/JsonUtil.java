package pers.cocoadel.learning.netty4.util;

import com.google.gson.Gson;

public class JsonUtil {
    private final static Gson GSON = new Gson();

    public static String toJson(Object object){
        return GSON.toJson(object);
    }

    public static <T> T toObject(String json,Class<T> type){
        return GSON.fromJson(json,type);
    }
}
