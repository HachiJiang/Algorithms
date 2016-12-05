import edu.princeton.cs.algs4.StdRandom;

/**
 * Created by jianghong on 2016/11/20.
 */
public class SelectionSort {
    /**
     * Swap two items in array
     * @param arr {int[]}
     * @param i {int}
     * @param j {int}
     */
    private static void exch(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    /**
     * Check whether the first item is less than the second one
     * @param arr {int[]}
     * @param i {int}
     * @param j {int}
     * @return {boolean}
     */
    private static boolean less(int[] arr, int i, int j) {
        return arr[i] < arr[j];
    }

    /**
     * Find the position of smallest one;
     * @param arr {int[]}
     * @param startIdx {int}
     * @return {int}
     */
    private static int findSmallest(int[] arr, int startIdx) {
        int len = arr.length;
        if (startIdx >= len) {
            throw new java.util.NoSuchElementException();
        }
        int min = startIdx;
        for (int i = startIdx + 1; i < len; i++) {
            if (less(arr, i, min)) {
                min = i;
            }
        }
        return min;
    }

    /**
     * Selection sort
     * @param arr {int[]}
     */
    private static void SelectionSort(int[] arr) {
        for (int i = 0, il = arr.length; i < il; i++) {
            exch(arr, i, findSmallest(arr, i));
        }
    }

    /**
     * Insertion sort
     * @param arr {int[]}
     */
    private static void InsertionSort(int[] arr) {
        int len = arr.length;
        if (len < 1) {
            return;
        }

        for (int i = 1; i < len; i++) {
            for (int j = i; j > 0 && less(arr, j, j - 1); j--) {
                exch(arr, j, j-1);
            }
        }
    }
    private static void InsertionSort(int[] arr, int lo, int hi) {
        int len = arr.length;
        if (len < 1 || lo > hi) {
            return;
        }

        for (int i = lo; i <= hi; i++) {
            for (int j = i; j >= lo && j > 0 && less(arr, j, j - 1); j--) {
                exch(arr, j, j - 1);
            }
        }
    }

    /**
     * Shell sort
     * @param arr {int[]}
     */
    private static void shellSort(int[] arr) {
        int len = arr.length;
        if (len < 1) {
            return;
        }

        int h = 1;
        while (h < len / 3) h = 3 * h + 1;

        while(h >= 1) {
            for (int i = h; i < len; i++) {
                for (int j = i; j >= h && less(arr, j, j - h); j -= h) {
                    exch(arr, j, j-h);
                }
            }
            h = h / 3;
        }
    }

    private static void merge(int[] arr, int[] aux, int lo, int mid, int hi) {
        for (int i = lo; i <= hi; i++) {
            aux[i] = arr[i];
        }

        int i = lo, j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            if (i > mid) arr[k] = aux[j++];
            else if (j > hi) arr[k] = aux[i++];
            else if (less(aux, i, j)) arr[k] = aux[i++];
            else arr[k] = aux[j++];
        }
    }

    private static final int CUTOFF = 7;
    private static void mergeSorting(int[] arr, int[] aux, int lo, int hi) {
        if (hi <= lo + CUTOFF - 1) {
            InsertionSort(arr, lo, hi);
            return;
        }
        int mid = lo + (hi - lo) / 2;
        mergeSorting(arr, aux, lo, mid);
        mergeSorting(arr, aux, mid + 1, hi);
        if (less(arr, mid, mid + 1)) return;
        merge(arr, aux, lo, mid, hi);
    }

    /**
     * Merge sort
     * @param arr {int[]}
     */
    private static void mergeSort(int[] arr) {
        int[] aux = new int[arr.length];
        mergeSorting(arr, aux, 0, arr.length - 1);
    }

    private static int partition(int[] arr, int lo, int hi) {
        int i = lo, j = hi + 1;
        while (true) {
            while (less(arr, ++i, lo))
                if (i == hi) break;

            while (less(arr, lo, --j))
                if (j == lo) break;

            if (j <= i) break;
            else exch(arr, i, j);
        }

        exch(arr, lo, j);
        return j;
    }

    private static void quickSorting(int[] arr, int lo, int hi) {
        if (hi <= lo) return;
        int j = partition(arr, lo, hi);
        quickSorting(arr, lo, j - 1);
        quickSorting(arr, j + 1, hi);
    }

    private static void quickSort(int[] arr) {
        StdRandom.shuffle(arr);
        quickSorting(arr, 0, arr.length - 1);
    }

    public static void main(String[] args) {
        int[] arr = new int[]{10, 9, 8, 7, 6, 5, 7, 5, 12, 32, 11, 0};
        quickSort(arr);
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }
    }

}
