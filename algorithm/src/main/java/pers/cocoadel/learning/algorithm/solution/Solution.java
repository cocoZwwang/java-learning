package pers.cocoadel.learning.algorithm.solution;


import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

class Solution {
    //10 ^ 5
//    Set<Integer> removeSet = new HashSet<>();
//    int[][] f;
//    public int maximumRemovals(String s, String p, int[] removable) {
//        int len = s.length();
//        f = new int[len + 1][26];
//        Arrays.fill(f[len],len + 1);
//        for(int i = len - 1; i >= 0; i--){
//            char ch = s.charAt(i);
//            for(char letter = 'a'; letter <= 'z'; letter++){
//                int j = letter - 'a';
//                if(letter == ch){
//                    f[i][j] = i;
//                }else{
//                    f[i][j] = f[i + 1][j];
//                }
//            }
//        }
//
//        for (int i = 0; i < removable.length; i++) {
//            removeSet.add(removable[i]);
//            if (!isCanRemove(s, p)) {
//                return i;
//            }
//        }
//        return removable.length;
//    }
//
//    private boolean isCanRemove(String s, String p) {
//
//    }

    Set<Integer> removeSet = new HashSet<>();
    public int maximumRemovals(String s, String p, int[] removable) {
        int left = 0;
        int right = s.length() - 1;

        while (left <= right){
            int mid = (left + right) >>> 1;
            removeSet = new HashSet<>();
            for(int i = 0; i <= mid; i++){
                removeSet.add(removable[i]);
            }
            if (isCanRemove(s, p)) {
                left = mid + 1;
            }else {
                right = mid - 1;
            }
        }
        return left;
    }

    private boolean isCanRemove(String s, String p){
        int j = 0;
        for(int i = 0; i < s.length() && j < p.length(); i++){
            if(removeSet.contains(i)){
                continue;
            }
            if(s.charAt(i) == p.charAt(j)){
                j++;
            }
        }
        return j == p.length();
    }
}
