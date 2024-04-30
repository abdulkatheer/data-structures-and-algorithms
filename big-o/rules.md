1. Consider worst case scenario
2. Drop the constants i.e. 1 -> 1, 1n -> n, 10n -> n, n-10 -> n, 1 + 10n -> n
3. Different terms for inputs i.e. mn, m+n, nlogm
4. Drop non-dominants i.e n + n<sup>2</sup> -> n<sup>2</sup>, 10n + n + logn -> n

## O(log n)
If the algorithm is dividing the number of elements being considered by x (eg 2 in case of Binary Search) on each iteration, 
then it most likely has a runtime complexity of O(log N).

### Ex: Binary Search
1. Set two variables. min = 0 and max = n - 1.
2. Find the mid-value between min and max by averaging min and max and rounding it down.
3. If array[mid] === target, return mid.
4. If array[mid] < target, set min = mid + 1.
5. Otherwise, set max = mid - 1.
6. Go back to step 2.

Let's say array = [4, 8, 10, 14, 27, 31, 46, 52] and our target is 46.

So,
- min = 0, max = 7, and mid = (0 + 7)/2 = 3.5 -> round to 3
- array[3] = 14 and therefore less than 46, so min = mid + 1 = 4
- min = 4, max = 7, and mid = (4 + 7)/2 = 5.5 -> round to 5
- array[5] = 31 and therefore less than 84, so min = mid + 1 = 6
- min = 6, max = 7, and mid = (6 + 7)/2 = 6.5 -> round to 6
- array[6] = 46, which equals our target! Return mid.

In the example we just looked at, we were able to find the target value in only 3 iterations of the code. 
The binary search algorithm accomplishes this by dividing the search area in half on each iteration. 
So at the start we have N elements to search. By the second step we only have N/2 elements to search, and by the third we 
only have N/4 elements to search.

In the above case that looks like this,

N = 8, [4, 8, 10, 14, 27, 31, 46, 52] //Compared and divide search area by 2<br>
N = 4, [27, 31, 46, 52] //Compared and divide search area by 2<br>
N = 2, [46, 52] //Compared mid to target. They matched, so returned mid.<br>

Notice that this took three steps and it's dividing by 2 each time. If we multiplied by 2 each time we would have 2 x 2 x 2 = 8, or 23 = 8.

2<sup>3</sup> = 8 -> log<sub>2</sub> 8 = 3<br>
2<sub>k</sub> = N -> log<sub>2</sub> N = k

So we can see that since the code was dividing by 2 each time and we started with N elements in our ordered array, 
it will takes log N iterates of the binary search algorithm to find the target value. Therefore, the Big O complexity of 
a binary search is O(log N).

And you may be wondering how to notate the base of the log when you're writing O(log N). Well you don't, because it 
doesn't matter. Why doesn't it matter? Well the answer is a bit too long to go though here, but suffice it to say that it's not relevant.

### Conclusion
Takeaways,

1. O(log N) is a common runtime complexity.
2. Examples include binary searches, finding the smallest or largest value in a binary search tree, and certain divide and conquer algorithms.
3. If an algorithm is dividing the elements being considered by 2 each iteration, then it likely has a runtime complexity of O(log N).