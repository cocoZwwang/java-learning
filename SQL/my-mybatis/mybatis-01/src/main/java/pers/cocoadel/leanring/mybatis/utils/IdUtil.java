package pers.cocoadel.leanring.mybatis.utils;

import java.util.UUID;

public class IdUtil {

    public static String nextId(){
        return UUID.randomUUID().toString().replace("-", "");
    }
}
