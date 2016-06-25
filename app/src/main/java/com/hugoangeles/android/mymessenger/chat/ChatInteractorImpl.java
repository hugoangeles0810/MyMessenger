package com.hugoangeles.android.mymessenger.chat;

/**
 * Created by hugo on 24/06/16.
 */
public class ChatInteractorImpl implements ChatInteractor {

    private ChatRepository repository;

    public ChatInteractorImpl() {
        this.repository = new ChatRepositoryImpl();
    }

    @Override
    public void sendMessage(String msg) {
        repository.sendMessage(msg);
    }

    @Override
    public void setRecipient(String recipient) {
        repository.setRecipient(recipient);
    }

    @Override
    public void subcribe() {
        repository.subcribe();
    }

    @Override
    public void unsubcribe() {
        repository.unsubcribe();
    }

    @Override
    public void destroyListener() {
        repository.destroyListener();
    }
}
