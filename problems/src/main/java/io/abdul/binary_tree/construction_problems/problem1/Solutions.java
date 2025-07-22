package io.abdul.binary_tree.construction_problems.problem1;

public class Solutions {

}

class Solution {

  public boolean uniqueBinaryTree(int a, int b) {
    // Both are the same OR preorder/postorder alone
    return !(a == b || (a == 1 && b == 3) || (a == 3 && b == 1));
  }
}

class Solution2 {

  public boolean uniqueBinaryTree(int a, int b) {
    // Both are the same
    if (a == b) {
      return false;
    }

    // At least one of them is inorder
    if (a == 2 || b == 2) {
      return true;
    }

    // Both are pre/postorder
    return false;
  }
}