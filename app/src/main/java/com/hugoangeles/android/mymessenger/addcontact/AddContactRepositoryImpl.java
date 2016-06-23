package com.hugoangeles.android.mymessenger.addcontact;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.hugoangeles.android.mymessenger.domain.FirebaseHelper;
import com.hugoangeles.android.mymessenger.entities.User;
import com.hugoangeles.android.mymessenger.lib.EventBus;
import com.hugoangeles.android.mymessenger.lib.GreenRobotEventBus;

/**
 * Created by hugo on 23/06/16.
 */
public class AddContactRepositoryImpl implements AddContactRepository {

    private EventBus eventBus;
    private FirebaseHelper helper;

    public AddContactRepositoryImpl() {
        this.eventBus = GreenRobotEventBus.getInstance();
        this.helper = FirebaseHelper.getInstance();
    }

    @Override
    public void addContact(String email) {
        final String key = email.replace(".", "_");
        DatabaseReference userReference = helper.getUserReference(key);
        userReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if (user != null) {
                    DatabaseReference myContactReference = helper.getMyContactsReference();
                    myContactReference.child(key).setValue(user.isOnline());

                    String currentUserEmail = helper.getAuthUserEmail();
                    String currentKey = currentUserEmail.replace(".", "_");

                    DatabaseReference reverseContactReference = helper.getContactsReference(key);
                    reverseContactReference.child(currentKey).setValue(User.ONLINE);

                    postSuccess();
                } else {
                    postError();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                postError();
            }
        });
    }

    private void postError() {
        post(true);
    }

    private void postSuccess() {
        post(false);
    }

    private void post(boolean error) {
        AddContactEvent event = new AddContactEvent();
        event.setError(error);
        eventBus.post(event);
    }
}
