package io.abdul.sliding_window.longest_smallest_window.problem6;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

public class Solutions {

  public static void main(String[] args) {
//    Solution solution = new Solution();
    Solution2 solution = new Solution2();

    // Basic examples
    assertEquals("BANC", solution.minWindow("ADOBECODEBANC", "ABC"));
    assertEquals("a", solution.minWindow("a", "a"));
    assertEquals("BDdc", solution.minWindow("aAbBDdcC", "Bc"));

    // Edge cases
    assertEquals("", solution.minWindow("a", "b")); // no match
    assertEquals("", solution.minWindow("", "ABC")); // empty source
    assertEquals("a", solution.minWindow("aa", "a")); // target smaller than source

    // Case sensitivity
    assertEquals("aA", solution.minWindow("aAaAaA", "Aa")); // exact casing matters

    // Duplicates in target
    assertEquals("aabb", solution.minWindow("aaabbbccc", "aabb")); // need 2 a's and 2 b's
    assertEquals("abbc",
        solution.minWindow("aabbc", "abc")); // unordered target with no duplication

    // Full match is required
    assertEquals("ADOBECODEBANC",
        solution.minWindow("ADOBECODEBANC", "ADOBECODEBANC")); // whole string is the answer

    // Stress case
    String largeS =
        "A".repeat(50000) + "B" + "C" + "D" + "E" + "F" + "G" + "H" + "I" + "J" + "K" + "L" + "M"
            + "N";
    String largeT = "ABCDEFGHIJKLMN";
    assertEquals("ABCDEFGHIJKLMN", solution.minWindow(largeS, largeT)); // check large case

    assertEquals(
        "ckwmJrwyduuiAZysMdrYoRAclhindHdpymrinDBnkwjcimjiceskPnqCpjfrsDDtzrkXtStlZMyRwtpYwyzptelloIpenjhuJkAcNHuhlrezcuwgacMSwQWfEAmtVvJmzcmZMhYmOtwuoWjtTfknIsrycBvCoDacnaKzIxfpTiCHlDOxoCeKgoPzDztrISfMbftDlgaSmuumbqlhSqHmqIjuzbYoMpeynPjxypmQIgnFIHgcIpTnqsqzyzFngrhbZrrCzvoGjxxVvAlgMbgsrMkZkxnSDfqEIDjLlEMJBfKsKpeoaoxnMeBQsfZtHACZUeLdwKeFaMqZziORQyMnnosbNbCejsFpiiQxIVkLntyWbMNHoZrUXMgbYXCsovNwWxyONZgwxPsKOfXIwaqHbmyfUUWIEjEXuPJADcriHBryispFgciAEKgzfJurbCLlyfxUIwKqpqyuZPTLHONvxzMYQSCDEueIvhecTJvZdgJJGQQTcogCSaDGfMapnUwTOHZAtExtEJvFKqrbTbeJvMoVMIuQUaQxaaYBgxHdhGwUbKuTEOyCQMqPZPREKdOFJUNxKkJJLZANFwJKYUMLZaBQZqMaYCAFYrPzeTinMDOzcCdWsWvSPgFYTbPsUvqVYHVfFXtUFKSaVHRJUBYXJhqJCRzSxPjJRPkOnKNFFHWrKKTkANQzeWFdtTOUoOaLLLLMgJmXDVRaBwbRaXBkqQSNrVYrrnMMVngdMXOOXRONUTvNXGgNUNQZgOuKQSyXUkXgNBogXNQXtOYdmRRaoXpRNQPUNoXFTgwEaqgJSFNODnUXQPsnVXWJbPrPPWglPXWPtWgCgzPDLXgksHfPQuKqaWTQSBGSHGWQBGYkAgBXSfBGQarRSStGWeUAVVVVQGGj",
        solution.minWindow(
            "sceKWtlbbtrcuejzcexBxdmafKnxrYwxSZuwgaIAdozvpujnGiTMhdgqGwjduIkATxveHqCpujNCbhYtkBzPnqrkwfneMbjXskiejjyaPWyznZZjbcVbshcimAArsVeqqxcdbcCjzxfthJqaxzHgyYvRgvcRwdyyKwsoKFcstNioloNeSaiCxhuzcckwmJrwyduuiAZysMdrYoRAclhindHdpymrinDBnkwjcimjiceskPnqCpjfrsDDtzrkXtStlZMyRwtpYwyzptelloIpenjhuJkAcNHuhlrezcuwgacMSwQWfEAmtVvJmzcmZMhYmOtwuoWjtTfknIsrycBvCoDacnaKzIxfpTiCHlDOxoCeKgoPzDztrISfMbftDlgaSmuumbqlhSqHmqIjuzbYoMpeynPjxypmQIgnFIHgcIpTnqsqzyzFngrhbZrrCzvoGjxxVvAlgMbgsrMkZkxnSDfqEIDjLlEMJBfKsKpeoaoxnMeBQsfZtHACZUeLdwKeFaMqZziORQyMnnosbNbCejsFpiiQxIVkLntyWbMNHoZrUXMgbYXCsovNwWxyONZgwxPsKOfXIwaqHbmyfUUWIEjEXuPJADcriHBryispFgciAEKgzfJurbCLlyfxUIwKqpqyuZPTLHONvxzMYQSCDEueIvhecTJvZdgJJGQQTcogCSaDGfMapnUwTOHZAtExtEJvFKqrbTbeJvMoVMIuQUaQxaaYBgxHdhGwUbKuTEOyCQMqPZPREKdOFJUNxKkJJLZANFwJKYUMLZaBQZqMaYCAFYrPzeTinMDOzcCdWsWvSPgFYTbPsUvqVYHVfFXtUFKSaVHRJUBYXJhqJCRzSxPjJRPkOnKNFFHWrKKTkANQzeWFdtTOUoOaLLLLMgJmXDVRaBwbRaXBkqQSNrVYrrnMMVngdMXOOXRONUTvNXGgNUNQZgOuKQSyXUkXgNBogXNQXtOYdmRRaoXpRNQPUNoXFTgwEaqgJSFNODnUXQPsnVXWJbPrPPWglPXWPtWgCgzPDLXgksHfPQuKqaWTQSBGSHGWQBGYkAgBXSfBGQarRSStGWeUAVVVVQGGjAGGOlGeqGBBVBygMBMBuBnFAAIquAAAAzAAZXALAYKAoFMbAGMqIRkToYaIFXIOfqlgYFTNqCNOgoRoPQWhGMXqiVLtGaWBNiCwcrzJlalvQzLBiamIQexYpvMkpuFXBJoWsurVSSwBmSRhRsAchpzlaFwQjnAglULMDftMlvunVBArRFOjMRNvtUXdvkilOmemzbURDXQiimuDRhpLwyUseUVprdwSKvJlYBMPUaELrLmUcLBpfyYdLychSiEePPmdUNrQzuWcwTWyMXynrGmQpcHxLxcMXqMRkyBTrWojPCGSXtAgalVsUKKxxqkgZQmmfIhEFwRWvjBvkSgTnKpmoTEdQpLGoAaKFYySLqwploULsKyanzGoCaSnRRanOKsyosXRShIIgJUYnMhbBPWjfsreVOILSRYARMHcknWZRbGvbyVjPszjGWgVwVtpBpPJGdiocCjDclgDndFVzpdFsvGMbFENVlQJjPstoQHiVUJHFeFWyLwgPsCZsdgNcBRlBwdWueMyCKBGFTATdESDJDuYVnikGwDrMtKIFYwugflMZhuCRFPURzcGLzqBKFksRBKmhpTuvxIvvwQTdeYHVVKMKWkbrWsaTGGogWcRyMREpLYAIBnuiJkfIDTdVryLcwPROqCbcrgyxbNabHuzKzzvsFiwJPyQeS",
            "vPvbKQCoehGjNedMZAbPMaJeargzcPrXmUcykpswncnFQuttQUzUPPxZXrPqaCkNbNoYrzFOAFwOSyZNugNExkgSsIcVIlAyxYazLRqgEDzSwVNFbaHyPnYjcbftwpHJsvdAMVQsQrcpitWntXRwOMxfkwpGBHreWjbgOMFXezygXggxNwQXikbfwPihhlQPsXmaVaBGeyaFmrxrGhXclTbXgHkzAnBLZzxTMDPblGswDpcagWKjwzLZDAHXjMjXazQgNqMSpXouMhaxdrWMKayzGfxiJisJdUOgPWaOwyHAIPqmwbFjZeQYVpKBNLOBuTqVINPaOQUKnxRcRBnDDbZJyFBbufcCxKABajgIlSBJGBgvPJUrLavzwgiCvEkRrdKTjLIrfjJtAcSTVKqUCzUoQRoedwtWHdzqnENXJCAyYvvibDBqqjugmjmKAMexxLScYodwFcGOZyIUlAUBZrMMeWKKTVnxMplQNrHDgDUnOiftUHtQaZkIZuCjOdOJchMggRGfVcNvypQcFCItQVPzGKpEbwObb"));
  }
}

