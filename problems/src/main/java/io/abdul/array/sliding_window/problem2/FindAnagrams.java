package io.abdul.array.sliding_window.problem2;

import java.util.*;

public class FindAnagrams {
    public static void main(String[] args) {
//        Solution solution = new Solution();
//        Solution2 solution = new Solution2();
        Solution3 solution = new Solution3();
        System.out.println(solution.findAnagrams("cbaebabacd", "abc"));
        System.out.println(solution.findAnagrams("abab", "ab"));
    }
}

// Brute-force
// Sorting and matching
/*
Time - O(p log(p)) + O(p) + ( O(n) x ( O(p log(p)) + O(p) + O(p) ) => O(n x p x log(p))
Space - O(p)
 */
class Solution {
    public List<Integer> findAnagrams(String s, String p) {
        if (s.length() < p.length()) {
            return Collections.emptyList();
        }
        List<Integer> matches = new ArrayList<>();

        char[] pArr = p.toCharArray();
        Arrays.sort(pArr); // Time - O(p log(p))

        char[] chars = new char[pArr.length]; // Space - O(p)
        int i = pArr.length - 1;
        int x = 0;
        for (int j = 0; j < p.length(); j++) { // Time O(p)
            chars[j] = s.charAt(j);
        }

        while (i < s.length()) { // O(n)
            Arrays.sort(chars); // O(p log(p))
            if (Arrays.equals(chars, pArr)) { // O(p)
                matches.add(i - pArr.length + 1);
            }
            i++;
            x++;
            if (i < s.length()) {
                for (int k = 0, y = x; k < p.length(); k++, y++) { // O(p)
                    chars[k] = s.charAt(y);
                }
            }
        }

        return matches;
    }
}

// Better
// HashMap and no sorting
/*
Time - O(p) + O(p) + ( O(n) x O(26) )
Space - O(26) (for all lowercase letters) => O(1)
 */
class Solution2 {
    public List<Integer> findAnagrams(String s, String p) {
        if (s.length() < p.length()) {
            return Collections.emptyList();
        }
        List<Integer> matches = new ArrayList<>();
        HashMap<Character, Integer> chars = new HashMap<>(); // Space - O(n + p)

        for (int j = 0; j < p.length(); j++) { // Time - O(p)
            chars.merge(s.charAt(j), 1, Integer::sum);
        }

        for (char theChar : p.toCharArray()) { // Time - O(p)
            chars.merge(theChar, -1, Integer::sum);
        }

        int i = p.length() - 1;
        while (i < s.length()) { // O(n)
            boolean match = true;
            for (Integer value : chars.values()) { // Time - O(26)
                if (value != 0) {
                    match = false;
                    break;
                }
            }

            if (match) {
                matches.add(i - p.length() + 1);
            }

            i++;
            if (i < s.length()) {
                // Remove first char and
                chars.merge(s.charAt(i), 1, Integer::sum);
                chars.merge(s.charAt(i - p.length()), -1, Integer::sum);
            }
        }

        return matches;
    }
}

// Better
// Frequency Array (Light weight and constant lookup time than HashMap)
/*
Time - O(p) + O(p) + ( O(n) x O(26) ) => O(n), where n is size of s and p is size of anagram
Space - O(26) (for all lowercase letters) => O(1)
 */
class Solution3 {

    private static final int MAX_CHARS = 26;

    public List<Integer> findAnagrams(String s, String p) {
        if (s.length() < p.length()) {
            return Collections.emptyList();
        }
        List<Integer> matches = new ArrayList<>();
        int[] charFrequencies = new int[26]; // Space - O(26)

        for (int j = 0; j < p.length(); j++) { // Time - O(p)
            int position = s.charAt(j) % MAX_CHARS;
            charFrequencies[position] = charFrequencies[position] + 1;
        }

        for (char theChar : p.toCharArray()) { // Time - O(p)
            int position = theChar % MAX_CHARS;
            charFrequencies[position] = charFrequencies[position] - 1;
        }

        int i = p.length() - 1;
        while (i < s.length()) { // O(n)
            boolean match = true;
            for (Integer value : charFrequencies) { // Time - O(26)
                if (value != 0) {
                    match = false;
                    break;
                }
            }

            if (match) {
                matches.add(i - p.length() + 1);
            }

            i++;
            if (i < s.length()) {
                // Remove first char and
                int nextPositionInWindow = s.charAt(i) % MAX_CHARS;
                int firstPositionInWindow = s.charAt(i - p.length()) % MAX_CHARS;
                charFrequencies[firstPositionInWindow] = charFrequencies[firstPositionInWindow] - 1;
                charFrequencies[nextPositionInWindow] = charFrequencies[nextPositionInWindow] + 1;
            }
        }

        return matches;
    }
}
