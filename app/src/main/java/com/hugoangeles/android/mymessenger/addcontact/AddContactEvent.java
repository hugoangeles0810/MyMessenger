package com.hugoangeles.android.mymessenger.addcontact;

/**
 * Created by hugo on 23/06/16.
 */
public class AddContactEvent {
    private boolean error = false;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }
}
