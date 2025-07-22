This is a crucial concept in builiding the Binary Tree back from a traversal data.

- Given a traversal data, can you distinguish who is on left, root and right?
  With a single traversal, we can't assert.
  But with two traversal, we can assert our evaluation.
- Can preorder, postorder alone (without inorder) help build the tree without ambiguity?
  No. Bcz preorder and postorder doesn't give a clear distinction on what is coming on left and
  right. So it creates multiple possibilities.
- Can inorder and preorder/postorder help build the tree without ambiguity?
  Yes. Bcz inorder is the only traversal which clearly distinguishes left, root and right nodes. And
  along with one another traversal, we can assert our decision to build the tree.
- Can 2 same traversal help build the tree without ambiguity?
  No. We need two traversals to assert the decision taken in one traversal. If both are same,
  assertion doesn't help as it'll always return true.