package io.abdul.problem39;

import org.junit.jupiter.api.Assertions;

// https://leetcode.com/problems/greatest-common-divisor-of-strings/
// tag:math tag:string
// string trick - to check if two string has any common patter, concat them and check equality
public class Solutions {

  public static void main(String[] args) {
    Solution solution = new Solution();
    Assertions.assertEquals("TAUXX", solution.gcdOfStrings("TAUXXTAUXXTAUXXTAUXXTAUXX",
        "TAUXXTAUXXTAUXXTAUXXTAUXXTAUXXTAUXXTAUXXTAUXX"));
  }
}

/*
Brute
T - O(min(m,n) * (m+n))
S - O(1)
where m and n are lengths of str1 and str2
 */
class Solution {

  public String gcdOfStrings(String str1, String str2) {
    if (str2.length() > str1.length()) {
      String temp = str1;
      str1 = str2;
      str2 = temp;
    }

    for (int i = str2.length(); i > 0; i--) {
      if (str1.length() % i != 0) {
        continue;
      }

      if (str2.length() % i != 0) {
        continue;
      }

            /*
            str1 = 6
            str2 = 3
            0 % 3 = 0
            1 % 3 = 1
            2 % 3 = 2
            3 % 3 = 0
            4 % 3 = 1
            5 % 3 = 2
            */
      boolean matchFound = true;
      for (int j = 0; j < str1.length(); j++) {
        if (str1.charAt(j) != str2.charAt(j % i)) {
          matchFound = false;
          break;
        }
      }

      if (!matchFound) {
        continue;
      }

      for (int j = 0; j < str2.length(); j++) {
        if (str2.charAt(j) != str2.charAt(j % i)) {
          matchFound = false;
          break;
        }
      }

      if (!matchFound) {
        continue;
      }

      return str2.substring(0, i); // first longest match
    }

    return ""; // no match
  }
}

/*
Optimal
T - O(m+n)
S - O(m+n)

If there exists some GCD between str1 and str2, then str1+str2 == str2+str1
if exists, we can just take GCD of their lengths
*/
class Solution2 {

  public String gcdOfStrings(String str1, String str2) {
    if (!(str1 + str2).equals(str2 + str1)) {
      return "";
    }

    int m = str1.length();
    int n = str2.length();
    int gcd;
    if (m > n) {
      gcd = gcd(m, n);
    } else {
      gcd = gcd(n, m);
    }

    return str1.substring(0, gcd);
  }

  private int gcd(int a, int b) {
    while (b != 0) {
      int temp = b;
      b = a % b;
      a = temp;
    }

    return a;
  }
}
