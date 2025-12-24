package io.abdul.problem13;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeSet;

// https://leetcode.com/problems/maximize-count-of-distinct-primes-after-split/
// tag:math tag:prime tag:sieve_of_eratosthenes tag:segment_tree tag:segment_tree_lazy_propagation
public class Solutions {

}

class Solution {

  public int[] maximumCount(int[] nums, int[][] queries) {
    // Step 1
    int max = 0;
    for (int num : nums) {
      max = Math.max(num, max);
    }
    for (int[] query : queries) {
      max = Math.max(query[1], max);
    }
    boolean[] primes = primes(max);

    // Step 2
    int[] distinctPrimesSinceI = new int[nums.length];
    int[] distinctPrimesUptoI = new int[nums.length];
    int[] result = new int[queries.length];
    for (int i = 0; i < queries.length; i++) {
      int[] query = queries[i];
      nums[query[0]] = query[1];

      populateDistinctPrimesSinceI(nums, distinctPrimesSinceI, primes);
      populateDistinctPrimesUptoI(nums, distinctPrimesUptoI, primes);

      int maxDistinctPrimes = 0;
      for (int k = 1; k < nums.length; k++) {
        // first part ending at k-1
        // second part starting at k
        maxDistinctPrimes = Math.max(maxDistinctPrimes,
            distinctPrimesUptoI[k - 1] + distinctPrimesSinceI[k]);
      }

      result[i] = maxDistinctPrimes;
    }

    return result;
  }

  private void populateDistinctPrimesSinceI(int[] nums, int[] result, boolean[] primes) {
    HashSet<Integer> distinctPrimes = new HashSet<>();
    for (int i = nums.length - 1; i >= 0; i--) {
      if (primes[nums[i]]) {
        distinctPrimes.add(nums[i]);
      }
      result[i] = distinctPrimes.size();
    }
  }

  private void populateDistinctPrimesUptoI(int[] nums, int[] result, boolean[] primes) {
    HashSet<Integer> distinctPrimes = new HashSet<>();
    for (int i = 0; i < nums.length - 1; i++) {
      if (primes[nums[i]]) {
        distinctPrimes.add(nums[i]);
      }
      result[i] = distinctPrimes.size();
    }
  }

  // T - O(n log log n), since n <= 10^5
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

// Ignore max check and find primes up to 10^5 always
class Solution2 {

  public int[] maximumCount(int[] nums, int[][] queries) {
    // Step 1
    boolean[] primes = primes((int) 1e5);

    // Step 2
    int[] distinctPrimesSinceI = new int[nums.length];
    int[] distinctPrimesUptoI = new int[nums.length];
    populateDistinctPrimesSinceI(nums, distinctPrimesSinceI, primes);
    populateDistinctPrimesUptoI(nums, distinctPrimesUptoI, primes);

    int[] result = new int[queries.length];

    int i = 0;
    while (i < queries.length) {
      int existingNum = nums[queries[i][0]];
      int newNum = queries[i][1];
      if ((existingNum == newNum) || (!primes[existingNum] && !primes[newNum])) {
        nums[queries[i][0]] = newNum;
        result[i] = i > 0 ? result[i - 1] : 0;
      } else {
        nums[queries[i][0]] = newNum;
        populateDistinctPrimesSinceI(nums, distinctPrimesSinceI, primes);
        populateDistinctPrimesUptoI(nums, distinctPrimesUptoI, primes);

        int maxDistinctPrimes = 0;
        for (int k = 1; k < nums.length; k++) {
          // first part ending at k-1
          // second part starting at k
          maxDistinctPrimes = Math.max(maxDistinctPrimes,
              distinctPrimesUptoI[k - 1] + distinctPrimesSinceI[k]);
        }

        result[i] = maxDistinctPrimes;
      }
      i++;
    }

    return result;
  }

  private void populateDistinctPrimesSinceI(int[] nums, int[] result, boolean[] primes) {
    HashSet<Integer> distinctPrimes = new HashSet<>();
    for (int i = nums.length - 1; i >= 0; i--) {
      if (primes[nums[i]]) {
        distinctPrimes.add(nums[i]);
      }
      result[i] = distinctPrimes.size();
    }
  }

  private void populateDistinctPrimesUptoI(int[] nums, int[] result, boolean[] primes) {
    HashSet<Integer> distinctPrimes = new HashSet<>();
    for (int i = 0; i < nums.length - 1; i++) {
      if (primes[nums[i]]) {
        distinctPrimes.add(nums[i]);
      }
      result[i] = distinctPrimes.size();
    }
  }

  // T - O(n log log n), since n <= 10^5
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

// Merge populateDistinctPrimes from 2n to n
// Skip populateDistinctPrimes for some obvious cases
class Solution3 {

