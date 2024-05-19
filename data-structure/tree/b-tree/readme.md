### Need

- Binary Trees (BST, AVL and Red Black) performance will become worse when number of elements increases as height grows
  bigger and bigger.
- As it takes log<sub>2</sub>(n) time for insert, delete and search.
- m-way tree reduces the height of the tree by storing multiple elements per node and more children.
- B-Tree is an m-way tree with rules
- B-Tree can be defined in two ways
    - Order of the tree
    - Minimum degree of the tree
- Order of tree
    - If order of tree is m (ex: 5)
    - It can have max m-1 elements in a node (5-1 = 4)
    - It should at least have round_ceiling(m/2) elements in a node (except root node) (5/2 = 2)
    - Each node should have (number of elements in it + 1) children
- Minimum degree of tree
    - If minimum degree of tree is t (ex: t = 3)
    - It can have max 2t - 1 elements in each node (6 - 1 = 5)
    - It should at lease have t - 1 elements in each node (except root node) (3 - 1 = 2)
- Order method involves division and rounding
- Minimum degree method involves multiplication and subtraction
- The minimum height of the B-Tree that can exist with n number of nodes and m is the maximum number of children of
  a node can have is:
    - h<sub>min</sub> =ceil of [log<sub>m</sub> (n + 1)] - 1
- The maximum height of the B-Tree that can exist with n number of nodes and t is the minimum number of children
  that a non-root node can have is:
    - h<sub>max</sub> =floor of [log<sub>t</sub>  (n + 1)/2]
    - t = ceil of [m/2] as the rule is that each node should have at least half of the order
- Elements in each Node should be sorted in ascending order
- Two nodes with minimum elements (t - 1) should form a node with less than maximum elements (2t - 1)
- If a non-lead node has n elements in it, it should have n+1 children with it
- All leaf nodes should be at same level
- Insertion always happens in the lead nodes

> 2-3 tree is a B-Tree with order 3</br>
> Nodes with two children are called 2-nodes </br>
> Nodes with three children are called 3-nodes </br>
> Each node can either be leaf, 2 node, or 3 node.

> 2-3-4 tree is a B-Tree with order 4</br>
> Nodes with two children are called 2-nodes </br>
> Nodes with three children are called 3-nodes </br>
> Nodes with four children are called 4-nodes </br>

### Insertion

1) Initialize x as root.
2) While x is not leaf, do following
    1) Find the child of x that is going to be traversed next. Let the child be y.
    2) If y is not full, change x to point to y.
    3) If y is full, split it and change x to point to one of the two parts of y. If k is smaller than mid key in y,
       then set x as the first part of y. Else second part of y. When we split y, we move a key from y to its parent x.
3) The loop in step 2 stops when x is leaf. x must have space for 1 extra key as we have been splitting all nodes in
   advance. So simply insert k to x.

Note that the algorithm follows the Cormen book. It is actually a *proactive insertion* algorithm where before going down
to a node, we split it if it is full. The advantage of splitting before is, we never traverse a node twice. If we don’t
split a node before going down to it and split it only if a new key is inserted (reactive), we may end up traversing all
nodes again from leaf to root. This happens in cases when all nodes on the path from the root to leaf are full. So when
we come to the leaf node, we split it and move a key up. Moving a key up will cause a split in parent node (because the
parent was already full). This cascading effect never happens in this proactive insertion algorithm. There is a
disadvantage of this proactive insertion though, we may do unnecessary splits. 

