package io.abdul.problem47;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

import java.util.ArrayList;
import java.util.List;

// https://leetcode.com/problems/pascals-triangle-ii/
// tag:math tag:recursion tag:dynamic_programming
public class Solutions {

  public static void main(String[] args) {
    Solution2 solution = new Solution2();
    assertIterableEquals(List.of(1, 3, 3, 1), solution.getRow(3));
  }
}

class Solution {

  public List<Integer> getRow(int rowIndex) {
    int row = rowIndex + 1;
    List<Integer> data = new ArrayList<>(row);
    for (int i = 0; i < row; i++) {
      data.add(0);
    }

    // Known solution
    data.set(0, 1);

        /*
        row = 10
        from backwards
        row[10] = row[10] + row[9]
        row[9] = row[9] + row[8]

        from front - doesn't work
        row[1] = row[1] + row[0] (out of bounds)
        row[2] = row[2] + row[1] (but its updated already!)

        Ex: 5
        1 0 0 0 0
        1 1 0 0 0
        1 2 1 0 0
        1 3 3 1 0
        1 4 6 4 1
        */
    for (int i = 2; i <= row; i++) {
      for (int j = row - 1; j > 0; j--) {
        data.set(j, data.get(j) + data.get(j - 1));
      }
    }

    return data;
  }
}

/*
ERROR: Integer Overflow
Using Combinatorics
nCr = n! / [r! * (n-r)!]

For r = 5
we need to find
5C0 5C1 5C2 5C3 5C4 5C5

5! is common for all, we find it once
0!, 1!, 2!, 3!, 4!, 5!
5!, 4!, 3!, 2!, 1!, 0!

We need to do safe multiplication, otherwise it may overflow.

Integer can handle only up to 12!
Long can handle only up to 20!
*/
class Solution2 {

  public List<Integer> getRow(int rowIndex) {
    List<Integer> result = new ArrayList<>(rowIndex + 1);

    int nFact = 1;
    for (int i = 1; i <= rowIndex; i++) {
      nFact *= i;
    }

    int rFact = 1;
    int nMinusRFact = nFact;

    result.add(1);
    for (int i = 1; i <= rowIndex; i++) {
      rFact *= i; // add i
      nMinusRFact /= (rowIndex - i + 1); // drop n-i
      result.add(nFact / (rFact * nMinusRFact));
    }

    return result;
  }
}

/*
Optimal

Using Optimized Combination formula for safe multiplication
nCr = n! / r! * (n-r)!
n=5, r=1 => 5x4x3x2x1 / (1) x (4x3x2x1) => 4x3x2x1 in cancelled => 5 / (1) x () -> x 5 and / 1
n=5, r=2 => 5x4x3x2x1 / (1x2) x (3x2x1) => 3x2x1 in cancelled => 5x4 / (1x2) x () -> x 4 and / 2
n=5, r=3 => 5x4x3x2x1 / (1x2x3) x (2x1) => 2x1 in cancelled => 5x4x3 / (1x2x3) x () -> x 3 and / 3
n=5, r=4 => 5x4x3x2x1 / (1x2x3x4) x (1) => 2x1 in cancelled => 5x4x3x2 / (1x2x3x4) x () -> x 2 and / 4
n=5, r=5 => 5x4x3x2x1 / (1x2x3x4x5) x (1) -> x 1 and / 5

So we keep multiplying the numerator from 5 to 1
and the denomicator from 1 to 5 so that the division happens without overflow
for ex: 5C1
combination = 1 --
1 * 5 = 5
5 / 1 = 5 --
5 * 4 = 20
20 / 2 = 10 --
10 * 3 = 30
30 / 3 = 10 --
10 * 2 = 20
20 / 4 = 5 --
5 * 1 = 5
5 / 5 = 1 --
 */
class Solution3 {
  public List<Integer> getRow(int rowIndex) {
    List<Integer> result = new ArrayList<>(rowIndex + 1);

    result.add(1);

    long combination = 1;
    for (int i = 0; i < rowIndex; i++) {
      combination = combination * (rowIndex - i);
      combination = combination / (i + 1);

      result.add((int) combination);
    }

    return result;
  }
}