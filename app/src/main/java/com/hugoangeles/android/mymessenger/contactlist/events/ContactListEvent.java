package com.hugoangeles.android.mymessenger.contactlist.events;

import com.hugoangeles.android.mymessenger.entities.User;

/**
 * Created by Hugo on 11/06/16.
 */
public class ContactListEvent {
    public static final int onContactAdded = 0;
    public static final int onContactChanged = 1;
    public static final int onContactRemoved = 2;
    public static final int onUserLogout = 4;

    private User user;
    private int eventType;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }
}
