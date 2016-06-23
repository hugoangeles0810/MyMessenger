package com.hugoangeles.android.mymessenger.contactlist;

/**
 * Created by hugo on 22/06/16.
 */
public class ContactListInteractorImpl implements ContactListInteractor {

    private ContactListRepository listRepository;

    public ContactListInteractorImpl() {
        this.listRepository = new ContactListRepositoryImpl();
    }

    @Override
    public void subscribe() {
        listRepository.subscribeToContactListEvents();
    }

    @Override
    public void unsubscribe() {
        listRepository.unsubscribeToContactListEvents();
    }

    @Override
    public void destroyListener() {
        listRepository.destroyListener();
    }

    @Override
    public void removeContact(String email) {
        listRepository.removeContact(email);
    }
}
