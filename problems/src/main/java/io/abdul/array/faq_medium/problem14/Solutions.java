package io.abdul.array.faq_medium.problem14;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

// https://takeuforward.org/plus/dsa/arrays/faqs-medium/next-permutation
public class Solutions {

  public static void main(String[] args) {
//    Solution sol = new Solution();
    Solution2 sol = new Solution2();

    // Example 1
    int[] a1 = {1, 2, 3};
    sol.nextPermutation(a1);
    assertArrayEquals(new int[]{1, 3, 2}, a1);

    // Example 2F
    int[] a2 = {3, 2, 1};
    sol.nextPermutation(a2);
    assertArrayEquals(new int[]{1, 2, 3}, a2);

    // Case: middle permutation
    int[] a3 = {1, 3, 2};
    sol.nextPermutation(a3);
    assertArrayEquals(new int[]{2, 1, 3}, a3);

    // Case: with duplicates
    int[] a4 = {1, 1, 5};
    sol.nextPermutation(a4);
    assertArrayEquals(new int[]{1, 5, 1}, a4);

    // Case: single element
    int[] a5 = {5};
    sol.nextPermutation(a5);
    assertArrayEquals(new int[]{5}, a5);

    // Case: two elements increasing
    int[] a6 = {1, 2};
    sol.nextPermutation(a6);
    assertArrayEquals(new int[]{2, 1}, a6);

    // Case: two elements decreasing
    int[] a7 = {2, 1};
    sol.nextPermutation(a7);
    assertArrayEquals(new int[]{1, 2}, a7);

    // Case: long increasing sequence
    int[] a8 = {1, 2, 3, 4};
    sol.nextPermutation(a8);
    assertArrayEquals(new int[]{1, 2, 4, 3}, a8);

    // Case: long decreasing sequence
    int[] a9 = {4, 3, 2, 1};
    sol.nextPermutation(a9);
    assertArrayEquals(new int[]{1, 2, 3, 4}, a9);

    // Case: pivot in the middle
    int[] a10 = {1, 4, 3, 2};
    sol.nextPermutation(a10);
    assertArrayEquals(new int[]{2, 1, 3, 4}, a10);

    // Case: repeated digits complex
    int[] a11 = {2, 2, 3, 1};
    sol.nextPermutation(a11);
    assertArrayEquals(new int[]{2, 3, 1, 2}, a11);

    // Case: already highest at tail but not prefix
    int[] a12 = {1, 5, 4, 3, 2};
    sol.nextPermutation(a12);
    assertArrayEquals(new int[]{2, 1, 3, 4, 5}, a12);

    // Case: big jump
    int[] a13 = {1, 5, 1};
    sol.nextPermutation(a13);
    assertArrayEquals(new int[]{5, 1, 1}, a13);
  }
}

/*
Brute-force - Recursive
Find all permutations
Sort it
Find the given one in that and return the immediate next to it as result

T - O(n * n!)
S - O(n) - stack
 */
class Solution {

  public void nextPermutation(int[] nums) {
    ArrayList<int[]> result = new ArrayList<>();
    findPermutations(nums, 0, result);

    Comparator<int[]> arrayComparator = (o1, o2) -> {
      for (int i = 0; i < o1.length; i++) {
        if (o1[i] != o2[i]) {
          return Integer.compare(o1[i], o2[i]);
        }
      }
      return 0;
    };
    result.sort(arrayComparator);

    int nextPermutation = 0; // 0 if last permutation
    for (int i = 0; i < result.size() - 1; i++) {
      int[] arr = result.get(i);
      int r = arrayComparator.compare(arr, nums);
      if (r == 0) {
        nextPermutation = i + 1;
      }
    }

    System.arraycopy(result.get(nextPermutation), 0, nums, 0, nums.length);
  }

  // T - O(n * n!)
  private void findPermutations(int[] nums, int i, List<int[]> result) {
    if (i == nums.length) {
      int[] per = new int[nums.length];
      System.arraycopy(nums, 0, per, 0, nums.length);
      result.add(per);
      return;
    }

    for (int j = i; j < nums.length; j++) {
      swap(nums, j, i); // choose
      findPermutations(nums, i + 1, result);
      swap(nums, j, i); // backtrack
    }
  }

  private void swap(int[] nums, int a, int b) {
    int temp = nums[a];
    nums[a] = nums[b];
    nums[b] = temp;
  }
}

/*
Optimal
T - O(n)
S - O(1)

The next permutation is smallest greater form
How to get that?

Ex 1:
2 1 5 4 3 0 0
0 is the only num, so no other option
0 0  has two form, both has same value, but not expected
3 0 0 has 6 forms, but none is expected
4 3 0 0 has 12 forms, but none is expected
5 4 3 0 0 has 60 forms, but none is expected
1 5 4 3 0 0 has 120 forms, one of them is expected
So index 1 is the "breaking point"

in the place of 1, we need the smallest greater element
2 3
Now 1 5 4 0 0 is left
To get smallest, we can sort this
0 0 1 4 5

2 3 0 0 1 4 5 is the next permutation

Ex 2:
5 4 3 2 1

Can't find the breaking point
So sort all and that's the result
 */
class Solution2 {

  public void nextPermutation(int[] nums) {
    int n = nums.length;

    // Step 1 - Find breaking point
    int p = breakingPoint(nums);

    if (p == -1) { // nums is sorted
      reverse(nums, 0, n - 1);
      return;
    }

    // Step 2 - Find smallestGreater element
    int g = smallestGreaterElement(nums, p);

    swap(nums, p, g);

    reverse(nums, p + 1, n - 1);
  }

  private void swap(int[] nums, int a, int b) {
    int temp = nums[a];
    nums[a] = nums[b];
    nums[b] = temp;
  }

  // T - O(n)
  private int smallestGreaterElement(int[] nums, int p) {
    int g = p;
    for (int i = nums.length - 1; i > p; i--) {
      if (nums[i] > nums[p]) {
        g = i;
        break;
      }
    }
    return g;
  }

  // T - O(n)
  private int breakingPoint(int[] nums) {
    for (int i = nums.length - 1; i > 0; i--) {
      if (nums[i] > nums[i - 1]) {
        return i - 1;
      }
    }

    return -1;
  }

  // T - O(n)
  private void reverse(int[] nums, int start, int end) {
    if (start == end) {
      return;
    }
    int elements = end - start;
    int mid = elements / 2;
    for (int i = 0; i <= mid; i++) {
      swap(nums, start + i, end - i);
    }
  }
}