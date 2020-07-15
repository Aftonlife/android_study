package com.zx.app.study_notes.javaBasis.jvm;

/**
 * author Afton
 * date 2020/6/21
 */
public class Person {

    public int work() {
        int x = 1;
        int y = 2;
        int z = (x + y) * 10;
        return z;
    }

    public static void main(String[] args) {
        Person person = new Person();
        person.work();
    }
}
