package com.windin.untitled;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import com.windin.untitled.utils.*;

/**
 * 599. 两个列表的最小索引总和
 * 2044. 统计按位或能得到最大值的子集数目 todo
 * 432. 全 O(1) 的数据结构 todo1
 * 720. 词典中最长的单词 自己写的运行时间慢
 * 2043. 简易银行系统
 * 167. 两数之和 II - 输入有序数组
 * 2038. 如果相邻两个颜色均相同则删除当前颜色
 * 693. 交替位二进制数 todo 虽然是简单，但解法是巧妙的
 * 2024. 考试的最大困扰度 todo 滑动窗口解法
 * 1606. 找到处理最多请求的服务器
 */
public class lc {

    // 反转链表
    class Solution {
        public ListNode reverseList(ListNode head) {
            ListNode prev = null;
            ListNode curr = head;
            while (curr != null) {
                ListNode next = curr.next;
                curr.next = prev;
                prev = curr;
                curr = next;
            }
            return prev;
        }
    }
    
    public static void main(String[] args) {
        lc lcInstance = new lc();
        lcInstance.run();
    }

    private void run() {
        mergeKLists(new ListNode[]{
            new ListNode(1, new ListNode(4, new ListNode(5))),
            new ListNode(1, new ListNode(3, new ListNode(4))),
            new ListNode(2, new ListNode(6))
        });
        utils.println(new findMedianSortedArraysSolution().findMedianSortedArrays(new int[]{1,3,5,7,8,9,15,66}, new int[]{2,7,12}));
        // utils.println(isMatch("acdcb", "a*c?b"));
        // utils.println(isScramble("abcde", "caebd"));
        // utils.println(largestRectangleArea(new int[]{2,1,5,6,2,3}));
        utils.println("");
        fullJustify(new String[]{"This", "is", "an", "justification.", "example", "of", "text"}, 16);
    }

    // 单调栈相关
    public int largestRectangleArea(int[] heights) {
        Stack<Integer> stack = new Stack<>();
        int[] tmp = Arrays.copyOf(heights, heights.length + 1);
        tmp[heights.length] = 0;
        heights = tmp;
        int max = 0;
        for (int i = 0; i < heights.length; i++) {
            while (stack.size() > 0 && heights[stack.peek()] > heights[i]) {
                int pop = stack.pop();
                int left = stack.size() == 0 ? 0 : stack.peek() + 1;
                int right = i; 
                max = Math.max(max, (right - left) * heights[pop]);
            }
            stack.push(i);
        }
        return max;
    }

    // 单调栈相关
    public int maximalRectangle(char[][] matrix) {
        if (matrix == null || matrix.length == 0) return 0;
        // todo
        return 0;
    }

    public List<String> fullJustify(String[] words, int maxWidth) {
        List<String> res = new ArrayList<>();
        if (words == null || words.length == 0) return res;
        int curWidth = 0;
        int widthStart = 0;
        for (int i = 0; i < words.length; i++) {
            if (curWidth + words[i].length() >= maxWidth) {
                int wordsCount = i - widthStart;
                int wordsLen = 0;
                for (int j = 0; j < wordsCount; j++) {
                    wordsLen += words[widthStart + j].length();
                }
                int space = maxWidth - wordsLen;
                int[] spaces = new int[wordsCount - 1 == 0 ? 1 : wordsCount - 1];
                if (spaces.length > 0) {
                    for (int j = 0; j < space; j++) {
                        spaces[j % spaces.length] ++;
                    }
                }
                StringBuilder stringBuilder = new StringBuilder();
                for (int j = 0; j < wordsCount; j++) {
                    stringBuilder.append(words[widthStart + j]);
                    if (j < spaces.length) {
                        for (int k = 0; k < spaces[j]; k++) {
                            stringBuilder.append(" ");
                        }
                    }
                }
                res.add(stringBuilder.toString());
                widthStart = i;
                curWidth = 0;
            }
            curWidth += words[i].length() + 1;
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = widthStart; i < words.length; i++) {
            stringBuilder.append(words[i]);
            if (i != words.length - 1) {
                stringBuilder.append(" ");
            }
        }
        for (int i = stringBuilder.length(); i < maxWidth; i++) {
            stringBuilder.append(" ");
        }
        res.add(stringBuilder.toString());
        return res;
    }

    public boolean isScramble(String s1, String s2) {
        if (s1 == null || s2 == null) return false;
        if (s1.length() == 0) return s2.length() == 0;
        if (s1.length() == 1) return s1.equals(s2);
        if (s1.length() != s2.length()) return false;

        return isScrambleCore(s1.toCharArray(), 0, s1.length(), s2.toCharArray());
    }

    public boolean isScrambleCore(char[] s1, int start, int end, char[] s2) {
        for (int i = start + 1; i < end - 1; i++) {
            if (isMatchString(s1, s2)) {
                return true;
            }
            if (isScrambleCore(s1, start, i, s2) || isScrambleCore(s1, i, end, s2)) {
                return true;
            }
            char[] s11 = new char[s1.length];
            for (int j = 0; j < s11.length; j++) {
                s11[j] = s1[j];
            }
            for (int j = 0; j < end - i; j++) {
                s11[start + j] = s1[i + j];
            }
            for (int j = 0; j < i - start; j++) {
                s11[i + j] = s1[start + j];
            }
            if (isMatchString(s11, s2)) {
                return true;
            }
            if (isScrambleCore(s11, start, start + end - i, s2) || isScrambleCore(s11, start + end - i, end, s2)) {
                return true;
            }
        }
        return false;
    }

