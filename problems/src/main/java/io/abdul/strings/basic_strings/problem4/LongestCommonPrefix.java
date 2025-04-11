package io.abdul.strings.basic_strings.problem4;

import java.util.Arrays;

// https://takeuforward.org/plus/dsa/beginner-problem/basic-strings/longest-common-prefix
public class LongestCommonPrefix {
    public static void main(String[] args) {
//        Solution solution = new Solution();
        Solution2 solution = new Solution2();
        System.out.println(solution.longestCommonPrefix(new String[]{"flowers", "flow", "fly", "flight"}));
        System.out.println(solution.longestCommonPrefix(new String[]{"dog", "cat", "animal", "monkey"}));
        System.out.println(solution.longestCommonPrefix(new String[]{"lady", "lazy"}));
    }
}

/*
T - O(n^2)
S - O(1)
 */
class Solution {
    public String longestCommonPrefix(String[] str) {
        Arrays.sort(str); // T- O(n log(n))

        String smallest = str[0];
        StringBuilder lcp = new StringBuilder();

        for (int i = 0; i < smallest.length(); i++) { // O(n^2)
            boolean mismatchFound = false;
            for (int j = 1; j < str.length; j++) {
                if (str[j].charAt(i) != smallest.charAt(i)) {
                    mismatchFound = true;
                    break;
                }
            }
            if (mismatchFound) {
                break;
            } else {
                lcp.append(smallest.charAt(i));
            }
        }

        return lcp.toString();
    }
}

/*
T - O(n log(n))
S - O(1)
 */
class Solution2 {
    /*
    Idea here is, when elements are sorted, we need to compare only the first and last elements.
    Bcz, if last element is matching with 3 char prefix, every element in between will also match at least the three char.
     */
    public String longestCommonPrefix(String[] str) {
        Arrays.sort(str); // T- O(n log(n))

        String smallest = str[0];
        String largest = str[str.length - 1];
        StringBuilder lcp = new StringBuilder();

        for (int i = 0; i < smallest.length(); i++) { // O(n^2)
            char charInSmallest = smallest.charAt(i);
            if (charInSmallest != largest.charAt(i)) {
                break; // Mismatch found stop
            }
            lcp.append(charInSmallest); // Means all elements has this matching prefix
        }

        return lcp.toString();
    }
}
