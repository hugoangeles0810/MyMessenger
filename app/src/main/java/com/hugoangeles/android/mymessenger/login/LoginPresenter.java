package com.hugoangeles.android.mymessenger.login;

import com.hugoangeles.android.mymessenger.login.events.LoginEvent;

/**
 * Created by Hugo on 10/06/16.
 */
public interface LoginPresenter {
    void onCreate();
    void onDestroy();
    void checkForAuthenticatedUser();
    void validateLogin(String email, String password);
    void registerNewUser(String email, String password);
    void onEventMainThread(LoginEvent event);

}