  public int[] maximumCount(int[] nums, int[][] queries) {
    // Step 1
    boolean[] primes = primes((int) 1e5);

    // Step 2
    int[] distinctPrimesSinceI = new int[nums.length];
    int[] distinctPrimesUptoI = new int[nums.length];
    populateDistinctPrimes(nums, distinctPrimesUptoI, distinctPrimesSinceI, primes);

    int[] result = new int[queries.length];

    int i = 0;
    while (i < queries.length) {
      int existingNum = nums[queries[i][0]];
      int newNum = queries[i][1];
      if ((existingNum == newNum) || (!primes[existingNum] && !primes[newNum])) {
        nums[queries[i][0]] = newNum;
        result[i] = i > 0 ? result[i - 1] : 0;
      } else {
        nums[queries[i][0]] = newNum;
        populateDistinctPrimes(nums, distinctPrimesUptoI, distinctPrimesSinceI, primes);

        int maxDistinctPrimes = 0;
        for (int k = 1; k < nums.length; k++) {
          // first part ending at k-1
          // second part starting at k
          maxDistinctPrimes = Math.max(maxDistinctPrimes,
              distinctPrimesUptoI[k - 1] + distinctPrimesSinceI[k]);
        }

        result[i] = maxDistinctPrimes;
      }
      i++;
    }

    return result;
  }

  private void populateDistinctPrimes(int[] nums, int[] primesFromStart, int[] primesFromEnd,
      boolean[] primes) {
    HashSet<Integer> distinctPrimesFromStart = new HashSet<>();
    HashSet<Integer> distinctPrimesFromEnd = new HashSet<>();
    int s = 0;
    int e = nums.length - 1;
    while (s < nums.length) {
      if (primes[nums[s]]) {
        distinctPrimesFromStart.add(nums[s]);
      }
      if (primes[nums[e]]) {
        distinctPrimesFromEnd.add(nums[e]);
      }
      primesFromStart[s] = distinctPrimesFromStart.size();
      primesFromEnd[e] = distinctPrimesFromEnd.size();
      s++;
      e--;
    }
  }

  // T - O(n log log n), since n <= 10^5
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

// Better - Segment Tree
class Solution4 {

  private static final int MAX_NUM = 100000;

  /**
   * Simple Segment Tree for Range Updates and Max Query
   * <p>
   * Simpler than lazy propagation:
   * <p>
   * - Updates propagate immediately from leaf to root
   * <p>
   * - No lazy array needed
   * <p>
   * - Slightly less efficient but easier to understand
   */
  static class SegmentTree {

    private final int n;
    private final int[] tree;  // tree[i] = max value in range covered by node i

    public SegmentTree(int n, int[] initial) {
      this.n = n;
      this.tree = new int[4 * n];
      build(0, 0, n - 1, initial);
    }

    /**
     * Build segment tree from initial array
     * <p>
     * Time: O(n) - Each element appears in exactly log(n) nodes - Total nodes: 4n
     */
    private void build(int node, int left, int right, int[] initial) {
      if (left == right) {
        // Leaf node
        tree[node] = initial[left];
        return;
      }

      int mid = (left + right) / 2;
      int leftChild = 2 * node + 1;
      int rightChild = 2 * node + 2;

      build(leftChild, left, mid, initial);
      build(rightChild, mid + 1, right, initial);

      // Internal node = max of children
      tree[node] = Math.max(tree[leftChild], tree[rightChild]);
    }

    /**
     * Add delta to a single position
     * <p>
     * Time: O(log n) - Update leaf - Propagate changes up to root
     * <p>
     * This is simpler than lazy propagation:
     * <p>
     * - No need to push down
     * <p>
     * - No lazy array - Update happens immediately
     */
    private void updatePoint(int node, int left, int right, int pos, int delta) {
      if (left == right) {
        // Leaf node - apply update
        tree[node] += delta;
        return;
      }

      int mid = (left + right) / 2;
      int leftChild = 2 * node + 1;
      int rightChild = 2 * node + 2;

      if (pos <= mid) {
        updatePoint(leftChild, left, mid, pos, delta);
      } else {
        updatePoint(rightChild, mid + 1, right, pos, delta);
      }

      // After updating child, recalculate current node
      tree[node] = Math.max(tree[leftChild], tree[rightChild]);
    }

    /**
     * Add delta to range [queryL, queryR]
     * <p>
     * Time: O((queryR - queryL + 1) × log n) - Update each position individually - Each position
     * update is O(log n)
     * <p>
     * Note: This is less efficient than lazy propagation's O(log n), but simpler and sufficient for
     * this problem since ranges are small
     */
    public void updateRange(int queryL, int queryR, int delta) {
      for (int pos = queryL; pos <= queryR; pos++) {
        updatePoint(0, 0, n - 1, pos, delta);
      }
    }

    /**
     * Query maximum value in entire tree
     * <p>
     * Time: O(1) - Root always contains max of entire range
     */
    public int queryMax() {
      return tree[0];
    }

    /**
     * Optional: Query max in any range [queryL, queryR]
     * <p>
     * Time: O(log n)
     * <p>
     * Included for completeness, though we only need queryMax() for this problem
     */
    private int queryRange(int node, int left, int right, int queryL, int queryR) {
      // No overlap
      if (right < queryL || queryR < left) {
        return Integer.MIN_VALUE;
      }

      // Complete overlap
      if (queryL <= left && right <= queryR) {
        return tree[node];
      }

      // Partial overlap
      int mid = (left + right) / 2;
      int leftChild = 2 * node + 1;
      int rightChild = 2 * node + 2;

      int leftMax = queryRange(leftChild, left, mid, queryL, queryR);
      int rightMax = queryRange(rightChild, mid + 1, right, queryL, queryR);

      return Math.max(leftMax, rightMax);
    }
  }

