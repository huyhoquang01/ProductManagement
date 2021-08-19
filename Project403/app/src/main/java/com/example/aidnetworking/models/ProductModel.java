package com.example.aidnetworking.models;

public class ProductModel {
    private int id, category_id;
    private double price;
    private String product_name, image_url;

    public ProductModel() {
    }

    public ProductModel(int id, double price, int category_id, String product_name, String image_url) {
        this.id = id;
        this.price = price;
        this.category_id = category_id;
        this.product_name = product_name;
        this.image_url = image_url;
    }

    public ProductModel(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public double getPrice() {
        return price;
    }

    public int getCategory_id() {
        return category_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public String getImage_url() {
        return image_url;
    }

    @Override
    public String toString() {
        return "ProductModel{" +
                "id=" + id +
                ", category_id=" + category_id +
                ", price=" + price +
                ", product_name='" + product_name + '\'' +
                ", image_url='" + image_url + '\'' +
                '}';
    }
}
