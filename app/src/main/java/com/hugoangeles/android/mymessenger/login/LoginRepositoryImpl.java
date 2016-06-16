package com.hugoangeles.android.mymessenger.login;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.hugoangeles.android.mymessenger.domain.FirebaseHelper;
import com.hugoangeles.android.mymessenger.entities.User;
import com.hugoangeles.android.mymessenger.lib.GreenRobotEventBus;
import com.hugoangeles.android.mymessenger.login.events.LoginEvent;

/**
 * Created by Hugo on 10/06/16.
 */
public class LoginRepositoryImpl implements LoginRepository {

    private FirebaseHelper helper;
    private DatabaseReference dataReference;
    private DatabaseReference myUserReference;

    public static final String TAG = LoginRepositoryImpl.class.getSimpleName();

    public LoginRepositoryImpl() {
        helper = FirebaseHelper.getInstance();
        dataReference = helper.getDataReference().getReference();
        myUserReference = helper.getMyUserReference();
    }

    @Override
    public void singUp(final String email, final String password) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        postEvent(LoginEvent.onSingUpSuccess);
                        singIn(email, password);
                    } else {
                        postEvent(LoginEvent.onSingUpError, task.getException().getMessage());
                    }
                }
            });
    }

    @Override
    public void singIn(String email, String password) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        initSignIn();
                    } else {
                        postEvent(LoginEvent.onSingInError, task.getException().getMessage());
                    }
                }
            });
    }

    @Override
    public void checkSession() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            initSignIn();
        } else {
            postEvent(LoginEvent.onFailedToRecoverSession);
        }
    }

    private void initSignIn() {
        myUserReference = helper.getMyUserReference();
        myUserReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User currentUser = dataSnapshot.getValue(User.class);

                if (currentUser != null) {
                    registerNewUser();
                }
                helper.changeUserConnection(User.ONLINE);
                postEvent(LoginEvent.onSingInSuccess);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    private void registerNewUser() {
        String email = helper.getAuthUserEmail();
        if (email != null) {
            User currentUser = new User();
            currentUser.setEmail(email);
            myUserReference.setValue(currentUser);
        }
    }

    private void postEvent(int type, String errorMessage) {
        LoginEvent loginEvent = new LoginEvent();
        loginEvent.setEvenType(type);
        if (errorMessage != null) {
            loginEvent.setErrorMessage(errorMessage);
        }

        GreenRobotEventBus.getInstance().post(loginEvent);
    }

    private void postEvent(int type) {
        postEvent(type, null);
    }


}