  /**
   * Main solution method
   * <p>
   * Algorithm:
   * <p>
   * 1. Precompute primes using Sieve of Eratosthenes
   * <p>
   * 2. Track positions of each prime
   * <p>
   * 3. Build initial overlap array using difference technique
   * <p>
   * 4. Create segment tree from overlap array
   * <p>
   * 5. For each query: - Remove old value's contribution - Add new value's contribution -
   * <p>
   * <p>
   * Answer = distinct primes + max overlap
   */
  public int[] maximumCount(int[] nums, int[][] queries) {
    int n = nums.length;

    // Step 1: Sieve of Eratosthenes
    boolean[] isPrime = sieve(MAX_NUM);

    // Step 2: Track indices for each prime
    HashMap<Integer, TreeSet<Integer>> primeIndices = new HashMap<>();

    for (int i = 0; i < n; i++) {
      if (isPrime[nums[i]]) {
        primeIndices.computeIfAbsent(nums[i], k -> new TreeSet<>()).add(i);
      }
    }

    // Step 3: Build initial overlap array using difference technique
    int[] overlap = new int[n];

    /*
     * For each prime with ≥2 occurrences:
     * - Contributes +1 to overlap for splits k ∈ [first+1, last]
     * - Use difference array for O(1) range marking
     */
    for (TreeSet<Integer> indices : primeIndices.values()) {
      if (indices.size() >= 2) {
        int first = indices.first();
        int last = indices.last();

        if (first + 1 < n) {
          overlap[first + 1]++;
        }
        if (last + 1 < n) {
          overlap[last + 1]--;
        }
      }
    }

    // Convert difference array to actual overlap counts
    for (int i = 1; i < n; i++) {
      overlap[i] += overlap[i - 1];
    }

    // Step 4: Build segment tree
    SegmentTree segTree = new SegmentTree(n, overlap);

    // Step 5: Process queries
    int[] result = new int[queries.length];

    for (int q = 0; q < queries.length; q++) {
      int idx = queries[q][0];
      int oldVal = nums[idx];
      int newVal = queries[q][1];

      // Update the array
      nums[idx] = newVal;

      // Remove old value's contribution
      if (isPrime[oldVal] && primeIndices.containsKey(oldVal)) {
        removeContribution(primeIndices, oldVal, idx, segTree);
      }

      // Add new value's contribution
      if (isPrime[newVal]) {
        addContribution(primeIndices, newVal, idx, segTree);
      }

      // Calculate answer
      int distinctPrimes = primeIndices.size();
      int maxOverlap = segTree.queryMax();
      result[q] = distinctPrimes + maxOverlap;
    }

    return result;
  }

  /**
   * Remove a prime's contribution when its position changes
   * <p>
   * Cases: 1. Prime appears ≥2 times AND we're removing a boundary: - Remove old range contribution
   * - If still ≥2 occurrences after removal, add new smaller range
   * <p>
   * 2. Prime appears ≥2 times AND we're removing from middle: - Just remove from set, range
   * unchanged
   * <p>
   * 3. Prime appears exactly once: - Remove prime entirely from map
   * <p>
   * Complexity: O(range_size × log n) for segment tree updates
   */
  private void removeContribution(HashMap<Integer, TreeSet<Integer>> primeIndices,
      int prime, int idx, SegmentTree segTree) {
    TreeSet<Integer> indices = primeIndices.get(prime);

    if (indices.size() >= 2) {
      int oldFirst = indices.first();
      int oldLast = indices.last();

      // Check if this is a boundary position
      if (idx == oldFirst || idx == oldLast) {
        // Remove old range [oldFirst+1, oldLast]
        segTree.updateRange(oldFirst + 1, oldLast, -1);

        indices.remove(idx);

        // Add new range if still ≥2 occurrences
        if (indices.size() >= 2) {
          int newFirst = indices.first();
          int newLast = indices.last();
          segTree.updateRange(newFirst + 1, newLast, 1);
        }
      } else {
        // Middle position: just remove from set
        // The range [first+1, last] doesn't change
        indices.remove(idx);
      }
    } else {
      // Only one occurrence: remove prime entirely
      primeIndices.remove(prime);
    }
  }

