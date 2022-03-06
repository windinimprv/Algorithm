package com.windin.untitled;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import com.windin.untitled.utils.ListNode;

public class lc200 {
    public static void main(String[] args) {
        Solution solution = new Solution();
        utils.println(Arrays.toString(solution.twoSum(new int[] { 1, 2, 54, 5, 6 }, 11)));
        utils.printListNode(solution.addTwoNumbers(new ListNode(1, new ListNode(3)), new ListNode(2)));
        utils.println(solution.lengthOfLongestSubstring("abnsklnabd"));
        utils.println(solution.findMedianSortedArrays(new int[] {}, new int[] {}));
        utils.println(solution.findMedianSortedArrays(new int[] { 1, 3, 5 }, new int[] {}));
        utils.println(solution.findMedianSortedArrays(new int[] { 1, 3, 5 }, new int[] { 2, 4, 6 }));
        utils.println(solution.longestPalindromeDp("abcdbadada"));
        utils.println(solution.longestPalindromeManacher("abcdbadada"));
        utils.println(solution.convert("PAYPALISHIRING", 3));
        utils.assumeTure("PAHNAPLSIIGYIR".equals(solution.convert("PAYPALISHIRING", 3)));
        utils.assumeTure("PINALSIGYAHRPI".equals(solution.convert("PAYPALISHIRING", 4)));
        utils.println(solution.reverse(-123456));
        utils.println(solution.reverse(Integer.MAX_VALUE));
        utils.println(solution.myAtoi("     -+111124124124124abc123"));
        utils.assumeTure(solution.isPalindrome(3));
        utils.assumeTure(solution.isMatch("aacd", "a*c."));
        solution.findSubstring("barfoothefoobarman", new String[] { "foo", "bar" });
        solution.findSubstring("wordgoodgoodgoodbestword", new String[] { "word", "good", "best", "good" });
        solution.nextPermutation(new int[] { 1, 2, 3 });
        solution.longestValidParentheses("((()))())");
    }

    static class Solution {
        /**
         * 1. 两数之和
         * 用map解决
         */
        public int[] twoSum(int[] nums, int target) {
            Map<Integer, Integer> map = new HashMap<>();
            for (int i = 0; i < nums.length; i++) {
                if (map.get(target - nums[i]) == null) {
                    map.put(nums[i], i);
                } else {
                    return new int[] { i, map.get(target - nums[i]) };
                }
            }
            return new int[0];
        }

        /**
         * 2. 两数相加
         * 注意写法就好
         */
        public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
            ListNode head = new ListNode();
            ListNode next = head;
            while (l1 != null && l2 != null) {
                if (l1.val < l2.val) {
                    next.next = l1;
                    l1 = l1.next;
                } else {
                    next.next = l2;
                    l2 = l2.next;
                }
                next = next.next;
            }
            if (l1 != null) {
                next.next = l1;
            }
            if (l2 != null) {
                next.next = l2;
            }
            return head.next;
        }

