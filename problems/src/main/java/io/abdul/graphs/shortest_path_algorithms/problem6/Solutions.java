package io.abdul.graphs.shortest_path_algorithms.problem6;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Solutions {

  public static void main(String[] args) {
    Solution sol = new Solution();

    // --- Test 1: Example 1 ---
    int[] arr1 = {2, 5, 7};
    assertEquals(2, sol.minimumMultiplications(arr1, 3, 30));
    // Path: 3*2=6, 6*5=30

    // --- Test 2: Example 2 ---
    int[] arr2 = {3, 4, 65};
    assertEquals(4, sol.minimumMultiplications(arr2, 7, 66175));
    // Path: 7→21→63→4095→66175

    // --- Test 3: Example 3 ---
    int[] arr3 = {3, 4, 65};
    assertEquals(1, sol.minimumMultiplications(arr3, 7, 21));
    // Path: 7*3 = 21

    // --- Test 4: Start equals end ---
    int[] arr4 = {2, 5, 7};
    assertEquals(0, sol.minimumMultiplications(arr4, 5, 5));
    // Already at destination

    // --- Test 5: Impossible case ---
    int[] arr5 = {2, 4, 6};
    assertEquals(-1, sol.minimumMultiplications(arr5, 3, 5));
    // 3 can never become 5 mod 100000

    // --- Test 6: Wrap around due to mod ---
    int[] arr6 = {99999};
    assertEquals(0, sol.minimumMultiplications(arr6, 2, 2));
    // Path: 2*99999 % 100000 = 99998 → 99998*99999 % 100000 = 2

    // --- Test 7: Large random reachable case ---
    int[] arr7 = {7, 11, 13};
    assertTrue(sol.minimumMultiplications(arr7, 3, 99991) > 0);
    // Just ensure it finds a valid positive step count (reachable)

    // --- Test 8: Single element same as end mod 100000 ---
    int[] arr8 = {10};
    assertEquals(3, sol.minimumMultiplications(arr8, 1, 1000));
    // 1→10→100→1000 (3 steps)
  }
}

/*
BFS
If problem asks to convert one element to another through a series of steps, think of Graph

Success case:
As we follow BFS, numbers with less steps are added first and they're polled first.
Same end will not appear multiple times in the queue, bcz we check if steps are lesser and then adding.
That means only first appearance of the number will exist in the queue and when we reach that, we stop!

Failure case:
Let's say we're able to get all numbers from 1 to 99999 but not end number.
So everything else will get some best steps. So we will not add new element to queue as steps will be higher than what we've.
So the loop will eventually end.
 */
class Solution {
  public int minimumMultiplications(int[] arr, int start, int end) {
    int max = (int) 1e5;
    int[] shortest = new int[max];
    Arrays.fill(shortest, Integer.MAX_VALUE);
    Queue<int[]> q = new LinkedList<>();
    q.add(new int[] {0, start});

    while (!q.isEmpty()) {
      int[] n = q.poll();

      if (n[1] == end) { // as earlier as we get to target, that's our best level
        return n[0];
      }

      // visit adjacent
      int newSteps = n[0] + 1;
      for (int num : arr) {
        int newNum = (n[1] * num) % max;

        /*
        Same number may appear multiple times in larger number of multiplications
        That'll keep this an infinite loop. Hence we maintain a fixed known shortest path array to skip duplicates
        */
        if (newSteps < shortest[newNum]) {
          q.add(new int[] {newSteps, newNum});
          shortest[newNum] = newSteps;
        }
      }
    }

    return -1;
  }
}

