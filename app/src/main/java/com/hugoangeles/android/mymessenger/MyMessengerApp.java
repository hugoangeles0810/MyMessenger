package com.hugoangeles.android.mymessenger;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Hugo on 08/06/16.
 */
public class MyMessengerApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        setupFirebase();
    }

    private void setupFirebase() {
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
