package io.abdul.strings.basic_strings.problem3;

// https://takeuforward.org/plus/dsa/beginner-problem/basic-strings/largest-odd-number-in-a-string
public class LargestOdd {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.largeOddNum("5347"));
        System.out.println(solution.largeOddNum("0214638"));
        System.out.println(solution.largeOddNum("0032579"));
        System.out.println(solution.largeOddNum("0025232427200"));
    }
}

class Solution {
    public String largeOddNum(String s) {
        // Strip off leading zeros
        int noOfLeadingZeroes = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) != '0') {
                break;
            }
            noOfLeadingZeroes++;
        }

        s = s.substring(noOfLeadingZeroes);

        int lastOddIndex = -1;
        for (int i = s.length() - 1; i >= 0; i--) {
            if ((int) s.charAt(i) % 2 != 0) {
                lastOddIndex = i;
                break;
            }
        }

        return s.substring(0, lastOddIndex + 1);
    }
}
