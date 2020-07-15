package com.zx.app.study_notes.designPatterns.decorator;

/**
 * author Afton
 * date 2020/6/7
 */
public class Milk extends Decorator {
    public Milk(Drink obj) {
        super(obj);
        setDes("牛奶");
        setPrice(1.0f);
    }
}
