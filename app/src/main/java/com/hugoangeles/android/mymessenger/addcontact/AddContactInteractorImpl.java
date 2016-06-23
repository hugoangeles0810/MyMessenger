package com.hugoangeles.android.mymessenger.addcontact;

/**
 * Created by hugo on 23/06/16.
 */
public class AddContactInteractorImpl implements AddContactInteractor {

    private AddContactRepository repository;

    public AddContactInteractorImpl() {
        this.repository = new AddContactRepositoryImpl();
    }

    @Override
    public void execute(String email) {
        repository.addContact(email);
    }
}
