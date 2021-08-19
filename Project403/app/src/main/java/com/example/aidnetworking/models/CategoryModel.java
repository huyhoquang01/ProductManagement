package com.example.aidnetworking.models;

public class CategoryModel {
    int id;
    String category_name;

    public int getId() {
        return id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public CategoryModel(int id, String category_name) {
        this.id = id;
        this.category_name = category_name;
    }

    public CategoryModel() {
    }

    @Override
    public String toString() {
        return category_name;
    }
}
