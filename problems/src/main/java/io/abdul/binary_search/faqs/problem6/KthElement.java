package io.abdul.binary_search.faqs.problem6;

public class KthElement {
    public static void main(String[] args) {
        Solution solution = new Solution();
//        System.out.println(solution.kthElement(new int[]{10, 11}, new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9}, 7));
//        System.out.println(solution.kthElement(new int[]{1, 10, 11}, new int[]{2, 3, 4, 5, 6, 7, 8, 9}, 7));
//        System.out.println(solution.kthElement(new int[]{1, 2, 10, 11}, new int[]{3, 4, 5, 6, 7, 8, 9}, 7));
//        System.out.println(solution.kthElement(new int[]{1, 2, 3, 4, 5, 6, 7, 10, 11}, new int[]{8, 9}, 7));
//        System.out.println(solution.kthElement(new int[]{2, 3, 6, 7, 9}, new int[]{1, 4, 8, 10}, 5));
//        System.out.println(solution.kthElement(new int[]{100, 112, 256, 349, 770}, new int[]{72, 86, 113, 119, 265, 445, 892}, 7));
//        System.out.println(solution.kthElement(new int[]{2, 3, 6}, new int[]{7, 9}, 4));
//        System.out.println(solution.kthElement(new int[]{15, 73, 104, 117, 221, 238, 365, 377, 395, 418, 450, 538, 603, 616, 634, 694, 718, 750, 754, 812, 817, 831, 873, 886, 921, 965, 978, 984, 985}, new int[]{5, 44, 210, 592}, 8));
        System.out.println(solution.kthElement(new int[]{73, 77, 113, 138, 143, 181, 239, 418, 439, 457, 501, 503, 508, 607, 663, 693, 721, 724, 727, 739, 795, 801, 849, 861, 877, 925, 971, 972, 972}, new int[]{354, 813}, 18));
    }
}

/*
Optimal - Modified Binary Search

Find elements who will be part of first k elements
Take left half incl mid from a and take remaining from b
If not enough elements there in b, expand a

1 2 3 4 5 6 7 8 9 10 11 and k = 7
No element from a
[10, 11] [1,2,3,4,5,6,7,8,9]
1 element from a
[1, 10, 11] [2,3,4,5,6,7,8,9]
2 element from a
[1, 2, 10, 11] [3,4,5,6,7,8,9]
All element from a
[1, 2, 3,4,5,6,7,10, 11] [8,9]
 */
class Solution {
    public int kthElement(int[] a, int[] b, int k) {
//        if (a.length > b.length) {
//            return kthElement(b, a, k);
//            /*
//            First array has to be smaller. Why?
//            high will be either k-1 or lesser than that
//            If smaller array
//             */
//        }
        int n1 = a.length, n2 = b.length;
        int low = 0;
        int requiredPos = k - 1;
        int high = Math.min(requiredPos, a.length - 1); // Max we only need k elements

        while (low <= high) {
            int mid = (low + high) / 2; // mid = 3, mid2 = 6-3-1 = 2

            int mid2 = requiredPos - mid - 1;

            if (mid2 > n2-1) { // if b is smaller, then expand a
                low = mid+1;
                continue;
            }

            int l1 = a[mid];
            int l2 = mid2 >= 0 && mid2 < n2 ? b[mid2] : Integer.MIN_VALUE;
            int r1 = mid + 1 >= 0 && mid + 1 < n1 ? a[mid + 1] : Integer.MAX_VALUE;
            int r2 = mid2 + 1 >= 0 && mid2 + 1 < n2 ? b[mid2 + 1] : Integer.MAX_VALUE;

            if (l1 > r2) {
                high = mid - 1;
            } else if (l2 > r1) {
                low = mid + 1;
            } else {
                return Math.max(l1, l2);
            }
        }

        // No element picked from a
        return b[requiredPos];
    }
}
