package pers.cocoadel.learning.algorithm.string;

public class KMPMatch implements StringMatch {
    @Override
    public int indexOf(String pattern, String src, int fromIndex) {
        char[] patternChars = pattern.toCharArray();
        char[] srcChars = src.toCharArray();
        return indexOf(patternChars, srcChars, fromIndex, srcChars.length);
    }

    private int indexOf(char[] pattern, char[] src, int fromIndex, int endIndex) {
        int pLen = pattern.length;
        if (pLen == 0) {
            return fromIndex;
        }
        int[] next = new int[pLen];
        initNext(pattern, next);
        int j = 0;
        for (int i = fromIndex; i < endIndex; i++) {
            while (j > 0 && pattern[j] != src[i]) {
                j = next[j - 1] + 1;
            }
            if (pattern[j] == src[i]) {
                j++;
            }
//            log(pattern,src,i - j);
            if (j == pLen) {
                return i - pLen + 1;
            }
        }
        return -1;
    }

    private void initNext(char[] pattern, int[] next) {
        int len = pattern.length;
        if (len == 0) {
            return;
        }
        next[0] = -1;
        int k = -1;
        for (int i = 1; i < len - 1; i++) {
            while (k != -1 && pattern[k + 1] != pattern[i]) {
                k = next[k];
            }
            if (pattern[k + 1] == pattern[i]) {
                k++;
            }
            next[i] = -1;
        }
    }
}
