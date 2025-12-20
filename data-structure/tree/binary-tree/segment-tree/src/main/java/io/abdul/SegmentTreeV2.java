package io.abdul;

// Segment Tree with Lazy Propagation
public class SegmentTreeV2 {

  private final int n;
  private final int[] tree;
  private final int[] pendingUpdates;

  public SegmentTreeV2(int[] nums) {
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
   * Apply delta for the position pos, which is in the tree rooted at node, which has range low and
   * high
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
