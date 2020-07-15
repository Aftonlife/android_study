package com.zx.app.study_notes.javaBasis.generic;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * author Afton
 * date 2020/5/31
 * 泛型
 * 作用：1.类型检测提前到编译期 2.增强代码复用性
 */
public class genericStudy {
    /*----------java 泛型只有三种 类 接口 方法---------*/
    //泛型类
    static class Box<T> implements Generic<T> {//泛型类继承

        //泛型方法
        public <T,K> void Method(T t,K k) {
        }

        //这不是泛型方法
        public void set(T t) {
        }

        //这不是泛型方法
        public void test(List<String> list) {
        }
    }

    //泛型接口
    interface Generic<T> {
    }

    /*------受限参数：所受限制可以使用共同的方法 例如排序的比较大小------*/
    //单个受限
    public <U extends Comparable<U>> void sort(U[] arr, U old) {
        int count = 0;
        for (U u : arr) {
            if (u.compareTo(old) > 0) {
                ++count;
            }
        }
    }

    //多个受限 如果有类必须排第一且只能有一个类（单继承）接口可多个
    public <U extends Number & Generic & Serializable> void sort1() {
    }
    /*-------- 泛型继承子类------*/
    //给定两种具体类型A B
    //无论A B 是什么关系 MyClass<A> 与MyClass<B> 没有关系
    //MyClass<A> 与MyClass<B> 的公共父类是Object
    //ArrayList<A> 是List<A>的子类 List<A>是Collection<A>的子类

    /*PESC 原则*/
    public <T extends ViewGroup> T getView(Context context) {
        // 返回类型T相当于一个标记 只有在调用时才知道T的具体类型
        // LinearLayout(context) 是具体的类型 所以这里要强转，
        // 因为有可能T是其他 例如RelativeLayout;
        return (T) new LinearLayout(context);
    }



    //    为了高效使用Java泛型，你必须考虑一下下面的约束条件：
//
//    无法利用原始类型来创建泛型
//            无法创建类型参数的实例
//    无法创建参数化类型的静态变量
//            无法对参数化类型使用转换或者instanceof关键字
//    无法创建参数化类型的数组
//    无法创建、捕获或是抛出参数化类型对象
//    当一个方法的所有重载方法的形参类型擦除后，如果它们具有了相同的原始类型，那么此方法不可重载
    //不支持泛型数组

    static class Pair<K, V> {
        private K key;
        private V value;

        public Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }

//        public static <T> void append(List<T> list) {
//            //无法创建类型参数的实例
//            T t = new T();
//            list.add(t);
//        }

        public static <T> void append(List<String> list) {
            //一个类中是无法存在类型擦除后具有相同签名的两个重载方法
            //这两个重载方法在classfile中具有相同的表达，因此会生成一个编译时错误
        }

        public static <T> void append(List<T> list, Class<T> cls) throws Exception {
            //反射可以创建实例
            T t = cls.newInstance();
            list.add(t);
        }
    }


    static class A<String>{
        private String str;
        public String getString(String str){
            this.str=(String)(str+"test");
            return str;
        }
    }
    static class B extends A{};

    public static void main(String[] args) {
//        ArrayList<Box<String>> list1=new ArrayList<>();
//        ArrayList<Pair<String ,Integer>>list2=new ArrayList<>();
//        System.out.println(list1.getClass()==list2.getClass());
        A[] as=new A[2];
        B[]bs=new B[2];
        as=bs;
//        Box<A>[]ba=new Box<A>[2];
        List<A>al=new ArrayList<>();
        ArrayList<A>aal=new ArrayList<>();
        List<B>bl=new ArrayList<>();
//        al=bl;
        al=aal;
        A test=new A();

    }
}
