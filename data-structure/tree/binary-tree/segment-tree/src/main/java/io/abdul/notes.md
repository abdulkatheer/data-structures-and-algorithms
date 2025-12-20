# Segment Trees: A Complete Guide

## The Problem That Motivates Segment Trees

Imagine you have an array of numbers and need to repeatedly:

1. **Query**: Find the sum (or min/max) of elements in a range [L, R]
2. **Update**: Change the value of an element

### Naive Approaches and Their Limitations

**Approach 1: Direct Array Access**

- Query: Loop through range → O(n) per query
- Update: Direct access → O(1) per update

**Approach 2: Prefix Sums**

- Query: `prefix[R] - prefix[L-1]` → O(1) per query
- Update: Rebuild entire prefix array → O(n) per update

**The Problem**: We need BOTH operations to be fast!

- If we have Q queries/updates, naive = O(Q·n) which is too slow for large inputs
- Competitive programming often has n, Q ≤ 10^5, making O(Q·n) = 10^10 operations infeasible

## Enter Segment Trees: The Intuition

### Core Insight: Divide and Conquer on Ranges

Think of it like a binary search tree, but for **ranges** instead of individual elements.

**The Key Idea**:

- Break the array into hierarchical segments
- Each node represents an interval and stores aggregate information (sum/min/max)
- Build a tree where each parent's range is the union of its children's ranges

### Visual Mental Model

For array: `[1, 3, 5, 7, 9, 11]`

```
Level 0:              [0,5]: 36
                         |
Level 1:        [0,2]: 9       [3,5]: 27
                   |               |
Level 2:      [0,1]: 4  [2]: 5  [3,4]: 16  [5]: 11
                 |                  |
Level 3:    [0]: 1  [1]: 3    [3]: 7  [4]: 9
```

**What this tree gives us**:

- Any range query can be answered by combining at most O(log n) nodes
- Any update affects at most O(log n) nodes (root to leaf path)

### Why It Works: The Decomposition Property

**Key Theorem**: Any range [L, R] can be decomposed into O(log n) disjoint segments from the tree.

**Intuition**:

