# Bellman-Ford Shortest Path Algorithm

- Works only on directed graphs
- Undirected graphs has to be converted to directed to use this algorithm. (But will it work for negative weights, because it'll cause negative cycles)
- Works with negative weights (where Dijkstra's does not work)
- Helps to detect negative cycles, where there is no shortest path solution to such graphs

## How it works?

- We've a list of edges with weight, with no particular order
- We need to visit all edges |V| - 1 times
- If there are no negative cycles, after |V| - 1 iterations, we'll have shortest path to all |V| - 1 vertices from source

## Why it works?

- In worst case, a shortest path from node A to node B will have |V| - 1 edges
- Let's say we've graph like 1 -> 2 -> 3 -> 4 -> 5 -> 6 -> 7 -> 8 -> 9 -> 10
- There are 10 vertices and 9 edges here. This is the only path which has the shortest cost from 1 to 10
- But edges may be in any order like 9->10, 7->8, 1->2, 3->4
- If we iterate for 9 times and relax cost everytime, in the worst case, in the 9th iteration we'll have the shortest path from 1 to 9

