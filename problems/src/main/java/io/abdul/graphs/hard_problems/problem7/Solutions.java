package io.abdul.graphs.hard_problems.problem7;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

/*
Indirect conversion:
bag -> cog
[dag, dog, cog]

Options
b -> c, cag not available X
a -> o, bog not available X

But
b -> d, dag available
a -> o, dog available
d -> c, cog available

Solution:
- We can't find direct start to end word conversions, it may be indirect as well.
- So we need to check all possibilities for each char at each level (or step)
- Whichever path gives target earlier, we take that
- BFS naturally fits in here
- We can't have visited node, we'll remove the visited node from main set. (Even if that's visited at any future steps, that path will definitely be longer as we've already visited it at earlier levels)
A -> L -> D -> Z
A -> K -> M -> L -> D -> Z X
L is already visited at level 2, so that will be shorter than this.
 */
public class Solutions {

  public static void main(String[] args) {
    Solution sol = new Solution();

    // Example 1
    List<String> wordList1 = Arrays.asList("des", "der", "dfr", "dgt", "dfs");
    assertEquals(3, sol.wordLadderLength("der", "dfs", wordList1));

    // Example 2
    List<String> wordList2 = Arrays.asList("geek", "gefk");
    assertEquals(2, sol.wordLadderLength("gedk", "geek", wordList2));

    // Example 3: targetWord not in wordList => impossible
    List<String> wordList3 = Arrays.asList("hot", "dot", "dog", "lot", "log");
    assertEquals(0, sol.wordLadderLength("hit", "cog", wordList3));

    // Edge case: startWord == targetWord (though problem says distinct)
    List<String> wordList4 = Arrays.asList("abc", "abd", "acd");
    assertEquals(1, sol.wordLadderLength("abc", "abc", wordList4));

    // Edge case: direct transformation available
    List<String> wordList5 = Arrays.asList("abc", "abd");
    assertEquals(2, sol.wordLadderLength("abc", "abd", wordList5));

    // Edge case: no path exists even though targetWord in list
    List<String> wordList6 = Arrays.asList("aaa", "aab", "abb", "bbb");
    assertEquals(4, sol.wordLadderLength("aaa", "bbb", wordList6));

    // Edge case: larger chain
    List<String> wordList7 = Arrays.asList("hot", "dot", "dog", "lot", "log", "cog");
    assertEquals(5, sol.wordLadderLength("hit", "cog", wordList7));
  }
}

class Solution {

  public int wordLadderLength(String startWord, String targetWord, List<String> wordList) {
    Set<String> words = new HashSet<>(wordList);

    // Edge case
    if (!words.contains(targetWord)) {
      return 0;
    }

    if (startWord.length() != targetWord.length()) {
      return 0;
    }

    Queue<String> q = new LinkedList<>();
    q.add(startWord);
    words.remove(startWord);

    int level = 0;
    while (!q.isEmpty()) {
      int size = q.size();
      for (int i = 0; i < size; i++) {
        String w = q.poll();

        if (w.equals(targetWord)) { // found target at level x
          return level + 1;
        }

        // visit adjacent nodes
        for (int j = 0; j < w.length(); j++) {
          char[] wordChars = w.toCharArray();
          for (char k = 'a'; k <= 'z'; k++) {
            wordChars[j] = k;
            String adj = new String(wordChars);
            if (words.remove(adj)) { // check and remove
              q.add(adj);
            }
          }
        }
      }

      level++;
    }

    return 0; // All paths explored, but target is not reached
  }
}
