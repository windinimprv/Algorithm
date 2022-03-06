package com.windin.untitled;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

class dp {

    public static void main(String[] args) {
        dp dp = new dp();
        utils.println("coins: ");
        utils.println(dp.coins(new int[]{2, 3, 5, 10}, 8));
        utils.println("apples: ");
        utils.println(dp.apples(new int[][]{
            {2, 3, 5, 10},
            {2, 3, 5, 10},
            {2, 3, 5, 10},
        }));
        utils.println("editDistance: ");
        utils.println(dp.editDistance("asdf", "assssdfsdv"));;
        utils.println(dp.lcs("s1abc", "s2bc"));
    }

    /**
     * 硬币找零
     * 假设有几种硬币，如1、3、5，并且数量无限。请找出能够组成某个数目的找零所使用最少的硬币数。 
     * f(x) = min(f(x - 1), f(x - 3), f(x - 5)) + 1;
     */
    private int coins(int[] n, int m) {
        if (n == null || n.length == 0) return 0;
        if (m <= 0) return 0;
        int[] f = new int[m + 1];
        Arrays.fill(f, Integer.MAX_VALUE);
        f[0] = 0;
        for (int i = 1; i < f.length; i++) {
            for (int j = 0; j < n.length; j++) {
                if (i - n[j] >= 0 && f[i - n[j]] != Integer.MAX_VALUE) {
                    f[i] = Math.min(f[i], f[i - n[j]]) + 1;
                }
            }
        }
        return f[m];
    }

    /**
     * 扩展
     * 一个矩形区域被划分为N*M个小矩形格子，在格子(i,j)中有A[i][j]个苹果。现在从左上角的格子(1,1)出发，要求每次只能向右走一步或向下走一步，最后到达(N,M)，每经过一个格子就把其中的苹果全部拿走。请找出能拿到最多苹果数的路线。
     * f(n, m) = max(f(n - 1, m), f(n, m - 1)) + A[n - 1][m - 1]
     * 
     * 这里偷懒改成返回最大个数
     */
    private int apples(int[][] apples) {
        int[][] ans = new int[apples.length][apples[0].length];
        if (apples == null || apples.length == 0 || apples[0].length == 0) return 0;
        ans[0][0] = apples[0][0];
        for (int i = 0; i < ans.length; i++) {
            for (int j = 0; j < ans[0].length; j++) {
                if (i == 0 && j == 0) continue;
                if (i == 0) ans[i][j] = ans[i][j - 1] + apples[i][j];
                else if (j == 0) ans[i][j] = ans[i - 1][j] + apples[i][j];
                else ans[i][j] = Math.max(ans[i - 1][j], ans[i][j - 1]) + apples[i][j];
            }
        }
        return ans[apples.length - 1][apples[0].length - 1];
    }

    /**
     * 字符串相似度/编辑距离（edit distance）
     * /对于序列S和T，它们之间距离定义为：对二者其一进行几次以下的操作(1)删去一个字符；(2)插入一个字符；(3)改变一个字符。每进行一次操作，计数增加1。将S和T变为同一个字符串的最小计数即为它们的距离。给出相应算法。
     */
    private int editDistance(String s1, String s2) {
        if (s1 == null && s2 == null) return 0;
        if (s1 == null || s1.length() == 0) return s2.length();
        if (s2 == null || s2.length() == 0) return s1.length();
        int[][] dif = new int[s1.length() + 1][s2.length() + 1];

        for (int i = 0; i < dif.length; i++) {
            dif[i][0] = i;
        }
        for (int i = 0; i < dif[0].length; i++) {
            dif[0][i] = i;
        }
        for (int i = 1; i < dif.length; i++) {
            for (int j = 1; j < dif[0].length; j++) {
                // 比较末位是否相同判断是否要发起一次操作
                int tmp = s1.charAt(i - 1) == s2.charAt(j - 1) ? 0 : 1;
                // dif[i][j] = Math.min(Math.min(dif[i - 1][j - 1], dif[i][j - 1]), dif[i - 1][j]) + tmp; 这里的写法是错的，值得注意
                dif[i][j] = Math.min(Math.min(dif[i - 1][j - 1] + tmp, dif[i][j - 1] + 1), dif[i - 1][j] + 1);
            }
        }
        return dif[s1.length()][s2.length()];
    }

    /**
     * 子串匹配
     * @return
     */

    /**
     * lcs
     * 最长公共子序列
     * @param s1
     * @param s2
     */
    private int lcs(String s1, String s2) {
        if (s1 == null || s2 == null || s1.length() == 0 || s2.length() == 0) return 0;
        int[][] dp = new int[s1.length() + 1][s2.length() + 1];
        for (int i = 1; i < dp.length; i++) {
            for (int j = 1; j < dp[0].length; j++) {
                // 不用写得这么麻烦
                // dp[i][j] = s1.charAt(i - 1) == s2.charAt(j - 1) 
                // ? Math.max(Math.max(dp[i - 1][j], dp[i][j - 1]), dp[i - 1][j - 1] + 1)
                // : Math.max(Math.max(dp[i - 1][j], dp[i][j - 1]), dp[i - 1][j - 1]);
                dp[i][j] = s1.charAt(i - 1) == s2.charAt(j - 1) 
                ? dp[i - 1][j - 1] + 1
                : Math.max(dp[i - 1][j], dp[i][j - 1]);
            }
        }
        return dp[s1.length()][s2.length()];
    }

    /**
     * lis
     * 最长递增子序列
     * 如1，-1，2，-3，4，-5，6，-7，其最长第增子序列为1,2,4,6。
     */
    private int lis(int[] n) {
        return 0;
    }

    /**
     * 最大连续子序列和/积
     * 输入是具有n个数的向量x，输出时输入向量的任何连续子向量的最大和。
     */
}