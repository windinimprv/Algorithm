package com.windin.untitled;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import com.windin.untitled.utils.*;

/**
 * 1606. 找到处理最多请求的服务器 todo 写的时候没有注意到题目中的i % k
 * 954. 二倍数对数组 todo
 * 420. 强密码检验器 todo 超过20字符后的条件难考虑
 * 796. 旋转字符串
 * 429. N 叉树的层序遍历
 * 357. 统计各位数字都不同的数字个数
 */
public class lc2 {
    
    public static void main(String[] args) {
        lc2 lcInstance = new lc2();
        lcInstance.run();
    }

    private void run() {
        utils.println(busiestServers(3, new int[]{1,2,3,4,5}, new int[]{5,2,3,3,3}));
        utils.println(busiestServers(3, new int[]{1,2,3,4}, new int[]{1,2,1,2}));
        utils.println(busiestServers(3, new int[]{1,2,3}, new int[]{10,12,11}));
        utils.println(busiestServers(3, new int[]{1,2,3,4,8,9,10}, new int[]{5,2,10,3,1,2,2}));
    }

    public List<Integer> busiestServers(int k, int[] arrival, int[] load) {
        return null;
    }

    /**
     * 429. N 叉树的层序遍历
     */
    class Solution {
        class Node {
            public int val;
            public List<Node> children;
        
            public Node() {}
        
            public Node(int _val) {
                val = _val;
            }
        
            public Node(int _val, List<Node> _children) {
                val = _val;
                children = _children;
            }
        };

        public List<List<Integer>> levelOrder(Node root) {
            List<List<Integer>> res = new ArrayList<>();
            List<Node> level = new ArrayList<>();
            level.add(root);
            levelOrderCore(res, level);
            return res;
        }

        private void levelOrderCore(List<List<Integer>> res, List<Node> level) {
            List<Integer> cur = new ArrayList<>();
            List<Node> next = new ArrayList<>();
            for (Node node : level) {
                cur.add(node.val);
                next.addAll(node.children);
            }
            res.add(cur);
            levelOrderCore(res, next);
        }
    }
}
