package io.abdul.heaps.theory_and_implementation.problem4;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Solutions {

  public static void main(String[] args) {
    Solution heap = new Solution();

    // Test Case 1
    heap.initializeHeap();
    heap.insert(4);
    heap.insert(1);
    heap.insert(10);
    assertEquals(10, heap.getMax());
    assertEquals(3, heap.heapSize());
    assertFalse(heap.isEmpty());
    heap.extractMax(); // Removes 10 → Heap becomes [4,1]
    heap.changeKey(0, 16); // Heap becomes [16,1]
    assertEquals(16, heap.getMax());

    // Test Case 2
    heap = new Solution();
    heap.initializeHeap();
    heap.insert(4);
    heap.insert(1);
    heap.extractMax(); // Removes 4 → Heap becomes [1]
    assertEquals(1, heap.getMax());
    heap.insert(4); // Heap becomes [4,1]
    assertEquals(2, heap.heapSize());
    assertFalse(heap.isEmpty());
    heap.extractMax(); // Removes 4 → Heap becomes [1]
    heap.changeKey(0, 2); // Heap becomes [2]
    assertEquals(2, heap.getMax());

    test();
  }

  public static void test() {
    String[] operations = {"initializeHeap", "insert", "changeKey", "heapSize", "heapSize",
        "extractMax", "isEmpty", "insert", "extractMax", "heapSize", "isEmpty", "isEmpty",
        "isEmpty", "isEmpty", "insert", "isEmpty", "isEmpty", "insert", "extractMax", "insert",
        "changeKey", "changeKey", "changeKey", "isEmpty", "heapSize", "getMax", "changeKey",
        "isEmpty", "extractMax", "isEmpty", "getMax", "insert", "extractMax", "insert", "getMax",
        "changeKey", "extractMax", "isEmpty", "getMax", "heapSize", "changeKey", "insert", "getMax",
        "heapSize", "changeKey", "extractMax", "changeKey", "insert", "heapSize", "changeKey",
        "extractMax", "isEmpty", "heapSize", "isEmpty", "getMax", "isEmpty", "heapSize",
        "extractMax", "heapSize", "isEmpty", "heapSize", "insert", "isEmpty", "changeKey",
        "isEmpty", "changeKey", "changeKey", "extractMax", "insert", "heapSize", "insert",
        "changeKey", "isEmpty", "heapSize", "insert", "changeKey", "insert", "changeKey",
        "changeKey", "heapSize", "isEmpty", "insert", "isEmpty", "insert", "getMax", "insert",
        "getMax", "isEmpty", "getMax", "insert", "insert", "changeKey", "getMax", "isEmpty",
        "heapSize", "changeKey", "getMax", "heapSize", "insert", "heapSize", "insert", "extractMax",
        "insert", "isEmpty", "insert", "heapSize", "extractMax", "changeKey", "heapSize",
        "heapSize", "insert", "isEmpty", "getMax", "changeKey", "extractMax", "insert", "changeKey",
        "getMax", "heapSize", "extractMax", "insert", "insert", "changeKey", "getMax", "changeKey",
        "heapSize", "insert", "heapSize", "isEmpty", "getMax", "isEmpty", "getMax", "isEmpty",
        "heapSize", "insert", "heapSize", "insert", "isEmpty", "heapSize", "changeKey", "isEmpty",
        "heapSize", "changeKey", "extractMax", "insert", "getMax", "extractMax", "extractMax",
        "changeKey", "changeKey", "isEmpty", "changeKey", "heapSize", "changeKey", "isEmpty",
        "extractMax", "isEmpty", "heapSize", "changeKey", "changeKey", "insert", "extractMax",
        "insert", "insert", "insert", "heapSize", "extractMax", "isEmpty", "isEmpty", "heapSize",
        "changeKey", "changeKey", "changeKey", "extractMax", "changeKey", "insert", "heapSize",
        "changeKey", "insert", "heapSize", "extractMax", "heapSize", "getMax", "heapSize", "getMax",
        "changeKey", "getMax", "getMax", "getMax", "isEmpty", "heapSize", "insert", "extractMax",
        "extractMax", "isEmpty", "insert", "extractMax", "insert", "heapSize", "isEmpty",
        "extractMax", "changeKey", "changeKey", "changeKey", "getMax", "changeKey", "insert",
        "heapSize", "getMax", "extractMax", "changeKey", "isEmpty", "changeKey", "insert",
        "heapSize", "insert", "heapSize", "heapSize", "heapSize", "insert", "extractMax",
        "extractMax", "insert", "insert", "isEmpty", "getMax", "changeKey", "heapSize", "isEmpty",
        "heapSize", "insert", "getMax", "changeKey", "changeKey", "isEmpty", "changeKey",
        "heapSize", "changeKey", "insert", "changeKey", "extractMax", "insert", "heapSize",
        "getMax", "extractMax", "changeKey", "isEmpty", "insert", "insert", "insert", "changeKey",
        "insert", "isEmpty", "getMax", "heapSize", "insert", "changeKey", "getMax", "insert",
        "insert", "getMax", "isEmpty", "insert", "heapSize", "isEmpty", "isEmpty", "insert",
        "insert", "changeKey", "isEmpty", "heapSize", "isEmpty", "changeKey", "heapSize",
        "changeKey", "heapSize", "insert", "isEmpty", "extractMax", "extractMax", "getMax",
        "isEmpty", "heapSize", "insert", "heapSize", "extractMax", "getMax", "heapSize",
        "changeKey", "changeKey", "changeKey", "changeKey", "heapSize", "changeKey", "changeKey",
        "isEmpty", "isEmpty", "heapSize", "insert", "changeKey", "getMax", "extractMax", "isEmpty",
        "extractMax", "getMax", "isEmpty", "heapSize", "insert", "changeKey", "heapSize",
        "changeKey", "getMax", "getMax", "isEmpty", "extractMax", "changeKey", "changeKey",
        "getMax", "isEmpty", "isEmpty", "getMax", "insert", "isEmpty", "insert", "insert",
        "heapSize", "getMax", "changeKey", "insert", "changeKey", "changeKey", "insert", "getMax",
        "extractMax", "isEmpty", "extractMax", "extractMax", "heapSize", "isEmpty", "heapSize",
        "isEmpty", "insert", "heapSize", "getMax", "extractMax", "getMax", "heapSize", "getMax",
        "heapSize", "getMax", "isEmpty", "heapSize", "heapSize", "isEmpty", "getMax", "changeKey",
        "extractMax", "extractMax", "getMax", "getMax", "getMax", "heapSize", "isEmpty", "heapSize",
        "isEmpty", "isEmpty", "extractMax", "getMax", "getMax", "insert", "insert", "isEmpty",
        "getMax", "heapSize", "extractMax", "insert", "isEmpty", "insert", "insert", "isEmpty",
        "heapSize", "insert", "heapSize", "isEmpty", "isEmpty", "getMax", "heapSize", "changeKey",
        "getMax", "heapSize", "changeKey", "getMax", "getMax", "changeKey", "extractMax", "insert",
        "extractMax", "extractMax", "getMax", "insert", "isEmpty", "changeKey", "getMax",
        "heapSize", "insert", "isEmpty", "getMax", "insert", "insert", "heapSize", "extractMax",
        "extractMax", "changeKey", "changeKey", "extractMax", "insert", "getMax", "changeKey",
        "isEmpty", "insert", "getMax", "heapSize", "getMax", "getMax", "getMax", "extractMax",
        "extractMax", "extractMax", "extractMax", "getMax", "isEmpty", "isEmpty", "insert",
        "extractMax", "heapSize", "insert", "getMax", "getMax", "heapSize", "extractMax",
        "heapSize", "heapSize", "getMax", "isEmpty", "extractMax", "insert", "getMax", "extractMax",
        "extractMax", "insert", "heapSize", "extractMax", "getMax", "changeKey", "extractMax",
        "extractMax", "heapSize", "heapSize", "getMax", "insert", "getMax", "heapSize", "heapSize",
        "heapSize", "getMax", "changeKey", "extractMax", "isEmpty", "getMax", "changeKey",
        "extractMax", "extractMax", "isEmpty", "isEmpty", "getMax", "insert", "heapSize",
        "changeKey", "getMax", "extractMax", "changeKey", "heapSize", "insert", "heapSize",
        "isEmpty", "isEmpty", "isEmpty", "insert", "insert", "extractMax", "isEmpty", "extractMax",
        "getMax", "isEmpty", "heapSize", "getMax", "changeKey", "getMax", "isEmpty", "changeKey"};
    int[][] data = {{899}, {0, 829}, {24}, {7}, {498}, {829}, {0, 729}, {1, 336}, {1, 599}, {1, 53},
        {795}, {472}, {0, 876}, {0, 889}, {141}, {1, 341}, {0, 349}, {606}, {0, 923}, {966},
        {0, 501}, {0, 826}, {0, 498}, {328}, {296}, {1, 768}, {77}, {2, 565}, {568}, {1, 467},
        {2, 646}, {256}, {990}, {312}, {920}, {436}, {2, 750}, {1, 190}, {646}, {231}, {556}, {150},
        {8, 327}, {858}, {6, 621}, {504}, {8, 306}, {124}, {608}, {11, 567}, {5, 69}, {902}, {692},
        {160}, {10, 431}, {2, 860}, {441}, {8, 819}, {4, 112}, {5, 347}, {8, 263}, {1, 296},
        {11, 269}, {447}, {240}, {200}, {682}, {1, 295}, {3, 21}, {1, 998}, {7, 218}, {909},
        {7, 765}, {62}, {2, 189}, {446}, {131}, {829}, {5, 514}, {2, 959}, {6, 233}, {13, 982},
        {419}, {13, 849}, {9, 785}, {399}, {236}, {150}, {680}, {361}, {3, 756}, {284}, {5, 646},
        {3, 933}, {14, 19}, {17, 25}, {575}, {12, 60}, {66}, {7, 897}, {582}, {360}, {634},
        {9, 160}, {388}, {279}, {13, 204}, {371}, {162}, {320}, {947}, {7}, {8, 626}, {9, 265},
        {19, 234}, {841}, {17}, {24, 752}, {14, 510}, {12, 229}, {15, 675}, {13, 629}, {1, 948},
        {628}, {5, 57}, {447}, {23, 358}, {22, 526}, {17, 617}, {17, 352}, {823}, {654}, {641},
        {11, 536}, {942}, {11, 249}, {10, 47}, {148}, {817}, {5, 574}, {415}, {964}, {492}, {993},
        {723}, {134}, {3, 432}, {19, 45}, {19, 761}, {865}, {498}, {13, 147}, {65}, {333}, {216},
        {1, 379}, {15, 574}, {218}, {3, 660}, {991}, {838}, {397}, {344}, {226}, {15, 629}, {637},
        {10, 560}, {5, 149}, {915}, {13, 494}, {0, 26}, {724}, {416}, {618}, {4, 159}, {14, 420}};
    Integer[] expected = {null, null, null, 1, 1, null, 1, null, null, 0, 1, 1, 1, 1, null, 0, 0,
        null, null, null, null, null, null, 0, 2, 729, null, 0, null, 0, 53, null, null, null, 472,
        null, null, 0, 53, 1, null, null, 889, 2, null, null, null, null, 2, null, null, 0, 1, 0,
        349, 0, 1, null, 0, 1, 0, null, 0, null, 0, null, null, null, null, 1, null, null, 0, 2,
        null, null, null, null, null, 4, 0, null, 0, null, 990, null, 990, 0, 990, null, null, null,
        990, 0, 9, null, 990, 9, null, 10, null, null, null, 0, null, 12, null, null, 11, 11, null,
        0, 858, null, null, null, null, 646, 12, null, null, null, null, 646, null, 13, null, 14, 0,
        902, 0, 902, 0, 14, null, 15, null, 0, 16, null, 0, 16, null, null, null, 860, null, null,
        null, null, 0, null, 14, null, 0, null, 0, 13, null, null, null, null, null, null, null, 16,
        null, 0, 0, 15, null, null, null, null, null, null, 15, null, null, 16, null, 15, 765, 15,
        765, null, 765, 765, 765, 0, 15, null, null, null, 0, null, null, null, 15, 0, null, null,
        null, null, 959, null, null, 15, 982, null, null, 0, null, null, 15, null, 16, 16, 16, null,
        null, null, null, null, 0, 785, null, 17, 0, 17, null, 785, null, null, 0, null, 18, null,
        null, null, null, null, 19, 785, null, null, 0, null, null, null, null, null, 0, 897, 22,
        null, null, 897, null, null, 897, 0, null, 26, 0, 0, null, null, null, 0, 28, 0, null, 28,
        null, 28, null, 0, null, null, 841, 0, 27, null, 28, null, 756, 27, null, null, null, null,
        27, null, null, 0, 0, 27, null, null, 948, null, 0, null, 752, 0, 26, null, null, 27, null,
        752, 752, 0, null, null, null, 634, 0, 0, 634, null, 0, null, null, 29, 823, null, null,
        null, null, null, 942, null, 0, null, null, 28, 0, 28, 0, null, 29, 817, null, 641, 28, 641,
        28, 641, 0, 28, 28, 0, 641, null, null, null, 628, 628, 628, 26, 0, 26, 0, 0, null, 626,
        626, null, null, 0, 964, 27, null, null, 0, null, null, 0, 29, null, 30, 0, 0, 993, 30,
        null, 993, 30, null, 993, 993, null, null, null, null, null, 723, null, 0, null, 723, 29,
        null, 0, 723, null, null, 32, null, null, null, null, null, null, 574, null, 0, null, 991,
        31, 991, 991, 991, null, null, null, null, 514, 0, 0, null, null, 27, null, 514, 514, 28,
        null, 27, 27, 510, 0, null, null, 492, null, null, null, 26, null, 415, null, null, null,
        23, 23, 397, null, 637, 24, 24, 24, 637, null, null, 0, 560, null, null, null, 0, 0, 379,
        null, 22, null, 915, null, null, 21, null, 22, 0, 0, 0, null, null, null, 0, null, 416, 0,
        22, 416, null, 416, 0, null};

    int dataIndex = 0;
    for (int i = 0; i < operations.length; i++) {
      String op = operations[i];
      System.out.print("Operation " + i + ": " + op);

      if (op.equals("insert")) {
        if (dataIndex >= data.length) {
          System.out.println(" -> ERROR: no data left for insert");
        } else if (data[dataIndex].length != 1) {
          System.out.println(
              " -> ERROR: insert expects 1 parameter but got " + data[dataIndex].length);
          dataIndex++;
        } else {
          System.out.println(" -> mapped data: " + data[dataIndex][0]);
          dataIndex++;
        }
      } else if (op.equals("changeKey")) {
        if (dataIndex >= data.length) {
          System.out.println(" -> ERROR: no data left for changeKey");
        } else if (data[dataIndex].length != 2) {
          System.out.print(" -> ERROR: changeKey expects 2 params but got ");
          System.out.println(data[dataIndex].length);
          dataIndex++;
        } else {
          System.out.println(
              " -> mapped data: index=" + data[dataIndex][0] + ", val=" + data[dataIndex][1]);
          dataIndex++;
        }
      } else {
        // no data expected
        System.out.println();
      }
    }

    if (dataIndex < data.length) {
      System.out.println("WARNING: Extra data entries starting from index " + dataIndex);
    }

    runOperationsAndAssert(operations, data, expected);
  }

  private static void runOperationsAndAssert(String[] operations, int[][] data,
      Object[] expectedOutputs) {
    Solution maxHeap = new Solution();
    Solution2 maxHeap2 = new Solution2();

    List<Object> actualOutputs = new ArrayList<>();
    int dataIndex = 0;

    for (int i = 0; i < operations.length; i++) {
      String op = operations[i];
      Object actualResult = null; // default null for ops without output

      if (i == 442) {
        System.out.printf("");
      }

      if (i == 289) {
        System.out.printf("");
      }

      switch (op) {
        case "initializeHeap":
          maxHeap.initializeHeap();
          maxHeap2.initializeHeap();
          actualResult = null;
          break;
        case "insert":
          // Insert takes one parameter
          if (dataIndex >= data.length) {
            fail("Missing input data for insert operation at index: " + i);
          }
          maxHeap.insert(data[dataIndex][0]);
          maxHeap2.insert(data[dataIndex][0]);
          dataIndex++;
          actualResult = null;
          break;
        case "changeKey":
          // changeKey takes two parameters: index and newVal
          if (dataIndex >= data.length) {
            fail("Missing input data for changeKey operation at index: " + i);
          }
          int idx = data[dataIndex][0];
          int newVal = data[dataIndex][1];
          maxHeap.changeKey(idx, newVal);
          maxHeap2.changeKey(idx, newVal);
          dataIndex++;
          actualResult = null;
          break;
        case "extractMax":
          // Remove max, no output expected
          maxHeap.extractMax();
          maxHeap2.extractMax();
          actualResult = null;
          break;
        case "getMax":
          try {
            actualResult = maxHeap.getMax();
            actualResult = maxHeap2.getMax();
          } catch (Exception e) {
            // If behavior is to throw when empty, record null
            actualResult = null;
          }
          break;
        case "heapSize":
          actualResult = maxHeap.heapSize();
          actualResult = maxHeap2.heapSize();
          break;
        case "isEmpty":
          actualResult = maxHeap.isEmpty() ? 1 : 0; // map boolean to 1 or 0
          actualResult = maxHeap2.isEmpty() ? 1 : 0; // map boolean to 1 or 0
          break;
        default:
          fail("Unknown operation: " + op);
      }

      actualOutputs.add(actualResult);

      List<Integer> expectedSorted = new ArrayList<>(maxHeap.heap);
      List<Integer> actualSorted = new ArrayList<>(maxHeap2.heap);

      Collections.sort(expectedSorted);
      Collections.sort(actualSorted);

      assertEquals(expectedSorted, actualSorted);

      if (!isMaxHeap(maxHeap.heap)) {
        fail("Unknown operation: " + op);
      }
    }

    // Assert actual outputs match expected outputs
    assertArrayEquals(expectedOutputs, actualOutputs.toArray(),
        "Actual outputs do not match expected outputs.");
  }

  public static boolean isMaxHeap(List<Integer> arr) {
    int n = arr.size();
    for (int i = 0; i <= (n - 2) / 2; i++) {
      if (2 * i + 1 < n && arr.get(i) < arr.get(2 * i + 1)) {
        return false;
      }
      if (2 * i + 2 < n && arr.get(i) < arr.get(2 * i + 2)) {
        return false;
      }
    }
    return true;
  }

  public static boolean isMaxHeap(int[] arr) {
    int n = arr.length;
    for (int i = 0; i <= (n - 2) / 2; i++) {
      if (2 * i + 1 < n && arr[i] < arr[2 * i + 1]) {
        return false;
      }
      if (2 * i + 2 < n && arr[i] < arr[2 * i + 2]) {
        return false;
      }
    }
    return true;
  }

}

