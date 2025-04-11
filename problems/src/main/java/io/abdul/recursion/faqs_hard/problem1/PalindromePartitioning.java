package io.abdul.recursion.faqs_hard.problem1;

import java.util.ArrayList;
import java.util.List;

public class PalindromePartitioning {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.partition("abba"));
        System.out.println(solution.partition("aabaa"));
    }
}

class Solution {
    /*
    Referer Partitioning to get all the ways a string can be partitioned
     */
    public List<List<String>> partition(String s) {
        List<List<String>> result = new ArrayList<>();
        partition(s, s.length(), 0, result, new ArrayList<>());
        return result;
    }

    private void partition(String s, int n, int position, List<List<String>> result, List<String> buffer) {
        if (position >= n) {
            result.add(new ArrayList<>(buffer));
            return;
        }

        for (int i = position; i < n; i++) {
            if (isPalindrome(s, position, i)) {
                buffer.add(s.substring(position, i + 1));
                partition(s, n, i + 1, result, buffer);
                buffer.remove(buffer.size() - 1);
            }
        }
    }

    private boolean isPalindrome(String text, int from, int to) {
        int length = to - from + 1;
        for (int i = 0; i < length; i++) {
            if (text.charAt(i + from) != text.charAt(to - i)) {
                return false;
            }
        }
        return true;
    }
}