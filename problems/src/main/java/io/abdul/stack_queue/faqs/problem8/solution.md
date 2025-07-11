1. Need constant time lookup on key
    - None other than our HashMap!
2. How to maintain a queue where it's ordered based on when it was put / get
    - Stack ? Can only insert and delete at one end
    - Queue ? Quiet possible. But how to find the element being get and move it to the head of the
      queue in constant time?
    - Singly Linked List based Queue - O(n) to remove a middle element from LL
    - Doubly Linked List based Queue - O(1) to remove a middle element from LL -- The way to go
3. put
    - If update, find the Node from Map O(1). Update value. Remove that node and reinsert after head
      in LL.
    - Why after head? We maintain a dummy head/tail pointer to get rid of updating head and tail
      during the cache operations.
    - So we always insert after head to insert a first element
    - If new element, check if size is full. Put the element, insert the new node after head. O(1)
    - If full, remove last element (before tail), remove it from Map. O(1)
4. get
    - get from map O(1)
    - If exists, remove and insert node as first element in LL