package com.windin.untitled;

import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import com.windin.untitled.utils.BTreePrinter;
import com.windin.untitled.utils.ListNode;
import com.windin.untitled.utils.TreeNode;

public class jzof {

    public static void main(String... args) {
        jzof fz = new jzof();
        boolean find = fz.find(new int[][]{
                {1, 3, 5, 10},
                {2, 5, 7, 11},
                {3, 6, 8, 13},
                {4, 8, 10, 14}}, 6);
        System.out.println(find);

        ListNode node = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5)))));
        utils.printListNode(fz.reverseNode(node));

        utils.println(String.valueOf(fz.minInRotateArray(new int[]{2, 3, 4, 5, 6})));

        int[] nums = new int[]{236,45,745,72,346,34,5,346,45,6,25,8,45,7,47,457,24,7,24,74,45,4,5};
        fz.reorder(nums);
        utils.printArray(nums);

        utils.println("match: " + fz.match("a", "ab*"));
        
        node = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5)))));
        ListNode findKthToTail = fz.findKthToTail(node, 2);
        utils.println(findKthToTail.val);
        
        ListNode node1 = utils.generateListNodes(new int[]{1,2,4,23,56,787,282457});
        ListNode node2 = utils.generateListNodes(new int[]{5,23,523});
        utils.printListNode(fz.merge(node1, node2));

        TreeNode root = new TreeNode(1,
         new TreeNode(2,
         new TreeNode(124, 
         new TreeNode(2345,
         new TreeNode(13), null), new TreeNode(245125)), new TreeNode(224)), new TreeNode(3, new TreeNode(12341), null));
         TreeNode sub =  new TreeNode(124, 
         new TreeNode(2345,
         new TreeNode(13), null), null);
        BTreePrinter.printNode(root);
        BTreePrinter.printNode(sub);
        utils.println(fz.hasSubTree(root, sub));

        fz.mirrorOfBinaryTree(sub);
        BTreePrinter.printNode(sub);

        int[][] matrix = new int[][]{
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9},
        };
        // fz.printMatrix(matrix); todo

        utils.println(fz.toInt("2147483647", -1));;
    }

    /**
     * 二维数组中的查找 46
     * todo
     *
     * @return
     */
    private boolean find(int[][] nums, int f) {
        if (nums == null || nums.length == 0) return false;
        int fx = nums.length - 1;
        int fy = 0;
        while (nums[fx][fy] > f) {
            fx--;
        }
        while (nums[fx][fy] < f) {
            fy++;
        }
        if (nums[fx][fy] == f) {
            return true;
        }
        return false;
    }

    // 反转链表 59
    private ListNode reverseNode(ListNode node) {
        if (node == null) return null;
        ListNode pre = node;
        ListNode cur = node.next;
        node.next = null; // Warning: 容易魅力把第一个节点的next置空
        while (cur != null) {
            ListNode tmp = cur.next;
            cur.next = pre;
            pre = cur;
            cur = tmp;
        }
        return pre;
    }

    /**
     * 重建二叉树
     * Warn 照着抄才会
     *
     * @param preorder
     * @param inorder
     */
    private TreeNode rebuildTreeNode(int[] preorder, int[] inorder) {
        if (preorder == null || inorder == null || preorder.length <= 0 || preorder.length != inorder.length)
            return null;
        return rebuildTreeNode(preorder, inorder, 0, preorder.length - 1, 0, inorder.length - 1);
    }

    private TreeNode rebuildTreeNode(int[] preorder, int[] inorder, int pStart, int pEnd, int iStart, int iEnd) {
        if (pStart > pEnd || iStart > iEnd) {
            return null;
        }
        TreeNode root = new TreeNode(preorder[pStart]);
        if (pStart == pEnd) {
            return root;
        }
        int mid = iStart;
        while (mid <= iEnd && inorder[mid] != root.val) {
            mid++;
        }
        int leftCount = mid - iStart;
        root.left = rebuildTreeNode(preorder, inorder, pStart + 1, pStart + leftCount, iStart, mid = 1);
        root.right = rebuildTreeNode(preorder, inorder, pStart + leftCount + 1, pEnd, mid + 1, iEnd);
        return root;
    }

    // 二叉树的下一个节点 这题感觉没啥好做，解题思路是考虑下一个节点的各种情况

    // 青蛙跳台阶问题 也可用动态规划法解(后面写)

    // 旋转数组的最小数字
    private Integer minInRotateArray(int[] nums) {
        if (nums == null || nums.length <= 0) return null;
        int start = 0, end = nums.length - 1;
        int mid = start;
        while (nums[start] >= nums[end]) {
            if (end - start == 1) {
                mid = end;
                break;
            }
            mid = (start + end) >> 1;
            if (nums[mid] >= nums[start]) {
                start = mid;
            } else if (nums[mid] <= nums[end]) {
                end = mid;
            }
        }
        return nums[mid];
    }

    /**
     * 矩阵中的路径
     * Warn 需要看着写
     *
     * @param board
     * @param string
     * @return
     */
    private boolean hasPath(char[][] board, String string) {
        if (board == null || board.length == 0 || string == null) return false;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (hasPath(board, string, i, j, 0)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean hasPath(char[][] board, String string, int i, int j, int k) {
        char kChar = string.charAt(k);
        if (i >= board.length || i < 0 || j >= board[0].length || j < 0 || board[i][j] != kChar) return false;
        if (k == string.length() - 1) return true;

        board[i][j] = '\0';
        boolean res = hasPath(board, string, i + 1, j, k + 1) || hasPath(board, string, i + 1, j, k + 1)
                || hasPath(board, string, i, j + 1, k + 1) || hasPath(board, string, i, j - 1, k + 1);
        board[i][j] = kChar;
        return res;
    }

    // 机器人的运动范围，与上题类似

    // 动态规划，求最优解，并且一个大问题可分解成小问题
    // 动态规划解决问题时，是自下而上，即从小问题开始解决

    // 剪绳子 Warn 完全不懂

    // 二进制中1的个数 考虑负数会不会死循环的问题，和高效的解法

    // 数值的整数次方pow实现 考虑
    // 1.底数为0，指数为负的情况，无法情况
    // 2.指数为0的情况
    // 3，指数为负，变成倒数的情况
    // 4. 如何高效求解的情况

    // 打印从1到最大的n位数 考虑最大数溢出的情况 Warn todo

    // 删除链表的节点 easy 要考虑最后一个节点的情况

    // 正则表达式匹配.* Warn: todo
    private boolean match(String content, String pattern) {
        if (content == null || pattern == null) return false;
        return matchCore(content, 0, pattern, 0);
    }


    private boolean matchCore(String content, int i, String pattern, int p) {
        if ((content.length() == i) && (pattern.length() == p)) {
            //出口1，input和pattern都到了字符串末尾
            return true;

        }
        if ((i != content.length()) && (pattern.length() == p)) {
            //出口2，字符串input没有到末尾，pattern到了末尾
            return false;
        }
        if ((content.length() == i) && (pattern.length() != p)) {
            //出口3，字符串input到末尾，pattern还没有到末尾
            return false;
        }

        if ((p + 1 < pattern.length()) && (pattern.charAt(p + 1) == '*')) {//pattern第二个字符为*
            if ((content.charAt(i) == pattern.charAt(p)) || (pattern.charAt(p) == '.')) {
                //首字母相匹配
                return matchCore(content, i + 1, pattern, p + 2) //*表示出现1次
                        || matchCore(content, i + 1, pattern, p)    //*表示出现多次
                        || matchCore(content, i, pattern, p + 2);   //*表示出现0次 ， a ...  p* ...
            } else {
                //首字母不匹配
                return matchCore(content, i, pattern, p + 2);
            }
        } //end pattern.charAt(p+1)=='*'

        if ((content.charAt(i) == pattern.charAt(p)) || (pattern.charAt(p) == '.')) {
            //pattern第二个字母不是*，且首字母匹配
            return matchCore(content, i + 1, pattern, p + 1);
        }
        return false;  //其余情况全部不匹配
    }

    // 调整数组顺序使奇数位于偶数前面
    private void reorder(int[] nums) {
        if (nums == null || nums.length == 0) return;
        int start = 0, end = nums.length - 1;
        while (start < end) {
            if ((nums[start] & 1) == 0) {
                if ((nums[end] & 1) != 0) {
                    utils.swap(nums, start, end);
                }
                end--;
            } else {
                start++;
            }
        }
    }

    // 链表中倒数第k个结点
    private ListNode findKthToTail(ListNode head, int k) {
        k--;
        if (head == null || k < 0) return null;
        ListNode next = head;
        int i = 0;
        while (i < k && next.next != null) {
            next = next.next;
            i++;
        }
        if (i != k) return null;
        ListNode pre = head;
        while (next.next != null) {
            next = next.next;
            pre = pre.next;
        }
        return pre;
    }

    // 合并两个排序的链表
    private ListNode merge(ListNode head1, ListNode head2) {
        if (head1 == null) return head2;
        if (head2 == null) return head1;

        ListNode tmp = new ListNode(0);
        ListNode next = tmp;
        while (head1 != null && head2 != null) {
            if (head1.val < head2.val) {
                next.next = head1;
                head1 = head1.next;
            } else {
                next.next = head2;
                head2 = head2.next;
            }
            next = next.next;
        }
        if (head1 != null) {
            next.next = head1;
        } else {
            next.next = head2;
        }
        return tmp.next;
    }

    // 树的子结构 Node: 这道题解得很精彩，比书上还简练
    private boolean hasSubTree(TreeNode root, TreeNode sub) {
        if (sub == null) { // 如果sub为空,则说明匹配
            return true;
        }
        if (root == null) { // 如果root为空,sub不为空，则说明不匹配
            return false;
        }
        if (root.val == sub.val) {
            return hasSubTree(root.left, sub.left) && hasSubTree(root.right, sub.right);
        } else {
            return hasSubTree(root.left, sub) || hasSubTree(root.right, sub);
        }
    }

    // 二叉树的镜像
    private void mirrorOfBinaryTree(TreeNode root) {
        if (root == null) return;
        TreeNode tmp = root.left;
        root.left = root.right;
        root.right = tmp;
        mirrorOfBinaryTree(root.left);
        mirrorOfBinaryTree(root.right);
    }

    // 顺时针打印矩阵 warn todo 矩阵应该如何存放
    private void printMatrix(int[][] matrix) {
       if (matrix == null || matrix.length == 0) return;
       printMatrix(matrix, 0, 0, matrix.length - 1, 0, matrix[0].length - 1);
    }
    
    private void printMatrix(int[][] matrix, int dir, int l, int r, int t, int b) {
        if (matrix == null) return;
        if (dir == 0) {
            for (int i = l; i <= r; i++) {
                System.out.print(matrix[i][t]);
                System.out.print(",");
            }
            t++;
        } else if (dir == 1) {
            for (int i = t; i <= b; i++) {
                System.out.print(matrix[r][i]);
                System.out.print(",");
            }
            r--;
        } else if (dir == 2) {
            for (int i = r; i >= l; i--) {
                System.out.print(matrix[i][b]);
                System.out.print(",");
            }
            b--;
        } else {
            for (int i = b; i >= t; i--) {
                System.out.print(matrix[l][i]);
                System.out.print(",");
            }
            l++;
        }
        if (l == r && t == b) {
            return;
        }
        printMatrix(matrix, dir == 3 ? 0 : dir++, l, r, t, b);
     }
     
     // 包含min函数的栈

     // 栈的压入、弹出序列

     // 从上往下打印二叉树 用一个队列保存左右节点即可
     private void printFromTopToBottom(TreeNode treeNode) {   
     }

     // 二叉搜索树的后序遍历序列

     // 二叉树中和为某一值的路径
     private boolean findPath(TreeNode root, int expectedNum) {
        return false;
     }

     private boolean findPath(TreeNode node, int expectedNum, int sum) {
        return false;
     }

    // 输入一个含有8个数字的数组，判断有没有可能使得这个8个数字分别放到正方体的8个顶点上，使得正方体上三组相对应的面上的4个顶点的和都相等

    // 在8×8格的国际象棋上摆放八个皇后，使其不能互相攻击，即任意两个皇后都不能处于同一行、同一列或同一斜线上，问有多少种摆法？
    // n皇后问题
    public List<List<String>> solveNQueens(int n) {
        List<List<String>> result = new ArrayList<>();
        if (n < 1) {
            return result;
        }
        int[] queens = new int[n];
        Arrays.fill(queens, -1);
        Set<Integer> cols = new HashSet<>();
        Set<Integer> diagonals1 = new HashSet<>();
        Set<Integer> diagonals2 = new HashSet<>();
        backTrack(result, queens, n, 0, cols, diagonals1, diagonals2);
        return result;
    }

    /**
     * 
     * @param result
     * @param queens
     * @param n
     * @param row 当前行
     * @param cols 列，用于判断
     * @param diagonals1 左上角斜线，用于判断
     * @param diagonals2 右下角斜线，用于判断
     */
    private void backTrack(List<List<String>> result, int[] queens, int n, int row, Set<Integer> cols, Set<Integer> diagonals1, Set<Integer> diagonals2) {
        if (row == n) {
            List<String> board = null; // todo = generateBoard(queens, n);
            result.add(board);
            return;
        }
        // row 已经固定
        // 遍历列
        for (int i = 0; i < n; i++) {
            if (cols.contains(i)) continue;
            int diagonal1 = row - i;
            if (diagonals1.contains(diagonal1)) continue;
            int diagonal2 = row + i;
            if (diagonals2.contains(diagonal2)) continue;
            queens[row] = i; // 这步忘了写了
            cols.add(i);
            diagonals1.add(diagonal1);
            diagonals2.add(diagonal2);
            backTrack(result, queens, n, row + 1, cols, diagonals1, diagonals2);
            queens[row] = -1;
            cols.remove(i);
            diagonals1.remove(diagonal1);
            diagonals2.remove(diagonal2);
        }
    }

    // 数组中出现次数大于一半的数 warn 这道题很有学习意义，结合快排

    // 最小的k个数 warn 这道题很有学习意义，两种解法都值得参考

    // 连续子数组的最大和 动态规划可以学下

    // 从1到n整数中1出现的次数 warn todo 这道题不会
    
    // 把数组排成最小的数 要搞个自定义规则的排序
    private void printMinNumber(int[] nums) {
    }



    // 第n个丑数

    // 第一个只出现一次的字符

    // 数组中的逆序对 利用归并排序的思想

    // 两个链表的第一个公共结点

    // 数字在排序数组中出现的次数

    // 二叉树的深度

    // 树的深度 warn 这个递归没写出来，参考答案的
    private int treeDepth(TreeNode root) {
        if (root == null) return 0;
        int leftDepth = treeDepth(root.left);
        int rightDepth = treeDepth(root.right);
        return leftDepth > rightDepth ? leftDepth + 1 : rightDepth + 1;
    }

    // 判断树是不是平衡二叉树

    // 数组中只出现一次的数字 warn 值得一做
    private int[][] findNumsApearsOnce(int[] nums) {
        return null;
    }

    // 和为s的两个数字VS和为s的连续正数序列 easy

    // 输入一个正数s，打印出所有和为s的连续正数序列（至少含有两个数）。例如输入15，由于1+2+3+4+5=4+5+6=7+8=15，所以结果打印出3个连续序列1～5、4～6和7～8。
    private void findContinueSequence(int sum) {

    }

    // 翻转单词顺序 VS左旋转字符串
    // 输入一个英文句子，翻转句子中单词的顺序，但单词内字符的顺序不变。为简单起见，标点符号和普通字母一样处理。例如输入字符串"I am a student. "，则输出"student. a am I"。
    private void reverseWords(String words) {
        // 书中是先翻转整个句子，再翻转各个单词
        // 因为书中是C++，字符串本身就是数组
    }

    // 字符串的左旋转操作是把字符串前面的若干个字符转移到字符串的尾部。请定义一个函数实现字符串左旋转操作的功能。
    private void leftRotateWords(String words, int n) {
        // 左旋理解为把前n个字母放到后面去
        // 书中的做法是先翻转前n个，再翻转后面的字母，最后翻转整个句子
    }

    // 建模能力
    // n个骰子的点数 warn todo 没看懂 主要注意的是如何在遍历所有结果的时候复用小问题产生的结果，这是一个动态规划问题

    // 扑克牌的顺子
    // 从扑克牌中随机选择5张牌，判断是不是一个顺子，A代表1,J代表11,Q代表12,K代表13,大小王可以看成任意数字。
    // 没有特别的技巧，不过书中的思路值得学习
    private void isContinuous(int[] nums) {

    }

    // 圆圈中最后剩下的数字 warn todo 这个也没看懂
    // 经典解法，环形链表解法

    // 发散思维能力
    // 求1＋2＋…＋n 没看太懂 用递归应该就可以吧

    // 不用加减乘除做加法 warn todo 这个解法很好，解法是用位运算

    // 把字符串换成整数
    private int toInt(String string, int def) {
        if (string == null || string.length() == 0) return def;
        int length = string.length();
        int num = 0;
        boolean positvie = string.charAt(0) != '-';
        if (!positvie && length == 1) return def; // 这里忽略了
        int start = positvie ? 0 : 1;
        for (int i = start; i < length; i++) {
            char c = string.charAt(i);
            if (c >= '0' && c < '9') {
                if (positvie) {
                    num = num * 10 + (c - '0');
                    if (num < 0) return def;
                } else {
                    num = num * 10 - (c - '0');
                    if (num > 0) return def;
                }
            } else {
                return def;
            }
        }
        return num;
    }

    // 树中两个结点的最低公共祖先 考的地方主要在于面试者对于题目的思考，是不是二叉搜索树，如果是的解法，如果不是的解法，有没有指向父节点的指针这些思考
}
