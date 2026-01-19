package io.abdul.problem49;

// https://leetcode.com/problems/elimination-game/
// tag:math tag:recursion
public class Solutions {

}

class Solution {

  public int lastRemaining(int n) {
    return lastRemaining(1, 1, n, true);
  }

  /*
  Idea is keep track of head!
  When head changes?
  when we remove from l to r, head changes
  when we remove from r to l and odd number of elements exists, head changes

  So we need to know how much a head to be increased and also how many numbers left?

  How many numbers left after each iteration?
  n / 2

  How much increment to be made?
  l to r -> increments by 1 (1 to 2)
  r to l -> increments by 2 (2 to 4)
  l to r -> increments by 4 (4 to 8)

  some increments may be skipped, but the value of increment keeps on going
  For ex, heap may skip updating from 2 to 4, but it may get updated to 2 to 6

  n = 20
  head = 1, count = 20, multiple = 1
  head = 2, count = 10, multiple = 2
  head = 2, count = 5, multiple = 4 -> skips update as count was even
  head = 6, count = 2, multiple = 8
  head = 6, count = 1, multiple = 16
  stop
  */
  private int lastRemaining(int head, int multiple, int count, boolean leftToRight) {
    if (count == 1) {
      return head;
    }

    int increment = (leftToRight || count % 2 == 1) ? multiple : 0;

    return lastRemaining(head + increment, multiple * 2, count / 2, !leftToRight);
  }
}
