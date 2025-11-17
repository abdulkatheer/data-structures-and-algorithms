package io.abdul.strings_advanced_algo.medium_problems.problem1;

import java.util.ArrayList;
import java.util.List;

public class Solutions {

  public static void main(String[] args) {

  }
}

/*
T - O(n)
S - O(n)
 */
class Solution {
  public String reverseWords(String s) {
    List<String> words = new ArrayList<>();
    int i = 0;
    while (i < s.length()) {
      int wordStart = skipSpaces(s, i);
      if (wordStart == s.length()) { // reached end of string
        break;
      }
      i = wordStart;
      int wordEnd = findWordEnd(s, i);
      i = wordEnd;

      words.add(s.substring(wordStart, wordEnd));
    }

    StringBuilder resultSb = new StringBuilder();
    for (int j = words.size() - 1; j > 0; j--) {
      resultSb.append(words.get(j)).append(" ");
    }

    if (!words.isEmpty()) {
      resultSb.append(words.get(0));
    }

    return resultSb.toString();
  }

  // Gives next non-space position including i
  // If nothing found, returns s.lenth()
  private int skipSpaces(String s, int i) {
    while (i < s.length() && s.charAt(i) == ' ') {
      i++;
    }
    return i;
  }

  // Gives word end position + 1
  // If no end found, returns s.lenth()
  private int findWordEnd(String s, int i) {
    while (i < s.length() && s.charAt(i) != ' ') {
      i++;
    }
    return i;
  }
}

/*
Optimal
T - O(n)
S - O(1)
 */
class Solution2 {
  public String reverseWords(String s) {
    StringBuilder resultSb = new StringBuilder();
    int i = s.length() - 1;
    while (i >= 0) {
      int wordStart = skipSpaces(s, i);
      if (wordStart == -1) { // reached end of string
        break;
      }
      i = wordStart;
      int wordEnd = findWordEnd(s, i);
      i = wordEnd;

      for (int j = wordEnd + 1; j <= wordStart; j++) {
        resultSb.append(s.charAt(j));
      }

      resultSb.append(' ');
    }

    if (!resultSb.isEmpty()) {
      resultSb.deleteCharAt(resultSb.length() - 1);
    }
    return resultSb.toString();
  }

  // Gives next non-space position including i
  // If nothing found, returns -1
  private int skipSpaces(String s, int i) {
    while (i >= 0 && s.charAt(i) == ' ') {
      i--;
    }
    return i;
  }

  // Gives word end position
  // If no end found, returns 0
  private int findWordEnd(String s, int i) {
    while (i >= 0 && s.charAt(i) != ' ') {
      i--;
    }
    return i;
  }
}
