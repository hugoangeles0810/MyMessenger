package com.hugoangeles.android.mymessenger.entities;

import com.google.firebase.database.Exclude;

/**
 * Created by hugo on 23/06/16.
 */
public class ChatMessage {

    private String message;
    private String sender;
    @Exclude
    private boolean sentByMe;

    public ChatMessage() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public boolean isSentByMe() {
        return sentByMe;
    }

    public void setSentByMe(boolean sentByMe) {
        this.sentByMe = sentByMe;
    }

    @Override
    public boolean equals(Object o) {
        boolean equal = false;

        if (o instanceof ChatMessage) {
            ChatMessage msg = (ChatMessage) o;
            return this.getSender().equals(msg.getSender()) &&
                    this.getMessage().equals(msg.getMessage()) &&
                    this.sentByMe == msg.sentByMe;
        }

        return equal;
    }
}
