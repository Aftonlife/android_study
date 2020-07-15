package com.zx.app.study_notes.designPatterns.decorator;

/**
 * author Afton
 * date 2020/6/7
 */
public class Chocolate extends Decorator {

    public Chocolate(Drink obj) {
        super(obj);
        setDes("巧克力");
        setPrice(3.0f);
    }
}
