package Sorts;

public class InsertionSort {
    public static <T extends Comparable<? super T>> void insertionSort(
            T[] arr) {
        for(int i = 1; i < arr.length; i++) {
            if(arr[i].compareTo(arr[i - 1]) < 0) {
                for(int j = i; j > 0; j--) {
                    if(arr[j].compareTo(arr[j - 1]) < 0) {
                        T temp = arr[j];
                        arr[j] = arr[j - 1];
                        arr[j - 1] = temp;
                    }
                    else
                        break;
                }
            }
        }
    }
}