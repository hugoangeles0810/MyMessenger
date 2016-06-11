package com.hugoangeles.android.mymessenger.login;

/**
 * Created by Hugo on 10/06/16.
 */
public interface LoginRepository {
    void singUp(String email, String password);
    void singIn(String email, String password);
    void checkSession();
}