        /**
         * 3. 无重复字符的最长子串
         * 这个用O(n)解决，类kmp的思路
         * 需要对比答案
         */
        public int lengthOfLongestSubstring(String s) {
            int max = 0;
            int[] map = new int[256];
            Arrays.fill(map, -1);
            int start = 0;
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                if (map[c] > start) {
                    start = i;
                }
                max = Math.max(max, i - start + 1);
                map[c] = i;
            }
            return max;
        }

        /**
         * 4. 寻找两个正序数组的中位数
         * todo 可以考虑再做一次
         */
        public double findMedianSortedArrays(int[] nums1, int[] nums2) {
            if (nums1 == null)
                nums1 = new int[0];
            if (nums2 == null)
                nums2 = new int[0];
            if (nums1.length == 0 && nums2.length == 0)
                return 0;
            int totalLength = nums1.length + nums2.length;
            boolean odd = totalLength % 2 == 1;
            // if odd, mid = k = n / 2 + 1;
            // if even, mid = k1 + k2 / 2.0D, k1 = (n - 1) / 2 + 1, k2 = n / 2 + 1;
            if (odd) {
                return findKInSortedArrays(nums1, nums2, totalLength / 2 + 1);
            } else {
                return (findKInSortedArrays(nums1, nums2, (totalLength - 1) / 2 + 1)
                        + findKInSortedArrays(nums1, nums2, totalLength / 2) + 1) / 2.0D;
            }
        }

        private int findKInSortedArrays(int[] nums1, int[] nums2, int k) {
            int start1 = 0, start2 = 0;
            while (true) {
                if (start1 == nums1.length)
                    return nums2[start2 + k - 1];
                if (start2 == nums2.length)
                    return nums1[start1 + k - 1];
                if (k == 1)
                    return Math.min(nums1[start1], nums2[start2]);
                int half = k / 2;
                int index1 = Math.min(start1 + half - 1, nums1.length - 1);
                int index2 = Math.min(start2 + half - 1, nums2.length - 1);
                if (nums1[index1] < nums2[index2]) {
                    k -= (index1 - start1 + 1);
                    start1 = index1 + 1;
                } else {
                    k -= (index2 - start2 + 1);
                    start2 = index2 + 1;
                }
            }
        }

        /**
         * 5. 最长回文子串
         * 这道题可以用中心扩展和马拉车做
         * 也可以用dp做
         */
        public String longestPalindrome(String s) {
            return longestPalindromeDp(s);
        }

        /**
         * todo 没法一次过
         */
        private String longestPalindromeManacher(String s) {
            if (s == null || s.length() == 0)
                return "";
            // 插入#，生成奇数长度字符串
            StringBuilder sb = new StringBuilder();
            sb.append("#");
            for (int i = 0; i < s.length(); i++) {
                sb.append(s.charAt(i));
                sb.append("#");
            }
            // 马拉车算法主要利用回文的特性
            // 在以i为中心遍历的过程中，如果这个i在之前遍历过的最长回文子串内，那i的回文长度与它关于之前的最长回文子串中心的对称点的回文长度是一致的
            String string = sb.toString();

            int maxInx = 0;
            int maxRight = 0;
            int[] maxArr = new int[string.length()];
            for (int i = 0; i < string.length(); i++) {
                int expand = 0;
                if (i < maxRight) {
                    int iLeft = 2 * maxInx - i; // maxInx + maxInx - i` = i
                    expand = maxArr[iLeft];
                    // 两个条件i可以利用对称的iLeft的回文长度
                    // iLeft的回文长度的最左边不在maxInx的最左边上
                    // iLeft的回文长度作用在i上时，i的回文长度的最右边超出maxRight
                    if (iLeft - expand > 2 * maxInx - maxRight && i + expand <= maxRight) {
                        maxArr[i] = expand;
                        continue;
                    }
                    if (i + expand > maxRight) {
                        expand = maxRight - i;
                    }
                }
                while (i - expand >= 0 && i + expand < string.length()) {
                    if (string.charAt(i - expand) == string.charAt(i + expand)) {
                        maxArr[i] = expand;
                        expand++;
                    } else {
                        break;
                    }
                }
                if (maxArr[i] > maxRight - maxInx) {
                    maxInx = i;
                    maxRight = i + maxArr[i];
                }
            }
            return string.substring(2 * maxInx - maxRight, maxRight + 1).replace("#", "");
        }

        /**
         * dp[i][j] = s[i] == s[j] && dp[i + 1][j - 1];
         * 0 1 2 3
         * 0 t a b d
         * 1 t c e
         * 2 t f
         * 3 t
         * abc字母表示判断的顺序，除了t
         * 
         * todo
         */
        private String longestPalindromeDp(String s) {
            if (s == null || s.length() == 0)
                return "";
            int max = 0, maxL = 0, maxR = 0;
            boolean[][] dp = new boolean[s.length()][s.length()];
            for (int right = 0; right < dp.length; right++) {
                for (int left = 0; left <= right; left++) {
                    if (right - left < 1) {
                        dp[left][right] = true;
                    } else {
                        dp[left][right] = s.charAt(left) == s.charAt(right) && dp[left + 1][right - 1];
                    }
                    if (dp[left][right]) {
                        if (right - left > max) {
                            max = right - left;
                            maxL = left;
                            maxR = right;
                        }
                    }
                }
            }
            return s.substring(maxL, maxR + 1); // 这里注意substring的endIndex是截取到它的前面一个字符
        }

        /**
         * Z 字形变换
         * 观察Z 字形的规则，去选择指定的index进行输出
         * todo 无法一次过
         */
        public String convert(String s, int numRows) {
            if (s == null || numRows < 1)
                return s;
            int step = numRows + numRows - 2;
            StringBuilder sb = new StringBuilder();
            int length = s.length();
            for (int i = 0; i < numRows; i++) {
                int start = 0;
                while (start + i < length) {
                    sb.append(s.charAt(start + i));
                    if (i != 0 && i != numRows - 1) {
                        if (start + step - i < length) {
                            sb.append(s.charAt(start + step - i));
                        }
                    }
                    start += step;
                }
            }
            return sb.toString();
        }

        /**
         * 7. 整数反转
         * todo 从数学上可以推出不等式，可以忽略掉res==Integer.MIN_VALUE / 10时后增加的个位数
         */
        public int reverse(int x) {
            int res = 0;
            while (x != 0) {
                if (res < Integer.MIN_VALUE / 10 || res > Integer.MAX_VALUE / 10)
                    return 0;
                res = res * 10 + x % 10;
                x /= 10;
            }
            return res;
        }

        /**
         * 8. 字符串转换整数 (atoi)
         * todo 虽然做对了，但这道题做自动状态机的写法会更加好
         */
        public int myAtoi(String s) {
            if (s == null || s.length() == 0)
                return 0;
            int res = 0;
            boolean numStart = false;
            boolean positive = true;
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                if (c == '-' || c == '+') {
                    if (numStart)
                        return res;
                    if (i == s.length() - 1 || !isNum(s.charAt(i + 1)))
                        return res;
                    positive = c == '+';
                    continue;
                }
                if (c == ' ' && !numStart)
                    continue;
                if (!numStart)
                    numStart = isNum(c);
                if (numStart) {
                    if (isNum(c)) {
                        int num = c - '0';
                        if (positive) {
                            if (res > Integer.MAX_VALUE / 10)
                                return Integer.MAX_VALUE;
                            if (res == Integer.MAX_VALUE / 10 && num > Integer.MAX_VALUE % 10)
                                return Integer.MAX_VALUE;
                        } else {
                            if (res < Integer.MIN_VALUE / 10)
                                return Integer.MIN_VALUE;
                            if (res == Integer.MIN_VALUE / 10 && num > Math.abs(Integer.MIN_VALUE % 10))
                                return Integer.MIN_VALUE;
                        }
                        res = positive ? res * 10 + num : res * 10 - num;
                    } else {
                        return res;
                    }
                } else {
                    return res;
                }
            }
            return res;
        }

        private boolean isNum(char c) {
            return c >= '0' && c <= '9';
        }

        /**
         * 9. 回文数
         */
        public boolean isPalindrome(int x) {
            if (x < 0)
                return false;
            if (x < 10)
                return true;
            int tmp = 0;
            int xx = x;
            while (xx != 0) {
                tmp = tmp * 10 + xx % 10;
                xx /= 10;
            }
            return x == tmp;
        }

        /**
         * 10. 正则表达式匹配
         * 给你一个字符串 s 和一个字符规律 p，请你来实现一个支持 '.' 和 '*' 的正则表达式匹配。
         * '.' 匹配任意单个字符
         * '*' 匹配零个或多个前面的那一个元素
         * 所谓匹配，是要涵盖 整个 字符串 s的，而不是部分字符串。
         * 
         * 用dp和回溯皆可
         * 
         * dp
         * dp[i][j] = dp[i - 1][j - 1] && (s[i] == p[j] || p[j] == '.')
         */
        public boolean isMatch(String s, String p) {
            if (s == null || s.length() == 0)
                return false;
            if (p == null || p.length() == 0)
                return false;
            return isMatchCore(s, 0, p, 0);
        }

        private boolean isMatchCore(String s, int sInx, String p, int pInx) {
            if (sInx == s.length())
                return pInx == p.length();
            if (pInx == p.length())
                return false;

            if (pInx < p.length() - 1 && p.charAt(pInx + 1) == '*') {
                if (p.charAt(pInx) == '.' || s.charAt(sInx) == p.charAt(pInx)) {
                    return isMatchCore(s, sInx, p, pInx + 2) || isMatchCore(s, sInx + 1, p, pInx)
                            || isMatchCore(s, sInx + 1, p, pInx + 2);
                }
            } else if (p.charAt(pInx) == '.' || s.charAt(sInx) == p.charAt(pInx)) {
                return isMatchCore(s, sInx + 1, p, pInx + 1);
            }
            return false;
        }

        /**
         * 13. 罗马数字转整数
         * 要理解解决的思路就容易做
         */
        public int romanToInt(String s) {
            // todo
            return 0;
        }

        /**
         * 14. 最长公共前缀
         * 这个效率只到60%，有空可以看下100%的解法
         */
        public String longestCommonPrefix(String[] strs) {
            if (strs == null || strs.length == 0)
                return "";
            StringBuilder stringBuilder = new StringBuilder();
            int inx = 0;
            while (true) {
                Character tmp = null;
                for (String string : strs) {
                    if (inx >= string.length())
                        return stringBuilder.toString();
                    if (tmp == null) {
                        tmp = string.charAt(inx);
                        continue;
                    }
                    if (!tmp.equals(string.charAt(inx)))
                        return stringBuilder.toString();
                }
                stringBuilder.append(tmp);
                inx++;
            }
        }

        /**
         * 15. 三数之和
         * 
         * todo 学习n数之和
         */
        public List<List<Integer>> threeSum(int[] nums) {
            List<List<Integer>> res = new ArrayList<>();
            if (nums == null || nums.length == 0)
                return res;

            Arrays.sort(nums);
            Map<Integer, Integer> map = new HashMap<>();
            for (int i = 0; i < nums.length; i++) {
                map.put(nums[i], i);
            }
            int l = 0, r = nums.length - 1;
            while (l < r) {
                int sum = nums[l] + nums[r];
                if (sum > 0)
                    break;
                Integer tmp = map.get(0 - sum);
                if (tmp != null && tmp != l && tmp != r) {
                    List<Integer> list = new ArrayList<>();
                    list.add(nums[l]);
                    list.add(0 - sum);
                    list.add(nums[r]);
                    res.add(list);
                }
            }
            return res;
        }

        /**
         * 16. 最接近的三数之和
         */
        public int threeSumClosest(int[] nums, int target) {
            return 0;
        }

        /**
         * 17. 电话号码的字母组合
         * 
         * 这种排列组合的问题也可以用回溯吗
         */
        public List<String> letterCombinations(String digits) {
            if (digits == null || digits.length() == 0)
                return new ArrayList<>();
            char[][] map = new char[][] {
                    {}, {},
                    { 'a', 'b', 'c' },
                    { 'd', 'e', 'f' },
                    { 'g', 'h', 'i' },
                    { 'j', 'k', 'l' },
                    { 'm', 'n', 'o' },
                    { 'p', 'q', 'r', 's' },
                    { 't', 'u', 'v' },
                    { 'w', 'x', 'y', 'z' }
            };
            List<String> tmp = new ArrayList<>();
            tmp.add("");
            for (int i = 0; i < digits.length(); i++) {
                char c = digits.charAt(i);
                char[] cs = map[c - '0'];
                List<String> t = new ArrayList<>();
                for (char ct : cs) {
                    for (String string : tmp) {
                        t.add(string + ct);
                    }
                }
                tmp = t;
            }
            return tmp;
        }

        /**
         * 18. 四数之和
         */
        public List<List<Integer>> fourSum(int[] nums, int target) {
            return null;
        }

        /**
         * 19. 删除链表的倒数第 N 个结点
         * ln1->ln2->ln3
         * n = 1: delete ln3, next和next2不相隔
         * n = 2: delete ln2, next和next2相隔1
         * n = 3: delete ln1, next和next2相隔2，并且删除的是head，要返回head.next
         * n = 4: 遍历中next2会为null, 不处理返回head
         * 
         * 没有思路时，用几个例子列出来帮助梳理思路
         */
        public ListNode removeNthFromEnd(ListNode head, int n) {
            if (head == null || n <= 0)
                return head;
            ListNode next = head, next2 = next;
            for (int i = 0; i < n - 1; i++) {
                next2 = next2.next;
                if (next2 == null) {
                    return head;
                }
            }
            if (next2.next == null) {
                return head.next;
            }
            ListNode pre = null;
            while (next2.next != null) {
                if (pre == null)
                    pre = next;
                else
                    pre = pre.next;
                next2 = next2.next;
                next = next.next;
            }
            if (pre != null) {
                pre.next = next.next;
            }
            return head;
        }

        /**
         * 20. 有效的括号
         * 
         * 给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串 s ，判断字符串是否有效。
         * 
         * 有效字符串需满足：
         * 
         * 左括号必须用相同类型的右括号闭合。
         * 左括号必须以正确的顺序闭合。
         * 
         * 
         * 这道题的要点是要储存只存在一边的未配对到的括号，方便后面匹配，结合特性用stack储存合适
         * 最后的return stack.size() == 0;一开始写成了return true;考虑不周到
         */
        public boolean isValid(String s) {
            if (s == null || s.length() == 0)
                return false;
            Stack<Character> stack = new Stack<>();
            for (int i = s.length() - 1; i >= 0; i--) {
                char c = s.charAt(i);
                if (c == ')' || c == '}' || c == ']') {
                    stack.push(c);
                    continue;
                }
                if (stack.size() == 0)
                    return false;
                if (c == '(') {
                    if (!stack.pop().equals(')'))
                        return false;
                } else if (c == '{') {
                    if (!stack.pop().equals('}'))
                        return false;
                } else if (c == '[') {
                    if (!stack.pop().equals(']'))
                        return false;
                } else {
                    return false;
                }
            }
            return stack.size() == 0;
        }

        /**
         * 21. 合并两个有序链表
         */
        public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
            ListNode head = new ListNode();
            ListNode next = head;
            while (list1 != null && list2 != null) {
                if (list1.val < list2.val) {
                    next.next = list1;
                    list1 = list1.next;
                } else {
                    next.next = list2;
                    list2 = list2.next;
                }
                next = next.next; // 这里忘了写了
            }
            if (list1 != null)
                next.next = list1;
            if (list2 != null)
                next.next = list2;
            return head.next;
        }

        /**
         * 22. 括号生成
         * 
         * todo 无法一次过
         */
        public List<String> generateParenthesis(int n) {
            List<String> res = new ArrayList<>();
            StringBuilder stringBuilder = new StringBuilder();
            backTrack(res, stringBuilder, n, 0, 0);
            return res;
        }

        private void backTrack(List<String> res, StringBuilder stringBuilder, int n, int leftCount, int rightCount) {
            if (stringBuilder.length() >= n * 2) { // 一开始这里的判断条件写成n==0了
                if (leftCount == rightCount) {
                    res.add(stringBuilder.toString());
                }
                return;
            }
            stringBuilder.append('(');
            backTrack(res, stringBuilder, n, leftCount + 1, rightCount); // n 忘了写n-1
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            if (leftCount > rightCount) {
                stringBuilder.append(')');
                backTrack(res, stringBuilder, n, leftCount, rightCount + 1);
                stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            }
        }

        /**
         * 23. 合并K个升序链表
         * 
         * 这种可以使用归并思想，两两合并
         * 时间复杂度？
         * 
         * todo 理解这道题的时间复杂度计算，和为什么归并会比逐个合并好
         */
        public ListNode mergeKLists(ListNode[] lists) {
            if (lists == null || lists.length == 0)
                return null;
            int step = 1;
            while (step - 1 < lists.length) {
                int start = 0;
                while (start < lists.length) {
                    lists[start] = merge2Lists(lists[start], start + step < lists.length ? lists[start + step] : null);
                    start += 2 * step;
                }
                step = step << 1;
            }
            return lists[0];
        }

        private ListNode merge2Lists(ListNode list1, ListNode list2) {
            return mergeTwoLists(list1, list2);
        }

        /**
         * 24. 两两交换链表中的节点
         * 
         * todo 无法一次过
         */
        public ListNode swapPairs(ListNode head) {
            ListNode h = new ListNode();
            h.next = head;
            ListNode next = head;
            ListNode pre = h;
            while (next != null && next.next != null) {
                // head: 1->2->3, pre: 0
                // 1: next, 2: next.next as tmp
                // 3: tmp.next
                ListNode tmp = next.next;
                // 1->2 to 1->3
                next.next = tmp.next;
                // 0->1 to ->2
                pre.next = tmp;
                // 1->2 to 2->1;
                tmp.next = next;
                // 0->2->1->3

                pre = next;
                next = next.next;
            }
            return h.next;
        }

        /**
         * 25. K 个一组翻转链表
         * 
         * 厉害了，一次过
         * 还是要通过举实例来帮助判断
         */
        public ListNode reverseKGroup(ListNode head, int k) {
            if (head == null || k <= 1)
                return head;
            ListNode h = new ListNode();
            h.next = head;
            ListNode preHead = h;
            while (true) {
                ListNode tmp = preHead;
                for (int i = 0; i < k; i++) {
                    tmp = tmp.next;
                    if (tmp == null)
                        return h.next;
                }
                // l0->l1->l2->l3->l4->l5
                ListNode pHead = preHead;
                ListNode first = pHead.next;
                ListNode next = pHead.next; // l1
                ListNode swap = next.next; // l2
                while (next != tmp) {
                    ListNode l = swap.next; // l3
                    swap.next = next; // l2->l1
                    next = swap; // l2
                    swap = l; // l3
                }
                pHead.next = next;
                first.next = swap;
                preHead = first;
            }
        }

        /**
         * 26. 删除有序数组中的重复项
         */
        public int removeDuplicates(int[] nums) {
            if (nums == null || nums.length == 0)
                return 0;
            int pre = nums[0] - 1;
            int index = 0;
            for (int i = 0; i < nums.length; i++) {
                if (pre == nums[i])
                    continue;
                nums[index] = nums[i];
                index++;
                pre = nums[i];
            }
            return index;
        }

        /**
         * 27. 移除元素
         */
        public int removeElement(int[] nums, int val) {
            if (nums == null || nums.length == 0)
                return 0;
            int index = 0;
            for (int i = 0; i < nums.length; i++) {
                if (nums[i] == val)
                    continue;
                nums[index] = nums[i];
                index++;
            }
            return index;
        }

        /**
         * 28. 实现 strStr()
         */
        public int strStr(String haystack, String needle) {
            if (haystack == null || haystack.length() == 0 || needle == null || needle.length() == 0)
                return 0;
            // todo
            return 0;
        }

        /**
         * 29. 两数相除
         * 除数不为 0。要求不使用乘法、除法和 mod 运算符。
         * 
         * todo 无法一次过
         */
        public int divide(int dividend, int divisor) {
            if (dividend == 0)
                return 0;
            boolean negative = (dividend < 0 && divisor > 0) || (dividend > 0 && divisor < 0);
            dividend = dividend > 0 ? -dividend : dividend;
            divisor = divisor > 0 ? -divisor : divisor;
            if (dividend > divisor)
                return 0;
            int multi = 1;
            int tmp = divisor;
            while (tmp < divisor) {
                int t = tmp << 1;
                if (t > 0)
                    break;
                tmp = t;
                multi++;
            }
            int res = 0;
            while (tmp >= divisor) {
                dividend -= tmp;
                res += multi;
                while (dividend < tmp) {
                    tmp = tmp >> 1;
                    multi--;
                }
            }
            return res == Integer.MIN_VALUE ? Integer.MAX_VALUE : (negative ? -res : res);
        }

        /**
         * 30. 串联所有单词的子串
         * 
         * 一种是拼出words的所有排序，然后用kmp算法去匹配，时间复杂度：words.n^2*s.length()
         * 一种是使用滑动窗口，首先计算每一个字符为起点对应的words中的单词并记下坐标映射，最后看窗口内的映射是否凑齐words中的单词
         * todo不过这个方法如果是使用map去映射word和坐标，如果有word相同但坐标不同的情况，结果就不正确了
         * 
         * todo 没法一次过，主要是变量声明太多写混了，可以给各变量取个易懂的别名
         * 
         */
        public List<Integer> findSubstring(String s, String[] words) {
            List<Integer> res = new ArrayList<>();
            if (s == null || s.length() == 0 || words == null || words.length == 0)
                return res;
            int window = words[0].length() * words.length;
            if (s.length() < window)
                return res;
            Map<String, Integer> wordMapIndex = new HashMap<>();
            int[] mapIndexs = new int[s.length() - words[0].length() + 1];
            for (int i = 0; i < words.length; i++) {
                wordMapIndex.put(words[i], i);
            }
            for (int i = 0; i < mapIndexs.length; i++) {
                mapIndexs[i] = wordMapIndex.getOrDefault(s.substring(i, i + words[0].length()), -1);
            }
            for (int i = 0; i < s.length() - window + 1; i++) {
                Set<Integer> set = new HashSet<>();
                int inx = i;
                int step = words[0].length();
                while (set.size() < words.length) {
                    if (mapIndexs[inx] != -1 && !set.contains(mapIndexs[inx]))
                        set.add(mapIndexs[inx]);
                    else
                        break;
                    inx += step;
                }
                if (set.size() == words.length)
                    res.add(i);
            }
            return res; // todo
        }

        /**
         * 31. 下一个排列
         * 
         * 即字典序大的下一个排列
         * 如果列出所有排列，时间、空间复杂度都高
         * 
         * 观察得出，如果最后一位要增加，那么要和前一位交换，交换后比原来小，就要将下一位大(非最大)的数交换
         * 如：1243->4234->1324
         * 再观察得出，后面的数(可以是多个的)中如果存在比前面要交换的数大的数，那么就可以产生下一个排列，否则要从小到大排列
         * 
         * todo 太厉害了，居然自己解决了这个问题，不过效率不高，看下答案
         * 
         * todo 无法一次过
         */
        public void nextPermutation(int[] nums) {
            if (nums == null || nums.length <= 1)
                return;
            int max = nums[nums.length - 1];
            for (int i = nums.length - 2; i >= 0; i--) {
                if (nums[i] >= max) {
                    max = nums[i];
                    continue;
                }
                for (int j = nums.length - 1; j >= i + 1; j--) { // 这里一开始写成顺序遍历，应该是逆序遍历
                    if (nums[i] < nums[j]) {
                        int swap = nums[i];
                        nums[i] = nums[j];
                        nums[j] = swap;
                        Arrays.sort(nums, i + 1, nums.length);
                        return;
                    }
                }
            }
            Arrays.sort(nums);
        }

        /**
         * 最长有效括号
         * 
         * 一个新题
         * 从时间复杂度入手
         * 考虑O(n)
         * 举例：
         * ((())：左边括号可累加
         * (()()：右括号后可以加左括号
         * ))()(：先出现的右括号不计数
         * ())(())：先出现在右括号会把左括号计数重置
         * ()(()：如果从左到右遍历，以出现)且无对应(重置计数，该项为计为4，实际应为2，这时候需要从右再遍历一遍，取最小值
         * ))))())()()(()：这种情况左右遍历两遍也解决不了
         * 
         * 所以这种单纯的遍历走不通，没有记录状态，所以要记录状态，使用dp试一下呢
         * dp[i] = if (dp[i - 1] == ')')
         * if (dp[i - 1] == '(')
         * 
         * 厉害呀，用dp解出来了，速度击败100%
         * 不过官方答案解法更聪明
         */
        public int longestValidParentheses(String s) {
            // "((()))())"
            if (s == null || s.length() <= 1)
                return 0;
            boolean[] dp = new boolean[s.length()];
            for (int i = 1; i < dp.length; i++) {
                char c = s.charAt(i);
                if (c == ')') {
                    if (s.charAt(i - 1) == '(') {
                        dp[i - 1] = true;
                        dp[i] = true;
                    } else if (s.charAt(i - 1) == ')') {
                        for (int j = i - 1; j >= 0; j--) {
                            if (!dp[j]) {
                                if (s.charAt(j) == '(') {
                                    dp[j] = true;
                                    dp[i] = true;
                                }
                                break;
                            }
                        }
                    }
                }
            }
            int max = 0;
            int length = 0;
            for (int i = 0; i < dp.length; i++) {
                if (dp[i]) {
                    length++;
                    max = Math.max(max, length);
                } else {
                    length = 0;
                }
            }
            return max;
        }

        public int longestValidParenthesesWrong(String s) {
            int max1 = longestValidParenthesesl2r(s);
            int max2 = longestValidParenthesesr2l(s);
            return Math.min(max1, max2);
        }

        private int longestValidParenthesesl2r(String s) {
            int max = 0;
            int l = 0;
            int length = 0;
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                if (c == '(') {
                    l++;
                } else if (c == ')') {
                    if (l > 0) {
                        l--;
                        length += 2;
                        max = Math.max(max, length);
                    } else {
                        length = 0;
                    }
                }
            }
            return max;
        }

        private int longestValidParenthesesr2l(String s) {
            int max = 0;
            int r = 0;
            int length = 0;
            for (int i = s.length() - 1; i >= 0; i--) {
                char c = s.charAt(i);
                if (c == ')') {
                    r++;
                } else if (c == '(') {
                    if (r > 0) {
                        r--;
                        length += 2;
                        max = Math.max(max, length);
                    } else {
                        length = 0;
                    }
                }
            }
            return max;
        }

        /**
         * 33. 搜索旋转排序数组
         */
        public int search(int[] nums, int target) {
            // todo
            return 0;
        }
    }
}
