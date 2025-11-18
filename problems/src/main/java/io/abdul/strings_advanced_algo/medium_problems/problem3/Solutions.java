package io.abdul.strings_advanced_algo.medium_problems.problem3;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Solutions {

  public static void main(String[] args) {
    Solution solution = new Solution();

    assertEquals("1", solution.countAndSay(1));
    assertEquals("11", solution.countAndSay(2));
    assertEquals("21", solution.countAndSay(3));
    assertEquals("1211", solution.countAndSay(4));
  }
}

class Solution {
  public String countAndSay(int n) {
    if (n == 1) {
      return "1";
    }

    String num = countAndSay(n - 1);
    return count(num);
  }

  private String count(String number) {
    StringBuilder result = new StringBuilder();

    int currentCount = 1;
    char currentValue = number.charAt(0);

    for (int i = 1; i < number.length(); i++) {
      char digit = number.charAt(i);
      if (digit == currentValue) {
        currentCount++;
      } else {
        result.append(currentCount);
        result.append(currentValue);
        currentCount = 1;
        currentValue = digit;
      }
    }

    result.append(currentCount);
    result.append(currentValue);

    return result.toString();
  }
}

