package per.cocoadel.learing.jvm.bytecode;

import java.util.HashMap;
import java.util.Map;

public class Hello {
    private final Map<Integer, Integer> resCache = new HashMap<>();

    public int fibonacci(int n){
        if(n <= 1){
            return n;
        }
        if(!resCache.containsKey(n)){
            int f1 = 1;
            int f2 = 2;
            for(int i = 3; i <= n; i++){
                int res = f1 + f2;
                f1 = f2;
                f2 = res;
            }
            resCache.put(n,f2);
        }
        return resCache.get(n);
    }
}
