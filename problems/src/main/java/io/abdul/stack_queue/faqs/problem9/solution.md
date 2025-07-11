Similar to LRU, but we need to count the frequency as well.

- How to maintain a doubly linked list sorted by count?
- If we've single DLL, it'll take O(n) time to remove and insert an element
- So we'll maintain one DLL per frequency

put

- If update, get from map. Get current frequency, remove from the corresponding DLL. Increment count
  and
  insert in next sized DLL.
- If insert
    - If capacity if full,
        - Find the minFrequency
        - Get the corresponding DLL
        - Find the last element and remove it
        - How do we know the minFrequency? TreeMap or maintain minFrequency table. Whenever we
          remove an element from DLL, if its size goes below currentMin, then that's the new min
    - Create new node and put in 1 frequency
    - update min frequency

- get
    - Get node from map
    - Remove from current DLL

## Query: We only increment the min frequency and when all elements at minFrequency are removed? Is it possible that no element exists at the next frequency?

No, bcz we support only get and put operations.

- Element is getting removed from minFrequency on insert and get
- When we insert, minFrequency will definitely go to 1 as the new element has 1 for sure
- When we get, it's possible that last element at minFrequency is promoted to nextFrequency. So
  it'll be there in next frequency for sure. Here if we check current DLL size is 0, and increment
  min frequency, the next DLL will definitely have an element. Bcz we just promoted one there.

If we remove operation:

- It's quite possible that this can happen.
- We can remove all elements from minFrequency+1 and we can remove 1 element. Now if we remove all
  from minFrequency as well, how will to choose the next minFrequency? So we can maintain TreeMap in
  that case.