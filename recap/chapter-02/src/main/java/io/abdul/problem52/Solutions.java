package io.abdul.problem52;

// https://leetcode.com/problems/count-all-valid-pickup-and-delivery-options/
// tag:math tag:recursion tag:dynamic_programming
public class Solutions {

}

class Solution {

  private static final int MOD = ((int) 1e9) + 7;

  /*
  Total permutations without constraints - 2n!

  We need to eliminate invalid permutations where delivery comes before pickup

  Exactly half of the permutations are valid, -> 2n! / 2^n.
  But can't be computed directly due to overflows.

  ----
  So let's take order by order
  we've 2n slots in total.

  Let's say n = 2, total permutations 2n * 2n-1
  Meaning, we've 2n total items, and they can be placed in any of 2n slots and
  the other pair can be placed in any of the remaining slots (2n-1)

  Slots _ _ _ _

  2nd order
  P2 D2 _ _
  P2 _ D2 _
  P2 _ _ D2
  _ P2 D2 _
  _ P2 _ D2
  _ _ P2 D2

  D2 P2 _ _
  D2 _ P2 _
  D2 _ _ P2
  _ D2 P2 _
  _ D2 _ P2
  _ _ D2 P2

  We can place one element (Delivery or Pickup) in 2k choices and the other in 2k-1 choices.
  for kth order, we've 2k * 2k-1 choices in total.

  But we need to eliminate the choices where Delivery comes first, that's half of it.

  ( 2k * 2k-1 ) / 2

  So kth order has k * 2k-1 choices in total
  */
  public int countOrders(int n) {
    if (n == 1) { // Base case
      return 1;
    }

    long choices = (((long) n * (n * 2 - 1)) % MOD);
    return (int) ((choices * countOrders(n - 1)) % MOD);
  }
}

class Solution2 {

  private static final int MOD = ((int) 1e9) + 7;

  /*
  There are two elements in each order.
  If one element can be placed in any of 2n choices, the other has to be placed in 2n-1 choices.
  So 2n * (2n - 1) permutations in total.

  But pickup has to happen first, so we eliminate all the choices where pickup element comes first.
  Which will be half of the permutations.
  So divide by 2

  and result = n * (2n -1)
  */
  public int countOrders(int n) {
    // Known solution
    long result = 1;

    for (int i = 2; i <= n; i++) {
      long permI = ((long) i * (2 * i - 1)) % MOD;
      result = (result * permI) % MOD;
    }

    return (int) result;
  }
}
