package io.abdul.binary_search.faqs.problem2;

import static org.junit.jupiter.api.Assertions.assertEquals;

/*
min = 12
max = 201
201 to 12, bcz we need min-max
201 possible? No
200
199
111 -> 90 and 67,34,12
46 -> 90,67 and 34,12
12 -> 90,67,34 and 12
 */
public class BookAllocation {
    public static void main(String[] args) {
//        Solution solution = new Solution();
//        Solution2 solution = new Solution2();
        Solution3 solution = new Solution3();
// Test case 1: Example from the problem statement
        assertEquals(113, solution.findPages(new int[]{12, 34, 67, 90}, 2));

        // Test case 2: Example from the problem statement
        assertEquals(71, solution.findPages(new int[]{25, 46, 28, 49, 24}, 4));

        // Test case 3: Example from the problem statement
        assertEquals(32, solution.findPages(new int[]{15, 17, 20}, 2));

        // Test case 4: Existing data from main method
        assertEquals(113, solution.findPages(new int[]{12, 34, 67, 90}, 2));
        assertEquals(71, solution.findPages(new int[]{25, 46, 28, 49, 24}, 4));
        assertEquals(32, solution.findPages(new int[]{15, 17, 20}, 2));
        assertEquals(968, solution.findPages(new int[]{462, 450, 652, 363, 96, 968, 493, 947, 669, 559, 758, 924, 931, 621, 334, 942, 724, 521, 227, 216, 781, 189, 758, 215, 278, 692, 125, 621, 281}, 22));
        assertEquals(-1, solution.findPages(new int[]{1, 2}, 3));
        assertEquals(2, solution.findPages(new int[]{1, 2}, 2));
        assertEquals(3, solution.findPages(new int[]{1, 2}, 1));

        // Test case 5: Single book and single student
        assertEquals(50, solution.findPages(new int[]{50}, 1));

        // Test case 6: Multiple books but only one student
        assertEquals(150, solution.findPages(new int[]{50, 40, 60}, 1));

        // Test case 7: More students than books
        assertEquals(-1, solution.findPages(new int[]{10, 20, 30}, 5));

        // Test case 8: Large input with multiple students
        assertEquals(60, solution.findPages(new int[]{10, 20, 30, 40, 50}, 3));
    }
}

/*
Brute - Linear
T - O(n * (sum-min_max))
S - O(1)
 */
class Solution {
    public int findPages(int[] nums, int m) {
        if (nums.length < m) {
            return -1;
        }
        int low = Integer.MIN_VALUE; // single max, means that if one book allocated to each student, the max of all books will be the max allocated
        int high = 0; // if only one student, exists, sum of all pages will be the max allocated
        for (int num : nums) {
            low = Math.max(low, num);
            high = high + num;
        }

        for (int i = low; i <= high; i++) {
            if (canAllocate(nums, i, m)) {
                return i;
            }
        }

        return low; // After trial and error, found that if no value fit between max(nums) and sum(nums), then low would be the answer
        // try it out by setting low to min(nums) and let allocate return max allocated. For any lower nums than max(nums), max allocated will always be max(nums)
    }

    private boolean canAllocate(int[] books, int maxPages, int students) {
        int sumOfPages = 0;
        for (int book : books) {
            if (sumOfPages + book <= maxPages) { // Accumulate till its less than max allowed
                sumOfPages += book;
            } else { // allocate once we can't accumulate more
                students--;
                sumOfPages = book;
            }
        }

        if (students > 0 && sumOfPages > 0 && sumOfPages <= maxPages) { // to handle the last bit
            students--;
            sumOfPages = 0;
        }

        return students == 0 && sumOfPages == 0;
    }
}

/*
Brute - Linear
T - O(n * log (sum-min_max))
S - O(1)
 */
class Solution2 {
    public int findPages(int[] nums, int m) {
        if (nums.length < m) {
            return -1;
        }
        int low = Integer.MIN_VALUE;
        int high = 0;
        for (int num : nums) {
            low = Math.max(low, num);
            high += num;
        }

        int minMax = low;
        while (low <= high) {
            int mid = (low + high) / 2;
            int result = count(nums, mid, m);

            if (result < 0) { // Pages left unallocated. increase page size
                low = mid + 1;
            } else if (result == 0) {
                minMax = mid;
                high = mid - 1; // find better (min) than this
            } else { // Allocated more
                high = mid - 1;
            }
        }

        return minMax;
    }

    private int count(int[] books, int maxPages, int m) {
        int sumOfPages = 0;
        int students = 0;
        for (int book : books) {
            if (sumOfPages + book <= maxPages) { // Accumulate till its less than max allowed
                sumOfPages += book;
            } else { // allocate once we can't accumulate more
                students++;
                sumOfPages = book;
            }
            if (students == m) {
                break;
            }
        }

        if (students < m && sumOfPages > 0 && sumOfPages <= maxPages) { // to handle the last bit
            students++;
            sumOfPages = 0;
        }

        if (sumOfPages > 0) { // Under allocated
            return -1; // Need more students or need to allocate more pages per student
        } else if (students < m) { // Over allocated, no more pages left, but not everyone got books
            return 1;
        } else { // Allocated properly (students == m)
            return 0;
        }
    }
}

// Simple Optimal
/*

Low = max of all, best case is everyone gets a single book
High = sum of all, worst case is one gets all books

Case 1: m == nums.length, low is the answer
Case 2: m > nums.length, less books. So -1
Case 3: m < nums.length, more students solve it

Answer criteria: When m <= countOfStudents in the binary search, means with this max page limit we can distribute all books. We can look for better ones.
Why not checking for ==?
Bcz we know that we've more books than students. If we distribute with a max number, means everyone will get at least one book.
We might have clubbed books unnecessarily. Just getting books from ppl having more than 1 and giving it to ones who don't have any is possible,
and it'll only reduce the max pages.

*/
class Solution3 {
    public int findPages(int[] nums, int m) {
        if (nums.length < m) { // Case 1: less books
            return -1;
        }
        int low = -1; // max(nums)
        int high = 0; // sum(nums)
        for (int num : nums) {
            low = Math.max(low, num);
            high += num;
        }
        if (nums.length == m) { // Case 2: Exact number of books
            return low; // max of all is the best possible max
        }

        // Case 3: m > nums.length
        int answer = -1;
        while (low <= high) {
            int mid = (low + high) / 2;

            if (canAssignBooks(nums, m, mid)) {
                answer = mid;
                high = mid - 1; // look for better
            } else {
                low = mid + 1;
            }
        }

        return answer;
    }

    private boolean canAssignBooks(int[] nums, int m, int maxPages) {
        int students = 1; // for the last part of book(s) which is not assigned in loop
        /* can't the last part go beyond maxPages?
        No. Bcz when we check last to last, if sum + last exceeds max, till last sum will be given to a student. Only last element will exist in sum.
        The last can't go beyond max as our low starts with the max. maxPages can't go below low.
        if sum + last doesn't exceed max, obviously it can be given to a student.
         */
        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            if (sum + nums[i] > maxPages) {
                students++;
                sum = nums[i];
                if (students > m) {
                    return false;
                }
            } else {
                sum += nums[i];
            }
        }
        return true; // last inc checks for > m, so if it comes here, it's <= m
    }
}