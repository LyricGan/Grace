package com.lyric.grace.xy;

/**
 * 冒泡排序<br/>
 * 将被排序的记录数组R[1..n]垂直排列，每个记录R[i]看作是重量为R[i].key的气泡。
 * 根据轻气泡不能在重气泡之下的原则，从下往上扫描数组 R：凡扫描到违反本原则的轻气泡，就使其向上"飘浮"。
 * 如此反复进行，直到最后任何两个气泡都是轻者在上，重者在下为止。
 *
 * <li>将小的元素往前移或者把大的元素往后移</li>
 * <li>如果两个元素相等，不作交换</li>
 * <li>如果两个相等的元素没有相邻，即使通过移动交换使元素相邻，也不会交换</li>
 *
 * 相同元素的前后顺序并没有改变，所以冒泡排序是一种稳定的排序算法。<br/>
 *
 * <li>时间复杂度 o(n^2)</li>
 * <li>空间复杂度 o(1)</li>
 * <li>比较次数 n(n+1)/2</li>
 *
 * @author lyricgan
 * @since 2018/12/6
 */
public class BubbleSort {

    /**
     * 冒泡排序
     * @param array 排序数组
     * @return 排序后的数组
     */
    public static int[] array(int[] array) {
        if (array == null || array.length == 0) {
            return array;
        }
        int length = array.length;
        int temp;
        for (int i = length - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (array[j] > array[j + 1]) {
                    temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
        return array;
    }

    /**
     * 双向冒泡排序
     * @param array 排序数组
     * @param min 数组最小索引
     * @param max 数组最大索引
     * @return 排序后的数组
     */
    public static int[] array(int[] array, int min, int max) {
        if (array == null || array.length == 0) {
            return array;
        }
        int i;
        int temp;
        while (min < max) {
            for (i = min; i < max; i++) {
                if (array[i] > array[i + 1]) {
                    temp = array[i];
                    array[i] = array[i + 1];
                    array[i + 1] = temp;
                }
            }
            max--;
            for (i = max; i > min; i--) {
                if (array[i] < array[i - 1]) {
                    temp = array[i];
                    array[i] = array[i - 1];
                    array[i - 1] = temp;
                }
            }
            min++;
        }
        return array;
    }
}
