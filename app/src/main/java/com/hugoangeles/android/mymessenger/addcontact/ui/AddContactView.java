package com.hugoangeles.android.mymessenger.addcontact.ui;

/**
 * Created by hugo on 23/06/16.
 */
public interface AddContactView {
    void showInput();
    void hideInput();
    void showProgress();
    void hideProgress();
    void contactAdded();
    void contactNotAdded();
}
