package pers.cocoadel.java.learning.base;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;

public class GsonGeneric {

    public static void main(String[] args) {
        List<Map<String,User>> list = new ArrayList<>();
        Gson gson = new Gson();
        TypeToken<List<Map<String,User>>> typeToken = new TypeToken<List<Map<String,User>>>(){};

        String json = gson.toJson(typeToken);
        System.out.println(typeToken.toString());

        LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<>();
        ConcurrentHashMap<String, Object> map = new ConcurrentHashMap<>();
        ConcurrentSkipListMap<String, Object> listMap = new ConcurrentSkipListMap<>();
    }
}
