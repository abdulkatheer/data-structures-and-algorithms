package io.abdul.maths.basic_maths.problem13;

import java.util.ArrayList;
import java.util.Arrays;

// https://takeuforward.org/plus/dsa/beginner-problem/basic-maths/divisors-of-a-number
public class Divisors {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(Arrays.toString(solution.divisors(6)));
    }
}

class Solution {
    public int[] divisors(int n) {
        ArrayList<Integer> divisors = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            if (n % i == 0) {
                divisors.add(i);
            }
        }
        return divisors.stream().mapToInt(Integer::intValue).toArray();
    }
}