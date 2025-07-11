package io.abdul.stack_queue.faqs.problem8;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

// https://takeuforward.org/plus/dsa/stack-and-queues/faqs/lru-cache
public class Solutions {

  public static void main(String[] args) {
    // Test case 1
    LRUCache cache1 = new LRUCache(2);
    cache1.put(1, 1);               // cache = {1=1}
    cache1.put(2, 2);               // cache = {1=1, 2=2}
    assertEquals(1, cache1.get(1)); // returns 1
    cache1.put(3, 3);               // evicts key 2, cache = {1=1, 3=3}
    assertEquals(-1, cache1.get(2));// returns -1
    cache1.put(4, 4);               // evicts key 1, cache = {3=3, 4=4}
    assertEquals(-1, cache1.get(1));// returns -1
    assertEquals(3, cache1.get(3)); // returns 3
    assertEquals(4, cache1.get(4)); // returns 4

    // Test case 2
    LRUCache cache2 = new LRUCache(1);
    cache2.put(1, 1);               // cache = {1=1}
    cache2.put(2, 2);               // evicts key 1, cache = {2=2}
    assertEquals(-1, cache2.get(1));// returns -1
    cache2.put(3, 3);               // evicts key 2, cache = {3=3}
    assertEquals(-1, cache2.get(2));// returns -1
    cache2.put(4, 4);               // evicts key 3, cache = {4=4}
    assertEquals(-1, cache2.get(3));// returns -1

    // Test case 3
    LRUCache cache3 = new LRUCache(2);
    cache3.put(1, 1);               // cache = {1=1}
    cache3.put(2, 2);               // cache = {1=1, 2=2}
    assertEquals(1, cache3.get(1)); // returns 1
    cache3.put(3, 3);               // evicts key 2, cache = {1=1, 3=3}
    cache3.put(4, 4);               // evicts key 1, cache = {3=3, 4=4}
    assertEquals(-1, cache3.get(2));// returns -1
    assertEquals(4, cache3.get(4)); // returns 4

    LRUCache cache = new LRUCache(5);

    assertEquals(-1, cache.get(5));       // [2,5]
    cache.put(6, 96); // 6
    cache.put(8, 43); // 8, 6
    cache.put(5, 76); // 5, 8, 6
    cache.put(6, 98); // 6, 5, 8
    cache.put(6, 7); // 6, 5, 8
    cache.put(6, 93); // 6, 5, 8
    assertEquals(-1, cache.get(4));
    assertEquals(-1, cache.get(9));
    assertEquals(-1, cache.get(2));
    cache.put(4, 7); // 4, 6, 5, 8
    assertEquals(76, cache.get(5)); // 5, 4, 6, 8
    assertEquals(-1, cache.get(7));
    assertEquals(76, cache.get(5)); // 5, 4, 6, 8
    cache.put(5, 83); // 5, 4, 6, 8
    cache.put(3, 16); // 3, 5, 4, 6, 8
    cache.put(8, 29); // 8, 3, 5, 4, 6
    assertEquals(16, cache.get(3));// 3, 8, 5, 4, 6
    cache.put(2, 76); // 2, 8, 3, 5, 4
    assertEquals(16, cache.get(3)); // 3, 2, 8, 5, 4
    assertEquals(16, cache.get(3)); // 3, 2, 8, 5, 4
    cache.put(10, 95); // 10, 3, 2, 8, 5
    cache.put(8, 97); // 8, 10, 3, 2, 8
    assertEquals(-1, cache.get(1));       // [2,1]
    cache.put(5, 94); // 5, 8, 10, 3, 2
    cache.put(3, 59); // 3, 5, 8, 10, 2
    cache.put(6, 14); // 6, 3, 5, 8, 10
    cache.put(5, 25); // 5, 6, 3, 8, 10
    assertEquals(59, cache.get(3)); // 3, 5, 6, 8, 10
    assertEquals(14, cache.get(6)); // 6, 3, 5, 8, 10
    assertEquals(-1, cache.get(9));
    assertEquals(-1, cache.get(9));
    assertEquals(14, cache.get(6)); // 6, 3, 5, 8, 10
    cache.put(1, 62); // 1, 6, 3, 5, 8
    assertEquals(-1, cache.get(2));
  }
}

class LRUCache {

  private final int capacity;
  private final Map<Integer, Node> map;
  private final Node head;
  private final Node tail;

  public LRUCache(int capacity) {
    this.capacity = capacity;
    this.map = new HashMap<>(capacity);
    this.head = new Node(-1, -1);
    this.tail = new Node(-1, -1);
    this.head.next = tail;
    this.tail.prev = head;
  }

  public int get(int key) {
    if (map.containsKey(key)) {
      Node node = map.get(key);
      remove(node);
      insertAtFirst(node);
      return node.value;
    }

    return -1; // not found
  }

  public void put(int key, int value) {
    // update
    if (map.containsKey(key)) {
      Node node = map.get(key);
      node.value = value;
      remove(node);
      insertAtFirst(node);
      return;
    }

    // insert
    if (map.size() == capacity) { // remove LRU
      Node removed = removeLast();
      map.remove(removed.key);
    }

    Node newNode = new Node(key, value);
    insertAtFirst(newNode);
    map.put(key, newNode);
  }

  private void insertAtFirst(Node node) {
    node.next = head.next;
    node.prev = head;
    head.next.prev = node;
    head.next = node;
  }

  private void remove(Node node) {
    node.prev.next = node.next;
    node.next.prev = node.prev;
  }

  private Node removeLast() {
    // assuming capacity is at least 1 and LL has at least 1 element
    Node lastElement = tail.prev;
    lastElement.prev.next = tail;
    tail.prev = lastElement.prev;
    return lastElement;
  }
}

class Node {

  int key;
  int value;
  Node prev;
  Node next;

  public Node(int key, int value) {
    this.key = key;
    this.value = value;
  }
}