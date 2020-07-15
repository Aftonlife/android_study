package com.zx.app.study_notes.designPatterns;

import com.zx.app.study_notes.designPatterns.decorator.Chocolate;
import com.zx.app.study_notes.designPatterns.decorator.Drink;
import com.zx.app.study_notes.designPatterns.decorator.LongBlack;
import com.zx.app.study_notes.designPatterns.decorator.Milk;
import com.zx.app.study_notes.designPatterns.decorator.ShortBlack;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * author Afton
 * date 2020/6/7
 */
public class Test {
    public static void main(String[] args) throws FileNotFoundException {
        /*装饰者*/
        decorator();

    }

    /**
     * 装饰者模式
     * 动态的将新功能附件到对象上。在对象功能扩展方面比继承更有弹性 避免类的爆炸式增长
     * 被装饰者与装饰者都继承一个抽象类，装饰者持有被装饰者
     */
    public static void decorator() throws FileNotFoundException {
        /** jdk IO 使用装饰者模式
         * OutputStream 就是 Components 抽象类
         * FileOutputStream 是OutputStream的子类  即被装饰者 类似单品咖啡
         * FilterOutputStream 是OutputStream的子类 装饰者 里面有protected OutputStream out; 持有了被装饰者
         * DataOutputStream 继承FilterOutputStream 具体的装饰者 类似调味品，
         * */
        new DataOutputStream(new FileOutputStream("D://abc.txt"));


        Drink order = new LongBlack();
        order = new Milk(order);
        order = new Chocolate(order);
        order = new Chocolate(order);
        System.out.println(order.getDes());
        System.out.println("两份份巧克力 + 一份牛奶 + 一份美式咖啡 =" + order.cost());
    }
}
