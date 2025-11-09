package io.abdul.graphs.scc.problem1;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

// https://www.spoj.com/problems/GOODA/cstart=0
// https://cp-algorithms.com/graph/strongly-connected-components.html
public class Solutions {
    public static void main(String[] args) {
//        getInput();

        Solution sol = new Solution();

        // -------- Test Case 1: Sample input --------
        int n1 = 5;
        int m1 = 6;
        int[] cities1 = {5, 4, 5, 10, 2};
        int[][] flights1 = {
                {0, 1},
                {0, 2},
                {1, 3},
                {2, 3},
                {3, 4},
                {4, 3}
        };
        int s1 = 0, e1 = 3; // start and end city (0-based)
        assertEquals(22, sol.goodTravels(n1, m1, cities1, flights1, s1, e1));

        // -------- Test Case 2: Simple linear graph --------
        int n2 = 4;
        int m2 = 3;
        int[] cities2 = {1, 2, 3, 4};
        int[][] flights2 = {
                {0, 1},
                {1, 2},
                {2, 3}
        };
        int s2 = 0, e2 = 3;
        assertEquals(10, sol.goodTravels(n2, m2, cities2, flights2, s2, e2));

        // -------- Test Case 3: Start and end in same SCC (cycle) --------
        int n3 = 3;
        int m3 = 3;
        int[] cities3 = {3, 2, 5};
        int[][] flights3 = {
                {0, 1},
                {1, 2},
                {2, 0} // cycle
        };
        int s3 = 0, e3 = 2;
        // max fun = sum of all cities in SCC = 3 + 2 + 5 = 10
        assertEquals(10, sol.goodTravels(n3, m3, cities3, flights3, s3, e3));

        // -------- Test Case 4: Multiple paths, pick max --------
        int n4 = 6;
        int m4 = 7;
        int[] cities4 = {1, 2, 3, 4, 5, 6};
        int[][] flights4 = {
                {0, 1},
                {0, 2},
                {1, 3},
                {2, 3},
                {3, 4},
                {2, 4},
                {4, 5}
        };
        int s4 = 0, e4 = 5;
        // max fun = 1+3+4+5+6 = 19 (0→2→3→4→5)
        assertEquals(19, sol.goodTravels(n4, m4, cities4, flights4, s4, e4));

        // -------- Test Case 5: Only two cities --------
        int n5 = 2;
        int m5 = 1;
        int[] cities5 = {7, 8};
        int[][] flights5 = {
                {0, 1}
        };
        int s5 = 0, e5 = 1;
        assertEquals(15, sol.goodTravels(n5, m5, cities5, flights5, s5, e5));
    }

    private static void getInput() {
        Scanner sc = new Scanner(System.in);

        // Step 1: Read N, M, S, E
        int n = sc.nextInt();
        int m = sc.nextInt();
        int s = sc.nextInt();
        int e = sc.nextInt();

        // Step 2: Read fun values
        int[] cities = new int[n];
        for (int i = 0; i < n; i++) {
            cities[i] = sc.nextInt();
        }

        // Step 3: Read flights as 2D array
        int[][] flights = new int[m][2];
        for (int i = 0; i < m; i++) {
            flights[i][0] = sc.nextInt(); // from city
            flights[i][1] = sc.nextInt(); // to city
        }

        // Step 4: Call solution
        Solution sol = new Solution();
        int result = sol.goodTravels(n, m, cities, flights, s, e);

        // Step 5: Output result
        System.out.println(result);
        sc.close();
    }
}

