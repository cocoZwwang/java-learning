package pers.cocoadel.learning.algorithm.string;

/**
 * 简单的BM匹配实现
 * 【好后缀匹配】 和 【好后缀字串匹配模式串前缀 】都是通过暴力方式进行匹配
 * 主要是用于理解 BM 匹配算法的流程
 */
public class SimpleBMStringMatcher implements StringMatch{

    /**
     * 匹配字符串
     * @param pattern 模式串（匹配串）
     * @param src 主串
     * @param fromIndex 从下标 fromIndex 开始匹配
     * @return 如果匹配成功返回字串的起始下标，否则返回 -1
     */
    @Override
    public int indexOf(String pattern, String src, int fromIndex) {
        return indexOf(pattern.toCharArray(), src.toCharArray(), fromIndex);
    }

    private int indexOf(char[] pattern, char[] src, int fromIndex){
        int srcLen = src.length - fromIndex;
        if (pattern.length > srcLen) {
            return -1;
        }

        for(int i = fromIndex; i <= src.length - pattern.length;){
            //从模式串的最后一个位置开始往前匹配
            int badIndex = -1;
            int goodIndex = -1;
            for(int j = pattern.length - 1; j >= 0; j--){
                if(pattern[j] != src[i + j]){
                    badIndex = j;
                    break;
                }
                goodIndex = j;
            }

            //匹配从成功
            if(badIndex == -1){
                return i;
            }

            int preBadIndex = findPreBadChar(pattern, badIndex - 1, src[i + badIndex]);
            int offset = badIndex - preBadIndex;
//            System.out.printf("i:%s badIndex: %s badChar:%s preBadIndex:%s offset:%s%n",
//                    i, badIndex, src[i + badIndex], preBadIndex, offset);
            //如果存在好后缀
            if(goodIndex > -1){
                int preGoodIndex = findPreGoodChars(pattern,goodIndex);
                int goodOffset = goodIndex - preGoodIndex;
                if(preGoodIndex == -1){
                    //前面好后缀已经匹配失败，所以好后缀本身不需要匹配
                    preGoodIndex = findGoodSuffix(pattern,goodIndex + 1);
                    goodOffset = preGoodIndex;
                }
                // 坏字符规则、好后缀规则，计算的偏移量，取大的一个
                offset = Math.max(goodOffset,offset);
//                System.out.printf("i:%s goodIndex: %s badChar:%s preBadIndex:%s offset:%s%n",
//                        i, goodIndex, src[i + badIndex], preGoodIndex, offset);
            }
            i += offset;
        }
        return -1;
    }
    //往前找下一个坏字符
    private int findPreBadChar(char[] pattern, int index, char srcBadChar) {
        for(int i = index; i >= 0; i--){
            if(pattern[i] == srcBadChar){
                return i;
            }
        }
        return -1;
    }

    //往前找另外一个好后缀字串
    private int findPreGoodChars(char[] pattern, int goodIndex) {
        for(int i = goodIndex - 1; i >= 0; i--){
            int j = pattern.length - 1;
            int pos = i;
            while(j >= goodIndex && pos >= 0 && pattern[pos] == pattern[j]){
                j--;
                pos--;
            }
            if(j == goodIndex - 1){
                return pos - 1;
            }
        }
        return -1;
    }

    //找和好后缀字串匹配的前缀字串
    private int findGoodSuffix(char[] pattern, int goodIndex) {
        if(goodIndex == pattern.length){
            return -1;
        }
        int j = goodIndex;
        int i = 0;
        while (i < pattern.length - goodIndex && pattern[i] == pattern[j]) {
            i++;
            j++;
        }
        if(j == pattern.length){
            return goodIndex;
        }
        return findGoodSuffix(pattern,goodIndex + 1);
    }


}
