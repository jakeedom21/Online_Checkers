package com.webcheckers.model;

public class Message {

    /**
     * Info types
     */
    public enum MessageType {
        info, error
    }

    private MessageType type;
    private String text;

    /**
     * Message constructor
     * @param type - type of message
     * @param text - text of message
     */
    public Message(MessageType type, String text) {
        this.type = type;
        this.text = text;
    }

    /**
     * Returns the type of the message
     * @return MessageType
     */
    public MessageType getType() {
        return type;
    }

    /**
     * Returns the text of the message
     * @return String
     */
    public String getText() {
        return text;
    }
}