  /**
   * Add a prime's contribution when a position is updated to it
   * <p>
   * Cases: 1. Prime doesn't exist yet: - Create new entry, no range contribution (only 1
   * occurrence)
   * <p>
   * 2. Prime exists and new position extends range: - Remove old range contribution (if it existed)
   * - Add new extended range contribution
   * <p>
   * 3. Prime exists and new position is inside existing range: - Just add to set, range unchanged
   * <p>
   * Complexity: O(range_size × log n) for segment tree updates
   */
  private void addContribution(HashMap<Integer, TreeSet<Integer>> primeIndices,
      int prime, int idx, SegmentTree segTree) {
    if (!primeIndices.containsKey(prime)) {
      // First occurrence of this prime
      primeIndices.put(prime, new TreeSet<>());
      primeIndices.get(prime).add(idx);
    } else {
      TreeSet<Integer> indices = primeIndices.get(prime);
      int oldFirst = indices.first();
      int oldLast = indices.last();

      // Check if new position extends the range
      if (idx < oldFirst || idx > oldLast) {
        // Remove old range if it existed (≥2 occurrences before)
        if (indices.size() >= 2) {
          segTree.updateRange(oldFirst + 1, oldLast, -1);
        }

        indices.add(idx);

        // Add new extended range
        int newFirst = indices.first();
        int newLast = indices.last();
        segTree.updateRange(newFirst + 1, newLast, 1);
      } else {
        // Inside existing range: just add to set
        indices.add(idx);
      }
    }
  }

  /**
   * Sieve of Eratosthenes - O(n log log n)
   * <p>
   * Classic algorithm to find all primes up to n:
   * <p>
   * 1. Assume all numbers ≥2 are prime
   * <p>
   * 2. For each prime p, mark all multiples of p as composite
   * <p>
   * 3. Optimization: Start marking from p² (smaller multiples already marked)
   */
  private boolean[] sieve(int n) {
    boolean[] isPrime = new boolean[n + 1];
    Arrays.fill(isPrime, true);
    isPrime[0] = isPrime[1] = false;

    int sqrt = (int) Math.sqrt(n);
    for (int i = 2; i <= sqrt; i++) {
      if (isPrime[i]) {
        for (long j = (long) i * i; j <= n; j += i) {
          isPrime[(int) j] = false;
        }
      }
    }

    return isPrime;
  }
}

// Optimal - Segment Tree + Lazy Updates
class Solution5 {

  private static final int MAX_NUM = 100000;

  /**
   * Segment Tree with Lazy Propagation for Range Updates and Max Query
   * <p>
   * Purpose: Track the overlap count at each split position efficiently - Each node stores the
   * maximum overlap in its range - Lazy propagation allows O(log n) range updates
   */
  static class SegmentTree {

    private final int n;
    private final int[] tree;  // tree[i] = max overlap in range covered by node i
    private final int[] lazy;  // lazy[i] = pending update to be pushed down

    public SegmentTree(int n, int[] initial) {
      this.n = n;
      this.tree = new int[4 * n];
      this.lazy = new int[4 * n];
      build(0, 0, n - 1, initial);
    }

    /**
     * Build the segment tree from initial overlap array
     * <p>
     * Recursion:
     * <p>
     * - Base case: leaf node stores initial[index]
     * <p>
     * - Recursive case: node stores max of its children
     */
    private void build(int node, int left, int right, int[] initial) {
      if (left == right) {
        tree[node] = initial[left];
        return;
      }

      int mid = (left + right) / 2;
      int leftChild = 2 * node + 1;
      int rightChild = 2 * node + 2;

      build(leftChild, left, mid, initial);
      build(rightChild, mid + 1, right, initial);

      tree[node] = Math.max(tree[leftChild], tree[rightChild]);
    }

    /**
     * Push down lazy updates to children
     * <p>
     * Why needed: Lazy propagation delays updates until necessary
     * <p>
     * - Apply pending update to current node
     * <p>
     * - Pass update to children (if not leaf) - Clear lazy flag
     */
    private void push(int node, int left, int right) {
      if (lazy[node] != 0) {
        tree[node] += lazy[node];

        if (left != right) {
          lazy[2 * node + 1] += lazy[node];
          lazy[2 * node + 2] += lazy[node];
        }

        lazy[node] = 0;
      }
    }

    /**
     * Range update: Add 'delta' to all positions in [queryL, queryR]
     * <p>
     * Cases:
     * <p>
     * 1. No overlap: return immediately
     * <p>
     * 2. Complete overlap: mark lazy and push
     * <p>
     * 3. Partial overlap: recurse to children
     * <p>
     * Invariant: After update, tree[node] = max value in its range
     */
    private void rangeUpdate(int node, int queryL, int queryR, int delta,
        int left, int right) {
      push(node, left, right);

      // No overlap
      if (right < queryL || queryR < left) {
        return;
      }

      // Complete overlap
      if (queryL <= left && right <= queryR) {
        lazy[node] += delta;
        push(node, left, right);
        return;
      }

      // Partial overlap
      int mid = (left + right) / 2;
      int leftChild = 2 * node + 1;
      int rightChild = 2 * node + 2;

      rangeUpdate(leftChild, queryL, queryR, delta, left, mid);
      rangeUpdate(rightChild, queryL, queryR, delta, mid + 1, right);

      // Update current node after children are updated
      push(leftChild, left, mid);
      push(rightChild, mid + 1, right);
      tree[node] = Math.max(tree[leftChild], tree[rightChild]);
    }

    /**
     * Query maximum overlap across all split positions
     * <p>
     * Special case: We always query [0, n-1] so just return root
     */
    public int queryMax() {
      push(0, 0, n - 1);
      return tree[0];
    }

    /**
     * Public interface for range update
     */
    public void update(int queryL, int queryR, int delta) {
      rangeUpdate(0, queryL, queryR, delta, 0, n - 1);
    }
  }

