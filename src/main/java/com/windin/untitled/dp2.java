package com.windin.untitled;

public class dp2 {

    public static void main(String[] args) {
        utils.println(new dp2().lcs("s1abc", "s2bc"));
    }

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
        
    }

    /**
     * 最大连续子序列和/积
     * 输入是具有n个数的向量x，输出时输入向量的任何连续子向量的最大和。
     */
}
