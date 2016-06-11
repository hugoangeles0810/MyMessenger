package com.hugoangeles.android.mymessenger.login;

/**
 * Created by Hugo on 10/06/16.
 */
public interface LoginInteractor {
    void checkSession();

    void doSingUp(String email, String password);
    void doSingIn(String email, String password);

}
