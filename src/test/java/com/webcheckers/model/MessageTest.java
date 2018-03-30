package com.webcheckers.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MessageTest {

    public static final String someText = "someText";
    public static final String someInfo = "someInfo";
    public static final String someError = "someError";

    @Test
    void getType() {
        Message m1 = new Message(Message.MessageType.info, someText);
        assertEquals(m1.getType(), Message.MessageType.info);
        Message m2 = new Message(Message.MessageType.error, someText);
        assertEquals(m2.getType(), Message.MessageType.error);
    }

    @Test
    void getText() {
        Message m1 = new Message(Message.MessageType.info, someInfo);
        assertEquals(m1.getText(), someInfo);
        Message m2 = new Message(Message.MessageType.error, someError);
        assertEquals(m2.getText(), someError);
    }
}