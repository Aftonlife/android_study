package com.zx.app.study_notes.kotlin

/**
 * author Afton
 * date 2020/6/28
 * 高阶函数：函数含有函数类型的参数或函数类型的返回值
 * 函数在 Kotlin 里可以作为对象存在
 */
fun main() {
    //::b 就是把函数b转成一个与函数b有相同功能的函数类型的对象::b
    //实际调用(::b).invoke(1)
    println((::b)(1))
    //val d=::b
    val d = fun(num: Int): String {//匿名函数（与::b一样）
        return num.toString()
    }
    a(2, fun(num: Int): String { return num.toString() })
    //如果 Lambda 是函数的最后一个参数，你可以把 Lambda 写在括号的外面
    a(2) { num: Int -> num.toString() }
    //而如果 Lambda 是函数唯一的参数，你还可以直接把括号去了
    c { num: Int -> num.toString() }
    //如果这个 Lambda 是单参数的，它的这个参数也省略掉不写 it代表单个参数
    //lambda靠上下文的推断
    c { it.toString() }
    //把一个匿名函数赋值给变量而不是作为函数参数传递的时候 就不能省略掉 Lambda 的参数类型 因为无法从上下文推断
    //Lambda 的返回值不是用 return 来返回，而是直接取最后一行代码的值
    val e = { num: Int -> num.toString() }
    val f: (Int) -> String = {
        //这里搞其他操作
        it.toString()
    }
    println(a(2, d))
}

fun b(num: Int): String {
    return num.toString()
}

fun a(num: Int, b: (Int) -> String): String {
    return b(num)
}

fun c(b: (Int) -> String): String {
    return b(3)
}