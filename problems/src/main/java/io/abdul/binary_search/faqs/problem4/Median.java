package io.abdul.binary_search.faqs.problem4;

/*
Case 1: 1 2 3 4 5 6 7 8 9 10 11 -> 6
Ex1: arr1=[1,3,5,7,8,9,11] arr2=[2,4,6,8,10]
Result - arr1=[1,3,5] arr2=[2,4,6]

Ex2: arr1=[1,2,5,6,9,10] arr2=[3,4,7,8,11]
Result - arr1=[1,2,5,6] arr2=[3,4]

Ex3: arr1=[1,2,3,4,5,6] arr2=[7,8,9,10,11]
Result - arr1=[1,2,3,4,5,6] arr2=[]

Ex4: arr1=[7,8,9,10,11] arr2=[1,2,3,4,5,6]
Result - arr1=[] arr2=[1,2,3,4,5,6]

Ex5: arr1=[1] arr2=[2,3,4,5,67,8,9,10,11]
Result - arr1=[1] arr2=[2,3,4,5,6]

Ex6: arr1=[2,3,4,5,67,8,9,10,11] arr2=[1]
Result - arr1=[2,3,4,5,6] arr2=[1]

In odd case, we need the left half + 1 th element. If n is 11, we need 6th element or 5th position.
So we take one of the arrays, take left part and for remaining take from right part.
Check if it's a valid pick, meaning, max of first left part should be lesser than min of second right part. Otherwise, first part to be shrunk and second part has to be expanded.
max of second left part should be lesser than min of first right part. Otherwise, second left part has to be shrunk and first left part has to expand.
If both are in right sides, means we've picked the correct elements of left part.
For the last of left half, last of first-left and last second-left are in consideration. Take the max as we need it to be sorted.

Case 2: 1 2 3 4 5 6 7 8 9 10 -> 5 & 6
Ex1: arr1=[1,3,5,7,8,9] arr2=[2,4,6,8,10]
Result - arr1=[1,3,5] arr2=[2,4]

Ex2: arr1=[1,2,5,6,9,10] arr2=[3,4,7,8]
Result - arr1=[1,2,5] arr2=[3,4]

Ex3: arr1=[1,2,3,4,5,6] arr2=[7,8,9,10]
Result - arr1=[1,2,3,4,5] arr2=[]

Ex4: arr1=[7,8,9,10] arr2=[1,2,3,4,5,6]
Result - arr1=[] arr2=[1,2,3,4,5]

Ex5: arr1=[1] arr2=[2,3,4,5,67,8,9,10]
Result - arr1=[1] arr2=[2,3,4,5]

Ex6: arr1=[2,3,4,5,67,8,9,10] arr2=[1]
Result - arr1=[2,3,4,5] arr2=[1]
 */
public class Median {
    public static void main(String[] args) {
//        Solution solution = new Solution();
        Solution2 solution = new Solution2();
        System.out.println(solution.median(new int[]{2, 4, 6}, new int[]{1, 3, 5}));
        System.out.println(solution.median(new int[]{2, 4, 6}, new int[]{1, 3}));
        System.out.println(solution.median(new int[]{2, 4, 5}, new int[]{1, 6}));
        System.out.println(solution.median(new int[]{2, 4, 6}, new int[]{1, 3, 7, 9}));
        System.out.println(solution.median(new int[]{1, 11, 13, 14}, new int[]{1, 2, 3, 4, 5, 6}));
        System.out.println(solution.median(new int[]{-959, -929, -543, -541, -503, -278, -240, -211, -151, -51, 140, 151, 174, 268, 364, 533, 799, 855, 926}, new int[]{838}));
        System.out.println(solution.median(new int[]{4}, new int[]{1, 2, 3, 5, 6}));
        System.out.println(solution.median(new int[]{1, 3, 5, 7, 9, 11}, new int[]{2, 4, 6, 8, 10}));
        System.out.println(solution.median(new int[]{1, 2, 5, 6, 9, 10}, new int[]{3, 4, 7, 8, 11}));
        System.out.println(solution.median(new int[]{1, 2, 3, 4, 5, 6}, new int[]{7, 8, 9, 10, 11}));
        System.out.println(solution.median(new int[]{7, 8, 9, 10, 11}, new int[]{1, 2, 3, 4, 5, 6}));
        System.out.println(solution.median(new int[]{1}, new int[]{2, 3, 4, 5, 6, 7, 8, 9, 10, 11}));
        System.out.println(solution.median(new int[]{2, 3, 4, 5, 6, 7, 8, 9, 10, 11}, new int[]{1}));
        System.out.println(solution.median(new int[]{1, 3, 5, 7, 9}, new int[]{2, 4, 6, 8, 10}));
        System.out.println(solution.median(new int[]{1, 2, 5, 6, 9, 10}, new int[]{3, 4, 7, 8}));
        System.out.println(solution.median(new int[]{1, 2, 3, 4, 5, 6}, new int[]{7, 8, 9, 10}));
        System.out.println(solution.median(new int[]{7, 8, 9, 10}, new int[]{1, 2, 3, 4, 5, 6}));
        System.out.println(solution.median(new int[]{1}, new int[]{2, 3, 4, 5, 6, 7, 8, 9, 10}));
        System.out.println(solution.median(new int[]{2, 3, 4, 5, 6, 7, 8, 9, 10}, new int[]{1}));
        System.out.println(solution.median(new int[]{8, 9, 10, 11}, new int[]{1, 2, 3, 4, 5, 6, 7})); // Out of while loop
        System.out.println(solution.median(new int[]{1, 9, 10, 11}, new int[]{2, 3, 4, 5, 6, 7, 8}));
        System.out.println(solution.median(new int[]{1, 2, 10, 11}, new int[]{3, 4, 5, 6, 7, 8, 9}));
        System.out.println(solution.median(new int[]{1, 2, 3, 11}, new int[]{4, 5, 6, 7, 8, 9, 10}));
        System.out.println(solution.median(new int[]{1, 2, 3, 4}, new int[]{5, 6, 7, 8, 9, 10, 11}));
        System.out.println(solution.median(new int[]{1, 2, 3, 4, 5}, new int[]{6, 7, 8, 9, 10, 11}));
    }
}

