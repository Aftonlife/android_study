package com.zx.app.study_notes.bean;

import java.util.List;

/**
 * author Afton
 * date 2020/6/15
 */
public class TestRoundBean {
    String name;
    List<TestBean> items;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TestBean> getItems() {
        return items;
    }

    public void setItems(List<TestBean> items) {
        this.items = items;
    }
}
