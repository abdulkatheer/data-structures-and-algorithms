package io.abdul.stack_queue.implementation.problem5;

public class Solutions {

}

/*
Singly linked list
Add and remove from head, that simply mimics a Stack
 */
class LinkedListStack {

  private Node head = null;

  private int size;

  public LinkedListStack() {
  }

  public void push(int x) {
    head = new Node(x, head);
    size++;
  }

  public int pop() {
    if (size == 0) {
      return -1;
    }
    int e = head.data;
    head = head.next;
    size--;
    return e;
  }

  public int top() {
    if (size == 0) {
      return -1;
    }

    return head.data;
  }

  public boolean isEmpty() {
    return size == 0;
  }
}

class Node {

  final int data;
  Node next;

  public Node(int data) {
    this.data = data;
  }

  public Node(int data, Node next) {
    this.data = data;
    this.next = next;
  }
}
