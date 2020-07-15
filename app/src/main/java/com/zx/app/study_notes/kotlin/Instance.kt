package com.zx.app.study_notes.kotlin

/**
 * author Afton
 * date 2020/6/18
 */
class Instance {
    object Holder {
        val instance = Instance()
    }

    companion object {
        fun getInstance(): Instance = Holder.instance
    }


    fun show(name: String) {
        println("name=$name")
    }
}

class Instance1 {
    companion object {
        private var instance: Instance1? = null
        fun getInstance(): Instance1 {
            if (instance == null) {
                instance = Instance1()
            }
            return instance!!
        }
    }
    fun show(name: String) {
        println("name=$name")
    }
}

fun main() {
    val instance = Instance.getInstance()
    instance.show("afton")
    val instance1=Instance1.getInstance()
    instance1?.show("zz")
}