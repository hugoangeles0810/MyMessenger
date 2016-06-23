package com.hugoangeles.android.mymessenger.addcontact;

/**
 * Created by hugo on 23/06/16.
 */
public interface AddContactPresenter {
    void onShow();
    void onDestroy();

    void addContact(String email);

    void onEventMainThread(AddContactEvent event);
}
