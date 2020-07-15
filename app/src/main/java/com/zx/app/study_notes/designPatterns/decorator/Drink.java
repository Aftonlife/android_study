package com.zx.app.study_notes.designPatterns.decorator;

/**
 * author Afton
 * date 2020/6/7
 * components
 */
public abstract class Drink {
    private String des;
    private float price;

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    //成本
    public abstract float cost();
}
