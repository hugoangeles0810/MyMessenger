package com.hugoangeles.android.mymessenger.contactlist;

/**
 * Created by hugo on 22/06/16.
 */
public class ContactListSessionInteractorImpl implements ContactListSessionInteractor {

    private ContactListRepository listRepository;

    public ContactListSessionInteractorImpl() {
        this.listRepository = new ContactListRepositoryImpl();
    }

    @Override
    public void singOff() {
        listRepository.signOff();
    }

    @Override
    public String getCurrentUserEmail() {
        return listRepository.getCurrentUserEmail();
    }

    @Override
    public void changeConnectionStatus(boolean status) {
        listRepository.changeConnectionStatus(status);
    }
}
