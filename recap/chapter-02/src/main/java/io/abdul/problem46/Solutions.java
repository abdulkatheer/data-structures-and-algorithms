package io.abdul.problem46;

import java.util.ArrayList;
import java.util.List;

// https://leetcode.com/problems/pascals-triangle/
// tag:math tag:recursion tag:dynamic_programming
public class Solutions {

}

class Solution {

  public List<List<Integer>> generate(int numRows) {
    List<List<Integer>> result = new ArrayList<>(numRows);
    generate(numRows, result);
    return result;
  }

  private void generate(int numRows, List<List<Integer>> result) {
    if (numRows == 1) {
      result.add(List.of(1));
      return;
    }

    generate(numRows - 1, result);
    List<Integer> prevRow = result.get(result.size() - 1);
    List<Integer> row = new ArrayList<>(numRows);
    row.add(1);
    for (int i = 1; i < prevRow.size(); i++) {
      row.add(prevRow.get(i - 1) + prevRow.get(i));
    }
    row.add(1);

    result.add(row);
  }
}

class Solution2 {
  public List<List<Integer>> generate(int numRows) {
    List<List<Integer>> result = new ArrayList<>(numRows);

    // Known solution
    result.add(List.of(1));

    for (int i = 2; i <= numRows; i++) {
      List<Integer> prevRow = result.get(i-2);
      List<Integer> row = new ArrayList<>(i);
      row.add(1);
      for (int j = 1; j < prevRow.size(); j++) {
        row.add(prevRow.get(j-1) + prevRow.get(j));
      }
      row.add(1);
      result.add(row);
    }

    return result;
  }
}