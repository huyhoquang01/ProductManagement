package com.example.aidnetworking.models;

public class MessageModel {
    private String message;
    private Boolean isMyMessage;

    public MessageModel(String message, Boolean isMyMessage) {
        this.message = message;
        this.isMyMessage = isMyMessage;
    }

    public MessageModel() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getIsMyMessage() {
        return isMyMessage;
    }

    public void setIsMyMessage(Boolean isMyMessage) {
        this.isMyMessage = isMyMessage;
    }
}
