package com.hugoangeles.android.mymessenger.chat;

/**
 * Created by hugo on 23/06/16.
 */
public interface ChatInteractor {
    void sendMessage(String msg);
    void setRecipient(String recipient);
    void subcribe();
    void unsubcribe();
    void destroyListener();
}