class Solution {

  public List<Integer> heap;

  public void initializeHeap() {
    heap = new ArrayList<>();
  }

  public void insert(int key) {
    heap.add(key);
    heapifyUp(heap.size() - 1);
  }

  public void changeKey(int index, int newVal) {
    if (newVal > heap.get(index)) {
      heap.set(index, newVal);
      heapifyUp(index);
    } else {
      heap.set(index, newVal);
      heapifyDown(index);
    }
  }

  public void extractMax() {
    if (heap.isEmpty()) {
      return;
    }
    heap.set(0, heap.get(heap.size() - 1));
    heap.remove(heap.size() - 1);
    heapifyDown(0);
  }

  public boolean isEmpty() {
    return heap.isEmpty();
  }

  public int getMax() {
    return heap.isEmpty() ? -1 : heap.get(0);
  }

  public int heapSize() {
    return heap.size();
  }

  private void heapifyUp(int index) {
    while (index > 0) {
      int parent = (index + 1) / 2 - 1;
      if (heap.get(parent) < heap.get(index)) {
        int temp = heap.get(parent);
        heap.set(parent, heap.get(index));
        heap.set(index, temp);
        index = parent;
      } else {
        break;
      }
    }
  }

  private void heapifyDown(int index) {
    while (true) {
      int left = 2 * index + 1;
      int right = 2 * index + 2;

      if (left < heap.size() && heap.get(left) > heap.get(index) && right < heap.size()
          && heap.get(right) > heap.get(index)) {
        if (heap.get(left) > heap.get(right)) {
          int temp = heap.get(left);
          heap.set(left, heap.get(index));
          heap.set(index, temp);
          index = left;
        } else {
          int temp = heap.get(right);
          heap.set(right, heap.get(index));
          heap.set(index, temp);
          index = right;
        }
      } else if (left < heap.size() && heap.get(left) > heap.get(index)) {
        int temp = heap.get(left);
        heap.set(left, heap.get(index));
        heap.set(index, temp);
        index = left;
      } else if (right < heap.size() && heap.get(right) > heap.get(index)) {
        int temp = heap.get(right);
        heap.set(right, heap.get(index));
        heap.set(index, temp);
        index = right;
      } else {
        break;
      }
    }
  }
}