    private boolean isMatchString(char[] s1, char[] s2) {
        for (int i = 0; i < s1.length; i++) {
            if (s1[i] != s2[i]) return false;
        }
        return true;
    }

    public boolean isMatch(String s, String p) {
        if (s == null || p == null) return false;
        return matchCore(s, 0, p, 0);
    }

    private boolean matchCore(String s, int sInx, String p, int pInx) {
        if (sInx == s.length() && pInx == p.length()) return true;
        if (sInx != s.length() && pInx == p.length()) return false;
        
        if (p.charAt(pInx) == '*') {
            return matchCore(s, sInx, p, pInx + 1)
            || matchCore(s, sInx + 1, p, pInx);
        }
        if (s.charAt(sInx) == p.charAt(pInx) || p.charAt(pInx) == '?') {
            return matchCore(s, sInx + 1, p, pInx + 1);
        }
        return false;
    }

    
    public ListNode mergeKLists(ListNode[] lists) {
        // 两种方法：一种使用归并的思想，一种使用优先队列
        int length = lists.length;
        int step = 1;
        while (step <= length) {
            int pre = step;
            step = step << 1;
            for (int i = 0; i < length; i += step) {
                ListNode ListNode1 = lists[i];
                ListNode ListNode2 = i + pre < length ? lists[i + pre] : null;
                lists[i] = mergeTwoLists(ListNode1, ListNode2);
            }
        }
        return lists[0];
    }

    private ListNode mergeTwoLists(ListNode listNode1, ListNode listNode2) {
        ListNode head = new ListNode();
        ListNode next = head;
        while (listNode1 != null && listNode2 != null) {
            if (listNode1.val < listNode2.val) {
                next.next = listNode1;
                listNode1 = listNode1.next;
            } else {
                next.next = listNode2;
                listNode2 = listNode2.next;
            }
            next = next.next;
        }
        if (listNode1 != null) {
            next.next = listNode1;
        }
        if (listNode2 != null) {
            next.next = listNode2;
        }
        return head.next;
    }

    class findMedianSortedArraysSolution {
        public double findMedianSortedArrays(int[] nums1, int[] nums2) {
            int length1 = nums1.length, length2 = nums2.length;
            int totalLength = length1 + length2;
            if (totalLength % 2 == 1) {
                int midIndex = totalLength / 2;
                double median = getKthElement(nums1, nums2, midIndex + 1);
                return median;
            } else {
                int midIndex1 = totalLength / 2 - 1, midIndex2 = totalLength / 2;
                double median = (getKthElement(nums1, nums2, midIndex1 + 1) + getKthElement(nums1, nums2, midIndex2 + 1)) / 2.0;
                return median;
            }
        }
    
        public int getKthElement(int[] nums1, int[] nums2, int k) {
            /* 主要思路：要找到第 k (k>1) 小的元素，那么就取 pivot1 = nums1[k/2-1] 和 pivot2 = nums2[k/2-1] 进行比较
             * 这里的 "/" 表示整除
             * nums1 中小于等于 pivot1 的元素有 nums1[0 .. k/2-2] 共计 k/2-1 个
             * nums2 中小于等于 pivot2 的元素有 nums2[0 .. k/2-2] 共计 k/2-1 个
             * 取 pivot = min(pivot1, pivot2)，两个数组中小于等于 pivot 的元素共计不会超过 (k/2-1) + (k/2-1) <= k-2 个
             * 这样 pivot 本身最大也只能是第 k-1 小的元素
             * 如果 pivot = pivot1，那么 nums1[0 .. k/2-1] 都不可能是第 k 小的元素。把这些元素全部 "删除"，剩下的作为新的 nums1 数组
             * 如果 pivot = pivot2，那么 nums2[0 .. k/2-1] 都不可能是第 k 小的元素。把这些元素全部 "删除"，剩下的作为新的 nums2 数组
             * 由于我们 "删除" 了一些元素（这些元素都比第 k 小的元素要小），因此需要修改 k 的值，减去删除的数的个数
             */
    
            int length1 = nums1.length, length2 = nums2.length;
            int index1 = 0, index2 = 0;
            int kthElement = 0;
    
            while (true) {
                // 边界情况
                if (index1 == length1) {
                    return nums2[index2 + k - 1];
                }
                if (index2 == length2) {
                    return nums1[index1 + k - 1];
                }
                if (k == 1) {
                    return Math.min(nums1[index1], nums2[index2]);
                }
                
                // 正常情况
                int half = k / 2;
                int newIndex1 = Math.min(index1 + half, length1) - 1;
                int newIndex2 = Math.min(index2 + half, length2) - 1;
                int pivot1 = nums1[newIndex1], pivot2 = nums2[newIndex2];
                if (pivot1 <= pivot2) {
                    k -= (newIndex1 - index1 + 1);
                    index1 = newIndex1 + 1;
                } else {
                    k -= (newIndex2 - index2 + 1);
                    index2 = newIndex2 + 1;
                }
            }
        }
    }
}
