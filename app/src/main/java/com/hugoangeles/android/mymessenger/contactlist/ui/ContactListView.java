package com.hugoangeles.android.mymessenger.contactlist.ui;

import com.hugoangeles.android.mymessenger.entities.User;

/**
 * Created by Hugo on 11/06/16.
 */
public interface ContactListView {
    void onContactAdded(User user);
    void onContactChanged(User user);
    void onContactRemoved(User user);
}
