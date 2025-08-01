The fundamental solutions to two sum problem are:

1) HashMap - Amortized constant time lookup, O(n) space, suitable if collection is not sorted
2) Two pointer approach - Suited if sorted, requires no additional space

BST is sorted. But we can't iterate it like a list. 
1) we can use HashMap, traverse the BST and find the pair
2) Convert BST to list, which will be sorted and use two pointer approach
3) Use BSTIterator to traverse from smallest to largest and largest to smallest and use two pointer approach