package com.webcheckers.model;

public class Message {

    public enum MessageType {
        info, error
    }

    private MessageType type;
    private String text;

    public Message(MessageType type, String text) {
        this.type = type;
        this.text = text;
    }

    public MessageType getType() {
        return type;
    }

    public String getText() {
        return text;
    }
}