/*
Step 1 -  Brute-force
Explore all possible substrings

T - O(n^2)
S - O(1)

First count all chars in t
And for each substring, count down the frequency.
If all chars of t found, the frequency be either 0 or negative.

How to find if we've found all chars of t in a substring?
frequency[char] > 0 means, we've incremented the count for one of the chars of t
If we find any frequency[char] > 0, means it's a left out char of t, which needs to be found
 */
class Solution {

  public String minWindow(String s, String t) {

    int n = s.length();
    int m = t.length();

    int[] charFrequency = new int[58]; // to hold a-z A-Z (65-90 & 97-122) - to hold 65 to 122, we need 58
    int minLength = Integer.MAX_VALUE;
    int startingPos = -1; // default no result

    for (int i = 0; i < n; i++) {
      for (int j = 0; j < m; j++) {
        charFrequency[t.charAt(j) - 'A']++;
      }
      // charFrequency has positive values for all chars of t (1 or more)

      int found = 0;
      for (int j = i; j < n; j++) {
        int jPos = s.charAt(j) - 'A';
        // positive means, it is one of t's left out chars. Now we've found that in substring, gonna reduce the count
        if (charFrequency[jPos] > 0) {
          found++;
        }
        charFrequency[jPos]--;

        if (found == t.length()) { // All chars of t are found
          int length = j - i + 1;
          if (length < minLength) {
            minLength = length;
            startingPos = i;
          }

          break; // if we expand substring further, length will always be bigger than minLength, so stop here for i
        }
      }

      Arrays.fill(charFrequency, 0); // reset for next i
    }

    return startingPos == -1 ? "" : s.substring(startingPos, startingPos + minLength);
  }

}

