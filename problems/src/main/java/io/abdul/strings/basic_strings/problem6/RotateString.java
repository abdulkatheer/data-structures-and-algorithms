package io.abdul.strings.basic_strings.problem6;

// https://takeuforward.org/plus/dsa/beginner-problem/basic-strings/rotate-string
public class RotateString {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.rotateString("abcde", "cdeab"));
        System.out.println(solution.rotateString("abcde", "adeac"));
        System.out.println(solution.rotateString("abcde", "abcde"));
        System.out.println(solution.rotateString("rkathee", "katheer"));
    }
}

class Solution {
    /*
    There can always be n-1 rotations, after that it'll repeat same positions
     */
    public boolean rotateString(String s, String goal) {
        if (s.length() != goal.length()) {
            return false;
        }

        if (s.equals(goal)) {
            return true;
        }

        int length = s.length();
        for (int i = 1; i < length; i++) {
            String afterIRotations = s.substring(i) + s.substring(0, i);
            if (afterIRotations.equals(goal)) {
                return true;
            }
        }

        return false;
    }
}

class Solution2 {
    /*
    If String is spliced in any size, if we repeat it, we can build original string again.
     */
    public boolean rotateString(String s, String goal) {
        if (s.length() != goal.length()) {
            return false;
        }

        if (s.equals(goal)) {
            return true;
        }

        s = s + s;
        return s.contains(goal);
    }
}
