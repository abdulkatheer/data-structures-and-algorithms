1) Why a single traversal data can't help rebuild the tree?

- We either know root or leaf data. But we need both.
- For ex: with preorder, we know which is root. But we don't know which is left and right subtree of
  it.
- with inorder, we can't find anything. first node might be root or left.
- with postorder, we know the root, but we don't know who is its left and right subtree

2) Why preorder and postorder can't help?

- preorder and postorder both gives same information. that is the root!
- Neither of them tells who is left/right subtree

3) Why inorder with pre/postorder helps?

- pre/postorder tells who is root
- with that information, inorder tells who is left and right subtree of the root