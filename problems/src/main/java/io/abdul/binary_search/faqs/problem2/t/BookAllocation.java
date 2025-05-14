package io.abdul.binary_search.faqs.problem2.t;

public class BookAllocation {

    public static int findPages(int[] arr, int m) {
        int n = arr.length;
        if (m > n) return -1; // not enough books

        int low = getMax(arr);
        int high = getSum(arr);
        int result = -1;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (isPossible(arr, m, mid)) {
                result = mid;
                high = mid - 1; // try for a better minimum
            } else {
                low = mid + 1;
            }
        }
        return result;
    }

    // Helper to check if allocation is possible with maxPages
    private static boolean isPossible(int[] arr, int m, int maxPages) {
        int students = 1;
        int sum = 0;
        for (int pages : arr) {
            if (sum + pages > maxPages) {
                students++;
                sum = pages;
                if (students > m) return false;
            } else {
                sum += pages;
            }
        }
        return true;
    }

    private static int getMax(int[] arr) {
        int max = arr[0];
        for (int num : arr) max = Math.max(max, num);
        return max;
    }

    private static int getSum(int[] arr) {
        int sum = 0;
        for (int num : arr) sum += num;
        return sum;
    }

    // Example usage
    public static void main(String[] args) {
        int[] books = {462, 450, 652, 363, 96, 968, 493, 947, 669, 559,
                       758, 924, 931, 621, 334, 942, 724, 521, 227, 216,
                       781, 189, 758, 215, 278, 692, 125, 621, 281};
        int students = 22;
        System.out.println("Minimum pages: " + findPages(books, students));
        System.out.println(findPages(new int[]{462, 450, 652, 363, 96, 968, 493, 947, 669, 559, 758, 924, 931, 621, 334, 942, 724, 521, 227, 216, 781, 189, 758, 215, 278, 692, 125, 621, 281}, 22));

    }
}