class Solution2 {

  public List<Integer> heap; // list to store the max-heap
  private int count; // to store the count of elements in max-heap

  // Constructor
  public Solution2() {
    heap = new ArrayList<>();
    count = 0;
  }

  // Function to recursively heapify the array upwards
  private void heapifyUp(int ind) {
    int parentInd = (ind - 1) / 2;

    // If current index holds larger value than the parent
    if (ind > 0 && heap.get(ind) > heap.get(parentInd)) {
      // Swap the values at the two indices
      int temp = heap.get(ind);
      heap.set(ind, heap.get(parentInd));
      heap.set(parentInd, temp);

      // Recursively heapify the upper nodes
      heapifyUp(parentInd);
    }

    return;
  }

  // Function to recursively heapify the array downwards
  private void heapifyDown(int ind) {
    int n = heap.size(); // Size of the array

    // To store the index of largest element
    int largestInd = ind;

    // Indices of the left and right children
    int leftChildInd = 2 * ind + 1;
    int rightChildInd = 2 * ind + 2;

    // If the left child holds larger value, update the largest index
    if (leftChildInd < n && heap.get(leftChildInd) > heap.get(largestInd)) {
      largestInd = leftChildInd;
    }

    // If the right child holds larger value, update the largest index
    if (rightChildInd < n && heap.get(rightChildInd) > heap.get(largestInd)) {
      largestInd = rightChildInd;
    }

    // If the largest element index is updated
    if (largestInd != ind) {
      // Swap the largest element with the current index
      int temp = heap.get(largestInd);
      heap.set(largestInd, heap.get(ind));
      heap.set(ind, temp);

      // Recursively heapify the lower subtree
      heapifyDown(largestInd);
    }

    return;
  }

