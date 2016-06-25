package com.hugoangeles.android.mymessenger.chat;

/**
 * Created by hugo on 24/06/16.
 */
public class ChatSessionInteractorImpl implements ChatSessionInteractor {

    private ChatRepository repository;

    public ChatSessionInteractorImpl() {
        repository = new ChatRepositoryImpl();
    }

    @Override
    public void changeConnectionStatus(boolean online) {
        repository.changeConnectionStatus(online);
    }
}
