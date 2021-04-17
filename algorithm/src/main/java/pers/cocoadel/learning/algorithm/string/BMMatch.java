package pers.cocoadel.learning.algorithm.string;


import java.util.Arrays;

public class BMMatch implements StringMatch {
    private final static int ENCODING_SIZE = 256;

    @Override
    public int indexOf(String pattern, String src, int fromIndex) {
        char[] patterChars = pattern.toCharArray();
        char[] srcChars = src.toCharArray();
        return indexOf(patterChars, srcChars, fromIndex, srcChars.length);
    }

    public int indexOf(char[] pattern, char[] src, int fromIndex, int endIndex) {
        int pLen = pattern.length;
        //init bc
        int[] bc = new int[ENCODING_SIZE];
        initBadCharArray(pattern, bc);
        //init good suffix
        int[] suffix = new int[pLen];
        boolean[] prefix = new boolean[pLen];
        initGoodSuffix(pattern, suffix, prefix);
        //matching
        for (int i = fromIndex; i < endIndex - pLen + 1;) {
            int j = pLen - 1;
            while (j >= 0 && pattern[j] == src[i + j]) {
                j--;
            }
            if (j == -1) {
                return i;
            }
            // offset by bad char
            char srcChar = src[i + j];
            int offset = j - bc[srcChar];
            // offset by good suffix
            if (j < pLen - 1) {
                int gsLen = pLen - j - 1;
                int gsOffset = pLen;
                if (suffix[gsLen] != -1) {
                    gsOffset = j - suffix[gsLen];
                } else {
                    for (int r = j + 2; r < pLen; r++) {
                        if (prefix[pLen - r]) {
                            gsOffset = r;
                            break;
                        }
                    }
                }
                offset = Math.max(offset, gsOffset);
            }
            i += offset;
            log(pattern, src, i);
        }

        return -1;
    }

    private void initBadCharArray(char[] pattern, int[] bc) {
        Arrays.fill(bc, -1);
        for (int i = 0; i < pattern.length; i++) {
            bc[pattern[i]] = i;
        }
    }

    private void initGoodSuffix(char[] pattern, int[] suffix, boolean[] prefix) {
        int pLen = pattern.length;
        Arrays.fill(suffix, -1);
        for (int i = 0; i < pLen - 1; i++) {
            int j = i;
            int k = 0;
            while (j >= 0 && pattern[j] == pattern[pLen - 1 - k]) {
                j--;
                k++;
                suffix[k] = j + 1;
            }
            if (j == -1) {
                prefix[k] = true;
            }
        }
    }


}
