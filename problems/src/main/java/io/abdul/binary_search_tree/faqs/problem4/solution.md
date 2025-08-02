Brute:

- At each node, we can find isValidBST and length
- n * n time

Optimal

- isValidBST itself can be modified to give max length
- isValidBST top-down approach stops as soon as the node is not making the root BST. It doesn't
  validate all subtrees independently
- isValidBST bottom-up approach checks from the smallest possible subtree and tries to build larger
  ones. This way we'll be able to find the max it built.