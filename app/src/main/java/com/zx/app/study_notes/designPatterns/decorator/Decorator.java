package com.zx.app.study_notes.designPatterns.decorator;

/**
 * author Afton
 * date 2020/6/7
 * 装饰者
 */
public class Decorator extends Drink {

    //被装饰者
    private Drink obj;

    public Decorator(Drink obj) {
        this.obj = obj;
    }

    @Override
    public float cost() {
        return super.getPrice() + obj.cost();
    }

    @Override
    public String getDes() {
        return super.getDes() + " " + super.getPrice() + " " + obj.getDes();
    }
}
