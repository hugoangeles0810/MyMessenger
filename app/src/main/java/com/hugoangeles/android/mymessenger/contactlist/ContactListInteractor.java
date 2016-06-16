package com.hugoangeles.android.mymessenger.contactlist;

/**
 * Created by Hugo on 11/06/16.
 */
public interface ContactListInteractor {
    void subscribe();
    void unsubscribe();
    void destroyListener();
    void removeContact(String email);

}
