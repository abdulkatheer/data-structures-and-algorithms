package io.abdul.stack_queue.faqs.problem9;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

public class Solutions {

  public static void main(String[] args) {
    testBasicScenario();
    testEvictionsOnSameFrequency();
  }

  static void testBasicScenario() {
    LFUCache lfu = new LFUCache(2);

    lfu.put(1, 1);         // cache=[1], cnt(1)=1
    lfu.put(2, 2);         // cache=[2,1], cnt(2)=1, cnt(1)=1
    assertEquals(1, lfu.get(1));   // cnt(1)=2
    lfu.put(3, 3);         // evict 2 (LFU), cache=[3,1]
    assertEquals(-1, lfu.get(2));  // not found
    assertEquals(3, lfu.get(3));   // cnt(3)=2
    lfu.put(4, 4);         // evict 1 (cnt=2, LRU), cache=[4,3]
    assertEquals(-1, lfu.get(1));  // not found
    assertEquals(3, lfu.get(3));   // cnt(3)=3
    assertEquals(4, lfu.get(4));   // cnt(4)=2

  }

  static void testEvictionsOnSameFrequency() {
    LFUCache lfu = new LFUCache(3);

    lfu.put(5, 7); // [5]
    lfu.put(4, 6); // [4,5]
    lfu.put(3, 5); // [3,4,5]
    lfu.put(2, 4); // evict 5 (LFU), [2,3,4]
    lfu.put(1, 3); // evict 4 (LFU), [1,2,3]

    assertEquals(3, lfu.get(1)); // cnt(1)=2
    assertEquals(4, lfu.get(2)); // cnt(2)=2
    assertEquals(5, lfu.get(3)); // cnt(3)=2
    assertEquals(-1, lfu.get(4)); // evicted
    assertEquals(-1, lfu.get(5)); // evicted
  }
}

class LFUCache {

  private final int capacity;
  private final Map<Integer, Node> map;
  private final Map<Integer, List> listByFrequency;
  private int minFrequency = 1;

  public LFUCache(int capacity) {
    this.capacity = capacity;
    map = new HashMap<>(capacity);
    listByFrequency = new HashMap<>(capacity);
  }

  public int get(int key) {
    Node node = map.get(key);
    if (node != null) {
      incrementFrequency(node);
      return node.value;
    }

    return -1;
  }

  public void put(int key, int value) {
    if (map.containsKey(key)) {
      Node node = map.get(key);
      node.value = value;
      incrementFrequency(node);
      return;
    }

    if (map.size() == capacity) {
      // TODO what if minFrequency doesn't have any values?, reproduce this
      /*
      it's poss
       */
      Node node = listByFrequency.get(minFrequency).removeLast();
      map.remove(node.key);
    }

    Node newNode = new Node(key, value);
    minFrequency = 1; // Bcz newly added element will have freq 1 and that's the known min frequency

    if (!listByFrequency.containsKey(minFrequency)) { // if List doesn't exist, create one
      listByFrequency.put(minFrequency, new List());
    }

    listByFrequency.get(minFrequency).addFirst(newNode);
    map.put(key, newNode);
  }

  private void incrementFrequency(Node node) {
    int currentFreq = node.frequency;
    listByFrequency.get(currentFreq).remove(node); // removing from current DLL
    node.frequency++;

    // if current DLL is the minFrequency and no elements exist, minFrequency has to be increased
    if (currentFreq == minFrequency && listByFrequency.get(currentFreq).size == 0) {
      minFrequency++;
    }

    if (!listByFrequency.containsKey(node.frequency)) {
      listByFrequency.put(node.frequency, new List());
    }

    listByFrequency.get(node.frequency).addFirst(node);
  }
}

class Node {

  int key;
  int value;
  int frequency;
  Node next;
  Node prev;

  public Node(int key, int value) {
    this.key = key;
    this.value = value;
    this.frequency = 1; // on creation, starts with 1
  }
}

class List {

  Node head;
  Node tail;
  int size = 0;

  public List() {
    head = new Node(-1, -1);
    tail = new Node(-1, -1);
    head.next = tail;
    tail.prev = head;
  }

  void addFirst(Node node) {
    node.next = head.next;
    node.prev = head;
    node.next.prev = node;
    head.next = node;
    size++;
  }

  void remove(Node node) { // Assuming node exists
    node.prev.next = node.next;
    node.next.prev = node.prev;
    node.next = null;
    node.prev = null;
    size--;
  }

  Node removeLast() { // Assuming size > 0
    Node theLast = tail.prev;
    theLast.prev.next = tail;
    tail.prev = theLast.prev;
    theLast.prev = null;
    theLast.next = null;
    size--;
    return theLast;
  }
}