package io.abdul.problem15;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

// https://leetcode.com/problems/minimum-jumps-to-reach-end-via-prime-teleportation/
// tag:math tag:prime tag:sieve_of_eratosthenes tag:graph tag:breadth_first_search
public class Solutions {

  public static void main(String[] args) {
    Solution solution = new Solution();
    assertEquals(2, solution.minJumps(new int[]{1, 2, 4, 6}));
    assertEquals(2, solution.minJumps(new int[]{2, 3, 4, 7, 9}));
    assertEquals(3, solution.minJumps(new int[]{4, 6, 5, 8}));
  }
}

/*
Why BFS?
Always remember, for problems where you can move from one index to adjacent or specific forward/backward indices, use BFS when all moves have equal (unit) cost, and Dijkstra’s Algorithm when the moves have variable (weighted) costs.

- Precompute primes up to the max we require
- Find all prime's multiples beforehand, so that we can visit them

ERROR : TLE
 */
class Solution {

  public int minJumps(int[] nums) {
    int n = nums.length;

    if (n == 1) {
      return 0;
    }

    int max = 0;
    for (int num : nums) {
      max = Math.max(max, num);
    }
    boolean[] primes = primes(max);

    // Early exit
    if (primes[nums[0]] && nums[n - 1] % nums[0] == 0) {
      return 1;
    }

    HashMap<Integer, List<Integer>> valueAndPositions = new HashMap<>();
    for (int i = 0; i < n; i++) {
      valueAndPositions.computeIfAbsent(nums[i], k -> new ArrayList<>())
          .add(i);
    }

    Queue<Integer> q = new LinkedList<>();
    HashSet<Integer> usedPrimes = new HashSet<>();
    boolean[] visited = new boolean[n];
    q.add(0);
    visited[0] = true;

    int level = 0;
    while (!q.isEmpty()) {
      int size = q.size();

      for (int i = 0; i < size; i++) {
        int pos = q.poll();

        if (pos == n - 1) { // reached destination
          return level;
        }

        if (pos < n - 1 && !visited[pos + 1]) {
          q.add(pos + 1);
          visited[pos + 1] = true;
        }

        if (pos > 0 && !visited[pos - 1]) {
          q.add(pos - 1);
          visited[pos - 1] = true;
        }

        if (primes[nums[pos]] && !usedPrimes.contains(nums[pos])) {
          for (int mul = nums[pos]; mul <= max; mul += nums[pos]) {
            if (!valueAndPositions.containsKey(mul)) {
              continue;
            }
            for (int teleportPos : valueAndPositions.get(mul)) {
              if (!visited[teleportPos]) {
                q.add(teleportPos);
                visited[teleportPos] = true;
                // Early exit
                if (teleportPos == n - 1) {
                  return level + 1;
                }
              }
            }
          }
          usedPrimes.add(nums[pos]);
        }
      }

      level++;
    }

    return -1;
  }

  private boolean[] primes(int n) {
    boolean[] primes = new boolean[n + 1];
    Arrays.fill(primes, true);
    primes[0] = primes[1] = false;

    int sqrt = (int) Math.sqrt(n);
    for (int i = 2; i <= sqrt; i++) {
      if (primes[i]) {
        for (long j = (long) i * i; j <= n; j += i) {
          primes[(int) j] = false;
        }
      }
    }

    return primes;
  }
}

/*
Optimal

- Precompute primes up to the max we require
- Instead of finding multiples of all primes beforehand, we only find them when we process a prime.
This eliminates unnecessary preprocessing and space required.
Also, we process a prime only once. Meaning when we meet a prime, we would have visited all its multiples already, so do once.
 */
class Solution2 {

  /*
  Always remember, for problems where you can move from one index to adjacent or specific forward/backward indices, use BFS when all moves have equal (unit) cost, and Dijkstra’s Algorithm when the moves have variable (weighted) costs.
  */
  public int minJumps(int[] nums) {
    int n = nums.length;

    if (n == 1) {
      return 0;
    }

    int max = 0;
    for (int num : nums) {
      max = Math.max(max, num);
    }
    boolean[] primes = primes(max);

    // Early exit
    if (primes[nums[0]] && nums[n - 1] % nums[0] == 0) {
      return 1;
    }

    HashMap<Integer, List<Integer>> valueAndPositions = new HashMap<>();
    for (int i = 0; i < n; i++) {
      valueAndPositions.computeIfAbsent(nums[i], k -> new ArrayList<>())
          .add(i);
    }

    Queue<Integer> q = new LinkedList<>();
    HashSet<Integer> usedPrimes = new HashSet<>();
    boolean[] visited = new boolean[n];
    q.add(0);
    visited[0] = true;

    int level = 0;
    while (!q.isEmpty()) {
      int size = q.size();

      for (int i = 0; i < size; i++) {
        int pos = q.poll();

        if (pos == n - 1) { // reached destination
          return level;
        }

        if (pos < n - 1 && !visited[pos + 1]) {
          q.add(pos + 1);
          visited[pos + 1] = true;
        }

        if (pos > 0 && !visited[pos - 1]) {
          q.add(pos - 1);
          visited[pos - 1] = true;
        }

        if (primes[nums[pos]] && !usedPrimes.contains(nums[pos])) {
          // find multiples of the prime on-demand and only up to the max possible value in the nums[]
          for (int mul = nums[pos]; mul <= max; mul += nums[pos]) {
            if (!valueAndPositions.containsKey(mul)) {
              continue;
            }
            for (int teleportPos : valueAndPositions.get(mul)) {
              if (!visited[teleportPos]) {
                q.add(teleportPos);
                visited[teleportPos] = true;
                // Early exit
                if (teleportPos == n - 1) {
                  return level + 1;
                }
              }
            }
          }
          usedPrimes.add(nums[pos]);
        }
      }

      level++;
    }

    return -1;
  }

  private boolean[] primes(int n) {
    boolean[] primes = new boolean[n + 1];
    Arrays.fill(primes, true);
    primes[0] = primes[1] = false;

    int sqrt = (int) Math.sqrt(n);
    for (int i = 2; i <= sqrt; i++) {
      if (primes[i]) {
        for (long j = (long) i * i; j <= n; j += i) {
          primes[(int) j] = false;
        }
      }
    }

    return primes;
  }
}

