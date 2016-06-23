package com.hugoangeles.android.mymessenger.domain;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hugoangeles.android.mymessenger.entities.User;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Hugo on 08/06/16.
 */
public class FirebaseHelper {
    private FirebaseDatabase dataReference;

    public static final String ONLINE = "online";
    private final static String SEPARATOR = "___";
    private final static String CHATS_PATH = "chats";
    private final static String USERS_PATH = "users";
    private final static String CONTACTS_PATH = "contacts";

    private static class SingletonHolder {
        private static final FirebaseHelper INSTANCE = new FirebaseHelper();
    }

    public static FirebaseHelper getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public FirebaseHelper () {
        this.dataReference = FirebaseDatabase.getInstance();
    }

    public FirebaseDatabase getDataReference() {
        return dataReference;
    }

    public String getAuthUserEmail() {
        String email = null;
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {
            email = firebaseUser.getEmail();
        }
        return email;
    }

    public DatabaseReference getUserReference(String email) {
        DatabaseReference userReference = null;
        if (email != null && !email.trim().isEmpty()) {
            String emailKey = email.replace(".", "_");
            userReference = dataReference.getReference().getRoot().child(emailKey);
        }

        return userReference;
    }

    public DatabaseReference getMyUserReference() {
        return getUserReference(getAuthUserEmail());
    }

    public DatabaseReference getContactsReference(String email) {
        return getUserReference(email).child(CONTACTS_PATH);
    }

    public DatabaseReference getMyContactsReference() {
        return getContactsReference(getAuthUserEmail());
    }

    public DatabaseReference getOneContactReference(String mainMail, String childMail) {
        String childKey = childMail.replace(".", "_");
        return getUserReference(mainMail).child(CONTACTS_PATH).child(childKey);
    }

    public DatabaseReference getChatsReference(String receiver) {
        String keySender = getAuthUserEmail().replace(".", "_");
        String keyReceiver = receiver.replace(".", "_");

        String keyChat = keySender + SEPARATOR + keyReceiver;
        if (keySender.compareTo(keyReceiver) > 0) {
            keyChat = keyReceiver + SEPARATOR + keySender;
        }

        return dataReference.getReference().getRoot().child(CHATS_PATH).child(keyChat);
    }

    public void changeUserConnection(boolean online) {
        DatabaseReference userReference = getMyUserReference();
        if (userReference != null) {
            Map<String, Object> updates = new HashMap<>();
            updates.put(ONLINE, online);
            userReference.updateChildren(updates);
            notifyContactsOfConnectionChange(online);
        }
    }

    public void notifyContactsOfConnectionChange(boolean online) {
        notifyContactsOfConnectionChange(online, false);
    }

    public void singOff() {
        notifyContactsOfConnectionChange(User.OFFLINE, true);
    }

    private void notifyContactsOfConnectionChange(final boolean online, final boolean signOff) {
        final String myEmail = getAuthUserEmail();
        getMyContactsReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    String email = child.getKey();
                    DatabaseReference reference = getOneContactReference(email, myEmail);
                    reference.setValue(online);
                }

                if (signOff) {
                    FirebaseAuth.getInstance().signOut();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }
}
