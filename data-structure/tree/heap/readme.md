1. Array representation of Binary Tree
    1. index starts from 1
    2. Let element be at index i
    3. i's left child will be at 2*i
    4. i's right child will be at 2*i + 1
    5. i's parent will be at floor(i/2), i.e. 3.5 -> 3
2. Complete Binary Tree
3. Heap
4. Heap Sort
5. Heapify
6. Priority Queue

### Insertion

**Two approaches**

1. Insert at root and heapify (**top-down approach**)
2. Insert at the next available position and heapify (**bottom-up approach**)

### Deletion

Only the root element can be removed, which will either be the smallest or largest element in the collection
Only one approach can be followed, which is **top-down approach**