package io.abdul.problem36;

import java.util.HashSet;

// https://leetcode.com/problems/number-of-good-ways-to-split-a-string/
// tag:array tag:array tag:prefix_array
public class Solutions {

}

/*
Better - Prefix Array
T - O(n)
S - O(n)

a a c a b a
1 1 2 2 3 1
3 3 3 2 2 1

*/
class Solution {

  public int numSplits(String s) {
    int n = s.length();
    int[] prefixCount = new int[n];
    HashSet<Character> chars = new HashSet<>();
    for (int i = 0; i < n; i++) {
      chars.add(s.charAt(i));
      prefixCount[i] = chars.size();
    }

    chars.clear();
    int count = 0;
    for (int i = n - 1; i > 0; i--) {
      chars.add(s.charAt(i));
      if (chars.size() == prefixCount[i - 1]) {
        count++;
      }
    }

    return count;
  }
}

/*
Optimal
T - O(n)
S - O(1)

 */
class Solution2 {
  public int numSplits(String s) {
    int[] right = new int[26];
    int[] left = new int[26];
    int rightUnique = 0;
    int leftUnique = 0;

    for (char c : s.toCharArray()) {
      if (right[c - 'a'] == 0) { // new char
        rightUnique++;
      }
      right[c - 'a']++;
    }

    // Now we know count of each char and total distinct chars
    // Now we try to take each char from left
    // like 1 and n-1, 2 and n-2, 3 and n-3
    // 1 and n-1 means we try removing 1st char from right and see if distinct count reduces and matches the left
    int count = 0;
    for (char c : s.toCharArray()) {
      if (left[c - 'a'] == 0) {
        leftUnique++;
      }
      left[c - 'a']++; // count in left
      right[c - 'a']--; // remove from right
      if (right[c - 'a'] == 0) { // does removing it reduces unique count
        rightUnique--;
      }
      if (leftUnique == rightUnique) {
        count++;
      }
    }

    return count;
  }
}