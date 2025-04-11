package io.abdul.recursion.basic_recursion.problem4;

import java.util.Vector;

// https://takeuforward.org/plus/dsa/beginner-problem/basic-recursion/reverse-a-string
public class ReverseString {
    public static void main(String[] args) {
        Solution solution = new Solution();
        Vector<Character> input;

        input = new Vector<>();
        input.add('K');
        System.out.println(solution.reverseString(input));

        input = new Vector<>();
        input.add('K');
        input.add('a');
        System.out.println(solution.reverseString(input));

        input = new Vector<>();
        input.add('K');
        input.add('a');
        input.add('t');
        System.out.println(solution.reverseString(input));

        input = new Vector<>();
        input.add('K');
        input.add('a');
        input.add('t');
        input.add('h');
        System.out.println(solution.reverseString(input));
    }
}

class Solution {
    public Vector<Character> reverseString(Vector<Character> s) {
        return reverseString(s, 0);
    }

    private Vector<Character> reverseString(Vector<Character> s, int i) {
        if (i >= s.size()) {
            return new Vector<>(s.size());
        }
        Vector<Character> reversed = reverseString(s, i + 1);
        reversed.add(s.get(i));
        return reversed;
    }
}
