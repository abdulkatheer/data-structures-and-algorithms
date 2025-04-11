package io.abdul.strings.basic_strings.problem7;

import java.util.Arrays;

// https://takeuforward.org/plus/dsa/beginner-problem/basic-strings/valid-anagram
public class Anagram {
    public static void main(String[] args) {
//        Solution solution = new Solution();
        Solution2 solution = new Solution2();
        System.out.println(solution.anagramStrings("anagram", "nagaram"));
        System.out.println(solution.anagramStrings("dog", "cat"));
        System.out.println(solution.anagramStrings("eat", "tea"));
    }
}

/*
T - O(n log(n)
S - O(1)
 */
class Solution {
    public boolean anagramStrings(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }

        char[] sArr = s.toCharArray();
        char[] tArr = t.toCharArray();
        Arrays.sort(sArr);
        Arrays.sort(tArr);

        for (int i = 0; i < sArr.length; i++) {
            if (sArr[i] != tArr[i]) {
                return false;
            }
        }

        return true;
    }
}

/*
T - O(n)
S - O(1)
 */
class Solution2 {
    public boolean anagramStrings(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }

        int[] sCounts = new int[26];
        int asciiStart = 97;

        int length = s.length();
        for (int i = 0; i < length; i++) {
            sCounts[((int) s.charAt(i)) - asciiStart]++;
        }

        for (int i = 0; i < length; i++) {
            sCounts[((int) t.charAt(i)) - asciiStart]--;
        }

        for (int sCount : sCounts) {
            if (sCount != 0) {
                return false;
            }
        }

        return true;
    }
}
