package com.zx.app.study_notes.leetcode.linkedList;

import java.util.List;

/**
 * author Afton
 * date 2020/6/29
 * 反转
 */
public class ReverseLinkedList {
    public static void main(String[] args) {
        ListNode head = new ListNode(1)
                .setNext(new ListNode(2)
                        .setNext(new ListNode(3)
                                .setNext(new ListNode(4)

                                        .setNext(new ListNode(5)))));

        head = reverseBetweenIt(head, 2, 3);
        head.print();
    }

    /*-------------- 全反转 start----------------*/
    //递归
    public static ListNode reverseRecursive(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode cur = reverseRecursive(head.next);
        head.next.next = head;
        head.next = null;
        return cur;
    }

    //迭代
    //pre cur temp
    public static ListNode reverse(ListNode head) {
        ListNode pre = null;
        ListNode cur = head;
        while (cur != null) {
            ListNode temp = cur.next;
            cur.next = pre;
            pre = cur;
            cur = temp;
        }
        return pre;
    }

    /*-------------- 全反转 end----------------*/

    /*-------------- 部分反转 start----------------*/
    /**
     * 反转从位置 m 到 n 的链表。请使用一趟扫描完成反转。
     * * 说明:
     * * 1 ≤ m ≤ n ≤ 链表长度。
     * * <p>
     * * 示例:
     * * <p>
     * * 输入: 1->2->3->4->5->NULL, m = 2, n = 4
     * * 输出: 1->4->3->2->5->NULL
     */
    //递归交换val 不改变链表结构只改变值
    static ListNode left;
    static Boolean stop;//false 数据交换，stop 停止交换

    public static ListNode reverseBetween(ListNode head, int m, int n) {
        int size = 0;
        left = head;
        while (left != null) {
            size++;
            left = left.next;
        }
        if (m < 1 || m > n || n > size) {
            System.out.println("请输入正确的 m n");
            return head;
        }
        left = head;
        stop = false;
        recurseAndReverse(head, m, n);
        return head;
    }

    //首先left到达m的位置，right到达n的位置
    //然后开始回溯交换值，直到停止交换 left==right 或者 right.next=left（right到了left左边）
    //由于链表只有向后指针没有向前指针利用回溯模拟right向前
    public static void recurseAndReverse(ListNode right, int m, int n) {
        if (n == 1) {
            return;
        }
        right = right.next;
        if (m > 1) {
            left = left.next;
        }
        recurseAndReverse(right, m - 1, n - 1);

        if (left == right || right.next == left) {
            stop = true;
        }
        //数据交换
        if (!stop) {
            int temp = left.val;
            left.val = right.val;
            right.val = temp;
            left = left.next;
        }
    }

    //迭代
    public static ListNode reverseBetweenIt(ListNode head, int m, int n) {
        if (head == null) {
            return null;
        }
        int size = 0;
        left = head;
        while (left != null) {
            size++;
            left = left.next;
        }
        if (m < 1 || m > n || n > size) {
            System.out.println("请输入正确的 m n");
            return head;
        }
        ListNode cur = head;
        ListNode per = null;
        ListNode tail = null;
        ListNode con = null;
        ListNode temp = null;
        while (m > 1) {
            per = cur;
            cur = cur.next;
            m--;
            n--;
        }
        tail = cur;
        con = per;
        while (n > 0) {
            temp = cur.next;
            cur.next = per;
            per = cur;
            cur = temp;
            n--;
        }
        tail.next = cur;
        //m==1
        if (null != con) {
            con.next = per;
        } else {
            head = per;
        }

        return head;
    }

    /*-------------- 部分反转 end----------------*/

    /*-------------- 分组反转 start----------------*/
    /*-------------- 分组反转 end----------------*/
}
