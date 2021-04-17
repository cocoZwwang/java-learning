package pers.cocoadel.java.learning.GenerateType;

import com.google.common.reflect.TypeToken;
import pers.cocoadel.java.learning.GenerateType.bean.CommConverter;
import pers.cocoadel.java.learning.GenerateType.bean.Converter;
import pers.cocoadel.java.learning.GenerateType.bean.IntegerConvert;
import pers.cocoadel.java.learning.GenerateType.bean.MyList;

import java.lang.reflect.Array;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class GuavaTypeTokenDemo {

    public static void main(String[] args) {
//        TypeToken<MyList> myListTypeToken = TypeToken.of(MyList.class);
//        TypeToken<ArrayList<String>> typeToken2 = new TypeToken<ArrayList<String>>() {
//        };
//        System.out.println(typeToken2.getSupertype(Iterable.class).getType());

        TypeToken<IntegerConvert> typeToken = TypeToken.of(IntegerConvert.class);
        Type type = typeToken.getSupertype(Converter.class).getType();
        if (type instanceof ParameterizedType) {
            ParameterizedType pt = (ParameterizedType) type;
            System.out.println(pt.getActualTypeArguments()[0]);
        }


//        TypeToken<? super MyList> supertype = myListTypeToken.getSupertype(MyList.class);
//        System.out.println(supertype);



//        TypeToken<? extends MyList> subtype = myListTypeToken.getSubtype(List.class);
//        System.out.println(subtype);
//        TypeToken<MyList>.TypeSet types = myListTypeToken.getTypes();
//        for (TypeToken<? super MyList> type : types) {
//            if(type.getRawType().equals(ArrayList.class)){
//                Type t = type.getType();
//                if (t instanceof ParameterizedType) {
//                    ParameterizedType parameterizedType = (ParameterizedType) t;
//                    parameterizedType.getActualTypeArguments();
//
//                }
//                System.out.println(type);
//                type.getTypes().forEach(System.out::println);
//            }
//        }
//
//        TypeToken<CommConverter<String>> converterTypeToken =new TypeToken<CommConverter<String>>(){};

    }
}
