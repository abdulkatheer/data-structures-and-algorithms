package io.abdul.strings.basic_strings.problem5;

// https://takeuforward.org/plus/dsa/beginner-problem/basic-strings/isomorphic-string
public class IsomorphicString {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.isomorphicString("egg", "add"));
        System.out.println(solution.isomorphicString("apple", "bbnbm"));
        System.out.println(solution.isomorphicString("paper", "title"));
    }
}

/*
T - O(n)
S - O(1)
 */
class Solution {
    public boolean isomorphicString(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }
        int totalChars = 26;
        int asciiStart = 97;
        int[] sCount = new int[totalChars];
        int[] tCount = new int[totalChars];

        int length = s.length();
        for (int i = 0; i < length; i++) {
            int sIndex = ((int) s.charAt(i)) - asciiStart;
            int tIndex = ((int) t.charAt(i)) - asciiStart;
            if (sCount[sIndex] != tCount[tIndex]) {
                return false;
            }
            sCount[sIndex]++;
            tCount[tIndex]++;
        }

        return true;
    }
}

