package io.abdul.graphs.hard_problems_ii.problem2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class Solutions {

}

/*
We've number of contacts
Person name can be duplicate, so email of Person X can't belong to same person
Email can't be duplicate, so 2 Persons holding email Y are same person

DisjointSet has emails and emails of same person will be in one component
At the end, we've groups of emails, we need to find the Person using of the email and attach emails to the person

So approach here is,
find distinct email and add monotonically increasing number to it - O(N+E)
Bcz we use numbering in DisjointSet
Email -> Node number map
Node number -> Email map
Email -> Person

Build Disjoint Set - O(E + 4a) 4a is a mathematical time complexity to find Ultimate parent of a node

Get all components of DisjointSet, that many number of distinct accounts exist - O(E + 4a)

Components have node number in them, find email by lookup using nodeNumber
Find the person using one of the emails in that component
Sort the emails and insert Person at the start

N - Number of accounts and E is number of emails
 */
class Solution {
  static List<List<String>> accountsMerge(List<List<String>> accounts) {
    // distinct emails and numbering
    Map<String, Integer> nodeByEmail = new HashMap<>();
    List<String> distinctEmails = new ArrayList<>();
    Map<String, String> personByEmail = new HashMap<>();
    int node = 0;
    for (List<String> account : accounts) {
      String person = account.get(0);
      for (int i = 1; i < account.size(); i++) {
        String email = account.get(i);
        if (!nodeByEmail.containsKey(email)) {
          nodeByEmail.put(email, node);
          node++;
          distinctEmails.add(email);
        }
        personByEmail.put(account.get(i), person);
      }
    }

    int n = node; // number of distinct emails

    // Now group them into distinct sets
    // [name, email1, email2, email3] -> email1 -- email2, email2 -- email3
    // [name, email1] -> no edges
    DisjointSet set = new DisjointSet(n);
    for (List<String> account : accounts) {
      for (int i = 1; i < account.size() - 1; i++) {
        int v1 = nodeByEmail.get(account.get(i));
        int v2 = nodeByEmail.get(account.get(i + 1));

        set.union(v1, v2);
      }
    }

    Collection<List<Integer>> components = set.getAllComponents();
    List<List<String>> result = new ArrayList<>(components.size());

    for (List<Integer> component : components) {
      List<String> acc = new ArrayList<>();

      for (int emailNode : component) {
        acc.add(distinctEmails.get(emailNode));
      }

      Collections.sort(acc);

      String person = personByEmail.get(distinctEmails.get(component.get(0)));
      acc.add(0, person);

      result.add(acc);
    }

    return result;
  }

  private static class DisjointSet {

    private final int[] parents;
    private final int[] ranks;

    DisjointSet(int n) {
      parents = new int[n];
      ranks = new int[n];

      for (int i = 0; i < n; i++) {
        parents[i] = i;
      }
    }

    void union(int u, int v) {
      int uUltimateParent = findUltimateParent(u);
      int vUltimateParent = findUltimateParent(v);

      if (uUltimateParent == vUltimateParent) {
        return;
      }

      if (ranks[uUltimateParent] < ranks[vUltimateParent]) {
        parents[uUltimateParent] = vUltimateParent;
      } else if (ranks[vUltimateParent] < ranks[uUltimateParent]) {
        parents[vUltimateParent] = uUltimateParent;
      } else {
        parents[vUltimateParent] = uUltimateParent;
        ranks[uUltimateParent]++;
      }
    }

    Collection<List<Integer>> getAllComponents() {
      Map<Integer, List<Integer>> components = new HashMap<>();
      for (int i = 0; i < parents.length; i++) {
        int up = findUltimateParent(i);
        if (!components.containsKey(up)) {
          components.put(up, new ArrayList<>());
        }

        components.get(up).add(i);
      }

      return components.values();
    }

    private int findUltimateParent(int x) {
      Stack<Integer> stack = new Stack<>();
      while (x != parents[x]) {
        stack.push(x);
        x = parents[x];
      }

      while (!stack.isEmpty()) {
        parents[stack.pop()] = x;
      }

      return x;
    }
  }
}

/*
Approach is, build DisjointSet with number of accounts given
So at the end we'll know the unique accounts
And also duplicate can given the same ultimate parent. We'll add email of duplicate accounts to ultimate parent account

DisjointSet has accounts and duplicate accounts will be in same component
At the end, we've groups of persons, distinct emails with personId. We can just group emails of duplicate account together and add Person name to it.
*/
class Solution2 {
  static List<List<String>> accountsMerge(List<List<String>> accounts) {
    int n = accounts.size(); // number of accounts = number of vertices
    DisjointSet set = new DisjointSet(n);

    Map<String, Integer> accountByEmail = new HashMap<>();
    for (int i = 0; i < n; i++) {
      // i is the account
      List<String> account = accounts.get(i);
      for (int j = 1; j < account.size(); j++) {
        String email = account.get(j);
        if (accountByEmail.containsKey(email)) {
          // Duplicate, so account i is also a duplicate and can be merged with one of the existing
          // account
          set.union(i, accountByEmail.get(email));
        } else {
          // Brand new email found and not linked to any previous account
          accountByEmail.put(email, i);
        }
      }
    }

    // Now we have all distinct emails by account, account may not be the unique account, but email
    // is unique
    // By finding ultimate parent of each account, we can group emails of same account together
    Map<Integer, List<String>> emailsByNode = new HashMap<>();
    for (Map.Entry<String, Integer> accByEmail : accountByEmail.entrySet()) {
      int node = accByEmail.getValue();
      String email = accByEmail.getKey();

      int ultimateParent = set.findUltimateParent(node);
      if (!emailsByNode.containsKey(ultimateParent)) {
        emailsByNode.put(ultimateParent, new ArrayList<>());
      }

      emailsByNode.get(ultimateParent).add(email);
    }

    List<List<String>> result = new ArrayList<>(emailsByNode.size());
    for(Map.Entry<Integer, List<String>> emailsByNd : emailsByNode.entrySet()) {
      int node = emailsByNd.getKey();
      List<String> emails = emailsByNd.getValue();
      String person = accounts.get(node).get(0);

      Collections.sort(emails);
      emails.add(0, person);

      result.add(emails);
    }

    return result;
  }

  private static class DisjointSet {

    private final int[] parents;
    private final int[] ranks;

    DisjointSet(int n) {
      parents = new int[n];
      ranks = new int[n];

      for (int i = 0; i < n; i++) {
        parents[i] = i;
      }
    }

    void union(int u, int v) {
      int uUltimateParent = findUltimateParent(u);
      int vUltimateParent = findUltimateParent(v);

      if (uUltimateParent == vUltimateParent) {
        return;
      }

      if (ranks[uUltimateParent] < ranks[vUltimateParent]) {
        parents[uUltimateParent] = vUltimateParent;
      } else if (ranks[vUltimateParent] < ranks[uUltimateParent]) {
        parents[vUltimateParent] = uUltimateParent;
      } else {
        parents[vUltimateParent] = uUltimateParent;
        ranks[uUltimateParent]++;
      }
    }

    private int findUltimateParent(int x) {
      Stack<Integer> stack = new Stack<>();
      while (x != parents[x]) {
        stack.push(x);
        x = parents[x];
      }

      while (!stack.isEmpty()) {
        parents[stack.pop()] = x;
      }

      return x;
    }
  }
}

