package pers.cocoadel.learning.algorithm.string;

public interface StringMatch {

    int indexOf(String pattern,String src,int fromIndex);

    default void log(char[] pattern, char[] src, int i) {
        StringBuilder sb = new StringBuilder();
        while (i-- > 0) {
            sb.append(" ");
        }
        System.out.println(new String(src));
        System.out.println(sb.toString() + new String(pattern));
        System.out.println();
    }
}
