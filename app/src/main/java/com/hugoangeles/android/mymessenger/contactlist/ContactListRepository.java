package com.hugoangeles.android.mymessenger.contactlist;

/**
 * Created by Hugo on 11/06/16.
 */
public interface ContactListRepository {
    void signOff();
    String getCurrentUserEmail();
    void changeConnectionStatus(boolean status);
    void subscribeToContactListEvents();
    void unsubscribeToContactListEvents();
    void destroyListener();
    void removeContact(String email);

}
