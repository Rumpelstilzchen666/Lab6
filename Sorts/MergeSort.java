package Sorts;

public class MergeSort {
    public static int nArrs = 4;

    public static <T extends Comparable<? super T>> void mergeSort(
            T[] array) {
        int n = array.length;
        Comparable<? super T>[][] arrs = new Comparable[nArrs][n];
        for(int i = 0; i < nArrs; i++)
            arrs[i] = new Comparable[n];

        int tn, l = 1, i, j, k, nMin;
        int[] atl = new int[nArrs];
        int[] atn = new int[nArrs];
        int[] attn = new int[nArrs];
        do {
            for(i = 0; i < nArrs; i++)
                atl[i] = 0;
            for(i = 0; i < n; ) {
                for(j = 0; j < nArrs && i < n; j++) {
                    for(k = 0; k < l && i < n; k++, atl[j]++, i++) {
                        arrs[j][atl[j]] = array[i];
                    }
                }
            }
            for(i = 0; i < nArrs; i++) {
                attn[i] = 0;
                atn[i] = 0;
            }
            tn = 0;
            for(k = 0; k < Math.ceil(n / (double) (nArrs * l)); k++) {
                for(j = 0; j < Math.min(nArrs * l, n - k * l * nArrs);
                    j++, tn++) {
                    nMin = -1;
                    for(i = 0; i < nArrs; i++) {
                        if(attn[i] - atn[i] < l && attn[i] < atl[i]) {
                            if(nMin == -1 || arrs[i][attn[i]].compareTo(
                                    (T) arrs[nMin][attn[nMin]]) < 0)
                                nMin = i;
                        }
                    }
                    array[tn] = (T) arrs[nMin][attn[nMin]];
                    attn[nMin]++;
                }
                for(i = 0; i < nArrs; i++) {
                    atn[i] = attn[i];
                }
            }
            l *= nArrs;
        } while(l < n);
    }

    public static <T extends Comparable<? super T>> void recursiveMergeSort(
            T[] array) {
        recursiveMergeSort(array, array.length);
    }

    private static <T extends Comparable<? super T>> void recursiveMergeSort(
            T[] array, int n) {
        Comparable<? super T>[][] arrs = new Comparable[nArrs][];
        for(int i = 0; i < nArrs; i++)
            arrs[i] = new Comparable[n];

        int i, j, nMin;
        int[] atl = new int[nArrs];
        int[] atn = new int[nArrs];
        for(i = 0; i < nArrs; i++)
            atl[i] = 0;
        for(i = 0; i < n; ) {
            for(j = 0; j < nArrs && i < n; j++, i++) {
                arrs[j][atl[j]] = array[i];
                atl[j]++;
            }
        }
        for(i = 0; i < nArrs; i++) {
            atn[i] = 0;
            if(atl[i] > 1) {
                recursiveMergeSort((T[]) arrs[i], atl[i]);
            }
        }

        for(j = 0; j < n; j++) {
            nMin = -1;
            for(i = 0; i < nArrs; i++) {
                if(atn[i] < atl[i]) {
                    if(nMin == -1 || arrs[i][atn[i]].compareTo(
                            (T) arrs[nMin][atn[nMin]]) < 0)
                        nMin = i;
                }
            }
            array[j] = (T) arrs[nMin][atn[nMin]];
            atn[nMin]++;
        }
    }
}