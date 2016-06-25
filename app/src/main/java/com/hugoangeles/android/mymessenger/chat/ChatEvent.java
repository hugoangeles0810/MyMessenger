package com.hugoangeles.android.mymessenger.chat;

import com.hugoangeles.android.mymessenger.entities.ChatMessage;

/**
 * Created by hugo on 23/06/16.
 */
public class ChatEvent {

    private ChatMessage msg;

    public ChatEvent() {
    }

    public ChatEvent(ChatMessage msg) {
        this.msg = msg;
    }

    public ChatMessage getMsg() {
        return msg;
    }

    public void setMsg(ChatMessage msg) {
        this.msg = msg;
    }
}
