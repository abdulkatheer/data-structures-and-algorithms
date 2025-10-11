Ah! Perfect — let’s make an **ultra-detailed, comprehensive set of Floyd–Warshall notes** including
**all subtle points**, with emphasis on why **k must be outermost**, how the recursion splits paths,
the handling of `-1`, loop ordering, memoization, tabulation, and examples. I’ll structure it for
later review.

---

# **Floyd–Warshall Algorithm: Full Detailed Notes**

---

## **1. Problem Definition**

**Goal:** Compute the **shortest path between all pairs of vertices** in a weighted graph.

**Graph Characteristics:**

* Directed or undirected
* Positive or negative weights (no negative cycles)
* Representation: adjacency matrix

    * `graph[i][j] = weight of edge i→j`
    * `graph[i][j] = -1` if no direct edge exists

**Example Graph:**

```
Vertices: 0,1,2,3
Edges:
0→1 = 5, 0→3 = 10
1→2 = 3
2→3 = 1
```

---

## **2. Recursive Formulation**

### **2.1 Definition**

$$
dist(i, j, k) = \text{shortest path from i → j using only vertices } \{0..k\} \text{ as intermediates}
$$

### **2.2 Recurrence**

$$
dist(i, j, k) =
\begin{cases}
graph[i][j], & k = 0 \\
\min(dist(i,j,k-1),\ dist(i,k,k-1) + dist(k,j,k-1)), & k > 0
\end{cases}
$$

**Explanation:**

* Base case (`k=0`) → only direct edge allowed
* Recursive case (`k>0`) → two possibilities:

    1. **Exclude k** → `dist(i,j,k-1)`
    2. **Include k** → `dist(i,k,k-1) + dist(k,j,k-1)`

---

### **2.3 Intuition**

* `k` represents **new allowed intermediate vertex**.
* Combining subpaths through `k`:

  ```
  i → ... → k → ... → j
  ```
* Both `dist(i,k,k-1)` and `dist(k,j,k-1)` use **vertices ≤ k-1**, so union gives all vertices ≤ k.
* Allowed vertices are a **permission set**, not “count of nodes” → no double counting.

---

### **2.4 Example**

Compute `dist(0,3,3)`:

```
dist(0,3,3) = min(dist(0,3,2), dist(0,2,2) + dist(2,3,2))
```

Subpaths:

* `dist(0,3,2) = 10` (direct)
* `dist(0,2,2) = 8` (via 0→1→2)
* `dist(2,3,2) = 1`

Result: `min(10, 8+1) = 9` → shortest path: **0→1→2→3**

---

## **3. Handling “No Path” with `-1`**

* Represent “no path” by `-1`.
* When adding subpaths:

```java
if(left !=-1&&right !=-1)
throughK =left +right;
```

* Comparing paths:

```java
if(withoutK ==-1&&throughK ==-1)return-1;
    else if(withoutK ==-1)return throughK;
else if(throughK ==-1)return withoutK;
else return Math.

min(withoutK, throughK);
```

* Prevents invalid sums like `-1 + 5` → ensures correctness.

---

## **4. Memoized Recursion (Top-Down DP)**

* Use 3D array `dp[i][j][k]` to store results.
* Before computing `floyd(i,j,k)`, check if result exists.
* Avoids recomputation → **O(V³)** time.
* Space: **O(V³)**.

---

## **5. Iterative Tabulation (Bottom-Up DP)**

### **5.1 Main Idea**

* Outer loop: `k = 0..n-1` → allow vertex `k` as intermediate
* Inner loops: all pairs `(i,j)` updated using `k`:

```java
for(int k = 0;
k<n;k++)
    for(
int i = 0;
i<n;i++)
    for(
int j = 0;
j<n;j++)
    if(dist[i][k]!=-1&&dist[k][j]!=-1){
int newDist = dist[i][k] + dist[k][j];
                if(dist[i][j]==-1||newDist<dist[i][j])
dist[i][j]=newDist;
            }
```

---

### **5.2 Why `k` Must Be Outermost**

**Key Reason:** Floyd–Warshall relies on **subproblem correctness**.

* `dist[i][k]` and `dist[k][j]` must already represent **shortest paths using vertices ≤ k-1**.
* If `k` is inner loop:

    * `dist[i][k]` and `dist[k][j]` may **not have considered intermediate vertex k yet**.
    * Leads to missing shorter paths → incorrect result.

**Analogy:** `k` is a “new allowed layover city”. Only after allowing it for all `(i,j)` pairs can
it be safely used in combined paths.

---

### **5.3 Correct Order**

| Loop            | Meaning                               |
|-----------------|---------------------------------------|
| `k` (outermost) | currently allowed intermediate vertex |
| `i` (middle)    | source vertex                         |
| `j` (innermost) | destination vertex                    |

---

## **6. Path Splitting via `k`**

* When including `k`, path splits into:

```
i → ... → k → ... → j
```

* Left: `i → k` using vertices ≤ k-1
* Right: `k → j` using vertices ≤ k-1
* Union gives all vertices ≤ k
* This guarantees **no vertex is double-counted**.

---

## **7. Complexity**

| Version    | Time   | Space      | Notes                 |
|------------|--------|------------|-----------------------|
| Recursive  | O(2^V) | O(V) stack | Only for small graphs |
| Memoized   | O(V³)  | O(V³)      | Avoid recomputation   |
| Tabulation | O(V³)  | O(V²)      | Standard, efficient   |

---

## **8. Example Updates**

Graph:

```
0→1=5, 0→3=10
1→2=3
2→3=1
```

**Updates per k:**

| k | Updated paths            | Notes           |
|---|--------------------------|-----------------|
| 0 | No change (direct edges) | Base case       |
| 1 | dist\[0]\[2]=8           | 0→1→2           |
| 2 | dist\[0]\[3]=9           | 0→1→2→3         |
| 3 | No change                | Already optimal |

Final `dist[][]` table:

| i\j | 0  | 1  | 2  | 3 |
|-----|----|----|----|---|
| 0   | 0  | 5  | 8  | 9 |
| 1   | -1 | 0  | 3  | 4 |
| 2   | -1 | -1 | 0  | 1 |
| 3   | -1 | -1 | -1 | 0 |

---

## **9. Key Takeaways**

1. Floyd–Warshall **gradually allows more vertices as intermediates**.
2. Recursive formula: two options (exclude or include k).
3. Memoization avoids recomputation; tabulation is standard.
4. **Handling `-1`** or ∞ is critical before addition.
5. **Loop order matters**: `k` outermost to ensure correctness.
6. All-pairs shortest paths computed; path reconstruction requires auxiliary table.
7. Time complexity: O(V³), space: O(V²) in tabulation.

---

If you want, I can now also make a **single-page “visual cheat sheet”** showing:

* Recursive tree → memoized table → DP table updates
* How `k` splits paths
* Why `k` outermost

It’s perfect for **revision at a glance**.

Do you want me to make that?
