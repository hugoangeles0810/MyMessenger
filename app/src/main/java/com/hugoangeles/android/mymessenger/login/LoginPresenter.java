package com.hugoangeles.android.mymessenger.login;

/**
 * Created by Hugo on 10/06/16.
 */
public interface LoginPresenter {
    void onDestroy();
    void checkForAuthenticatedUser();
    void validateLogin(String email, String password);
    void registerNewUser(String email, String password);

}
