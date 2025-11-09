package io.abdul.graphs.additional_algorithms.problem2;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Solutions {
    public static void main(String[] args) {
//        Solution sol = new Solution();
        Solution2 sol = new Solution2();

        // Test Case 1: Example 1
        int V1 = 4;
        List<List<Integer>> E1 = Arrays.asList(
                Arrays.asList(0, 1),
                Arrays.asList(1, 2),
                Arrays.asList(2, 0),
                Arrays.asList(1, 3)
        );
        List<List<Integer>> expected1 = Arrays.asList(Arrays.asList(1, 3));
        assertEquals(normalize(expected1), normalize(sol.criticalConnections(V1, E1)));

        // Test Case 2: Example 2
        int V2 = 3;
        List<List<Integer>> E2 = Arrays.asList(
                Arrays.asList(0, 1),
                Arrays.asList(1, 2),
                Arrays.asList(2, 0)
        );
        List<List<Integer>> expected2 = Collections.emptyList();
        assertEquals(normalize(expected2), normalize(sol.criticalConnections(V2, E2)));

        // Test Case 3: Example 3 (Simple edge)
        int V3 = 2;
        List<List<Integer>> E3 = Arrays.asList(Arrays.asList(0, 1));
        List<List<Integer>> expected3 = Arrays.asList(Arrays.asList(0, 1));
        assertEquals(normalize(expected3), normalize(sol.criticalConnections(V3, E3)));

        // Test Case 4: Linear chain
        int V4 = 5;
        List<List<Integer>> E4 = Arrays.asList(
                Arrays.asList(0, 1),
                Arrays.asList(1, 2),
                Arrays.asList(2, 3),
                Arrays.asList(3, 4)
        );
        List<List<Integer>> expected4 = Arrays.asList(
                Arrays.asList(0, 1),
                Arrays.asList(1, 2),
                Arrays.asList(2, 3),
                Arrays.asList(3, 4)
        );
        assertEquals(normalize(expected4), normalize(sol.criticalConnections(V4, E4)));

        // Test Case 5: Square with a diagonal (only diagonal is not a bridge)
        int V5 = 4;
        List<List<Integer>> E5 = Arrays.asList(
                Arrays.asList(0, 1),
                Arrays.asList(1, 2),
                Arrays.asList(2, 3),
                Arrays.asList(3, 0),
                Arrays.asList(0, 2)
        );
        List<List<Integer>> expected5 = Collections.emptyList();
        assertEquals(normalize(expected5), normalize(sol.criticalConnections(V5, E5)));

        // Test Case 6: Two triangles connected by one bridge
        int V6 = 6;
        List<List<Integer>> E6 = Arrays.asList(
                Arrays.asList(0, 1),
                Arrays.asList(1, 2),
                Arrays.asList(2, 0),
                Arrays.asList(2, 3),
                Arrays.asList(3, 4),
                Arrays.asList(4, 5),
                Arrays.asList(5, 3)
        );
        List<List<Integer>> expected6 = Arrays.asList(Arrays.asList(2, 3));
        assertEquals(normalize(expected6), normalize(sol.criticalConnections(V6, E6)));

        // Test Case 7: Fully connected graph (no bridges)
        int V7 = 4;
        List<List<Integer>> E7 = Arrays.asList(
                Arrays.asList(0, 1),
                Arrays.asList(0, 2),
                Arrays.asList(0, 3),
                Arrays.asList(1, 2),
                Arrays.asList(1, 3),
                Arrays.asList(2, 3)
        );
        List<List<Integer>> expected7 = Collections.emptyList();
        assertEquals(normalize(expected7), normalize(sol.criticalConnections(V7, E7)));
    }

    // Helper: normalize edges (sort pairs and list)
    private static List<List<Integer>> normalize(List<List<Integer>> list) {
        List<List<Integer>> sorted = new ArrayList<>();
        for (List<Integer> edge : list) {
            List<Integer> e = new ArrayList<>(edge);
            Collections.sort(e);
            sorted.add(e);
        }
        sorted.sort((a, b) -> {
            if (!a.get(0).equals(b.get(0))) return a.get(0) - b.get(0);
            return a.get(1) - b.get(1);
        });
        return sorted;
    }
}

// Iterative
class Solution {
    public List<List<Integer>> criticalConnections(int v, List<List<Integer>> edgeList) {
        List<List<Integer>> adjList = toAdjacencyList(v, edgeList);
        boolean[] visited = new boolean[v];
        List<List<Integer>> criticalEdges = new ArrayList<>();

        for (int i = 0; i < v; i++) {
            if (!visited[i]) { // new component found
                dfs(i, v, adjList, visited, criticalEdges);
            }
        }

        return criticalEdges;
    }

    private List<List<Integer>> toAdjacencyList(int v, List<List<Integer>> edgeList) {
        List<List<Integer>> adjList = new ArrayList<>();
        for (int i = 0; i < v; i++) {
            adjList.add(new ArrayList<>());
        }

        for (List<Integer> edge : edgeList) {
            adjList.get(edge.get(0)).add(edge.get(1));
            adjList.get(edge.get(1)).add(edge.get(0));
        }
        return adjList;
    }

