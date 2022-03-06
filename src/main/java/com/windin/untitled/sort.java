package com.windin.untitled;

public class sort {
    public static void main(String[] args) {
        int[] tmp = new int[]{23, 423, 5, 347, 458, 4, 63, 5, 234, 12, 235, 32, 6};
//        new sort().insert_sort(tmp);
//        new sort().bubble_sort(tmp);
        new sort().selection_sort(tmp);
        utils.printArray(tmp);
    }

    /**
     * for n 循环，将每次0-n中的n向前交换到适当位置
     *
     * @param nums
     */
    private void insert_sort(int[] nums) {
        if (nums == null || nums.length == 0) return;
        for (int i = 1; i < nums.length; i++) {
            for (int j = i; j > 0; j--) {
                if (nums[j] < nums[j - 1]) {
                    swap(nums, j, j - 1);
                } else {
                    break;
                }
            }
        }
    }

    /**
     * 每次循环前两位两两比较前一位比后一位大就交换，这样最大的就会排在后面
     * 如无发生交换说明已排序完毕，有最好和最坏情况
     *
     * @param nums
     */
    private void bubble_sort(int[] nums) {
        if (nums == null || nums.length == 0) return;
        for (int i = 0; i < nums.length; i++) {
            boolean noSwap = true;
            for (int j = 1; j < nums.length - i; j++) {
                if (nums[j - 1] > nums[j]) {
                    swap(nums, j - 1, j);
                    noSwap = false;
                }
            }
            if (noSwap) {
                break;
            }
        }
    }

    /**
     * Warn: 没法一次过
     * 每次循环中，选取最小的数放在每次循环的第一位
     *
     * 不稳定排序
     * @param nums
     */
    private void selection_sort(int[] nums) {
        if (nums == null || nums.length == 0) return;
        for (int i = 0; i < nums.length; i++) {
            int minIndex = i;
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[minIndex] > nums[j]) {
                    minIndex = j;
                }
            }
            swap(nums, minIndex, i);
        }
    }

    /**
     * ? 没懂
     * @param arr
     */
    public static void shell_sort(int[] arr) {
        int length = arr.length;
        int temp;
        for (int step = length / 2; step >= 1; step /= 2) {
            for (int i = step; i < length; i++) {
                temp = arr[i];
                int j = i - step;
                while (j >= 0 && arr[j] > temp) {
                    arr[j + step] = arr[j];
                    j -= step;
                }
                arr[j + step] = temp;
            }
        }
    }

    /**
     * 归并排序 稳定排序
     * @param nums
     */
    private void merge_sort(int[] nums) {
        if (nums == null || nums.length == 0) return;

    }

    /**
     * ?
     * @param arr
     * @param left
     * @param right
     * @return
     */
    private int[] quickSort(int[] arr, int left, int right) {
        if (left < right) {
            int partitionIndex = partition(arr, left, right);
            quickSort(arr, left, partitionIndex - 1);
            quickSort(arr, partitionIndex + 1, right);
        }
        return arr;
    }

    private int partition(int[] arr, int left, int right) {
        // 设定基准值（pivot）
        int pivot = left;
        int index = pivot + 1;
        for (int i = index; i <= right; i++) {
            if (arr[i] < arr[pivot]) {
                swap(arr, i, index);
                index++;
            }
        }
        swap(arr, pivot, index - 1);
        return index - 1;
    }

    private void heap_sort() {

    }

    private void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }
}
