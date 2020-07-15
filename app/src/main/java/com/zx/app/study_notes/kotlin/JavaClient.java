package com.zx.app.study_notes.kotlin;

/**
 * author Afton
 * date 2020/6/27
 */
public class JavaClient {

    public static String val = "java val";

    public static void main(String[] args) {
        //java 调用kotlin 方法
        KotlinBasisKt.ktShow("java transfer kotlin");
        new KotlinBasis().ktShow("java transfer kotlin");
    }

    public void setJavaCb(JavaCb javaCb) {
        javaCb.show("setJavaCb");
    }

    public String getString(){
        return "java getString";
    }
}