  /**
   * Main solution method
   * <p>
   * Strategy:
   * <p>
   * 1. Precompute all primes using Sieve
   * <p>
   * 2. Track positions of each prime using TreeSet (for fast first/last)
   * <p>
   * 3. Build initial overlap array using difference technique
   * <p>
   * 4. For each query:
   * <p>
   * a. Remove old value's contribution (if prime)
   * <p>
   * b. Add new value's contribution (if prime)
   * <p>
   * c. Answer = distinct primes + max overlap
   */
  public int[] maximumCount(int[] nums, int[][] queries) {
    int n = nums.length;

    // Step 1: Sieve of Eratosthenes
    boolean[] isPrime = sieve(MAX_NUM);

    // Step 2: Track indices for each prime
    // TreeSet gives us O(log k) access to first and last elements
    HashMap<Integer, TreeSet<Integer>> primeIndices = new HashMap<>();

    for (int i = 0; i < n; i++) {
      if (isPrime[nums[i]]) {
        primeIndices.computeIfAbsent(nums[i], k -> new TreeSet<>()).add(i);
      }
    }

    // Step 3: Build initial overlap array using difference technique
    int[] overlap = new int[n];

    /*
     * For each prime with ≥2 occurrences:
     * - Let first = smallest index, last = largest index
     * - This prime contributes +1 to overlap for splits k ∈ [first+1, last]
     * - Using difference array: overlap[first+1]++, overlap[last+1]--
     */
    for (TreeSet<Integer> indices : primeIndices.values()) {
      if (indices.size() >= 2) {
        int first = indices.first();
        int last = indices.last();

        // Range [first+1, last] gets +1
        if (first + 1 < n) {
          overlap[first + 1]++;
        }
        if (last + 1 < n) {
          overlap[last + 1]--;
        }
      }
    }

    // Convert difference array to actual overlap counts
    for (int i = 1; i < n; i++) {
      overlap[i] += overlap[i - 1];
    }

    // Step 4: Build segment tree
    SegmentTree segTree = new SegmentTree(n, overlap);

    // Step 5: Process queries
    int[] result = new int[queries.length];

    for (int q = 0; q < queries.length; q++) {
      int idx = queries[q][0];
      int oldVal = nums[idx];
      int newVal = queries[q][1];

      // Update the array
      nums[idx] = newVal;

      // Remove old value's contribution
      if (isPrime[oldVal] && primeIndices.containsKey(oldVal)) {
        removeContribution(primeIndices, oldVal, idx, segTree);
      }

      // Add new value's contribution
      if (isPrime[newVal]) {
        addContribution(primeIndices, newVal, idx, segTree);
      }

      // Calculate answer
      int distinctPrimes = primeIndices.size();
      int maxOverlap = segTree.queryMax();
      result[q] = distinctPrimes + maxOverlap;
    }

    return result;
  }

  /**
   * Remove a prime's contribution when its position is updated
   * <p>
   * Cases:
   * <p>
   * 1. Prime appears ≥2 times: May need to update overlap range
   * <p>
   * a. If removing boundary: shrink range
   * <p>
   * b. If removing middle: range unchanged
   * <p>
   * 2. Prime appears exactly once: Remove from map
   */
  private void removeContribution(HashMap<Integer, TreeSet<Integer>> primeIndices,
      int prime, int idx, SegmentTree segTree) {
    TreeSet<Integer> indices = primeIndices.get(prime);

    if (indices.size() >= 2) {
      int oldFirst = indices.first();
      int oldLast = indices.last();

      // Check if this is a boundary position
      if (idx == oldFirst || idx == oldLast) {
        // Remove old range contribution
        segTree.update(oldFirst + 1, oldLast, -1);

        indices.remove(idx);

        // Add new range contribution if still ≥2 occurrences
        if (indices.size() >= 2) {
          int newFirst = indices.first();
          int newLast = indices.last();
          segTree.update(newFirst + 1, newLast, 1);
        }
      } else {
        // Middle position: just remove from set
        indices.remove(idx);
      }
    } else {
      // Only one occurrence: remove prime entirely
      primeIndices.remove(prime);
    }
  }

  /**
   * Add a prime's contribution when a position is updated to it
   * <p>
   * Cases:
   * <p>
   * 1. Prime doesn't exist: Create new entry
   * <p>
   * 2. Prime exists with ≥1 occurrence:
   * <p>
   * a. New  position extends range: Update overlap
   * <p>
   * b. New position inside existing range: No change
   */
  private void addContribution(HashMap<Integer, TreeSet<Integer>> primeIndices,
      int prime, int idx, SegmentTree segTree) {
    if (!primeIndices.containsKey(prime)) {
      // First occurrence of this prime
      primeIndices.put(prime, new TreeSet<>());
      primeIndices.get(prime).add(idx);
    } else {
      TreeSet<Integer> indices = primeIndices.get(prime);
      int oldFirst = indices.first();
      int oldLast = indices.last();

      // Check if new position extends the range
      if (idx < oldFirst || idx > oldLast) {
        // Remove old range if it existed
        if (indices.size() >= 2) {
          segTree.update(oldFirst + 1, oldLast, -1);
        }

        indices.add(idx);

        // Add new extended range
        int newFirst = indices.first();
        int newLast = indices.last();
        segTree.update(newFirst + 1, newLast, 1);
      } else {
        // Inside existing range: just add to set
        indices.add(idx);
      }
    }
  }

