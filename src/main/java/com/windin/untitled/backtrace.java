package com.windin.untitled;

import java.util.List;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;

class backtrace {

    public static void main(String[] args) {
        backtrace bkt = new backtrace();
        utils.println("combinationSum: ");
        utils.printlnIntergerListList(bkt.combinationSum(new int[]{2, 3, 5}, 8));
        utils.println("combinationSumII: ");
        utils.printlnIntergerListList(bkt.combinationSum2(new int[]{10,1,2,7,6,1,5}, 8));
        utils.println("combinationSumIII: ");
        utils.printlnIntergerListList(bkt.combinationSum3(3, 9));
        utils.println("generateParenthesis: ");
        utils.println(bkt.generateParenthesis(3));
        utils.println("permute: ");
        utils.printlnIntergerListList(bkt.permute(new int[]{1,2,3}));
        utils.println("permute: ");
        utils.printlnIntergerListList(bkt.permuteUnique(new int[]{1,1,3}));
    }

    /**
     * Combination Sum
     * 给定一个无重复元素的数组 candidates 和一个目标数 target ，找出 candidates 中所有可以使数字和为 target 的组合。
     * candidates 中的数字可以无限制重复被选取。
     */
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> ans = new ArrayList<>();
        if (candidates == null || candidates.length == 0) return res;
        backtrace(candidates, target, 0, 0, ans, res);
        return res;
    }

    // 还可以优化为：先排序，如果当前sum结果是大于target的，for循环即可跳出.
    private void backtrace(int[] candidates, int target, int inx, int sum, List<Integer> ans, List<List<Integer>> res) {
        if (sum == target) {
            res.add(new ArrayList<>(ans));
            return;
        }
        if (sum > target)
            return;
        for (int i = inx; i < candidates.length; i++) {
            ans.add(candidates[i]);
            backtrace(candidates, target, i, sum + candidates[i], ans, res); // 这里的i写成了inx，导致重复数组的出现
            ans.remove(ans.size() - 1);
        }
    }

    /**
     * Combination Sum II
     * 给定一个数组 candidates 和一个目标数 target ，找出 candidates 中所有可以使数字和为 target 的组合。
     * candidates 中的每个数字在每个组合中只能使用一次。
     * todo
     */
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> ans = new ArrayList<>();
        if (candidates == null || candidates.length == 0) return res;
        Arrays.sort(candidates);
        dfs(candidates, target, candidates[0] - 1, 0, 0, ans, res);
        return res;
    }

    private void dfs(int[] candidates, int target, int pre, int inx, int sum, List<Integer> ans, List<List<Integer>> res) {
        if (sum > target) return;
        if (sum == target) {
            res.add(new ArrayList<>(ans));
            return;
        }
        for (int i = inx; i < candidates.length; i++) {
            // System.out.print(inx + ", " + i + ", ");
            // utils.println(ans);
            // 这个的判断要想明白,如1,1,7 有两个1，让第1个遍历后，到第2个1，第2个1可以添加，但后面的遍历要跳过，应该第1个遍历时已经有相同组合了
            // if (inx > 0 && i > inx && candidates[inx] == candidates[inx - 1]) continue;
            // 还有另一种写法
            if (i > inx && candidates[i] == candidates[i - 1]) continue;
            ans.add(candidates[i]);
            dfs(candidates, target, candidates[i], i + 1, sum + candidates[i], ans, res);
            ans.remove(ans.size() - 1);
        }
    }

    /**
     * Combination Sum III
     * 找出所有相加之和为 n 的 k 个数的组合。组合中只允许含有 1 - 9 的正整数，并且每种组合中不存在重复的数字。
     */
    public List<List<Integer>> combinationSum3(int k, int n) {
        List<List<Integer>> res = new ArrayList<>();
        if (n < 1 || k < 1) return res;
        List<Integer> ans = new ArrayList<>();
        dfs(k, n, 1, 0, ans, res);
        return res;
    }

    private void dfs(int k, int n, int start, int sum, List<Integer> ans, List<List<Integer>> res) {
        if (ans.size() == k && sum == n) {
            res.add(new ArrayList<>(ans));
            return;
        }
        if (sum > n) {
            return;
        }
        for (int i = start; i <= 9; i++) {
            ans.add(i);
            dfs(k, n, i + 1, sum + i, ans, res);
            ans.remove(ans.size() - 1);
        }
    }

    /**
     * Generate Parentheses
     * 给出 n 代表生成括号的对数，请你写出一个函数，使其能够生成所有可能的并且有效的括号组合。
     */
    public List<String> generateParenthesis(int n) {
        List<String> res = new ArrayList<>();
        if (n <= 0) return res;
        StringBuilder stringBuilder = new StringBuilder();
        dfs(n, 0, 0, stringBuilder, res);
        return res;
    }

    private void dfs(int n, int left, int right, StringBuilder stringBuilder, List<String> res) {
        // 判定条件
        if (left == right && stringBuilder.length() == 2 * n) {
            res.add(stringBuilder.toString());
            return;
        }
        // 超出条件
        if (stringBuilder.length() > 2 * n) return;
        // 递归
        if (left < n) {
            dfs(n, left + 1, right, stringBuilder.append("("), res);
            stringBuilder.delete(stringBuilder.length() - 1, stringBuilder.length());
        }
        if (left - right > 0) {
            dfs(n, left, right + 1, stringBuilder.append(")"), res);
            stringBuilder.delete(stringBuilder.length() - 1, stringBuilder.length());
        }
    }

    /**
     * Permutations
     * 给定一个没有重复数字的序列，返回其所有可能的全排列。
     * 可基于map也可基于交换
     * todo 基于交换的方法思维上更灵活，可以好好理解下
     * @param nums
     * @return
     */
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        if (nums == null || nums.length == 0) return res;
        dfs(nums, 0, res);
        return res;
    }
    
    private void dfs(int[] nums, int inx, List<List<Integer>> res) {
        if (inx == nums.length) {
            List<Integer> ans = new ArrayList<>();
            for (int i = 0; i < nums.length; i++) {
                ans.add(nums[i]);
            }
            res.add(ans);
            return;
        }
        if (inx >= nums.length) return;
        for (int i = inx; i < nums.length; i++) {
            utils.swap(nums, i, inx);
            dfs(nums, inx + 1, res);
            utils.swap(nums, i, inx);
        }
    }

    /**
     * Permutations II
     * 给定一个可包含重复数字的序列，返回所有不重复的全排列。
     * todo
     */
    public List<List<Integer>> permuteUnique(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        if (nums == null || nums.length == 0) return res;
        Arrays.sort(nums);
        backTrace(nums, 0, res);
        return res;
    }

    private void backTrace(int[] nums, int inx, List<List<Integer>> res) {
        if (inx == nums.length) {
            List<Integer> ans = new ArrayList<>();
            for (int i = 0; i < nums.length; i++) {
                ans.add(nums[i]);
            }
            res.add(ans);
            return;
        }
        for (int i = inx; i < nums.length; i++) {
            if (i > inx && nums[i] == nums[i - 1]) {
                continue;
            }
            utils.swap(nums, i, inx);
            dfs(nums, inx + 1, res);
            utils.swap(nums, i, inx);
        }
    }
}