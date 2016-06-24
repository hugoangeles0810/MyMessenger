package com.hugoangeles.android.mymessenger.chat;

/**
 * Created by hugo on 23/06/16.
 */
public interface ChatPresenter {
    void onPause();
    void onResumen();
    void onCreate();
    void onDestroy();

    void setChatRecipient(String recipient);
    void sendMessage(String msg);
    void onMainThread(ChatEvent event);
}