/*
Brute - Linear Search
 */
class Solution {
    public double median(int[] arr1, int[] arr2) {
        int left = 0, right = 0;
        int total = arr1.length + arr2.length;
        int requiredPos = total / 2;
        int requiredElement = -1, prevElement = -1;
        int sortedCount = 0;
        boolean found = false;

        while (left < arr1.length && right < arr2.length) {
            if (arr1[left] < arr2[right]) {
                prevElement = requiredElement;
                requiredElement = arr1[left];
                left++;
            } else {
                prevElement = requiredElement;
                requiredElement = arr2[right];
                right++;
            }
            sortedCount++;
            if (sortedCount - 1 == requiredPos) {
                found = true;
                break;
            }
        }

        if (!found) {
            while (left < arr1.length) {
                prevElement = requiredElement;
                requiredElement = arr1[left];
                left++;

                sortedCount++;
                if (sortedCount - 1 == requiredPos) {
                    found = true;
                    break;
                }
            }
        }

        if (!found) {
            while (right < arr2.length) {
                prevElement = requiredElement;
                requiredElement = arr2[right];
                right++;

                sortedCount++;
                if (sortedCount - 1 == requiredPos) {
                    found = true;
                    break;
                }
            }
        }

        return (total & 1) == 0 ? (double) (requiredElement + prevElement) / 2 : requiredElement;
    }
}

/*
Optimal - Modified Binary Search
T - O(log n1) -> O(log min(n1,n2)
S - O(1)

Pick elements for left part as above. But for the first of right half, it'll either be first of first-right half or first of second-right half. Take the minimum as we need sorted.

 */
class Solution2 {
    public double median(int[] arr1, int[] arr2) {
        int n1 = arr1.length;
        int n2 = arr2.length;

        // To convert TC from O(log n1) to O(log min(n1,n2), completely optional step
        if (n2 < n1) {
            return median(arr2, arr1);
        }

        int n = n1 + n2;
        int nMid = (n - 1) / 2; // 0-based n=10 nMid=4; n=11 nMid=5

        int low = 0, high = n1 - 1;
        int mid2 = 0;
        // Looking for elements of left half from arr1.
        // If no elements can be part of it, loop will exit unsuccessfully with high as -1
        // If all elements can be part of it, at the last element rightMin1 will be Int.MAX and leftMax2 can't be bigger than that. So loop will end successfully
        while (low <= high) {
            int mid1 = (low + high) >>> 1; // mid can go to any single element at the end from 0 to n-1
            mid2 = nMid - mid1 - 1; // n=11, nMid=5, mid1 = 1, mid2=3; mid1=5, mid2=-1;

            int leftMax1 = mid1 >= 0 && mid1 < n1 ? arr1[mid1] : Integer.MIN_VALUE;
            int leftMax2 = mid2 >= 0 && mid2 < n2 ? arr2[mid2] : Integer.MIN_VALUE;
            int rightMin1 = mid1 >= -1 && mid1 < n1 - 1 ? arr1[mid1 + 1] : Integer.MAX_VALUE;
            int rightMin2 = mid2 >= -1 && mid2 < n2 - 1 ? arr2[mid2 + 1] : Integer.MAX_VALUE;

            if (leftMax1 > rightMin2) { // arr1 to shrink and arr2 to expand
                high = mid1 - 1;
            } else if (leftMax2 > rightMin1) { // arr2 to shrink and arr1 to expand
                low = mid1 + 1;
            } else { // Both are in right groups, so this is the required to be left and right halves
                int leftHalfMax = Math.max(leftMax1, leftMax2); // Last of left half will be max of (max of arr1 and arr2)
                int rightHalfMin = Math.min(rightMin1, rightMin2); // First of right half will be min of (min of arr1 and arr2)
                if ((n & 1) == 1) { // Odd
                    return leftHalfMax;
                } else {
                    return (leftHalfMax + rightHalfMin) / 2.0;
                }
            }
        }

        // If it comes here, no elements have been picked from arr1 for left half, last element also didn't make up.
        // Means first element in arr1 will be in any position of right half. So rightHalfMin will be minimum of arr1 first element arr2 next element
        // So we take next element in arr2 as leftHalfMax and next to that as rightHalfMin
        mid2++; // last element of arr1 couldn't make for left half, so we take that one from arr2
        int leftHalfMax = arr2[mid2];
        if ((n & 1) == 1) { // Odd
            return leftHalfMax;
        } else {
            int rightMin1 = n1 > 0 ? arr1[0] : Integer.MAX_VALUE;
            int rightMin2 = mid2 >= -1 && mid2 < n2 - 1 ? arr2[mid2 + 1] : Integer.MAX_VALUE;
            int rightHalfMin = Math.min(rightMin1, rightMin2); // check to avoid Out Of Bounds error when all elements from right half picked and a odd n case
            return (leftHalfMax + rightHalfMin) / 2.0;
        }
    }
}