  /**
   * Sieve of Eratosthenes - O(n log log n)
   * <p>
   * Algorithm:
   * <p>
   * 1. Assume all numbers ≥2 are prime
   * <p>
   * 2. For each prime p, mark all multiples of p as composite
   * <p>
   * 3. Optimization: Start marking from p²
   */
  private boolean[] sieve(int n) {
    boolean[] isPrime = new boolean[n + 1];
    Arrays.fill(isPrime, true);
    isPrime[0] = isPrime[1] = false;

    int sqrt = (int) Math.sqrt(n);
    for (int i = 2; i <= sqrt; i++) {
      if (isPrime[i]) {
        for (long j = (long) i * i; j <= n; j += i) {
          isPrime[(int) j] = false;
        }
      }
    }

    return isPrime;
  }
}

// Optimal - Written
class Solution6 {

  private static final int MAX_NUM = 100000;

  static class SegmentTree {

    private final int n;
    private final int[] tree;
    private final int[] pendingUpdates;

    public SegmentTree(int[] nums) {
      this.n = nums.length;
      /*
       * node = i (0-based)
       * left child = 2*i + 1
       * right child = 2*i + 2
       */
      this.tree = new int[4 * n]; // TODO Why 4? Check
      this.pendingUpdates = new int[4 * n];

      build(nums);
    }

    private void build(int[] nums) {
      // We know that node 0 stores aggregate of range between 0 and n-1, known data
      buildRecursive(0, 0, n - 1, nums);
    }

    /**
     * Builds initial Segment Tree
     * <p>
     * Base Case: low == high, we can split further. Max is itself.
     * <p>
     * Recursive Case: max is max of left and right child
     * <p>
     * Recurrence Relation - T(n) = 2T(n/2) + 1
     * <p>
     * T - O(n)
     * <p>
     * T - O(log n) - Stack
     *
     * @param node Segment tree node
     * @param low  Start range of tree rooted at node
     * @param high End range of tree rooted at node
     * @param nums Initial values to be used
     */
    private void buildRecursive(int node, int low, int high, int[] nums) {
      if (low == high) {
        tree[node] = nums[low];
        return;
      }

      int mid = (low + high) / 2;
      int leftChildPos = 2 * node + 1;
      buildRecursive(leftChildPos, low, mid, nums);
      int rightChildPos = 2 * node + 2;
      buildRecursive(rightChildPos, mid + 1, high, nums);
      tree[node] = Math.max(tree[leftChildPos], tree[rightChildPos]);
    }

    /**
     * Returns the max value between range low and high
     *
     * @param low  Start range
     * @param high End range
     * @return Max value between start and end inclusive
     */
    public int queryMax(int low, int high) {
      // We know that node 0 stores aggregate of range between 0 and n-1, known data
      return queryRecursive(0, 0, n - 1, low, high);
    }

    public int queryMax() {
      // We know that node 0 stores aggregate of range between 0 and n-1, known data
      return queryRecursive(0, 0, n - 1, 0, n - 1);
    }

    /**
     * Base Case 1: high and low are totally within qLow and qHigh -> Return max at node
     * <p>
     * Base Case 2: high and low are totally outside qLow and qHigh -> Return Integer.MIN_VALUE
     * <p>
     * Recursive Case : high and low are overlapping qLow and qHigh -> Max of left and right child
     * <p>
     * Recurrence Relation - T(n) = 2T(n/2) + 1 (Master theorem can't be applied as the branching is
     * not constant, we skip many in the base case). So use recursion tree to analyse complexity.
     * <p>
     * T - O(log n)
     * <p>
     * S - O(log n) - Stack
     *
     * @param node  Root of the segment tree
     * @param low   Start range of tree rooted at node
     * @param high  End range of tree rooted at node
     * @param qLow  Start range of query
     * @param qHigh End range of query
     * @return Max value of tree rooted at node and within query range qLow and qHigh
     */
    private int queryRecursive(int node, int low, int high, int qLow, int qHigh) {
      // Process pending updates on the way
      if (pendingUpdates[node] != 0) {
        tree[node] += pendingUpdates[node];

        // Propagate updates to children
        if (low != high) { // has children
          int leftNodePos = 2 * node + 1;
          int rightNodePos = 2 * node + 2;
          pendingUpdates[leftNodePos] += pendingUpdates[node];
          pendingUpdates[rightNodePos] += pendingUpdates[node];
        }

        pendingUpdates[node] = 0; // clear flag
      }

      // Base Case 1 : Complete overlap
      if (low >= qLow && high <= qHigh) {
        return tree[node];
      }

      // Base Case 2 : No overlap
      if (low > qHigh || high < qLow) {
        return Integer.MIN_VALUE;
      }

      // Recursive Case : Partial overlap
      int mid = (low + high) / 2;
      int leftChildPos = 2 * node + 1;
      int leftMax = queryRecursive(leftChildPos, low, mid, qLow, qHigh);
      int rightChildPos = 2 * node + 2;
      int rightMax = queryRecursive(rightChildPos, mid + 1, high, qLow, qHigh);
      return Math.max(leftMax, rightMax);
    }

