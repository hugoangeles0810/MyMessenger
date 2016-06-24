package com.hugoangeles.android.mymessenger.chat.ui;

import com.hugoangeles.android.mymessenger.entities.ChatMessage;

/**
 * Created by hugo on 23/06/16.
 */
public interface ChatView {
    void onMessageReceived(ChatMessage msg);
}
