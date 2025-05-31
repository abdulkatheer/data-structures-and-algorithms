package io.abdul.util;

public class SortTwoArrays {
    // Heap sort based on arr2
    public static void heapSort(int[] arr1, int[] arr2) {
        int n = arr2.length;

        // Build max heap
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr1, arr2, n, i);
        }

        // Extract elements one by one from the heap
        for (int i = n - 1; i >= 0; i--) {
            // Move current root to end
            swap(arr1, 0, i);
            swap(arr2, 0, i);

            // call max heapify on the reduced heap
            heapify(arr1, arr2, i, 0);
        }
    }

    private static void heapify(int[] arr1, int[] arr2, int n, int i) {
        int largest = i; // Initialize largest as root
        int left = 2 * i + 1; // left = 2*i + 1
        int right = 2 * i + 2; // right = 2*i + 2

        // If left child is larger than root
        if (left < n && arr2[left] > arr2[largest]) {
            largest = left;
        }

        // If right child is larger than largest so far
        if (right < n && arr2[right] > arr2[largest]) {
            largest = right;
        }

        // If largest is not root
        if (largest != i) {
            swap(arr2, i, largest);
            swap(arr1, i, largest);

            // Recursively heapify the affected sub-tree
            heapify(arr1, arr2, n, largest);
        }
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
