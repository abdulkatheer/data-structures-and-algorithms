package io.abdul.problem37;

import java.util.HashMap;

// https://leetcode.com/problems/check-if-array-pairs-are-divisible-by-k/
public class Solutions {

}

/*
Better
T - O(n)
S - O(k) - map will contain only values from 0 to k-1
 */
class Solution {

  /*
  2 - 3
  4 - 1

  2 2
  */
  public boolean canArrange(int[] arr, int k) {
    HashMap<Integer, Integer> modCount = new HashMap<>();
    for (int num : arr) {
      // Handle negative nums %
      int numModK = ((num % k) + k) % k;
      // We insert and find match on the go
      // if we do it separately, we need to handle duplicate elements somehow
      // 2 2 2 4 and k = 4
      // first 2 matched
      // third 2 matches with self
      // as we do it on the go, such duplicate will not exist

      int need = (k - numModK) % k;
      if (modCount.containsKey(need)) {
        int c = modCount.get(need);
        if (c == 1) {
          modCount.remove(need);
        } else {
          modCount.put(need, c - 1);
        }
      } else {
        int count = modCount.getOrDefault(numModK, 0);
        modCount.put(numModK, count + 1);
      }
    }

    return modCount.size() == 0;
  }
}

/*
Better
T - O(n)
S - O(k)
 */
// As map is bounded, between 0 and k, we can use array instead
class Solution2 {

  /*
  2 - 3
  4 - 1

  2 2
  */
  public boolean canArrange(int[] arr, int k) {
    int[] modCount = new int[k];
    for (int num : arr) {
      // Handle negative nums %
      int numModK = ((num % k) + k) % k;
      // We insert and find match on the go
      // if we do it separately, we need to handle duplicate elements somehow
      // 2 2 2 4 and k = 4
      // first 2 matched
      // third 2 matches with self
      // as we do it on the go, such duplicate will not exist

      int need = (k - numModK) % k;
      if (modCount[need] > 0) {
        modCount[need]--;
      } else {
        modCount[numModK]++;
      }
    }

    for (int c : modCount) {
      if (c > 0) {
        return false;
      }
    }

    return true;
  }
}

/*
Optimal
T - O(k)
S - O(k)
 */
// As array has only between 0 and k-1, we can check if array has balanced values
class Solution3 {

  /*
  2 - 3
  4 - 1

  2 2
  */
  public boolean canArrange(int[] arr, int k) {
    int[] modCount = new int[k];
    for (int num : arr) {
      // Handle negative nums %
      int numModK = ((num % k) + k) % k;
      modCount[numModK]++;
    }

        /*
        let k = 5
        0 needs 0
        1 needs 4
        2 needs 3
        3 needs 2
        4 needs 1
        So up to k/2 need to be checked
        */

    /*
    Exception 1 - 0 pairs with self
    Exception 2 - if k is even, k/2 pairs with self
     */
    // handle zero
    if ((modCount[0] & 1) == 1) { // Odd zeroes, mean they don't pair properly
      return false;
    }

    for (int i = 1; i <= k / 2; i++) {
      if (modCount[i] != modCount[k - i]) {
        return false;
      }
    }

    return true;
  }
}