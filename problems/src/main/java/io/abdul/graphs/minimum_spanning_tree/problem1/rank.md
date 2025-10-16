Excellent — that’s *the* key detail most people gloss over when learning Disjoint Set (Union-Find).
Let’s unpack it **step by step**, visually and conceptually.

---

## 🎯 Goal Recap

We want to keep the “tree” representing each set **as shallow as possible**, so that future `find()`
calls are **fast**.

Each disjoint set can be represented as a **tree**, where:

* Each element has a parent pointer.
* The **root** represents the whole set.

When we do `union(x, y)`, we link one root to the other’s root.

The problem:
If we always attach randomly, we might end up with tall trees → `find()` becomes slow.

---

## 🌳 1. What is "Rank"?

**Rank** is a *rough measure of the height* (or depth) of the tree.

Formally:

* It’s *not* always the exact height — it’s an **upper bound** of tree height.
* We maintain it only for **root nodes** (since only roots represent whole sets).

---

## ⚙️ 2. How It’s Used

When we perform `union(x, y)`:

1. Find their roots: `rootX`, `rootY`
2. Compare their ranks:

    * If one root has **lower rank**, attach it under the higher rank root.
    * If both have **equal rank**, attach one under the other **and increment the resulting root’s
      rank by 1**.

---

## 📘 3. Why Increase Rank *Only When Equal*

Let’s reason this carefully.

Imagine ranks as tree heights (simplified view).

### Case 1 — Different Ranks

```
rank[rootX] = 2
rank[rootY] = 1
```

Attach smaller tree under bigger tree → overall height **doesn’t increase**:

```
Height remains 2
```

✅ So we **don’t increase rank**.

---

### Case 2 — Equal Ranks

```
rank[rootX] = rank[rootY] = 2
```

Now whichever you attach to the other,
you’ll increase the height by 1.

Before union:

```
   rootX           rootY
   /  \            /  \
```

After union:

```
     rootX
    /     \
 rootY    ...
```

So the new height = `old height + 1`

✅ Hence, we **increase rank by 1**.

---

### Visual Summary

| Case              | Before Union | After Union         | Rank Update?   |
|-------------------|--------------|---------------------|----------------|
| rank(X) > rank(Y) | X deeper     | Y under X           | ❌ no           |
| rank(X) < rank(Y) | Y deeper     | X under Y           | ❌ no           |
| rank(X) = rank(Y) | same height  | one under the other | ✅ yes (rank++) |

---

## 📈 4. Why “Rank” Instead of “Height”

Because after **path compression**, the actual height shrinks dramatically —
but we don’t recompute height each time (too costly).

So `rank` becomes just a **heuristic** — an approximate measure that stays monotonic (never
decreases).

It still correctly guides the union operation even if the actual height shrinks later.

---

## 🧠 5. Example

Let’s simulate:

| Step | Union      | Resulting Root                           | Rank[root]             |
|------|------------|------------------------------------------|------------------------|
| 1    | union(0,1) | root=0                                   | rank[0]=1 (equal case) |
| 2    | union(2,3) | root=2                                   | rank[2]=1              |
| 3    | union(0,2) | both rank=1 → attach 2 under 0           | rank[0]=2              |
| 4    | union(4,5) | equal rank                               | rank[4]=1              |
| 5    | union(0,4) | rank[0]=2 > rank[4]=1 → attach 4 under 0 | rank unchanged         |

Result: very shallow tree with minimal height.

---

## 🧩 6. Summary Table

| Term                                | Meaning                                | Notes                            |
|-------------------------------------|----------------------------------------|----------------------------------|
| **Rank**                            | Upper bound on tree height             | Used for balancing               |
| **Increased only when ranks equal** | Because only then height increases     | Prevents unnecessary rank growth |
| **Not actual height**               | After path compression, height shrinks | But we don’t recalc it           |
| **Effect**                          | Keeps trees shallow                    | `find()` becomes almost O(1)     |

---

Would you like me to visualize this (with trees and arrows showing how rank affects union)? It helps
a lot to see the height change intuitively.