    /**
     * Update value of position
     *
     * @param pos   Position to be updated
     * @param delta Delta to be applied
     */
    public void update(int pos, int delta) {
      updateRecursive(pos, delta);
    }

    /**
     * Applies delta to all elements in the range low and high inclusive
     *
     * @param low   Start range
     * @param high  End range
     * @param delta Delta to be applied
     */
    public void updateRange(int low, int high, int delta) {
      updateRangeRecursive(low, high, delta);
    }

    private void updateRangeRecursive(int low, int high, int delta) {
      updateRangeRecursive(0, 0, n - 1, low, high, delta);
    }

    private void updateRangeRecursive(int node, int low, int high, int qLow, int qHigh, int delta) {
      // Process pending updates on the way
      if (pendingUpdates[node] != 0) {
        tree[node] += pendingUpdates[node];

        // Propagate updates to children
        if (low != high) { // has children
          int leftNodePos = 2 * node + 1;
          int rightNodePos = 2 * node + 2;
          pendingUpdates[leftNodePos] += pendingUpdates[node];
          pendingUpdates[rightNodePos] += pendingUpdates[node];
        }

        pendingUpdates[node] = 0; // clear flag
      }

      // Case 1 - No overlap
      if (low > qHigh || high < qLow) {
        return;
      }

      // Case 2 - Complete overlap
      if (low >= qLow && high <= qHigh) {
        tree[node] += delta; // delta is going to be applied to all nums including the max num. So just do it now.
        if (low != high) { // has children
          // Defer updates for left and right subtree
          int leftNodePos = 2 * node + 1;
          int rightNodePos = 2 * node + 2;
          pendingUpdates[leftNodePos] += delta;
          pendingUpdates[rightNodePos] += delta;
        }
        return;
      }

      // Case 3 - Partial overlap
      int mid = (low + high) / 2;
      int leftNodePos = 2 * node + 1;
      int rightNodePos = 2 * node + 2;
      updateRangeRecursive(leftNodePos, low, mid, qLow, qHigh, delta);
      updateRangeRecursive(rightNodePos, mid + 1, high, qLow, qHigh, delta);
      tree[node] = Math.max(tree[leftNodePos], tree[rightNodePos]);
    }

    /**
     * Applies the delta to element at pos and updates the tree towards root
     *
     * @param pos   Position of the element where delta being applied
     * @param delta Delta to be applied
     */
    private void updateRecursive(int pos, int delta) {
      // We know that node 0 stores aggregate of range between 0 and n-1, known data
      updateRecursive(0, 0, n - 1, pos, delta);
    }

    /**
     * Apply delta for the position pos, which is in the tree rooted at node, which has range low
     * and high
     * <p>
     * Base Case : When we met the exact position
     * <p>
     * Recursive Case : When pos lies in either left or right half
     * <p>
     * Recurrence Relation - T(n) = T(n/2) + 1
     * <p>
     * T - O(log n)
     * <p>
     * S - O(log n) - Stack
     *
     * @param node  Root of the tree where Position resides
     * @param low   Start range of the tree rooted at node
     * @param high  End range of the tree rooted at node
     * @param pos   Position of the element to be updated
     * @param delta Delta to be applied
     */
    private void updateRecursive(int node, int low, int high, int pos, int delta) {
      if (low == high) {
        tree[node] += delta;
        return;
      }

      int mid = (low + high) / 2;
      int leftChildPos = 2 * node + 1;
      int rightChildPos = 2 * node + 2;
      if (pos <= mid) {
        updateRecursive(leftChildPos, low, mid, pos, delta);
      } else {
        updateRecursive(rightChildPos, mid + 1, high, pos, delta);
      }

      tree[node] = Math.max(tree[leftChildPos], tree[rightChildPos]);
    }
  }