    private void dfs(int startNode, int v, List<List<Integer>> adjList, boolean[] visited, List<List<Integer>> criticalEdges) {
        int[] discovery = new int[v];
        int[] lowDiscovery = new int[v];
        int[] parents = new int[v];
        Arrays.fill(parents, -1); // no parents for any

        int discoveryCount = 1;
        Stack<int[]> stack = new Stack<>();
        stack.push(new int[]{startNode, 0});
        visited[startNode] = true;
        discovery[startNode] = discoveryCount;
        lowDiscovery[startNode] = discoveryCount;
        discoveryCount++;

        while (!stack.isEmpty()) {
            int[] nodeData = stack.peek();
            int node = nodeData[0];
            int nextAdjNodePos = nodeData[1];

            List<Integer> adjNodes = adjList.get(node);
            if (nextAdjNodePos < adjNodes.size()) {
                int adjNode = adjNodes.get(nextAdjNodePos);
                if (!visited[adjNode]) {
                    // New node, so visit with init with discovery time
                    stack.push(new int[]{adjNode, 0});
                    parents[adjNode] = node;
                    visited[adjNode] = true;
                    discovery[adjNode] = discoveryCount;
                    lowDiscovery[adjNode] = discoveryCount;
                    discoveryCount++;
                } else if (adjNode != parents[node]) {
                    // Back edge, adjNode but not the parent
                    if (discovery[adjNode] < lowDiscovery[node]) {
                        // One of the subtree nodes can be visited earlier in another route
                        // Meaning node can be reached in a better time than current route
                        // Also we found a better low now
                        lowDiscovery[node] = discovery[adjNode];
                    }
                }
                nodeData[1]++;
            } else {
                // all adjacent nodes are visited
                stack.pop();
                // Backtracking - parent of node may have higher lowDiscovery. But child node may have lower lowDiscovery
                int parentNode = parents[node];
                if (parentNode != -1) { // not the root node
                    if (lowDiscovery[node] < lowDiscovery[parentNode]) {
                        // there's an alternate route to reach node than parent->node
                        // hence not a bridge edge
                        // parent node's lowDiscovery should be lowest if its entire subtree, so updating better low
                        lowDiscovery[parentNode] = lowDiscovery[node];
                    }
                    if (discovery[parentNode] < lowDiscovery[node]) {
                        // there's no alternate route to reach node than parent->node
                        // hence bridge ege
                        criticalEdges.add(Arrays.asList(parentNode, node));
                    }
                }
            }
        }
    }
}

class Solution2 {
    public List<List<Integer>> criticalConnections(int v, List<List<Integer>> edgeList) {
        List<List<Integer>> adjList = toAdjacencyList(v, edgeList);
        boolean[] visited = new boolean[v];
        List<List<Integer>> criticalEdges = new ArrayList<>();

        for (int i = 0; i < v; i++) {
            if (!visited[i]) { // new component found
                dfs(i, v, adjList, visited, criticalEdges);
            }
        }

        return criticalEdges;
    }

    private List<List<Integer>> toAdjacencyList(int v, List<List<Integer>> edgeList) {
        List<List<Integer>> adjList = new ArrayList<>();
        for (int i = 0; i < v; i++) {
            adjList.add(new ArrayList<>());
        }

        for (List<Integer> edge : edgeList) {
            adjList.get(edge.get(0)).add(edge.get(1));
            adjList.get(edge.get(1)).add(edge.get(0));
        }
        return adjList;
    }
    
    private void dfs(int startNode, int v, List<List<Integer>> adjList, boolean[] visited, List<List<Integer>> criticalEdges) {
        int[] lowestDisc = new int[v];
        int[] discovery = new int[v];
        int[] discoveryTime = new int[] {1};
        
        dfs(startNode, -1, adjList, visited, criticalEdges, lowestDisc, discovery, discoveryTime);
    }
    
    private void dfs(int node, int parent, List<List<Integer>> adjList, boolean[] visited, List<List<Integer>> criticalEdges,
                     int[] lowestDisc, int[] discovery, int[] discoveryTime) {
        discovery[node] = discoveryTime[0];
        lowestDisc[node] = discoveryTime[0];
        visited[node] = true;
        discoveryTime[0]++;

        for (Integer adjNode : adjList.get(node)) {
            if (adjNode == parent) {
                continue;
            }

            if (!visited[adjNode]) {
                dfs(adjNode, node, adjList, visited, criticalEdges, lowestDisc, discovery, discoveryTime);
                
                // Backtracking
                // adjNode may have better low
                // node's low should be lowest of all
                lowestDisc[node] = Math.min(lowestDisc[node], lowestDisc[adjNode]);
                
                if (discovery[node] < lowestDisc[adjNode]) {
                    // no alternate route to adjNode than node->adjNode
                    criticalEdges.add(Arrays.asList(node, adjNode));
                }
            } else {
                // Back edge
                if (discovery[adjNode] < lowestDisc[node]) {
                    // one of it adjNodes in subtree has better alternate route
                    // node's low should be lowest of all
                    lowestDisc[node] = discovery[adjNode];
                }
            }
        }
    }
}
