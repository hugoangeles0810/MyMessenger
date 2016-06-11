package com.hugoangeles.android.mymessenger.login;

import android.util.Log;

import com.hugoangeles.android.mymessenger.domain.FirebaseHelper;

/**
 * Created by Hugo on 10/06/16.
 */
public class LoginRepositoryImpl implements LoginRepository {

    private FirebaseHelper helper;

    public static final String TAG = LoginRepositoryImpl.class.getSimpleName();

    public LoginRepositoryImpl() {
        helper = FirebaseHelper.getInstance();
    }

    @Override
    public void singUp(String email, String password) {
        Log.d(TAG, "singUp");
    }

    @Override
    public void singIn(String email, String password) {
        Log.d(TAG, "singIn");
    }

    @Override
    public void checkSession() {
        Log.d(TAG, "checkSession");
    }
}
