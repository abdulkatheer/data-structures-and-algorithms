package io.abdul.problem26;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/*
https://takeuforward.org/plus/dsa/contest/maths/longest-factor-chain
tag:math tag:priority_queue
 */
public class Solutions {

}

class Solution {

  public List<Integer> maxFactorsChain(List<Integer> nums, List<List<String>> queries) {
    Comparator<int[]> factorChainComparator =
        (a, b) -> {
          int lengthRes = Integer.compare(b[0], a[0]);
          if (lengthRes == 0) { // same
            return Integer.compare(a[1], b[1]);
          }
          return lengthRes;
        };
    PriorityQueue<int[]> pq = new PriorityQueue<>(factorChainComparator);

    for (int num : nums) {
      pq.add(new int[]{factorsChain(num), num});
    }

    List<Integer> output = new ArrayList<>();
    for (List<String> query : queries) {
      String operator = query.get(0);
      if ("+".equals(operator)) {
        int operand = Integer.parseInt(query.get(1));
        pq.add(new int[]{factorsChain(operand), operand});
      } else if ("-".equals(operator)) {
        if (!pq.isEmpty()) {
          pq.poll();
        }
      } else {
        if (!pq.isEmpty()) {
          output.add(pq.peek()[1]);
        }
      }
    }

    return output;
  }

  private int factorsChain(int num) {
    int factorsChain = 0;
    while (true) {
      int factors = 2; // 1 and itself
      int mid = num / 2;
      for (int i = 2; i <= mid; i++) {
        if (num % i == 0) {
          factors++;
        }
      }
      if (num == factors) { // gonna be an infinite loop
        break;
      }
      num = factors;
      factorsChain++;
    }
    return factorsChain;
  }
}
