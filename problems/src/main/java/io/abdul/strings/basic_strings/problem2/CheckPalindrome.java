package io.abdul.strings.basic_strings.problem2;

public class CheckPalindrome {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.palindromeCheck("hannah"));
        System.out.println(solution.palindromeCheck("aabbaaa"));
    }
}

class Solution {
    public boolean palindromeCheck(String s) {
        int mid = s.length() / 2;
        int n = s.length();

        for (int i = 0; i < mid; i++) {
            if (s.charAt(i) != s.charAt(n-i-1)) {
                return false;
            }
        }
        return true;
    }
}