class Solution {
    /*
    Cities numbered from 0 to n-1
    SCCs numbered from 0 to n-1
     */
    public int goodTravels(int n, int m, int[] cities, int[][] flights, int s, int e) {
        // SCC condensation (Kosaraju's) -> DAG DFS (Topo-sort)

        List<List<Integer>> adjList = toAdjacencyList(n, flights);

        // Step 1 - SCC condensation - Returns component to scc array
//        SccKosaraju scc = new SccKosaraju(n, adjList);
        SccTarjan scc = new SccTarjan(n, adjList);
        int[] sccComponents = scc.getComponents();
        int sccs = scc.getSccs();

        // Step 2 - Calculate fun for each SCC
        int[] sccFuns = new int[sccs];
        for (int i = 0; i < cities.length; i++) {
            sccFuns[sccComponents[i]] += cities[i];
        }

        // Step 3 - Build SCC DAG
        List<Set<Integer>> sccAdjList = buildSccGraph(n, sccs, adjList, sccComponents);


        // Step 4 - SCC DAG DFS from S to E to find the best max path (recursion / DP with topo-sort)
//        LongestPathDfs longestPathDfs = new LongestPathDfs(sccs, sccAdjList, sccFuns, sccComponents[s], sccComponents[e]);
//        LongestPathDfsMemoized longestPathDfs = new LongestPathDfsMemoized(sccs, sccAdjList, sccFuns, sccComponents[s], sccComponents[e]);
        LongestPathDpTopoSort longestPathDfs = new LongestPathDpTopoSort(sccs, sccAdjList, sccFuns, sccComponents[s], sccComponents[e]);
        return longestPathDfs.getValue();
    }

    private List<Set<Integer>> buildSccGraph(int n, int sccs, List<List<Integer>> adjList, int[] sccComponents) {
        List<Set<Integer>> sccAdjList = new ArrayList<>();
        for (int i = 0; i < sccs; i++) {
            sccAdjList.add(new HashSet<>());
        }
        for (int x = 0; x < n; x++) {
            for (Integer y : adjList.get(x)) {
                int sccX = sccComponents[x];
                int sccY = sccComponents[y];

                if (sccX != sccY) {
                    sccAdjList.get(sccX).add(sccY);
                }
            }
        }
        return sccAdjList;
    }

    private List<List<Integer>> toAdjacencyList(int n, int[][] edges) {
        List<List<Integer>> adjList = new ArrayList<>(n + 1);
        for (int i = 0; i < n; i++) {
            adjList.add(new ArrayList<>());
        }

        for (int[] edge : edges) {
            adjList.get(edge[0]).add(edge[1]);
        }
        return adjList;
    }


}

class SccKosaraju {
    private final int[] components;
    private int sccs;

    public SccKosaraju(int n, List<List<Integer>> adjList) {
        components = findScc(n, adjList);
    }

    public int[] getComponents() {
        return components;
    }

    public int getSccs() {
        return sccs;
    }

    private int[] findScc(int n, List<List<Integer>> adjList) {
        // Step 1 - DFS topo-sort
        Stack<Integer> dfsTopoSort = dfsTopoSort(n, adjList);

        // Step 2 - Transpose Graph
        List<List<Integer>> revAdjList = reverseEdges(adjList);

        // Step 3 - DFS on Transposed Graph
        List<List<Integer>> sccs = dfs(n, dfsTopoSort, revAdjList);
        this.sccs = sccs.size();

        int[] nodeScc = new int[n];
        for (int sccId = 0; sccId < sccs.size(); sccId++) {
            for (Integer node : sccs.get(sccId)) {
                nodeScc[node] = sccId;
            }
        }

        return nodeScc;
    }

    private List<List<Integer>> reverseEdges(List<List<Integer>> adjList) {
        List<List<Integer>> revAdjList = new ArrayList<>();
        for (int i = 0; i < adjList.size(); i++) {
            revAdjList.add(new ArrayList<>());
        }

        for (int x = 0; x < adjList.size(); x++) {
            for (Integer y : adjList.get(x)) {
                revAdjList.get(y).add(x);
            }
        }

        return revAdjList;
    }

    private List<List<Integer>> dfs(int n, Stack<Integer> dfsTopoSort, List<List<Integer>> revAdjList) {
        List<List<Integer>> sccs = new ArrayList<>();
        boolean[] visited = new boolean[n];
        while (!dfsTopoSort.isEmpty()) {
            Integer node = dfsTopoSort.pop();
            if (visited[node]) {
                continue;
            }

            List<Integer> scc = dfs(node, revAdjList, visited);
            sccs.add(scc);
        }
        return sccs;
    }

