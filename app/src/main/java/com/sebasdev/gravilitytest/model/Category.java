package com.sebasdev.gravilitytest.model;

/**
 * Created by m4605 on 16/03/16.
 */
public class Category {

    private int id;
    private String label;

    public Category() {
    }

    public Category(int id, String label) {
        this.id = id;
        this.label = label;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