class Solution3 {
    public double median(int[] arr1, int[] arr2) {
        int n1 = arr1.length;
        int n2 = arr2.length;

        // To convert TC from O(log n1) to O(log min(n1,n2), completely optional step
        if (n2 < n1) {
            return median(arr2, arr1);
        }

        int n = n1 + n2;
        int nMid = (n - 1) / 2; // 0-based n=10 nMid=4; n=11 nMid=5

        // Is no element from arr1 part of mid

        int low = 0, high = n1 - 1;
        int mid2 = 0;
        // Looking for elements of left half from arr1.
        // If no elements can be part of it, loop will exit unsuccessfully with high as -1
        // If all elements can be part of it, at the last element rightMin1 will be Int.MAX and leftMax2 can't be bigger than that. So loop will end successfully
        while (low <= high) {
            int mid1 = (low + high) >>> 1; // mid can go to any single element at the end from 0 to n-1
            mid2 = nMid - mid1 - 1; // n=11, nMid=5, mid1 = 1, mid2=3; mid1=5, mid2=-1;

            int leftMax1 = mid1 >= 0 && mid1 < n1 ? arr1[mid1] : Integer.MIN_VALUE;
            int leftMax2 = mid2 >= 0 && mid2 < n2 ? arr2[mid2] : Integer.MIN_VALUE;
            int rightMin1 = mid1 >= -1 && mid1 < n1 - 1 ? arr1[mid1 + 1] : Integer.MAX_VALUE;
            int rightMin2 = mid2 >= -1 && mid2 < n2 - 1 ? arr2[mid2 + 1] : Integer.MAX_VALUE;

            if (leftMax1 > rightMin2) { // arr1 to shrink and arr2 to expand
                high = mid1 - 1;
            } else if (leftMax2 > rightMin1) { // arr2 to shrink and arr1 to expand
                low = mid1 + 1;
            } else { // Both are in right groups, so this is the required to be left and right halves
                int leftHalfMax = Math.max(leftMax1, leftMax2); // Last of left half will be max of (max of arr1 and arr2)
                int rightHalfMin = Math.min(rightMin1, rightMin2); // First of right half will be min of (min of arr1 and arr2)
                if ((n & 1) == 1) { // Odd
                    return leftHalfMax;
                } else {
                    return (leftHalfMax + rightHalfMin) / 2.0;
                }
            }
        }

        // If it comes here, no elements have been picked from arr1 for left half, last element also didn't make up.
        // Means first element in arr1 will be in any position of right half. So rightHalfMin will be minimum of arr1 first element arr2 next element
        // So we take next element in arr2 as leftHalfMax and next to that as rightHalfMin
        mid2++; // last element of arr1 couldn't make for left half, so we take that one from arr2
        int leftHalfMax = arr2[mid2];
        if ((n & 1) == 1) { // Odd
            return leftHalfMax;
        } else {
            int rightMin1 = n1 > 0 ? arr1[0] : Integer.MAX_VALUE;
            int rightMin2 = mid2 >= -1 && mid2 < n2 - 1 ? arr2[mid2 + 1] : Integer.MAX_VALUE;
            int rightHalfMin = Math.min(rightMin1, rightMin2); // check to avoid Out Of Bounds error when all elements from right half picked and a odd n case
            return (leftHalfMax + rightHalfMin) / 2.0;
        }
    }
}