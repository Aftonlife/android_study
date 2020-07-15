package com.zx.app.study_notes.leetcode.linkedList

/**
 * author Afton
 * date 2020/6/23
 * 两数相加
 * 给出两个 非空 的链表用来表示两个非负的整数。其中，它们各自的位数是按照 逆序 的方式存储的，并且它们的每个节点只能存储 一位 数字。
 * 如果，我们将这两个数相加起来，则会返回一个新的链表来表示它们的和。
 * 您可以假设除了数字 0 之外，这两个数都不会以 0 开头。
 *
 *
 * 示例：
 * 输入：(2 -> 4 -> 3) + (5 -> 6 -> 4)
 * 输出：7 -> 0 -> 8
 * 原因：342 + 465 = 807
 */
object AddTwoNumber {
    @JvmStatic
    fun main(args: Array<String>) {
        val l1 = ListNode(2)
        l1.next = ListNode(4)
        l1.next.next = ListNode(3)
        val l2 = ListNode(5)
        l2.next = ListNode(6)
        addTwoN(l1, l2).print()
    }

    /*自己写的
    执行用时：3 ms, 在所有 Java 提交中击败了26.20%的用户
    内存消耗：39.7 MB, 在所有 Java 提交中击败了94.74%的用户*/
    fun addTwoNumbers(l1: ListNode?, l2: ListNode?): ListNode {
        var l1 = l1
        var l2 = l2
        var head = ListNode(0)
        val cur = head
        var temp = 0
        var temp1 = 0
        var temp2 = 0
        var isOver = false
        while (l1 != null || l2 != null) {
            temp1 = l1?.`val` ?: 0
            temp2 = l2?.`val` ?: 0
            temp = temp1 + temp2 + if (isOver) 1 else 0
            val result = ListNode(temp % 10)
            head.next = result
            isOver = temp > 9
            if (l1 != null) {
                l1 = l1.next
            }
            if (l2 != null) {
                l2 = l2.next
            }
            head = head.next
        }
        if (isOver) {
            val result = ListNode(1)
            head.next = result
        }
        return cur.next
    }

    //``在Kotlin中可以用反引号解决关键字冲突
    fun addTwoN(l1: ListNode?, l2: ListNode?): ListNode {
        var l1 = l1
        var l2 = l2
        val dummy = ListNode()
        var cur = dummy
        var result = 0
        while (l1 != null || l2 != null || result > 0) {
            cur.next = ListNode()
            cur = cur.next
            if (l1 != null) {
                result += l1.`val`
                l1 = l1.next
            }
            if (l2 != null) {
                result += l2.`val`
                l2 = l2.next
            }
            cur.`val` = result % 10
            result /= 10
        }
        return dummy.next
    }
}