package pers.cocoadel.learning.algorithm.string;


public class KMPStringMatcher implements StringMatch {
    @Override
    public int indexOf(String pattern, String src, int fromIndex) {
        return indexOf(pattern.toCharArray(), src.toCharArray(), fromIndex);
    }


    private int indexOf(char[] pattern, char[] src, int fromIndex){
        int m = pattern.length;
        int n = src.length;
        if(m == 0){
            return fromIndex;
        }
        if (m > n - fromIndex) {
            return - 1;
        }
        int[] next = pretreatmentPrefix(pattern);
        int q = -1;
        for(int i = fromIndex; i < n; i++){
            while (q > -1 && pattern[q + 1] != src[i]) {
                q = next[q];
            }

            if (pattern[q + 1] == src[i]) {
                q++;
            }

            if (q == m - 1) {
                return i - m + 1;
            }
        }
        return -1;
    }

    private int[] pretreatmentPrefix(char[] pattern) {
        int m = pattern.length;
        int[] next = new int[m];
        next[0] = -1;
        int q = -1;
        for(int i = 1; i < m; i++){
            //q == - 1 或者找到相等字符
            while(q > - 1 && pattern[i] != pattern[q + 1]){
                q = next[q];
            }

            if (pattern[i] == pattern[q + 1]) {
                q++;
            }

            next[i] = q;
        }
        return next;
    }
}
