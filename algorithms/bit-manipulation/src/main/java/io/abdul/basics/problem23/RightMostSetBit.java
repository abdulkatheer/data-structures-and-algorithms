package io.abdul.basics.problem23;

public class RightMostSetBit {
    public static void main(String[] args) {
        Solution solution = new Solution();
        for (int i = 1; i <= 20; i++) {
            System.out.println(Integer.toBinaryString(i) + " => " + Integer.toBinaryString(solution.rightMostSetBit(i)));
        }
    }
}

class Solution {
    public int rightMostSetBit(int num) {
        return num & (-num);
    }
}