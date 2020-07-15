package com.zx.app.study_notes.kotlin

import kotlin.reflect.KClass

/**
 * author Afton
 * date 2020/6/27
 */
fun main() {
    var basis: KotlinBasis = KotlinInherit()
    (basis as? KotlinInherit)?.action()

    //kt调用java
    //冲突用··
    println(JavaClient.`val`)

    //调用java 代码注意null
    var msg: String? = JavaClient().string
    println(msg?.length)

    //java对象形参
    showClass(JavaClient::class.java)

    //k对象形参
    showClassKt(KotlinBasis::class)

    //kt 使用Java cb
    JavaClient().setJavaCb { msg -> println(msg) }

    JavaClient().setJavaCb {
        println(it)
    }

    val javaCb = JavaCb { println(it) }

    JavaClient().setJavaCb(javaCb)

    val javaCb1 = object : JavaCb {
        override fun show(msg: String?) {
            println(msg)
        }
    }
    JavaClient().setJavaCb(javaCb1)

    //kt 调用 ktcb

}

//java class 形参
fun showClass(clazz: Class<JavaClient>) {
}

//kt class 形参
fun showClassKt(clazz: KClass<KotlinBasis>) {

}