package com.example.aidnetworking.models;

public class Response2PikModel {
    String saved;

    public Response2PikModel() {
    }

    public Response2PikModel(String saved) {
        this.saved = saved;
    }

    public String getSaved() {
        return "https://2.pik.vn/" + saved;
    }
}