  // Method to intialize the max-heap data structure
  public void initializeHeap() {
    heap.clear();
    count = 0;
  }

  // Method to insert a given value in the max-heap
  public void insert(int key) {
    // Insert the value at the back of the list
    heap.add(key);

    // Heapify upwards
    heapifyUp(count);
    count = count + 1; // Increment the counter;

    return;
  }

  // Method to change the value at a given index in max-heap
  public void changeKey(int index, int new_val) {
    // If the current value is replaced with a larger value
    if (heap.get(index) < new_val) {
      heap.set(index, new_val);
      heapifyUp(index);
    }
    // Otherwise (if the current value is replaced with smaller value)
    else {
      heap.set(index, new_val);
      heapifyDown(index);
    }

    return;
  }

  // Method to extract the maximum value from the max-heap
  public void extractMax() {
    int ele = heap.get(0); // maximum value in the heap

    // Swap the top value with the value at last index
    int temp = heap.get(count - 1);
    heap.set(count - 1, heap.get(0));
    heap.set(0, temp);

    heap.remove(count - 1); // Pop the maximum value from the list
    count = count - 1; // Decrement the counter

    // Heapify the root value downwards
    if (count > 0) {
      heapifyDown(0);
    }
  }

  // Method to return if the max-heap is empty
  public boolean isEmpty() {
    return (count == 0);
  }

  // Method to return the maximum value in the max-heap
  public int getMax() {
    // Return the value stored at the root
    return heap.get(0);
  }

  // Method to return the size of max-heap
  public int heapSize() {
    return count;
  }
}