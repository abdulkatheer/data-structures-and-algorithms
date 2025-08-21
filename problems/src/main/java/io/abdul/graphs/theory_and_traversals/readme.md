## DFS

### Approach 1

- We add one element to stack
- Pop last added
- Only if it's not already processed, process it
- Add its adjacent nodes to stack if they're not processed already

Let's say we've nodes 1,2,3,4,5,6,7,8
1 -> 2,3,4,5
2 -> 1,6,7,8
3 -> 1,6,7,8
4 -> 1,6,7,8
5 -> 1,6,7,8
6 -> 2,3,4,5
7 -> 2,3,4,5
8 -> 2,3,4,5

stack = [1]
processed=[]
pop and process 1
processed=[1]
Add 5,4,3,2 to stack
stack=[2,3,4,5]

pop and process 2
stack=[3,4,5]
processed=[1,2]
Adj -> 1 processed, add 8,7,6 to stack
stack=[6,7,8,3,4,5]

pop and process 6
processed=[1,2,6]
stack=[7,8,3,4,5]
Adj -> 2 processed, add 3,4,5 to stack
stack=[3,4,5,7,8,3,4,5]

pop and process 3
processed=[1,2,6,3]
stack=[4,5,7,8,3,4,5]
Adj -> 1,6 processed; Add 7,8 to stack
stack=[7,8,4,5,7,8,3,4,5]

pop and process 7
stack=[8,4,5,7,8,3,4,5]
processed=[1,2,6,3,7]
Adj -> 2,3 processed; Add 4,5 to stack
stack=[4,5,8,4,5,7,8,3,4,5]

pop and process 4
stack=[5,8,4,5,7,8,3,4,5]
processed=[1,2,6,3,7,4]
Adj -> 1,6,7 processed; Add 8 to stack
stack=[8,5,8,4,5,7,8,3,4,5]

pop and process 8
stack=[5,8,4,5,7,8,3,4,5]
processed=[1,2,6,3,7,4,8]
Adj -> 2,3,4 processed; Add 5 to stack
stack=[5,5,8,4,5,7,8,3,4,5]

pop and process 5
stack=[5,8,4,5,7,8,3,4,5]
processed=[1,2,6,3,7,4,8,5]
Adj -> 1,6,7,8 processed

-- after this all are just popped and ignored
Duplicate elements added to stack!!!

### Approach 2

stack = [1]
processed=[1]

pop 1
stack = []
Adj -> 2,3,4,5
process 2 and add
stack = [2]
processed=[1,2]
process 3 and add
stack = [3,2]
processed=[1,2,3]
process 4 and add
stack = [4,3,2]
processed=[1,2,3,4]
process 5 and add
stack = [5,4,3,2]
processed=[1,2,3,4,5]

pop 5
stack = [4,3,2]

Adj -> 1,6,7,8
1 processed, ignore
process 6 and add
stack = [6,4,3,2]
processed=[1,2,3,4,5,6]
process 7 and add
stack = [7,6,4,3,2]
processed=[1,2,3,4,5,6,7]
process 8 and add
stack = [8,7,6,4,3,2]
processed=[1,2,3,4,5,6,7,8]

pop 8 and everything else and just ignore

### Summary

- In both approaches, we end up adding duplicates to stack

### Correct approach

- Maintain dedicated visited array
- While adding to stack or queue, check if its visited already, if not add it and mark as visited
- While popping out process them
- Do visiting and processing separately