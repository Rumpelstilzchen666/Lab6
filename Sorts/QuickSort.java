package Sorts;

public class QuickSort {
    public static <T extends Comparable<? super T>> void QuickSort(T[] arr,
            int first, int last) {
        int i = first, j = last;
        T key = arr[(first + last) / 2];

        while(i < j) {
            while(arr[i].compareTo(key) > 0) {
                i++;
            }
            while(arr[j].compareTo(key) < 0) {
                j--;
            }

            if(i <= j) {
                if(i < j) {
                    T temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                }
                i++;
                j--;
            }
        }
        if(i < last) {
            QuickSort(arr, i, last);
        }
        if(first < j) {
            QuickSort(arr, first, j);
        }
    }
}