- At each level, we either:
    - Take a complete segment (if it's fully inside [L, R])
    - Recurse into children (if it partially overlaps)
- Tree height = O(log n), and we explore at most 2 branches per level

## Building the Segment Tree

### Structure

**Array Representation**: Store tree in a 1D array (like heap)

- Node at index `i` has:
    - Left child: `2*i`
    - Right child: `2*i + 1`
- Array size: `4*n` (safe upper bound, actual need is ~2n-1)

### Construction Algorithm

**Recursive Build Process**:

```
build(node, start, end):
    if start == end:  # Leaf node
        tree[node] = arr[start]
    else:
        mid = (start + end) // 2
        build(2*node, start, mid)         # Build left subtree
        build(2*node+1, mid+1, end)       # Build right subtree
        tree[node] = tree[2*node] + tree[2*node+1]  # Merge
```

**Thought Process**:

1. **Base case**: Single element → leaf node stores that element
2. **Recursive case**: Split range in half, build both subtrees
3. **Combine**: Parent stores aggregate of children (sum/min/max)

### Implementation

```python
class SegmentTree:
    def __init__(self, arr):
        self.n = len(arr)
        self.arr = arr
        self.tree = [0] * (4 * self.n)  # Allocate space
        if self.n > 0:
            self._build(1, 0, self.n - 1)
    
    def _build(self, node, start, end):
        """Build segment tree recursively"""
        if start == end:
            # Leaf node - stores single array element
            self.tree[node] = self.arr[start]
        else:
            mid = (start + end) // 2
            left_child = 2 * node
            right_child = 2 * node + 1
            
            # Recursively build left and right subtrees
            self._build(left_child, start, mid)
            self._build(right_child, mid + 1, end)
            
            # Internal node stores sum of children
            self.tree[node] = self.tree[left_child] + self.tree[right_child]
```

### Trace Example

Array: `[1, 3, 5, 7]`

```
Build steps:
1. build(1, 0, 3) → Split into [0,1] and [2,3]
2.   build(2, 0, 1) → Split into [0,0] and [1,1]
3.     build(4, 0, 0) → tree[4] = 1
4.     build(5, 1, 1) → tree[5] = 3
5.   tree[2] = tree[4] + tree[5] = 1 + 3 = 4
6.   build(3, 2, 3) → Split into [2,2] and [3,3]
7.     build(6, 2, 2) → tree[6] = 5
8.     build(7, 3, 3) → tree[7] = 7
9.   tree[3] = tree[6] + tree[7] = 5 + 7 = 12
10. tree[1] = tree[2] + tree[3] = 4 + 12 = 16
```

**Final tree array**:

```
Index:  0  1   2  3   4  5  6  7
Value:  -  16  4  12  1  3  5  7
```

## Range Query

### Algorithm Logic

**Goal**: Find sum of arr[L...R]

**Strategy**:

- If current segment is completely inside [L, R] → return its value
- If completely outside → return 0 (neutral element)
- Otherwise → query both children and combine

```
query(node, start, end, L, R):
    if L > end or R < start:  # No overlap
        return 0
    if L <= start and end <= R:  # Complete overlap
        return tree[node]
    # Partial overlap - query both children
    mid = (start + end) // 2
    left_sum = query(2*node, start, mid, L, R)
    right_sum = query(2*node+1, mid+1, end, L, R)
    return left_sum + right_sum
```

### Implementation

```python
def query(self, L, R):
    """Query sum of arr[L...R]"""
    return self._query(1, 0, self.n - 1, L, R)

def _query(self, node, start, end, L, R):
    """Recursive query helper"""
    # No overlap
    if R < start or L > end:
        return 0
    
    # Complete overlap
    if L <= start and end <= R:
        return self.tree[node]
    
    # Partial overlap
    mid = (start + end) // 2
    left_sum = self._query(2 * node, start, mid, L, R)
    right_sum = self._query(2 * node + 1, mid + 1, end, L, R)
    
    return left_sum + right_sum
```

### Query Trace Example

Array: `[1, 3, 5, 7]`, Query: sum of [1, 3]

```
Tree structure:
        [0,3]:16
       /        \
   [0,1]:4    [2,3]:12
    /   \      /    \
[0]:1 [1]:3 [2]:5 [3]:7

Query(1, 0, 3, 1, 3):
  - [0,3] partially overlaps [1,3] → recurse
  
  Query(2, 0, 1, 1, 3):
    - [0,1] partially overlaps [1,3] → recurse
    
    Query(4, 0, 0, 1, 3):
      - [0,0] outside [1,3] → return 0
    
    Query(5, 1, 1, 1, 3):
      - [1,1] completely inside [1,3] → return 3 ✓
    
    Return: 0 + 3 = 3
  
  Query(3, 2, 3, 1, 3):
    - [2,3] completely inside [1,3] → return 12 ✓
  
  Return: 3 + 12 = 15
```

**Answer**: 15 (which is 3 + 5 + 7 ✓)

**Notice**: We only accessed 3 nodes from the tree, not all 4 array elements!

## Point Update

### Algorithm Logic

**Goal**: Update arr[idx] = val

**Strategy**:

- Find the leaf node corresponding to idx
- Update it
- Propagate changes up to root by recalculating parent values

```
update(node, start, end, idx, val):
    if start == end:  # Found the leaf
        tree[node] = val
    else:
        mid = (start + end) // 2
        if idx <= mid:
            update(2*node, start, mid, idx, val)
        else:
            update(2*node+1, mid+1, end, idx, val)
        # Recalculate current node
        tree[node] = tree[2*node] + tree[2*node+1]
```

### Implementation

```python
def update(self, idx, val):
    """Update arr[idx] = val"""
    self.arr[idx] = val
    self._update(1, 0, self.n - 1, idx, val)

def _update(self, node, start, end, idx, val):
    """Recursive update helper"""
    if start == end:
        # Leaf node - update it
        self.tree[node] = val
    else:
        mid = (start + end) // 2
        left_child = 2 * node
        right_child = 2 * node + 1
        
        # Recurse to appropriate child
        if idx <= mid:
            self._update(left_child, start, mid, idx, val)
        else:
            self._update(right_child, mid + 1, end, idx, val)
        
        # Update current node
        self.tree[node] = self.tree[left_child] + self.tree[right_child]
```

### Update Trace Example

Array: `[1, 3, 5, 7]`, Update: arr[1] = 10

```
Before:
        [0,3]:16
       /        \
   [0,1]:4    [2,3]:12
    /   \      /    \
[0]:1 [1]:3 [2]:5 [3]:7

Update(1, 0, 3, 1, 10):
  - [0,3] not a leaf, idx=1 <= mid=1 → go left
  
  Update(2, 0, 1, 1, 10):
    - [0,1] not a leaf, idx=1 > mid=0 → go right
    
    Update(5, 1, 1, 1, 10):
      - [1,1] is leaf → tree[5] = 10 ✓
    
    tree[2] = tree[4] + tree[5] = 1 + 10 = 11 ✓
  
  tree[1] = tree[2] + tree[3] = 11 + 12 = 23 ✓

After:
        [0,3]:23
       /        \
   [0,1]:11   [2,3]:12
    /   \      /    \
[0]:1 [1]:10 [2]:5 [3]:7
```

**Path taken**: Root → Left child → Right child (3 nodes updated)

## Time Complexity Analysis

### Build Operation

**Recurrence**: T(n) = 2·T(n/2) + O(1)

- We process each node once
- Total nodes ≈ 2n - 1
- **Time**: O(n)

**Space**: O(n) for the tree array

### Query Operation

**Analysis**:

- Tree height = ⌈log₂ n⌉
- At each level, we explore at most 2 branches
- We access at most 2·log n nodes

**Proof sketch**:

- At each level, the query range can intersect at most 2 segments
- Why? Because segments at same level are disjoint, and a range has only 2 endpoints
- Total levels = O(log n)

**Time**: O(log n) per query

### Update Operation

**Analysis**:

- Follow a path from root to leaf
- Path length = tree height = O(log n)
- Update each node on path

**Time**: O(log n) per update

### Summary Table

| Operation       | Time           | Space | Notes           |
|-----------------|----------------|-------|-----------------|
| Build           | O(n)           | O(n)  | One-time cost   |
| Query           | O(log n)       | O(1)  | Per operation   |
| Update          | O(log n)       | O(1)  | Per operation   |
| Total for Q ops | O(n + Q log n) | O(n)  | vs O(Q·n) naive |

## Complete Working Code

```python
class SegmentTree:
    """Segment Tree for range sum queries and point updates"""
    
    def __init__(self, arr):
        self.n = len(arr)
        self.arr = arr[:]
        self.tree = [0] * (4 * self.n)
        if self.n > 0:
            self._build(1, 0, self.n - 1)
    
    def _build(self, node, start, end):
        """Build segment tree recursively"""
        if start == end:
            self.tree[node] = self.arr[start]
        else:
            mid = (start + end) // 2
            self._build(2 * node, start, mid)
            self._build(2 * node + 1, mid + 1, end)
            self.tree[node] = self.tree[2 * node] + self.tree[2 * node + 1]
    
    def query(self, L, R):
        """Query sum of arr[L...R]"""
        return self._query(1, 0, self.n - 1, L, R)
    
    def _query(self, node, start, end, L, R):
        """Recursive query helper"""
        if R < start or L > end:
            return 0
        if L <= start and end <= R:
            return self.tree[node]
        mid = (start + end) // 2
        left_sum = self._query(2 * node, start, mid, L, R)
        right_sum = self._query(2 * node + 1, mid + 1, end, L, R)
        return left_sum + right_sum
    
    def update(self, idx, val):
        """Update arr[idx] = val"""
        self.arr[idx] = val
        self._update(1, 0, self.n - 1, idx, val)
    
    def _update(self, node, start, end, idx, val):
        """Recursive update helper"""
        if start == end:
            self.tree[node] = val
        else:
            mid = (start + end) // 2
            if idx <= mid:
                self._update(2 * node, start, mid, idx, val)
            else:
                self._update(2 * node + 1, mid + 1, end, idx, val)
            self.tree[node] = self.tree[2 * node] + self.tree[2 * node + 1]


# Example usage and testing
if __name__ == "__main__":
    arr = [1, 3, 5, 7, 9, 11]
    st = SegmentTree(arr)
    
    print("Original array:", arr)
    print("Sum of arr[1:4]:", st.query(1, 4))  # 3+5+7+9 = 24
    print("Sum of arr[0:2]:", st.query(0, 2))  # 1+3+5 = 9
    
    st.update(1, 10)
    print("\nAfter updating arr[1] = 10:")
    print("Sum of arr[1:4]:", st.query(1, 4))  # 10+5+7+9 = 31
    print("Sum of arr[0:2]:", st.query(0, 2))  # 1+10+5 = 16
```

## Key Takeaways

1. **When to use**: Need fast range queries + fast updates (both O(log n))
2. **Core principle**: Hierarchical range decomposition
3. **Tree structure**: Binary tree stored in array, node i has children 2i and 2i+1
4. **Three operations**: Build O(n), Query O(log n), Update O(log n)
5. **Versatility**: Works for sum, min, max, GCD, or any associative operation

Once you've absorbed this, we'll move to **Lazy Propagation** for handling range updates
efficiently!

---

# Lazy Propagation in Segment Trees: Complete Deep Dive

## The Problem That Motivates Lazy Propagation

### What We Have So Far

With basic segment tree:

- **Point update**: arr[idx] = val → O(log n) ✓
- **Range query**: sum/min/max of [L, R] → O(log n) ✓

### The New Challenge: Range Updates

**Problem**: Update all elements in range [L, R] by adding a value (or setting to a value)

**Example**: Add 5 to all elements in arr[2...6]

### Naive Approach with Basic Segment Tree

```python
def range_update_naive(L, R, val):
    for i in range(L, R + 1):
        update(i, arr[i] + val)  # Point update
```

**Time Complexity**: O((R - L + 1) · log n)

- In worst case: O(n log n) when updating entire array
- With Q queries: O(Q · n log n) - **way too slow!**

**For competitive programming**:

- n = 10^5, Q = 10^5
- Naive = 10^10 operations with log factor → TLE (Time Limit Exceeded)

## The Core Intuition Behind Lazy Propagation

### Key Insight: Defer Work Until Necessary

**Analogy**: Imagine you're a manager with a hierarchical organization:

- Traditional approach: Tell each employee individually about policy change
- Lazy approach: Tell department heads, they'll tell team leads when needed

**In segment trees**:

- Don't update all affected nodes immediately
- Store a "pending update" marker at higher-level nodes
- Push down (propagate) updates only when we actually need to look at child nodes

### Visual Mental Model

**Before range update** (add 10 to [2, 5]):

```
            [0,7]: sum=36
           /              \
      [0,3]: sum=16    [4,7]: sum=20
      /        \        /        \
  [0,1]: 9  [2,3]: 7  [4,5]: 11  [6,7]: 9
```

**After lazy update** (without propagating):

```
            [0,7]: sum=36
           /              \
      [0,3]: sum=16    [4,7]: sum=20
       lazy=10           lazy=10      ← Markers stored here
      /        \        /        \
  [0,1]: 9  [2,3]: 7  [4,5]: 11  [6,7]: 9
  (unchanged until accessed)
```

**Key observations**:

1. We only updated 2 nodes (O(log n)), not all 4 elements in range
2. The tree is temporarily "inconsistent" - sums don't reflect pending updates
3. We'll fix inconsistencies lazily when we query/update those nodes

## The Lazy Propagation Algorithm

### Data Structure Enhancement

Add a second array to store pending updates:

```python
class LazySegmentTree:
    def __init__(self, arr):
        self.n = len(arr)
        self.arr = arr[:]
        self.tree = [0] * (4 * self.n)      # Segment tree
        self.lazy = [0] * (4 * self.n)      # Lazy propagation array
        self._build(1, 0, self.n - 1)
```

**Meaning of lazy[node]**:

- lazy[node] = 0 → No pending updates
- lazy[node] = x → Add x to all elements in this node's range (when we get to them)

### The Three Core Operations

#### Operation 1: Push Down (Propagate)

**When**: Before accessing a node, check if it has pending updates

**What**: Apply pending update to current node and pass it to children

```python
def _push_down(self, node, start, end):
    """Apply lazy update to current node and propagate to children"""
    if self.lazy[node] == 0:
        return  # No pending updates
    
    # Apply pending update to current node
    range_length = end - start + 1
    self.tree[node] += self.lazy[node] * range_length
    
    # If not a leaf, propagate to children
    if start != end:
        self.lazy[2 * node] += self.lazy[node]
        self.lazy[2 * node + 1] += self.lazy[node]
    
    # Clear lazy value for current node
    self.lazy[node] = 0
```

**Thought process**:

1. If lazy[node] = 10 and range is [2, 5] (length 4):

- Add 10 × 4 = 40 to tree[node] (sum increases by 40)

2. Pass the "add 10" instruction to both children
3. Mark current node as "up to date" (lazy[node] = 0)

#### Operation 2: Range Update

**Goal**: Add `val` to all elements in [L, R]

**Strategy**: Similar to query, but mark nodes instead of collecting values

```python
def range_update(self, L, R, val):
    """Add val to all elements in [L, R]"""
    self._range_update(1, 0, self.n - 1, L, R, val)

def _range_update(self, node, start, end, L, R, val):
    """Recursive range update helper"""
    # ALWAYS push down first!
    self._push_down(node, start, end)
    
    # Case 1: No overlap
    if R < start or L > end:
        return
    
    # Case 2: Complete overlap - mark this node as lazy
    if L <= start and end <= R:
        self.lazy[node] += val
        self._push_down(node, start, end)  # Apply immediately to update tree[node]
        return
    
    # Case 3: Partial overlap - recurse to children
    mid = (start + end) // 2
    self._range_update(2 * node, start, mid, L, R, val)
    self._range_update(2 * node + 1, mid + 1, end, L, R, val)
    
    # Update current node from children
    self.tree[node] = self.tree[2 * node] + self.tree[2 * node + 1]
```

**Key points**:

- Push down BEFORE doing anything (ensure node is up-to-date)
- Complete overlap: mark lazy and return (don't recurse!)
- Partial overlap: recurse to both children
- After recursion: recalculate parent from children

#### Operation 3: Range Query (Modified)

**Changes from basic segment tree**: Push down before accessing any node

```python
def range_query(self, L, R):
    """Query sum of [L, R]"""
    return self._range_query(1, 0, self.n - 1, L, R)

def _range_query(self, node, start, end, L, R):
    """Recursive range query with lazy propagation"""
    # ALWAYS push down first!
    self._push_down(node, start, end)
    
    # Case 1: No overlap
    if R < start or L > end:
        return 0
    
    # Case 2: Complete overlap
    if L <= start and end <= R:
        return self.tree[node]
    
    # Case 3: Partial overlap
    mid = (start + end) // 2
    left_sum = self._range_query(2 * node, start, mid, L, R)
    right_sum = self._range_query(2 * node + 1, mid + 1, end, L, R)
    return left_sum + right_sum
```

**Only change**: Added `_push_down()` at the beginning

## Complete Working Implementation

```python
class LazySegmentTree:
    """Segment Tree with Lazy Propagation for range updates and range queries"""
    
    def __init__(self, arr):
        self.n = len(arr)
        self.arr = arr[:]
        self.tree = [0] * (4 * self.n)
        self.lazy = [0] * (4 * self.n)
        if self.n > 0:
            self._build(1, 0, self.n - 1)
    
    def _build(self, node, start, end):
        """Build segment tree recursively"""
        if start == end:
            self.tree[node] = self.arr[start]
        else:
            mid = (start + end) // 2
            self._build(2 * node, start, mid)
            self._build(2 * node + 1, mid + 1, end)
            self.tree[node] = self.tree[2 * node] + self.tree[2 * node + 1]
    
    def _push_down(self, node, start, end):
        """Apply lazy update to current node and propagate to children"""
        if self.lazy[node] == 0:
            return
        
        # Apply pending update to current node
        range_length = end - start + 1
        self.tree[node] += self.lazy[node] * range_length
        
        # If not a leaf, propagate to children
        if start != end:
            self.lazy[2 * node] += self.lazy[node]
            self.lazy[2 * node + 1] += self.lazy[node]
        
        # Clear lazy value
        self.lazy[node] = 0
    
    def range_update(self, L, R, val):
        """Add val to all elements in [L, R]"""
        self._range_update(1, 0, self.n - 1, L, R, val)
    
    def _range_update(self, node, start, end, L, R, val):
        """Recursive range update helper"""
        # Push down any pending updates
        self._push_down(node, start, end)
        
        # No overlap
        if R < start or L > end:
            return
        
        # Complete overlap
        if L <= start and end <= R:
            self.lazy[node] += val
            self._push_down(node, start, end)
            return
        
        # Partial overlap
        mid = (start + end) // 2
        self._range_update(2 * node, start, mid, L, R, val)
        self._range_update(2 * node + 1, mid + 1, end, L, R, val)
        
        # Update current node
        self.tree[node] = self.tree[2 * node] + self.tree[2 * node + 1]
    
    def range_query(self, L, R):
        """Query sum of [L, R]"""
        return self._range_query(1, 0, self.n - 1, L, R)
    
    def _range_query(self, node, start, end, L, R):
        """Recursive range query helper"""
        # Push down any pending updates
        self._push_down(node, start, end)
        
        # No overlap
        if R < start or L > end:
            return 0
        
        # Complete overlap
        if L <= start and end <= R:
            return self.tree[node]
        
        # Partial overlap
        mid = (start + end) // 2
        left_sum = self._range_query(2 * node, start, mid, L, R)
        right_sum = self._range_query(2 * node + 1, mid + 1, end, L, R)
        return left_sum + right_sum
    
    def point_query(self, idx):
        """Query value at single index"""
        return self.range_query(idx, idx)
```

## Detailed Trace Example

Let's trace through operations step-by-step.

**Initial array**: [1, 3, 5, 7, 9, 11, 13, 15]

### Step 1: Build Tree

```
Tree after build:
                    [0,7]: 64
                   /          \
            [0,3]: 16          [4,7]: 48
           /        \          /        \
      [0,1]: 4   [2,3]: 12  [4,5]: 20  [6,7]: 28
      /    \     /     \    /     \    /     \
   [0]:1 [1]:3 [2]:5 [3]:7 [4]:9 [5]:11 [6]:13 [7]:15

All lazy values = 0
```

### Step 2: Range Update - Add 10 to [2, 5]

**Call**: `range_update(2, 5, 10)`

```
Execution trace:

_range_update(node=1, start=0, end=7, L=2, R=5, val=10):
  push_down(1) → lazy[1]=0, nothing to do
  [0,7] partially overlaps [2,5] → recurse
  
  _range_update(node=2, start=0, end=3, L=2, R=5, val=10):
    push_down(2) → lazy[2]=0, nothing to do
    [0,3] partially overlaps [2,5] → recurse
    
    _range_update(node=4, start=0, end=1, L=2, R=5, val=10):
      push_down(4) → lazy[4]=0, nothing to do
      [0,1] no overlap with [2,5] → return
    
    _range_update(node=5, start=2, end=3, L=2, R=5, val=10):
      push_down(5) → lazy[5]=0, nothing to do
      [2,3] completely inside [2,5] → mark lazy!
      lazy[5] += 10 → lazy[5] = 10
      push_down(5) immediately:
        tree[5] += 10 * 2 = 12 + 20 = 32 ✓
        lazy[10] += 10, lazy[11] += 10 (children marked)
        lazy[5] = 0
      return
    
    tree[2] = tree[4] + tree[5] = 4 + 32 = 36 ✓
  
  _range_update(node=3, start=4, end=7, L=2, R=5, val=10):
    push_down(3) → lazy[3]=0, nothing to do
    [4,7] partially overlaps [2,5] → recurse
    
    _range_update(node=6, start=4, end=5, L=2, R=5, val=10):
      push_down(6) → lazy[6]=0, nothing to do
      [4,5] completely inside [2,5] → mark lazy!
      lazy[6] += 10 → lazy[6] = 10
      push_down(6) immediately:
        tree[6] += 10 * 2 = 20 + 20 = 40 ✓
        lazy[12] += 10, lazy[13] += 10
        lazy[6] = 0
      return
    
    _range_update(node=7, start=6, end=7, L=2, R=5, val=10):
      push_down(7) → lazy[7]=0, nothing to do
      [6,7] no overlap with [2,5] → return
    
    tree[3] = tree[6] + tree[7] = 40 + 28 = 68 ✓
  
  tree[1] = tree[2] + tree[3] = 36 + 68 = 104 ✓
```

**State after update**:

```
                    [0,7]: 104 ← Updated
                   /            \
            [0,3]: 36            [4,7]: 68 ← Updated
           /        \            /        \
      [0,1]: 4   [2,3]: 32    [4,5]: 40  [6,7]: 28
                 lazy=0        lazy=0
      /    \     /     \      /     \    /     \
   [0]:1 [1]:3 [2]:5 [3]:7  [4]:9 [5]:11 [6]:13 [7]:15
            lazy=10 lazy=10  lazy=10 lazy=10 ← Marked but not applied
```

**Key observation**:

- Leaf nodes still show old values!
- They have lazy markers indicating pending +10
- We updated only O(log n) nodes, not all 4 elements

### Step 3: Query - Sum of [3, 6]

**Call**: `range_query(3, 6)`

```
Execution trace:

_range_query(node=1, start=0, end=7, L=3, R=6):
  push_down(1) → lazy[1]=0, nothing to do
  [0,7] partially overlaps [3,6] → recurse
  
  _range_query(node=2, start=0, end=3, L=3, R=6):
    push_down(2) → lazy[2]=0, nothing to do
    [0,3] partially overlaps [3,6] → recurse
    
    _range_query(node=4, start=0, end=1, L=3, R=6):
      push_down(4) → lazy[4]=0, nothing to do
      [0,1] no overlap → return 0
    
    _range_query(node=5, start=2, end=3, L=3, R=6):
      push_down(5) → lazy[5]=0, nothing to do (already pushed in update)
      [2,3] partially overlaps [3,6] → recurse
      
      _range_query(node=10, start=2, end=2, L=3, R=6):
        push_down(10) → lazy[10]=10!
          tree[10] += 10 * 1 = 5 + 10 = 15 ✓
          lazy[10] = 0 (it's a leaf, no children)
        [2,2] no overlap with [3,6] → return 0
      
      _range_query(node=11, start=3, end=3, L=3, R=6):
        push_down(11) → lazy[11]=10!
          tree[11] += 10 * 1 = 7 + 10 = 17 ✓
          lazy[11] = 0
        [3,3] completely inside [3,6] → return 17 ✓
      
      return 0 + 17 = 17
    
    return 0 + 17 = 17
  
  _range_query(node=3, start=4, end=7, L=3, R=6):
    push_down(3) → lazy[3]=0, nothing to do
    [4,7] partially overlaps [3,6] → recurse
    
    _range_query(node=6, start=4, end=5, L=3, R=6):
      push_down(6) → lazy[6]=0, nothing to do
      [4,5] completely inside [3,6] → return 40 ✓
    
    _range_query(node=7, start=6, end=7, L=3, R=6):
      push_down(7) → lazy[7]=0, nothing to do
      [6,7] partially overlaps [3,6] → recurse
      
      _range_query(node=14, start=6, end=6, L=3, R=6):
        push_down(14) → lazy[14]=0, nothing to do
        [6,6] completely inside [3,6] → return 13 ✓
      
      _range_query(node=15, start=7, end=7, L=3, R=6):
        push_down(15) → lazy[15]=0, nothing to do
        [7,7] no overlap → return 0
      
      return 13 + 0 = 13
    
    return 40 + 13 = 53
  
  return 17 + 53 = 70 ✓
```

**Verification**:

- Original arr[3:7] = [7, 9, 11, 13]
- After adding 10 to [2,5]: arr[3:7] = [17, 19, 21, 13]
- Sum = 17 + 19 + 21 + 13 = 70 ✓

**What happened**:

- Lazy values were pushed down on-demand
- Some leaf nodes got updated (nodes 10, 11)
- Others stayed lazy (nodes 12, 13 - not accessed)

## Time Complexity Analysis

### Range Update

**Analysis**: Same as range query

- Visit O(log n) nodes
- Each node: O(1) work (push_down + marking)
- **Time**: O(log n) ✓

**Improvement over naive**: O(log n) vs O((R-L+1) · log n)

### Range Query

**Analysis**:

- Still O(log n) nodes visited
- Each node: O(1) work (push_down is O(1))
- **Time**: O(log n) ✓

**No degradation**: Lazy propagation doesn't slow down queries!

### Space Complexity

**Additional space**: O(n) for lazy array

- Total space: O(n) for tree + O(n) for lazy = O(n)

### Amortized Analysis

**Key insight**: Each lazy value is pushed down at most once

- When we mark a node lazy: O(1)
- When we push down: O(1) and clear the mark
- Total push-downs over all operations: O(Q · log n) where Q = # operations

## Common Variations

### Variation 1: Range Set (Instead of Add)

**Problem**: Set all elements in [L, R] to value `val`

**Change**: Modify push_down logic

```python
def _push_down_set(self, node, start, end):
    if self.lazy[node] is None:
        return
    
    # Set all elements in range to lazy[node]
    range_length = end - start + 1
    self.tree[node] = self.lazy[node] * range_length
    
    if start != end:
        self.lazy[2 * node] = self.lazy[node]
        self.lazy[2 * node + 1] = self.lazy[node]
    
    self.lazy[node] = None  # Use None as "no pending update"
```

**Key difference**:

- Assignment operation (not additive)
- Later lazy updates override earlier ones
- Use None or special sentinel value to indicate "no update"

### Variation 2: Multiple Lazy Operations

**Problem**: Support both add and multiply operations

**Solution**: Store two lazy values per node

```python
self.lazy_mult = [1] * (4 * self.n)  # Multiply first
self.lazy_add = [0] * (4 * self.n)   # Then add

def _push_down(self, node, start, end):
    # Apply: new_val = old_val * mult + add
    range_len = end - start + 1
    self.tree[node] = self.tree[node] * self.lazy_mult[node] + self.lazy_add[node] * range_len
    
    if start != end:
        # Push to children: compose operations
        self.lazy_mult[2*node] *= self.lazy_mult[node]
        self.lazy_add[2*node] = self.lazy_add[2*node] * self.lazy_mult[node] + self.lazy_add[node]
        # Same for right child
    
    self.lazy_mult[node] = 1
    self.lazy_add[node] = 0
```

### Variation 3: Range Min/Max with Updates

**For range minimum** queries with range add updates:

```python
def _push_down_min(self, node, start, end):
    if self.lazy[node] == 0:
        return
    
    # Add to minimum
    self.tree[node] += self.lazy[node]
    
    if start != end:
        self.lazy[2 * node] += self.lazy[node]
        self.lazy[2 * node + 1] += self.lazy[node]
    
    self.lazy[node] = 0
```

**Key point**: For min/max, adding same value to all elements just shifts the min/max by that value

## Common Pitfalls and Debugging Tips

### Pitfall 1: Forgetting to Push Down

```python
# WRONG
def _range_query(self, node, start, end, L, R):
    # Missing push_down here!
    if L <= start and end <= R:
        return self.tree[node]  # Might return stale value!
```

**Fix**: ALWAYS call push_down at the beginning of query and update functions

### Pitfall 2: Pushing Down After Checking Conditions

```python
# WRONG
def _range_update(self, node, start, end, L, R, val):
    if R < start or L > end:
        return
    self._push_down(node, start, end)  # Too late!
```

**Fix**: Push down BEFORE checking any conditions (except the lazy check itself)

### Pitfall 3: Not Updating Parent After Range Update

```python
# WRONG
def _range_update(self, node, start, end, L, R, val):
    self._push_down(node, start, end)
    # ... recursive calls ...
    # Missing: tree[node] = tree[2*node] + tree[2*node+1]
```

**Fix**: Always recalculate parent value after recursing to children

### Pitfall 4: Range Set vs Range Add Confusion

```python
# WRONG for range set
self.lazy[2 * node] += self.lazy[node]  # Should be = for set!
```

**Fix**: Use assignment (=) for set operations, addition (+=) for add operations

## Practice Problems

To master lazy propagation:

1. **CSES - Range Update Queries**: Basic range add + point query
2. **CSES - Range Updates and Sums**: Range add + range sum
3. **Codeforces - Sereja and Brackets**: Counting valid bracket subsequences
4. **SPOJ - HORRIBLE**: Range add + range sum (classic)
5. **Codeforces - Xenia and Bit Operations**: Range set + custom merge function

## Summary

### Key Concepts

1. **Lazy propagation**: Defer updates until necessary
2. **Lazy array**: Stores pending updates at each node
3. **Push down**: Apply and propagate lazy values
4. **Three operations**: Build O(n), Range Update O(log n), Range Query O(log n)

### When to Use

- Need both range updates AND range queries to be fast
- Point updates suffice → use basic segment tree
- Only range queries → use prefix sums or sparse table

### The Algorithm in One Sentence

**Mark high-level nodes with pending updates instead of updating all affected elements, and
propagate these updates down the tree only when we need to access specific nodes.**

This gives us O(log n) for both range updates and range queries - the best of both worlds!