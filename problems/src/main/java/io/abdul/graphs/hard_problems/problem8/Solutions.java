package io.abdul.graphs.hard_problems.problem8;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

/*
It's a backtracking type of question, but without Stack or recursion.

Instead of adding the node to the queue, we create a new List with the copy of parent elements and the new node.
This way, we can keep track of all the nodes visited in that path at each level.

Second thing, at a level, there can be multiple nodes.
Multiple node may lead to a same node. Just assume the last step, multiple nodes in the queue may lead to the target node.
So if we remove target node after first visit, other paths will be ignored.
So remove at the end of level processing.

Why is it not needed at next levels?
If visited node comes in further levels, it'll only give longer paths.
 */
public class Solutions {

  public static void main(String[] args) {
    Solution sol = new Solution();

    // Example 1
    List<String> wordList1 = Arrays.asList("des", "der", "dfr", "dgt", "dfs");
    List<List<String>> result1 = sol.findSequences("der", "dfs", wordList1);
    List<List<String>> expected1 = Arrays.asList(
        Arrays.asList("der", "dfr", "dfs"),
        Arrays.asList("der", "des", "dfs")
    );
    assertTrue(containsAllSequences(result1, expected1));

    // Example 2
    List<String> wordList2 = Arrays.asList("geek", "gefk");
    List<List<String>> result2 = sol.findSequences("gedk", "geek", wordList2);
    List<List<String>> expected2 = Arrays.asList(
        Arrays.asList("gedk", "geek")
    );
    assertTrue(containsAllSequences(result2, expected2));

    // Example 3: no transformation possible
    List<String> wordList3 = Arrays.asList("abc", "ayc", "ayz", "xyz");
    List<List<String>> result3 = sol.findSequences("abc", "xyz", wordList3);
    List<List<String>> expected3 = Arrays.asList(
        Arrays.asList("abc", "ayc", "ayz", "xyz")
    );
    assertTrue(containsAllSequences(result3, expected3));

    // Edge case: beginWord == endWord (trivial sequence of length 1)
    List<String> wordList4 = Arrays.asList("abc","abd","acd");
    List<List<String>> result4 = sol.findSequences("abc", "abc", wordList4);
    List<List<String>> expected4 = Arrays.asList(
        Arrays.asList("abc")
    );
    assertTrue(containsAllSequences(result4, expected4));

    // Edge case: multiple shortest sequences exist
    List<String> wordList5 = Arrays.asList("hot","dot","dog","lot","log","cog");
    List<List<String>> result5 = sol.findSequences("hit", "cog", wordList5);
    List<List<String>> expected5 = Arrays.asList(
        Arrays.asList("hit","hot","dot","dog","cog"),
        Arrays.asList("hit","hot","lot","log","cog")
    );
    assertTrue(containsAllSequences(result5, expected5));
  }

  private static boolean containsAllSequences(List<List<String>> actual, List<List<String>> expected) {
    Set<List<String>> actualSet = new HashSet<>(actual);
    Set<List<String>> expectedSet = new HashSet<>(expected);
    return actualSet.equals(expectedSet);
  }
}

class Solution {

  public List<List<String>> findSequences(String beginWord, String endWord, List<String> wordList) {
    Set<String> words = new HashSet<>(wordList);
    Set<String> visitedAtLevel = new HashSet<>();
    List<List<String>> result = new ArrayList<>();

    Queue<List<String>> q = new LinkedList<>();
    q.add(Collections.singletonList(beginWord));
    words.remove(beginWord);

    while (!q.isEmpty()) {
      int size = q.size();

      for (int i = 0; i < size; i++) {
        List<String> path = q.poll();
        String word = path.get(path.size() - 1);

        if (word.equals(endWord)) {
          result.add(path);
        }

        for (int j = 0; j < word.length(); j++) {
          char[] wordChars = word.toCharArray();
          for (char k = 'a'; k <= 'z'; k++) {
            wordChars[j] = k;
            String adj = new String(wordChars);
            if (words.contains(adj)) {
              List<String> adjPath = new ArrayList<>(path);
              adjPath.add(adj);
              q.add(adjPath);
              visitedAtLevel.add(adj);
            }
          }
        }
      }

      if (!result.isEmpty()) { // stop after finding at least one result at a level, that's the best path
        break;
      }

      // remove all visited nodes at this level, they're not needed in further levels
      words.removeAll(visitedAtLevel);
      visitedAtLevel.clear();
    }

    return result;
  }
}
