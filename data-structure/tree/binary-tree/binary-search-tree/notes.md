Here’s a complete breakdown of **Binary Search Tree (BST)** and its various **types**, **purposes**,
and **pros & cons** — useful for interviews, system design, and foundational understanding.

---

## 🌳 1. What is a Binary Search Tree (BST)?

A **BST** is a binary tree where for every node:

* **Left subtree** has values **less than the node**
* **Right subtree** has values **greater than the node**
* No duplicates (in standard BSTs)

### Example:

```
      8
     / \
    3   10
   / \    \
  1   6    14
```

---

## 🧠 2. Purpose of BST

BSTs provide:

* **Efficient search, insert, delete** operations: O(log n) in balanced case
* **Sorted data traversal** via in-order traversal
* **Underlying structure** for associative containers like `TreeMap`, `TreeSet`

---

## 📂 3. Types of BSTs

| Type                    | Description                                             |
|-------------------------|---------------------------------------------------------|
| **Standard BST**        | Unbalanced tree with basic insert/search logic          |
| **AVL Tree**            | Self-balancing BST with height difference ≤ 1           |
| **Red-Black Tree**      | Self-balancing BST with coloring and rules              |
| **Splay Tree**          | Recently accessed elements moved to root                |
| **Treap (Tree + Heap)** | BST with heap-based priority balancing                  |
| **Segment Tree**        | Range query tree (not strictly a BST)                   |
| **Cartesian Tree**      | Combines BST and heap properties                        |
| **Scapegoat Tree**      | Rebalances only when size threshold is exceeded         |
| **B-Trees / B+ Trees**  | Generalizations for disk-based (multi-way search trees) |

---

## ✅ 4. Pros of BSTs

| Advantage                 | Explanation                   |
|---------------------------|-------------------------------|
| **Efficient search**      | O(log n) in balanced trees    |
| **Sorted traversal**      | In-order gives sorted list    |
| **Dynamic insert/delete** | More flexible than arrays     |
| **Memory-efficient**      | Nodes only as needed          |
| **Custom ordering**       | Based on comparator functions |

---

## ❌ 5. Cons of BSTs

| Limitation                  | Why it matters                                              |
|-----------------------------|-------------------------------------------------------------|
| **Can be unbalanced**       | Worst case: O(n) time for all operations (like linked list) |
| **Insert/delete overhead**  | Costly compared to arrays in some cases                     |
| **Complex balancing logic** | Red-Black, AVL require rotations and rules                  |
| **Slower than hash maps**   | For pure key-based lookup, hashing is faster on average     |

---

## 🧮 6. Time Complexities

| Operation     | Balanced BST | Unbalanced BST (worst case) |
|---------------|--------------|-----------------------------|
| Search        | O(log n)     | O(n)                        |
| Insert/Delete | O(log n)     | O(n)                        |
| Traversal     | O(n)         | O(n)                        |

---

## 🛠 7. When to Use Which BST Variant?

| Use Case                             | Best Choice          |
|--------------------------------------|----------------------|
| Need consistent O(log n) ops         | AVL / Red-Black Tree |
| Frequent recent-access reuse         | Splay Tree           |
| Disk/Database indexes                | B+ Tree              |
| Rare insert/delete but frequent read | Standard BST         |
| Memory-critical & sorted order       | Treap / Scapegoat    |

---

## 📌 Interview Tip

> “A BST gives sorted access with dynamic insert/delete. But if balance is not maintained,
> performance degrades to O(n). That's why self-balancing trees like AVL or Red-Black are preferred
> in
> real systems.”

---

Would you like a visual comparison or code samples for any of the types (like AVL or Red-Black)?

---

Great! You're asking specifically for **actual types of Binary Search Trees (BSTs)** — **not general
binary trees** like segment trees or Cartesian trees that don't always follow BST rules.

Here’s a **focused list of BST variants**, all of which **maintain the BST invariant** (left <
root < right):

---

## ✅ Actual Types of Binary Search Trees (BSTs)

| BST Type           | Description                                                                   |
|--------------------|-------------------------------------------------------------------------------|
| **Standard BST**   | Basic unbalanced BST with no self-balancing logic.                            |
| **AVL Tree**       | Self-balancing BST: maintains height balance (difference ≤ 1) via rotations.  |
| **Red-Black Tree** | Self-balancing BST with color rules to ensure roughly balanced height.        |
| **Splay Tree**     | Self-adjusting BST: moves accessed nodes to root via rotations.               |
| **Treap**          | BST based on keys, and a min/max heap property based on random priorities.    |
| **Scapegoat Tree** | Maintains balance by rebuilding entire subtrees when balance threshold fails. |
| **Tango Tree**     | BST optimized for dynamic optimality (used in theoretical research).          |

---

## 🧠 All of These Are True BSTs

Each of these:

* Preserves **BST ordering property**
* Supports **insert, delete, search** in O(log n) (amortized/average for some)
* Uses different **balancing strategies** to avoid degeneration

---

## 🔍 Comparison Summary

| Type           | Balanced?       | Rotation-based? | Best Case | Worst Case | Notes                                  |
|----------------|-----------------|-----------------|-----------|------------|----------------------------------------|
| Standard BST   | ❌ No            | ❌ No            | O(log n)  | O(n)       | Can degrade to linked list             |
| AVL Tree       | ✅ Strict        | ✅ Yes           | O(log n)  | O(log n)   | Faster lookups, slower inserts         |
| Red-Black Tree | ✅ Looser        | ✅ Yes           | O(log n)  | O(log n)   | Widely used (Java TreeMap, C++ map)    |
| Splay Tree     | ✅ Amortized     | ✅ Yes           | O(1)      | O(n)       | Self-adjusts, good for locality        |
| Treap          | ✅ Probabilistic | ✅ Yes           | O(log n)  | O(log n)   | Combines BST + heap, randomized        |
| Scapegoat Tree | ✅ Rebuilds      | ❌ No            | O(log n)  | O(log n)   | Simple insert/delete, good in practice |
| Tango Tree     | ✅ Dynamic       | ✅ Yes           | O(log n)  | O(log n)   | Used in theoretical optimizations      |

---

## 📌 Interview Tip

> “These are all true BSTs — they maintain the invariant and offer search, insert, and delete. The
> main difference lies in how they **maintain balance**: rotations (AVL, Red-Black), self-adjustment (
> Splay), randomized priority (Treap), or subtree rebuilds (Scapegoat).”

---

Would you like:

* Rotations visualized (AVL vs Red-Black)?
* Code samples for each?
* When to prefer one over the other in real systems?
