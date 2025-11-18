package io.abdul.strings_advanced_algo.advanced_problems.problem1;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Solutions {

  public static void main(String[] args) {
//    Solution solution = new Solution();
    Solution2 solution = new Solution2();

    // 1. Multiple matches
    assertEquals(
        Arrays.asList(2, 5, 10),
        solution.search("abc", "ababcabcababc")
    );

    // 2. Single match
    assertEquals(
        Collections.singletonList(2),
        solution.search("ll", "hello")
    );

    // 3. No match
    assertEquals(
        Collections.emptyList(),
        solution.search("gh", "abcdef")
    );

    // 4. Overlapping occurrences
    assertEquals(
        Arrays.asList(0, 1, 2),
        solution.search("aaa", "aaaaa")
    );

    // 5. Pattern longer than text
    assertEquals(
        Collections.emptyList(),
        solution.search("abcd", "abc")
    );

    // 6. Empty pattern → return empty list
    assertEquals(
        Collections.emptyList(),
        solution.search("", "abcde")
    );

    // 7. Empty text
    assertEquals(
        Collections.emptyList(),
        solution.search("abc", "")
    );

    // 8. Both empty
    assertEquals(
        Collections.emptyList(),
        solution.search("", "")
    );

    // 9. Special characters
//    assertEquals(
//        Arrays.asList(0, 6),
//        solution.search("@#$", "@#$abc@#$abc")
//    );

    // 10. Case sensitivity check
    assertEquals(
        Collections.singletonList(3),
        solution.search("abc", "AbCabcABC")
    );

    // 11. Repeating single character pattern
    assertEquals(
        Arrays.asList(0, 1, 2, 3, 4),
        solution.search("bb", "bbbbbb")
    );

    // 12. Large text, small pattern
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < 5000; i++) {
      sb.append("abc");
    }
    String largeText = sb.toString();

    List<Integer> expected = new ArrayList<>();
    for (int i = 1; i < largeText.length(); i += 3) {
      expected.add(i);
    }

    assertEquals(
        expected,
        solution.search("bc", largeText)
    );
  }
}

/*
Brute-force
T - O(n*m)
S - O(1)
where n = length of text and m = length of pattern
 */
class Solution {

  public List<Integer> search(String pat, String txt) {
    int n = txt.length();
    int m = pat.length();

    if (n == 0 || m == 0 || n < m) {
      return Collections.emptyList();
    }

    List<Integer> result = new ArrayList<>();
    for (int i = 0; i <= n - m; i++) {
      boolean matchFound = true;
      for (int j = 0; j < m; j++) {
        if (pat.charAt(j) != txt.charAt(i + j)) {
          matchFound = false;
          break;
        }
      }

      if (matchFound) {
        result.add(i);
      }
    }

    return result;
  }
}

/*
Rabin Karp Algorithm

n = length of text
m = length of pattern

Hash = (char1 * p^0 + char2 * p^1 + char3 * p^2 ... charn * p^n-1) mod q
p, q are prime
p=7, q=101

patternHash = pc1 * p0 + pc2 * p1 + pc3 * p2
initialTextHash = tc1 * p0 + tc2 * p1 + tc3 * p2

Instead of calculating pow every time, we can maintain two var
pLeft and pRight
pRight starts from p^0 and goes till p^n-1
pRight starts with 1 and multiplied in initial hash calculation as well as matching loop. So n-1 times in total.

pLeft starts from p^0 and goes till p^n-m-1
pLeft starts with 1 and multiplied in matching loop.

At each step, we match hash and if matches, we also do string match as usual.
If our hash function has more collision, this will go till O(n*m).
If no collisions, O(n)

At each step, we need to drop left char which is at power pLeft
and add right char which is at power pRight

We move the powers at each step.
 */
class Solution2 {

  public List<Integer> search(String pat, String txt) {
    int n = txt.length();
    int m = pat.length();
    int p = 7;
    int q = 101;

    if (n == 0 || m == 0 || n < m) {
      return Collections.emptyList();
    }

    List<Integer> result = new ArrayList<>();
    int patternHash = 0;
    int textHash = 0;

    int pRight = 1; // starts with p^0 goes till p^n-1
    int pLeft = 1; // starts with p^0, goes till p^n-m-1

    for (int i = 0; i < m; i++) {
      char pc = pat.charAt(i);
      int pcNum = charToNum(pc);

      char tc = txt.charAt(i);
      int tcNum = charToNum(tc);

      patternHash = (patternHash + (pcNum * pRight) % q) % q;
      textHash = (textHash + (tcNum * pRight) % q) % q;

      pRight = (pRight * p) % q; // raising to next power for p
    }
    // pRight is at p^m-1 now

    // Hash pre-computed for i=0
    // pRight is already at m, we can use it
    for (int i = 0; i <= n - m; i++) {
      if (textHash == patternHash) { // At i = 0
        if (txt.substring(i, i + m).equals(pat)) { // i to i+m-1 string
          result.add(i);
        }
      }

      if (i < n - m) { // will not happen for n-m th char
        // Drop left
        char leftTc = txt.charAt(i);
        int leftTcNum = charToNum(leftTc);
        int leftTcHash = (leftTcNum * pLeft) % q;
        textHash = (textHash - leftTcHash + q) % q; // to avoid negative values, we add q

        // Add right
        char rightTc = txt.charAt(i + m); // i+m th char
        int rightTcNum = charToNum(rightTc);
        int rightTcHash = (rightTcNum * pRight) % q;
        textHash = (textHash + rightTcHash) % q;

        // Now textHash has one p extra, instead of dividing it, we can multiply patterHash by p
        patternHash = (patternHash * p) % q;

        pLeft = (pLeft * p) % q; // moving left 1 step
        pRight = (pRight * p) % q; // moving right 1 step
      }
    }

    return result;
  }

  private int charToNum(char c) {
    return c - 'a' + 1;
  }
}
