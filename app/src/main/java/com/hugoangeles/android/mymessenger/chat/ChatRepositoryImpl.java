package com.hugoangeles.android.mymessenger.chat;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.hugoangeles.android.mymessenger.domain.FirebaseHelper;
import com.hugoangeles.android.mymessenger.entities.ChatMessage;
import com.hugoangeles.android.mymessenger.lib.EventBus;
import com.hugoangeles.android.mymessenger.lib.GreenRobotEventBus;

/**
 * Created by hugo on 24/06/16.
 */
public class ChatRepositoryImpl implements ChatRepository {

    private FirebaseHelper firebase;
    private EventBus eventBus;
    private ChildEventListener chatEventListener;
    private String recipient;


    public ChatRepositoryImpl() {
        this.firebase = FirebaseHelper.getInstance();
        this.eventBus = GreenRobotEventBus.getInstance();
    }

    @Override
    public void changeConnectionStatus(boolean online) {
        firebase.changeUserConnection(online);
    }

    @Override
    public void sendMessage(String msg) {
        String sender = firebase.getAuthUserEmail();
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setSender(sender);
        chatMessage.setMessage(msg);

        DatabaseReference chatsReferences = firebase.getChatsReference(recipient);
        chatsReferences.push().setValue(chatMessage);
    }

    @Override
    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    @Override
    public void subcribe() {
        if (chatEventListener == null) {
            chatEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    ChatMessage chatMessage = dataSnapshot.getValue(ChatMessage.class);
                    String msgSender = chatMessage.getSender();

                    chatMessage.setSentByMe(msgSender.equals(firebase.getAuthUserEmail()));

                    ChatEvent chatEvent = new ChatEvent(chatMessage);
                    eventBus.post(chatEvent);
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {}

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {}

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {}

                @Override
                public void onCancelled(DatabaseError databaseError) {}
            };
        }

        firebase.getChatsReference(recipient).addChildEventListener(chatEventListener);
    }

    @Override
    public void unsubcribe() {
        if (chatEventListener != null) {
            firebase.getChatsReference(recipient).removeEventListener(chatEventListener);
        }
    }

    @Override
    public void destroyListener() {
        chatEventListener = null;
    }
}
