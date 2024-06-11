### Groups

#### Based on worst-case time complexity

1. Quadratic O(n<sup>2</sup>)
    1. Selection sort - O(1) space
    2. Bubble sort - O(1) space
    3. Insertion sort - O(1) space
    4. Quick Sort - O(log (n))
2. Linear logarithmic O(n * logn)
    1. Merge sort - O(n) space
    2. Heap sort - O(1) space
3. Polynomial O(n * k) ?? Explore
    1. Radix sort - O(n + k) space
4. Linear O(n + l) ?? Explore
    1. Counting sort - O(k) space

#### Based on stability

Stable sort maintains the relative order when the keys are same or equal.
That is, a sorting algorithm is stable if whenever there are two records R and S with the same key and with R appearing
before S in the original list, R will appear before S in the sorted list

1. Insertion sort
2. Bubble sort
3. Merge sort
4. Radix sort
5. Counting sort

### Which one to use?

#### Merge Sort

##### Pros

1. Large sized list
2. Linked List -> no additional memory needed for merge
3. External sorting
    1. Merge sort is particularly well-suited for external sorting due to its efficient use of disk I/O and its ability
       to handle large datasets that do not fit entirely into memory.
    2. Minimal Disk I/O: Merge sort minimizes the number of disk accesses by reading and writing data in large blocks.
       It reads chunks of data from disk, sorts them in memory, and then writes the sorted chunks back to disk. This
       reduces the overall number of disk reads and writes compared to other sorting algorithms that may read or write
       data in smaller increments.
    3. Efficient Merge Operation: The merge operation in merge sort is efficient and straightforward to implement for
       external sorting. It involves merging two sorted lists, which can be done by sequentially reading blocks of data
       from disk and merging them in memory. This allows merge sort to efficiently merge large sorted chunks of data
       during the sorting process.
    4. Divide-and-Conquer Strategy: Merge sort's divide-and-conquer strategy naturally lends itself to external sorting.
       It recursively divides the input data into smaller chunks until they fit into memory, sorts each chunk
       internally, and then merges the sorted chunks together. This approach allows merge sort to handle datasets that
       are larger than the available memory by processing them in manageable chunks.
4. Stable Sorting: Merge sort is a stable sorting algorithm, meaning it preserves the relative order of equal elements.
   This property is important in many applications, especially when sorting data with multiple keys or when preserving
   the original order of equal elements is necessary. Merge sort's stability makes it suitable for applications where
   maintaining the order of equal elements is important, such as database operations or file processing.
5. Predictable Performance: Merge sort has a consistent and predictable performance regardless of the input data
   distribution. Its time complexity is O(n log n) in all cases, making it suitable for sorting large datasets
   efficiently. This predictability is advantageous in external sorting, where the size and distribution of the data may
   vary widely.

##### Cons

1. Extra space (not an in-place sort)
    1. While merging, we create a copy of it
    2. Not needed for Linked List though
2. Recursive - Needs log n sized method call stack
3. Not Adaptive: Merge sort's time complexity remains O(n log n) regardless of the input data distribution. While this
   predictability can be advantageous in many scenarios, it also means that merge sort does not adapt to already
   partially sorted input. In contrast, adaptive sorting algorithms like insertion sort or quicksort can be more
   efficient when dealing with partially sorted data, as they exploit existing order to improve performance.
4. Not Suitable for Small Datasets: Merge sort's overhead in terms of time and space complexity makes it less efficient
   than simpler sorting algorithms like insertion sort or selection sort for very small datasets. When sorting only a
   few elements, the overhead of merge sort may outweigh its benefits, and simpler algorithms may be preferred.

### References

1. Sorting dances - https://www.youtube.com/playlist?list=PLcX11VWS1PdA4dSPip8-1JfKxFa32X53y
2. Sorting animated
    1. https://www.toptal.com/developers/sorting-algorithms
    2. https://visualgo.net/en/sorting
    3. https://www.cs.usfca.edu/~galles/visualization/ComparisonSort.html
    4. https://anim.ide.sk/sorting_algorithms_1.php
    5. https://www.sortvisualizer.com/
    6. https://emre.me/algorithms/sorting-algorithms/
3. Cheatsheet
    1. https://www.interviewcake.com/sorting-algorithm-cheat-sheet
    2. https://algodaily.com/lessons/types-of-sorting-algorithm-cheat-sheet
    3. https://www.bigocheatsheet.com/
4. https://stackoverflow.com/questions/1517793/what-is-stability-in-sorting-algorithms-and-why-is-it-important