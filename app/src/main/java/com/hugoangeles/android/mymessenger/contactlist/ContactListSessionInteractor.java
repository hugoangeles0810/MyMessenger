package com.hugoangeles.android.mymessenger.contactlist;

/**
 * Created by Hugo on 11/06/16.
 */
public interface ContactListSessionInteractor {
    void singOff();
    String getCurrentUserEmail();
    void changeConnectionStatus(boolean status);
}