    private List<Integer> dfs(Integer startNode, List<List<Integer>> adjList, boolean[] visited) {
        Stack<Integer> stack = new Stack<>();
        List<Integer> nodes = new ArrayList<>();

        stack.push(startNode);
        visited[startNode] = true;

        while (!stack.isEmpty()) {
            Integer node = stack.pop();
            nodes.add(node);

            for (Integer adjNode : adjList.get(node)) {
                if (!visited[adjNode]) {
                    stack.push(adjNode);
                    visited[adjNode] = true;
                }
            }
        }

        return nodes;
    }

    private Stack<Integer> dfsTopoSort(int n, List<List<Integer>> adjList) {
        boolean[] visited = new boolean[n];
        Stack<Integer> topoSort = new Stack<>();

        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                dfsTopoSort(i, adjList, visited, topoSort);
            }
        }

        return topoSort;
    }

    private void dfsTopoSort(int startNode, List<List<Integer>> adjList, boolean[] visited, Stack<Integer> topoSort) {
        Stack<int[]> stack = new Stack<>();
        stack.push(new int[]{startNode, 0});
        visited[startNode] = true;

        while (!stack.isEmpty()) {
            int[] nodeData = stack.peek();
            int node = nodeData[0];
            int nextAdjNodePos = nodeData[1];

            if (nextAdjNodePos < adjList.get(node).size()) {
                int adjNode = adjList.get(node).get(nextAdjNodePos);
                if (!visited[adjNode]) {
                    stack.push(new int[]{adjNode, 0});
                    visited[adjNode] = true;
                }
                // ignore cycle check
                nodeData[1]++;
            } else {
                stack.pop();
                topoSort.push(node);
            }
        }
    }
}

// Tarjan's algo to find SCC in directed graph
class SccTarjan {
    private final int[] components;
    private int sccs;

    public SccTarjan(int n, List<List<Integer>> adjList) {
        components = findScc(n, adjList);
    }

    public int[] getComponents() {
        return components;
    }

    public int getSccs() {
        return sccs;
    }

    private int[] findScc(int n, List<List<Integer>> adjList) {
        boolean[] visited = new boolean[n];
        boolean[] inStack = new boolean[n];
        int[] components = new int[n];
        Arrays.fill(components, -1);

        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                findScc(i, n, adjList, visited, inStack, components);
            }
        }

        return components;
    }

    private void findScc(int startNode, int n, List<List<Integer>> adjList, boolean[] visited, boolean[] inStack, int[] components) {
        int[] discovery = new int[n];
        int[] lowDisc = new int[n];
        int[] parents = new int[n];
        int discTime = 1;

        Stack<int[]> stack = new Stack<>();
        Stack<Integer> data = new Stack<>();

        stack.push(new int[]{startNode, 0});
        data.push(startNode);
        discovery[startNode] = discTime;
        lowDisc[startNode] = discTime;
        inStack[startNode] = true;
        visited[startNode] = true;
        parents[startNode] = -1;
        discTime++;

        while (!stack.isEmpty()) {
            int[] nodeData = stack.peek();
            int node = nodeData[0];
            int nextAdjNodePos = nodeData[1];

            if (nextAdjNodePos < adjList.get(node).size()) {
                int adjNode = adjList.get(node).get(nextAdjNodePos);
                if (!visited[adjNode]) {
                    stack.push(new int[]{adjNode, 0});
                    data.push(adjNode);
                    visited[adjNode] = true;
                    inStack[adjNode] = true;
                    discovery[adjNode] = discTime;
                    lowDisc[adjNode] = discTime;
                    parents[adjNode] = node;
                    discTime++;
                } else if (inStack[adjNode]) {
                    // Back edge to a node which is not part of any SCC
                    // Meaning adjNode is part of current scc
                    lowDisc[node] = Math.min(lowDisc[node], discovery[adjNode]);
                }
                /*
                if visited and not inStack, the adjNode is already part of another SCC and doesn't have back to node
                 */
                nodeData[1]++;
            } else {
                // all adjNodes of node are processed
                stack.pop();

                int parentNode = parents[node];
                if (parentNode != -1) {
                    lowDisc[parentNode] = Math.min(lowDisc[parentNode], lowDisc[node]);
                }
                // Backtracking
                if (discovery[node] == lowDisc[node]) {
                    while (true) {
                        Integer v = data.pop();
                        inStack[v] = false;
                        components[v] = this.sccs;
                        if (v == node) {
                            break;
                        }
                    }
                    this.sccs++;
                }
            }
        }
    }
}

