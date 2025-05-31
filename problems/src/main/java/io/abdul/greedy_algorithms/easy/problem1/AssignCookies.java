package io.abdul.greedy_algorithms.easy.problem1;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

/*
Optimal Substructure property - Student size can be split into smaller sizes and assigned cookies
Greedy Choice property - Exists
 */
public class AssignCookies {
    public static void main(String[] args) {
        Solution2 solution = new Solution2();

        // Test Case 1: Some students cannot be assigned cookies
        int[] students1 = {1, 2, 3};
        int[] cookies1 = {1, 1};
        assertEquals(1, solution.findMaximumCookieStudents(students1, cookies1), "Test Case 1 Failed");

        // Test Case 2: All students can be assigned cookies
        int[] students2 = {1, 2};
        int[] cookies2 = {1, 2, 3};
        assertEquals(2, solution.findMaximumCookieStudents(students2, cookies2), "Test Case 2 Failed");

        // Test Case 3: All students can be assigned cookies with larger sizes
        int[] students3 = {4, 5, 1};
        int[] cookies3 = {6, 4, 2};
        assertEquals(3, solution.findMaximumCookieStudents(students3, cookies3), "Test Case 3 Failed");

        // Test Case 4: No cookies available
        int[] students4 = {1, 2, 3};
        int[] cookies4 = {};
        assertEquals(0, solution.findMaximumCookieStudents(students4, cookies4), "Test Case 4 Failed");

        // Test Case 5: No students
        int[] students5 = {};
        int[] cookies5 = {1, 2, 3};
        assertEquals(0, solution.findMaximumCookieStudents(students5, cookies5), "Test Case 5 Failed");

        // Test Case 6: Students and cookies with the same size
        int[] students6 = {1, 2, 3};
        int[] cookies6 = {1, 2, 3};
        assertEquals(3, solution.findMaximumCookieStudents(students6, cookies6), "Test Case 6 Failed");

        // Test Case 7: Cookies smaller than all students' requirements
        int[] students7 = {5, 6, 7};
        int[] cookies7 = {1, 2, 3};
        assertEquals(0, solution.findMaximumCookieStudents(students7, cookies7), "Test Case 7 Failed");

    }
}

/*
Brute - Find smallest cookie and assign to smallest possible student
T - O(n^2 x m^2) - n - size of cookies, m - size of student
S - O(1)
 */
class Solution {
    public int findMaximumCookieStudents(int[] Student, int[] Cookie) {
        return -1;
    }
}

/*
Optimal - Two pointer approach
T - O(n logn) + O(m logm) + O(n+m)
 */
class Solution2 {
    public int findMaximumCookieStudents(int[] Student, int[] Cookie) {
        if (Cookie.length == 0) {
            return 0;
        }
        Arrays.sort(Student);
        Arrays.sort(Cookie);

        int count = 0;
        int c = 0;
        int s = 0;
        while (c < Cookie.length && s < Student.length) {
            if (Cookie[c] >= Student[s]) {
                count++;
                c++;
                s++;
            } else {
                c++;
            }
        }
        return count;
    }
}
