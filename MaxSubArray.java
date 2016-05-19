package algrithm;

/**
 * Created by zhangyy on 5/19/16.
 */
public class MaxSubArray {

    public static void main(String[] args) {
        int[] arr = new int[]{13, -3, -25, 20, -3, -16, -23, 18, 20, -7, 12, -5, -22, 15, -4, 7};

        System.out.println("--------分治算法-------");
        Result result = find_max_subarray(arr, 0, arr.length - 1);
        System.out.print("最大子数组为: ");
        for (int i = result.low; i <= result.high; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
        System.out.println("连续子数组的最大和为: " + result.sum);

        System.out.println("-------暴力算法-------");
        Result result1 = find_subarray(arr, 0, arr.length - 1);
        System.out.print("最大子数组为: ");
        for (int i = result1.low; i <= result1.high; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
        System.out.print("连续子数组的最大和为: " + result1.sum);
    }

    /**
     * 使用暴力算法直接求解
     *
     * @param arr
     * @param low
     * @param high
     * @return
     */
    public static Result find_subarray(int[] arr, int low, int high) {
        int result_sum = Integer.MIN_VALUE;
        int result_low = -1;
        int result_high = -1;
        for (int i = low; i <= high; i++) {
            int sum = 0;
            for (int j = i; j <= high; j++) {
                sum += arr[j];
                if (sum > result_sum) {
                    result_sum = sum;
                    result_low = i;
                    result_high = j;
                }
            }
        }
        return new Result(result_low, result_high, result_sum);
    }

    /**
     * 分治策略解决最大子数组的问题
     * 最大子数组:和最大的连续子数组
     *
     * @param arr
     * @param low
     * @param high
     * @return
     */

    public static Result find_max_subarray(int[] arr, int low, int high) {
        if (low == high) {
            return new Result(low, high, arr[low]);
        } else {
            int mid = (low + high) / 2;
            Result left_result = find_max_subarray(arr, low, mid);
            Result right_result = find_max_subarray(arr, mid + 1, high);
            Result cross_result = find_cross_subarray(arr, low, mid, high);

            if (left_result.sum > right_result.sum && left_result.sum > cross_result.sum)
                return left_result;
            else if (right_result.sum > left_result.sum && right_result.sum > cross_result.sum)
                return right_result;
            else
                return cross_result;
        }
    }


    private static Result find_cross_subarray(int[] arr, int low, int mid, int high) {
        int sum = 0;

        int left_sum = Integer.MIN_VALUE;
        int left_max = mid;
        for (int i = mid; i >= low; i--) {
            sum += arr[i];
            if (sum > left_sum) {
                left_sum = sum;
                left_max = i;
            }
        }

        sum = 0;
        int right_sum = Integer.MIN_VALUE;
        int right_max = mid + 1;
        for (int j = mid + 1; j <= high; j++) {
            sum += arr[j];
            if (sum > right_sum) {
                right_sum = sum;
                right_max = j;
            }
        }

        return new Result(left_max, right_max, left_sum + right_sum);
    }

    private static class Result {
        private int low;
        private int high;
        private int sum;

        public Result(int low, int high, int sum) {
            this.low = low;
            this.high = high;
            this.sum = sum;
        }

    }
}
