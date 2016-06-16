package com.hugoangeles.android.mymessenger.login.ui;

/**
 * Created by Hugo on 10/06/16.
 */
public interface LoginView {
    void enableInputs();
    void disableInputs();
    void showProgress();
    void hideProgress();

    void handleSingUp();
    void handleSingIn();

    void navigateToMainScreen();
    void loginError(String error);

    void newUserSuccess();
    void newUserError(String error);

}
