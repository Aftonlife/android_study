package com.zx.app.study_notes.leetcode.linkedList;

/**
 * author Afton
 * date 2020/6/23
 * 链表
 */
public class ListNode {
    public int val;
    public ListNode next;

    public ListNode(){}

    public ListNode(int val) {
        this.val = val;
    }

    public ListNode setNext(ListNode next){
        this.next=next;
        return this;
    }
    public void print() {
        ListNode temp = this;
        while (temp != null) {
            System.out.println(temp.val);
            temp = temp.next;
        }
    }
}