class LongestPathDfs {
    private final int value;

    public LongestPathDfs(int n, List<Set<Integer>> adjList, int[] funs, int start, int end) {
        this.value = findLongestPath(start, end, n, adjList, funs);
    }

    public int getValue() {
        return value;
    }

    private int findLongestPath(int start, int end, int n, List<Set<Integer>> adjList, int[] funs) {
        if (start == end) {
            return funs[end];
        }
        int max = -1;
        for (Integer adjNode : adjList.get(start)) {
            int longestPath = findLongestPath(adjNode, end, n, adjList, funs);
            if (longestPath != -1) {
                max = Math.max(max, longestPath + funs[start]);
            }
        }
        return max; // can be -1 if no path reaches end
    }
}

class LongestPathDfsMemoized {
    private final int value;

    public LongestPathDfsMemoized(int n, List<Set<Integer>> adjList, int[] funs, int start, int end) {
        int[] longestPath = new int[n];
        Arrays.fill(longestPath, -1);
        this.value = findLongestPath(start, end, n, adjList, funs, longestPath);
    }

    public int getValue() {
        return value;
    }

    private int findLongestPath(int start, int end, int n, List<Set<Integer>> adjList, int[] funs, int[] longestPath) {
        if (start == end) {
            return funs[end];
        }
        if (longestPath[start] != -1) {
            return longestPath[start];
        }

        int max = -1;
        for (Integer adjNode : adjList.get(start)) {
            int longest = findLongestPath(adjNode, end, n, adjList, funs, longestPath);
            if (longest != -1) {
                max = Math.max(max, longest + funs[start]);
            }
        }

        longestPath[start] = max;
        return max; // can be -1 if no path reaches end
    }
}

class LongestPathDpTopoSort {
    private final int value;

    public LongestPathDpTopoSort(int n, List<Set<Integer>> adjList, int[] funs, int start, int end) {

        this.value = findLongestPath(start, end, n, adjList, funs);
    }

    public int getValue() {
        return value;
    }

    private int findLongestPath(int start, int end, int n, List<Set<Integer>> adjList, int[] funs) {
        /* Find topo-sort order for the graph
        dp[x] is best fun from start to x
        Base - dp[start] = funs[start]
        We can begin from start and go only till end
        Even if we begin from 0, as it'll all be -1 till start, it won't do any logic
        */

        int[] indegree = new int[n];
        for (Set<Integer> adjNodes : adjList) {
            for (Integer adjNode : adjNodes) {
                indegree[adjNode]++;
            }
        }

        int[] dp = new int[n];
        Arrays.fill(dp, -1);
        dp[start] = funs[start];
        Queue<Integer> q = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            if (indegree[i] == 0) {
                q.add(i);
            }
        }

        while (!q.isEmpty()) {
            Integer node = q.poll();

            if (node == end) {
                break;
            }

            for (Integer adjNode : adjList.get(node)) {
                if (funs[node] != -1) {
                    dp[adjNode] = Math.max(dp[adjNode], dp[node] + funs[adjNode]);
                }
                indegree[adjNode]--;

                if (indegree[adjNode] == 0) {
                    q.add(adjNode);
                }
            }
        }

        return dp[end];
    }

}

