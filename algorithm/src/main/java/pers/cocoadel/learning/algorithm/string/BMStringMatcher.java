package pers.cocoadel.learning.algorithm.string;

import java.util.Arrays;

public class BMStringMatcher implements StringMatch {
    private static final int CODE_SIZE = 256;

    @Override
    public int indexOf(String pattern, String src, int fromIndex) {
        return indexOf(pattern.toCharArray(), src.toCharArray(), fromIndex);
    }

    /**
     *
     * @param pattern 模式串
     * @param src 主串
     * @param fromIndex 从主串下标 fromIndex 开始匹配
     * @return 返回匹配的第一个字串的起始下标，如果匹配失败则返回 -1。
     */
    private int indexOf(char[] pattern, char[] src, int fromIndex) {
        int srcLen = src.length - fromIndex;
        if (pattern.length > srcLen) {
            return -1;
        }
        //字符集，这里只考虑 ascii 码
        int[] lastIndexOfArr = new int[CODE_SIZE];
        pretreatmentLastIndexInPattern(lastIndexOfArr, pattern);
        //下标 i: 表示模式串好后缀长度
        //记录最后一个跟【长度为 i 的好后缀字串】匹配的字串的起始坐标。
        int[] suffix = new int[pattern.length];
        //记录长度为 i 的后缀是否存在跟它匹配的前缀
        boolean[] prefix = new boolean[pattern.length];
        pretreatmentGoodSuffix(suffix, prefix, pattern);
        for (int i = fromIndex; i < src.length - pattern.length + 1; ) {
            int badIndex = -1;
            int goodIndex = -1;
            for (int j = pattern.length - 1; j >= 0; j--) {
                if (pattern[j] != src[i + j]) {
                    badIndex = j;
                    break;
                }
                goodIndex = j;
            }
            //匹配成功
            if (badIndex == -1) {
                return i;
            }

            int preBadIndex = findPreBadIndex(lastIndexOfArr, src[i + badIndex]);
            int offset = badIndex - preBadIndex;
//            System.out.printf("i:%s badIndex: %s badChar:%s preBadIndex:%s offset:%s%n",
//                i, badIndex, src[i + badIndex], preBadIndex, offset);
            //如果存在好后缀
            if (goodIndex > -1) {
                int goodLen = pattern.length - goodIndex;
                int preGoodIndex = findPreGoodIndex(suffix, goodLen);
                int goodOffset = goodIndex - preGoodIndex;
//                System.out.printf("i:%s goodIndex: %s preGoodIndex:%s goodLen:%s offset:%s%n",
//                        i, goodIndex, preGoodIndex, goodLen, offset);
                //如果好后缀匹配失败，则使用【好后缀】的子串匹配【模式串】的前缀，匹配字串要尽量长。
                if (preGoodIndex == -1) {
                    preGoodIndex = findPrefix(prefix, goodLen);
                    goodOffset = pattern.length - preGoodIndex - 1;
                }
                offset = Math.max(offset, goodOffset);
            }
            offset = offset <= 0 ? 1 : offset;
            i += offset;
        }
        return -1;
    }

    /**
     * 使用 hash code 的方式来存储每个字符集最后出现的位置
     * 相对于 {@link SimpleBMStringMatcher} 的暴力方法，该方法效率更加高。
     * 但是可能会出现返回值是【坏字符】 后面的位置，因为 hash code 没办法从坏字符位置开始往前遍历。
     * 因此一般还需要配合 【好后缀规则】
     */
    private int findPreBadIndex(int[] lastIndexOfArr, char srcBadChar) {
        return lastIndexOfArr[srcBadChar];
    }

    /**
     * 初始化字符集位置数组
     */
    private void pretreatmentLastIndexInPattern(int[] lastIndexOfArr, char[] pattern) {
        Arrays.fill(lastIndexOfArr, -1);
        for (int i = 0; i < pattern.length; i++) {
            lastIndexOfArr[pattern[i]] = i;
        }
    }

    private int findPreGoodIndex(int[] suffix, int suffixLen) {
        return suffix[suffixLen];
    }

    private int findPrefix(boolean[] prefix, int suffixLen) {
        for (int i = suffixLen; i >= 1; i--) {
            if (prefix[i]) {
                return i - 1;
            }
        }
        return -1;
    }

    //suffix: 下标 i 表示模式串好后缀长度
    //记录最后一个跟【长度为 i 的好后缀字串】匹配的字串的起始坐标。
    //prefix: 记录长度为 i 的后缀是否存在跟它匹配的前缀
    private void pretreatmentGoodSuffix(int[] suffix, boolean[] prefix, char[] pattern) {
        Arrays.fill(suffix, -1);
        int len = pattern.length;
        for (int i = 0; i < len - 1; i++) {
            int j = i;
            int k = 0;
            while (j >= 0 && pattern[j] == pattern[len - k - 1]) {
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
