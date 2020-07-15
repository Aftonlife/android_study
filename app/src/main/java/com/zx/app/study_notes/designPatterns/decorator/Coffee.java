package com.zx.app.study_notes.designPatterns.decorator;

/**
 * author Afton
 * date 2020/6/7
 */
public class Coffee extends Drink {
    @Override
    public float cost() {
        return super.getPrice();
    }
}
