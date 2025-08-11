package io.abdul.heaps.theory_and_implementation.problem9;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.PriorityQueue;

public class Solutions {

  public static void main(String[] args) {
    // Test case 1
    KthLargest kthLargest1 = new KthLargest(3, new int[]{1, 2, 3, 4});
    assertEquals(3, kthLargest1.add(5)); // [1, 2, 3, 4, 5] -> 3rd largest = 3
    assertEquals(3, kthLargest1.add(2)); // [1, 2, 2, 3, 4, 5] -> 3rd largest = 3
    assertEquals(4, kthLargest1.add(7)); // [1, 2, 2, 3, 4, 5, 7] -> 3rd largest = 4

    // Test case 2
    KthLargest kthLargest2 = new KthLargest(2, new int[]{5, 5, 5, 5});
    assertEquals(5, kthLargest2.add(2));  // -> 2nd largest = 5
    assertEquals(5, kthLargest2.add(6));  // -> 2nd largest = 5
    assertEquals(6, kthLargest2.add(60)); // -> 2nd largest = 6

    // Test case 3
    KthLargest kthLargest3 = new KthLargest(4, new int[]{5, 1, 2, 7});
    assertEquals(2, kthLargest3.add(8)); // -> 4th largest = 2
    assertEquals(2, kthLargest3.add(2)); // -> 4th largest = 2
    assertEquals(5, kthLargest3.add(6)); // -> 4th largest = 5

    // Edge case: all numbers negative
    KthLargest kthLargest4 = new KthLargest(1, new int[]{-10, -5, -3});
    assertEquals(-1, kthLargest4.add(-1)); // -> 1st largest = -1
    assertEquals(0, kthLargest4.add(0));   // -> 1st largest = 0
  }
}

class KthLargest {

  private final PriorityQueue<Integer> q;
  private final int k;

  public KthLargest(int k, int[] nums) {
    q = new PriorityQueue<>(k);
    this.k = k;

    for (int i = 0; i < k && i < nums.length; i++) {
      q.offer(nums[i]);
    }

    for (int i = k; i < nums.length; i++) {
      if (nums[i] > q.peek()) {
        q.poll();
        q.offer(nums[i]);
      }
    }
  }

  public int add(int val) {
    if (q.size() < k) {
      // We've not reached k elements yet, so add and take the smallest element in q
      q.offer(val);
    } else if (val > q.peek()) {
      // We already have k elements. val replaces the smallest one
      q.poll();
      q.offer(val);
    }

    return q.peek();
  }
}
