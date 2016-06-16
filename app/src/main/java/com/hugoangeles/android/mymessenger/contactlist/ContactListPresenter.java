package com.hugoangeles.android.mymessenger.contactlist;

import com.hugoangeles.android.mymessenger.contactlist.events.ContactListEvent;

/**
 * Created by Hugo on 11/06/16.
 */
public interface ContactListPresenter {
    void onCreate();
    void onDestroy();

    void onPause();
    void onResume();

    void signOff();
    String getCurrentUserEmail();
    void removeContact(String email);
    void onEventMainThread(ContactListEvent event);
}
