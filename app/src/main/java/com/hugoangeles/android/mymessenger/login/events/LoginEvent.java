package com.hugoangeles.android.mymessenger.login.events;

/**
 * Created by Hugo on 10/06/16.
 */
public class LoginEvent {

    public final static int onSingInError = 0;
    public final static int onSingUpError = 1;
    public final static int onSingInSuccess = 2;
    public final static int onSingUpSuccess = 3;
    public final static int onFailedToRecoverSession = 4;

    private int evenType;
    private String errorMessage;

    public int getEvenType() {
        return evenType;
    }

    public void setEvenType(int evenType) {
        this.evenType = evenType;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
