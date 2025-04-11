package io.abdul.recursion.basic_recursion.problem5;

// https://takeuforward.org/plus/dsa/beginner-problem/basic-recursion/check-if-string-is-palindrome-or-not-
public class PalindromeCheck {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.palindromeCheck("h"));
        System.out.println(solution.palindromeCheck("he"));
        System.out.println(solution.palindromeCheck("hel"));
        System.out.println(solution.palindromeCheck("hello"));
        System.out.println(solution.palindromeCheck("hannah"));
        System.out.println(solution.palindromeCheck("aabbaaa"));
        System.out.println(solution.palindromeCheck("hah"));
        System.out.println(solution.palindromeCheck("hh"));
    }
}

class Solution {
    public boolean palindromeCheck(String s) {
        return palindromeCheck(s, 0, s.length(), (s.length() / 2));
    }

    public boolean palindromeCheck(String s, int i, int n, int mid) {
        if (i >= mid) { // Stop at mid, all are matched
            return true;
        }
        if (s.charAt(i) != s.charAt(n - i - 1)) { // Stop when no match
            return false;
        }
        return palindromeCheck(s, i + 1, n, mid);
    }
}
