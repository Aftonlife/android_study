package com.zx.app.study_notes.kotlin


/**
 * author Afton
 * date 2020/6/16
 */

fun main(): Unit {

    //静态语言 编译期决定了类型
//    println("add2=" + add2(1, 4))
//    lenght(1, 2, 1, 2, 3, 4, 5, 6)


    val name = "张三"
    val age = 28
    val sex = 0
    var data = DataClass(name, age, sex)

    val(myName,myAge,mySex)=data.copy()
    val(_,myAge1,mySex1)=data.copy()

    println("myName=$myName,myAge=$myAge,mySex=$mySex")
    println("myAge=$myAge1,mySex=$mySex1")



    val infoMesage = """
        ?AAAAAAAAAAA
        BBBBBBBBBBB
        ?CCCCCCCCCCC
        DDDDDDDDDDD
        ?EEEEEEEEEEE
        """.trimMargin("?")

    val price = """
        $999.9
    """.trimIndent()

    var info: String? = null;
//    inDemo()
}

fun inDemo() {
    //1--9
    for (i in 1..9) {
        print(i)
    }
    println("\n9到1")
    for (i in 9 downTo 1) {
        print(i)
    }
    val v = 88
    if (v in 1..100) {
        println("\nv在区间")
    }
    for (i in 1..20 step 2) {
        print(i)
        print(" ")
    }
    //排除最后元素
    for (i in 'a' until 'z') {
        print(i)
        print(" ")
    }

    println()
    val int1: Long? = 127
    val int2: Long? = 127

    println("int1==int2:" + (int1 == int2))
    println(int1 === int2)

    val test1: Int = 10000
    val test2: Int = 10001
    println(test1 === test2) // false

    val numbers = arrayOf(1, 2, 3, 4, 5, 6)
    val arr = Array(10) { v: Int -> (v + 20) }
    for (number in numbers) {
        print(number)
        print(" ")
    }
    for (i in arr) {
        print(i)
        print(" ")
    }
    val result = when (5) {
        in 10..20 -> {
            "funny"
        }
        in 30..40 -> {
            3
        }
        1, 2, 3, 4, 5, 6 -> {
            println("no body")
            'b'
        }
        else -> {
            4
        }
    }
    println(result)

    cao@ for (i in 1..10) {
        for (j in 1..5) {
            println("i:$i,j:$j")
            if (i == 5) {
                break@cao
            }
        }
    }
    arr.forEach {
        print(it)
        print(" ")
    }
    for (index in arr.indices) {
        println("下标：$index,对应的值：${arr[index]}")
    }

}

fun test(name: String): Int? {
    if (name === "zx") {
        return 9999
    }
    return null;
}


 open class KotlinBasis(id: Int) {
    //次构造
    constructor(id: Int, name: String) : this(id) {
        println("n1")
    }

    constructor() : this(99) {
        println("n2")
    }


    fun add(n1: Int, n2: Int) = n1 + n2

    val name: String = "zx"

    //内部类
  inner  class inClass{
        fun show(){
            println(name)
        }
    }
    fun ktShow(msg: String){
        println("inner msg:$msg")
    }
}

class KotlinInherit :KotlinBasis(){
    fun action(){

    }
}

fun ktShow(msg:String){
    println("msg:$msg")
}


