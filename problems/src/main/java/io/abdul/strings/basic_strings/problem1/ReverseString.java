package io.abdul.strings.basic_strings.problem1;

import java.util.List;
import java.util.Vector;

// https://takeuforward.org/plus/dsa/beginner-problem/basic-strings/reverse-a-string
public class ReverseString {
    public static void main(String[] args) {
        Solution solution = new Solution();
        Vector<Character> input;

        input = new Vector<>(List.of('a'));
        solution.reverseString(input);
        System.out.println(input);

        input = new Vector<>(List.of('a', 'b'));
        solution.reverseString(input);
        System.out.println(input);

        input = new Vector<>(List.of('a', 'b', 'c'));
        solution.reverseString(input);
        System.out.println(input);
    }
}

class Solution {
    public void reverseString(Vector<Character> s) {
        int mid = s.size() / 2;
        int n = s.size();

        for (int i = 0; i < mid; i++) {
            Character temp = s.get(i);
            s.set(i, s.get(n - i - 1));
            s.set(n - i - 1, temp);
        }
    }
}
