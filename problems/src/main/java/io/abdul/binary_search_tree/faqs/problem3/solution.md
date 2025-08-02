- Inorder traversal of a BST gives sorted data

Brute:

- Traverse inorder and get the data.
- Sort it
- Traverse again and update the node values with sorted data
- We can't find exact node which is being swapped, we just update all

Optimal:

- Two cases
- Case 1: Swapping the non-adjacent nodes. Ex: 3 25 7 8 10 15 20 5
- Case 2: Swapping the adjacent nodes. Ex: 3 5 8 7 10 15 20 25

Case 1 -> We find two violations. 25 > 7, 20 > 5
Here 25 is first, 5 is last. We need to swap first and last

Case 2 -> We find only one violation. 8 > 7
Here 8 is first, 7 is middle
We keep middle to handle a single-violation case