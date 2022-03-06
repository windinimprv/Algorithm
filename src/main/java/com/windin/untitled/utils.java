package com.windin.untitled;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class utils {

    public static void printArray(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            System.out.print(nums[i]);
            if (i < nums.length - 1) {
                System.out.print(",");
            }
        }
        System.out.println();
    }

    public static void printListNode(ListNode node) {
        if (node == null) return;
        while (node != null) {
            System.out.print(node.val);
            if (node.next != null) {
                System.out.print(",");
            }
            node = node.next;
        }
        System.out.println();
    }

    public static void println(String log) {
        System.out.println(log);
    }

    public static void println(int log) {
        System.out.println(log);
    }

    public static void println(double log) {
        System.out.println(log);
    }

    public static void println(boolean log) {
        System.out.println(log);
    }

    public static void println(List list) {
        if (list == null)
            System.out.println("null");
        else {
            for (int i = 0; i < list.size(); i++) {
                System.out.print(list.get(i));
                if (i < list.size() - 1)
                    System.out.print(",");
            }
            System.out.println();
        }
    }

    public static void printlnStringListList(List<List<String>> strings) {
        if (strings == null)
            System.out.println("null");
        else {
            for (List<String> ss : strings) {
                for (String string : ss) {
                    System.out.print(string);
                    if (string != ss.get(ss.size() - 1))
                        System.out.print(",");
                }
                System.out.println();
            }
        }
    }

    public static void printlnIntergerListList(List<List<Integer>> strings) {
        if (strings == null)
            System.out.println("null");
        else {
            for (List<Integer> ss : strings) {
                for (int i = 0; i < ss.size(); i++) {
                    System.out.print(ss.get(i));
                    if (i < ss.size() - 1)
                        System.out.print(",");
                }
                System.out.println();
            }
        }
    }

    public static void print(String log) {
        System.out.print(log);
    }

    public static void assumeTure(boolean adjudgement) {
        if (!adjudgement) {
            throw new IllegalArgumentException("The result error.");
        }
        System.out.println("adjudgement true.");
    }

    public static void swap(int[] array, int i, int j) {
        int tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
    }

    public static ListNode generateListNodes(int[] array) {
        if (array == null) return null;
        ListNode tmp = null, next = null;
        for (int i : array) {
            if (tmp == null) {
                 tmp = new ListNode(i);
                 next = tmp;
            } else {
                next.next = new ListNode(i);
                next = next.next;
            }
        }
        return tmp;
    }

    public static class ListNode {

        public ListNode() {
        }

        public ListNode(int val) {
            this.val = val;
        }

        public ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }

        int val;
        ListNode next;
    }

    public static class TreeNode {

        public TreeNode(int val) {
            this.val = val;
        }

        public TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }

        int val;
        TreeNode left;
        TreeNode right;
    }
    

static class BTreePrinter {
    public static <T extends Comparable<?>> void printNode(TreeNode root) {
        int maxLevel = BTreePrinter.maxLevel(root);

        printNodeInternal(Collections.singletonList(root), 1, maxLevel);
    }

    private static <T extends Comparable<?>> void printNodeInternal(List<TreeNode> nodes, int level, int maxLevel) {
        if (nodes.isEmpty() || BTreePrinter.isAllElementsNull(nodes))
            return;

        int floor = maxLevel - level;
        int endgeLines = (int) Math.pow(2, (Math.max(floor - 1, 0)));
        int firstSpaces = (int) Math.pow(2, (floor)) - 1;
        int betweenSpaces = (int) Math.pow(2, (floor + 1)) - 1;

        BTreePrinter.printWhitespaces(firstSpaces);

        List<TreeNode> newNodes = new ArrayList<>();
        for (TreeNode node : nodes) {
            if (node != null) {
                System.out.print(node.val);
                newNodes.add(node.left);
                newNodes.add(node.right);
            } else {
                newNodes.add(null);
                newNodes.add(null);
                System.out.print(" ");
            }

            BTreePrinter.printWhitespaces(betweenSpaces);
        }
        System.out.println("");

        for (int i = 1; i <= endgeLines; i++) {
            for (int j = 0; j < nodes.size(); j++) {
                BTreePrinter.printWhitespaces(firstSpaces - i);
                if (nodes.get(j) == null) {
                    BTreePrinter.printWhitespaces(endgeLines + endgeLines + i + 1);
                    continue;
                }

                if (nodes.get(j).left != null)
                    System.out.print("/");
                else
                    BTreePrinter.printWhitespaces(1);

                BTreePrinter.printWhitespaces(i + i - 1);

                if (nodes.get(j).right != null)
                    System.out.print("\\");
                else
                    BTreePrinter.printWhitespaces(1);

                BTreePrinter.printWhitespaces(endgeLines + endgeLines - i);
            }

            System.out.println("");
        }

        printNodeInternal(newNodes, level + 1, maxLevel);
    }

    private static void printWhitespaces(int count) {
        for (int i = 0; i < count; i++)
            System.out.print(" ");
    }

    private static <T extends Comparable<?>> int maxLevel(TreeNode node) {
        if (node == null)
            return 0;

        return Math.max(BTreePrinter.maxLevel(node.left), BTreePrinter.maxLevel(node.right)) + 1;
    }

    private static <T> boolean isAllElementsNull(List<T> list) {
        for (Object object : list) {
            if (object != null)
                return false;
        }

        return true;
    }

}
}
