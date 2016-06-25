package com.hugoangeles.android.mymessenger.contactlist;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.hugoangeles.android.mymessenger.MyMessengerApp;
import com.hugoangeles.android.mymessenger.contactlist.events.ContactListEvent;
import com.hugoangeles.android.mymessenger.domain.FirebaseHelper;
import com.hugoangeles.android.mymessenger.entities.User;
import com.hugoangeles.android.mymessenger.lib.EventBus;
import com.hugoangeles.android.mymessenger.lib.GreenRobotEventBus;

/**
 * Created by hugo on 22/06/16.
 */
public class ContactListRepositoryImpl implements ContactListRepository {

    private FirebaseHelper firebase;
    private ChildEventListener contactEvenListener;
    private EventBus eventBus;

    public ContactListRepositoryImpl() {
        this.firebase = FirebaseHelper.getInstance();
        this.eventBus = GreenRobotEventBus.getInstance();
    }

    @Override
    public void signOff() {
        firebase.singOff();
    }

    @Override
    public String getCurrentUserEmail() {
        return firebase.getAuthUserEmail();
    }

    @Override
    public void changeConnectionStatus(boolean status) {
        firebase.changeUserConnection(status);
    }

    @Override
    public void subscribeToContactListEvents() {
        if (contactEvenListener == null) {
            contactEvenListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    handleContact(dataSnapshot, ContactListEvent.onContactAdded);
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    handleContact(dataSnapshot, ContactListEvent.onContactChanged);
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                    handleContact(dataSnapshot, ContactListEvent.onContactRemoved);
                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {}

                @Override
                public void onCancelled(DatabaseError databaseError) {}
            };
        }

        DatabaseReference myContactsReference = firebase.getMyContactsReference();
        if (myContactsReference != null) {
            myContactsReference.addChildEventListener(contactEvenListener);
        }

    }

    private void handleContact(DataSnapshot dataSnapshot, int eventType) {
        String email = dataSnapshot.getKey();
        email = email.replace("_", ".");
        boolean online = (boolean) dataSnapshot.getValue();
        User user = new User();
        user.setEmail(email);
        user.setOnline(online);

        post(eventType, user);
    }

    @Override
    public void unsubscribeToContactListEvents() {
        if (contactEvenListener != null) {
            firebase.getMyContactsReference().removeEventListener(contactEvenListener);
        }
    }

    @Override
    public void destroyListener() {
        contactEvenListener = null;
    }

    @Override
    public void removeContact(String email) {
        String currentUserMail = firebase.getAuthUserEmail();
        firebase.getOneContactReference(currentUserMail, email).removeValue();
        firebase.getOneContactReference(email, currentUserMail).removeValue();
    }

    private void post(int eventType, User user) {
        ContactListEvent event = new ContactListEvent();
        event.setEventType(eventType);
        event.setUser(user);
        eventBus.post(event);
    }
}
