package com.lyric.grace.xy;

/**
 * 快速排序
 * @author lyricgan
 * @since 2018/12/6
 */
public class QuickSort {

    public static int[] sort(int[] array, int low, int high) {
        if (array == null || array.length == 0) {
            return array;
        }
        if (low < high) {
            int middle = partition(array, low, high);
            sort(array, low, middle - 1);
            sort(array, middle + 1, high);
        }
        return array;
    }

    private static int partition(int[] array, int low, int high) {
        if (array == null || array.length == 0) {
            return 0;
        }
        int i = low - 1;
        int j;
        int temp;
        for (j = low; j < high; ++ j) {
            if (array[j] < array[high]) {
                temp = array[++i];
                array[i] = array[j];
                array[j] = temp;
            }
        }
        temp = array[i + 1];
        array[i + 1] = array[high];
        array[high] = temp;
        return (i + 1);
    }
}