  /**
   * Main solution method
   * <p>
   * Strategy:
   * <p>
   * 1. Precompute all primes using Sieve
   * <p>
   * 2. Track positions of each prime using TreeSet (for fast first/last)
   * <p>
   * 3. Build initial overlap array using difference technique
   * <p>
   * 4. For each query:
   * <p>
   * a. Remove old value's contribution (if prime)
   * <p>
   * b. Add new value's contribution (if prime)
   * <p>
   * c. Answer = distinct primes + max overlap
   */
  public int[] maximumCount(int[] nums, int[][] queries) {
    int n = nums.length;

    // Step 1: Sieve of Eratosthenes
    boolean[] isPrime = sieve(MAX_NUM);

    // Step 2: Track indices for each prime
    // TreeSet gives us O(log k) access to first and last elements
    HashMap<Integer, TreeSet<Integer>> primeIndices = new HashMap<>();

    for (int i = 0; i < n; i++) {
      if (isPrime[nums[i]]) {
        primeIndices.computeIfAbsent(nums[i], k -> new TreeSet<>()).add(i);
      }
    }

    // Step 3: Build initial overlap array using difference technique
    int[] overlap = new int[n];

    /*
     * For each prime with ≥2 occurrences:
     * - Let first = smallest index, last = largest index
     * - This prime contributes +1 to overlap for splits k ∈ [first+1, last]
     * - Using difference array: overlap[first+1]++, overlap[last+1]--
     */
    for (TreeSet<Integer> indices : primeIndices.values()) {
      if (indices.size() >= 2) {
        int first = indices.first();
        int last = indices.last();

        // Range [first+1, last] gets +1
        if (first + 1 < n) {
          overlap[first + 1]++;
        }
        if (last + 1 < n) {
          overlap[last + 1]--;
        }
      }
    }

    // Convert difference array to actual overlap counts
    for (int i = 1; i < n; i++) {
      overlap[i] += overlap[i - 1];
    }

    // Step 4: Build segment tree
    SegmentTree segTree = new SegmentTree(overlap);

    // Step 5: Process queries
    int[] result = new int[queries.length];

    for (int q = 0; q < queries.length; q++) {
      int idx = queries[q][0];
      int oldVal = nums[idx];
      int newVal = queries[q][1];

      // Update the array
      nums[idx] = newVal;

      // Remove old value's contribution
      if (isPrime[oldVal] && primeIndices.containsKey(oldVal)) {
        removeContribution(primeIndices, oldVal, idx, segTree);
      }

      // Add new value's contribution
      if (isPrime[newVal]) {
        addContribution(primeIndices, newVal, idx, segTree);
      }

      // Calculate answer
      int distinctPrimes = primeIndices.size();
      int maxOverlap = segTree.queryMax();
      result[q] = distinctPrimes + maxOverlap;
    }

    return result;
  }

  /**
   * Remove a prime's contribution when its position is updated
   * <p>
   * Cases:
   * <p>
   * 1. Prime appears ≥2 times: May need to update overlap range
   * <p>
   * a. If removing boundary: shrink range
   * <p>
   * b. If removing middle: range unchanged
   * <p>
   * 2. Prime appears exactly once: Remove from map
   */
  private void removeContribution(HashMap<Integer, TreeSet<Integer>> primeIndices,
      int prime, int idx, SegmentTree segTree) {
    TreeSet<Integer> indices = primeIndices.get(prime);

    if (indices.size() >= 2) {
      int oldFirst = indices.first();
      int oldLast = indices.last();

      // Check if this is a boundary position
      if (idx == oldFirst || idx == oldLast) {
        // Remove old range contribution
        segTree.updateRange(oldFirst + 1, oldLast, -1);

        indices.remove(idx);

        // Add new range contribution if still ≥2 occurrences
        if (indices.size() >= 2) {
          int newFirst = indices.first();
          int newLast = indices.last();
          segTree.updateRange(newFirst + 1, newLast, 1);
        }
      } else {
        // Middle position: just remove from set
        indices.remove(idx);
      }
    } else {
      // Only one occurrence: remove prime entirely
      primeIndices.remove(prime);
    }
  }

  /**
   * Add a prime's contribution when a position is updated to it
   * <p>
   * Cases:
   * <p>
   * 1. Prime doesn't exist: Create new entry
   * <p>
   * 2. Prime exists with ≥1 occurrence:
   * <p>
   * a. New  position extends range: Update overlap
   * <p>
   * b. New position inside existing range: No change
   */
  private void addContribution(HashMap<Integer, TreeSet<Integer>> primeIndices,
      int prime, int idx, SegmentTree segTree) {
    if (!primeIndices.containsKey(prime)) {
      // First occurrence of this prime
      primeIndices.put(prime, new TreeSet<>());
      primeIndices.get(prime).add(idx);
    } else {
      TreeSet<Integer> indices = primeIndices.get(prime);
      int oldFirst = indices.first();
      int oldLast = indices.last();

      // Check if new position extends the range
      if (idx < oldFirst || idx > oldLast) {
        // Remove old range if it existed
        if (indices.size() >= 2) {
          segTree.updateRange(oldFirst + 1, oldLast, -1);
        }

        indices.add(idx);

        // Add new extended range
        int newFirst = indices.first();
        int newLast = indices.last();
        segTree.updateRange(newFirst + 1, newLast, 1);
      } else {
        // Inside existing range: just add to set
        indices.add(idx);
      }
    }
  }

  /**
   * Sieve of Eratosthenes - O(n log log n)
   * <p>
   * Algorithm:
   * <p>
   * 1. Assume all numbers ≥2 are prime
   * <p>
   * 2. For each prime p, mark all multiples of p as composite
   * <p>
   * 3. Optimization: Start marking from p²
   */
  private boolean[] sieve(int n) {
    boolean[] isPrime = new boolean[n + 1];
    Arrays.fill(isPrime, true);
    isPrime[0] = isPrime[1] = false;

    int sqrt = (int) Math.sqrt(n);
    for (int i = 2; i <= sqrt; i++) {
      if (isPrime[i]) {
        for (long j = (long) i * i; j <= n; j += i) {
          isPrime[(int) j] = false;
        }
      }
    }

    return isPrime;
  }
}