/*
Step 2 - Optimal

T - O(n) - 2n
S - O(1)

Expand until all are found=t.length
Shrink until found!=t.length

 */
class Solution2 {

  public String minWindow(String s, String t) {

    int n = s.length();
    int m = t.length();

    int[] charFrequency = new int[256]; // to hold a-z A-Z
    int minLength = Integer.MAX_VALUE;
    int startingPos = -1; // default no result
    int left = 0, right = 0;

    for (int j = 0; j < m; j++) {
      charFrequency[t.charAt(j)]++;
    }

    int count = 0;
    while (right < n) {
      if (charFrequency[s.charAt(right)] > 0) {
        count++;
      }

      charFrequency[s.charAt(right)]--;

      // Shrink until we find a better solution and end up at an invalid solution (count < m)
      while (count == m) { // found a solution
        int length = right - left + 1;
        if (length < minLength) {
          minLength = length;
          startingPos = left;
        }

        // shrink to find a better solution
        charFrequency[s.charAt(left)]++;
        // if positive after giving up a char, it means it was positive at the start (part of t's char)
        // and we reduced to 0 or negative for a match. Now when we give up the match, count becomes postive
        if (charFrequency[s.charAt(left)] > 0) { // char of t removed
          count--;
        }
        left++;
      }

      right++;
    }

    return startingPos == -1 ? "" : s.substring(startingPos, startingPos + minLength);
  }
}

// We can't just shrink once as we need min. So shrink as much as possible is needed here!