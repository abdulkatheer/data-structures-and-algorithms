package io.abdul.recursion.implementation_problems.problem6;

import java.util.ArrayList;
import java.util.List;

public class Partitioning {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.partition("abc"));
    }
}

/*
T - O(2^n)
O - O(n)
 */
class Solution {
    public List<List<String>> partition(String s) {
        List<List<String>> result = new ArrayList<>();
        partition(s, s.length(), 0, new ArrayList<>(), result);
        return result;
    }

    private void partition(String s, int n, int position, List<String> buffer, List<List<String>> result) {
        if (position >= n) {
            result.add(new ArrayList<>(buffer));
            return;
        }

        for (int i = position; i < n; i++) {
            buffer.add(s.substring(position, i + 1));
            partition(s, n, i + 1, buffer, result);
            buffer.remove(buffer.size() - 1);
        }
    }
}
