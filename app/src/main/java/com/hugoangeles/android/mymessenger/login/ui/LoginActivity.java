package com.hugoangeles.android.mymessenger.login.ui;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.hugoangeles.android.mymessenger.R;
import com.hugoangeles.android.mymessenger.contactlist.ui.ContactListActivity;
import com.hugoangeles.android.mymessenger.login.LoginPresenter;
import com.hugoangeles.android.mymessenger.login.LoginPresenterImpl;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements LoginView {

    @Bind(R.id.txt_login_username)
    EditText inputUsername;

    @Bind(R.id.txt_login_password)
    EditText inputPassword;

    @Bind(R.id.btn_login_sing_in)
    Button btnSingIn;

    @Bind(R.id.btn_login_sing_up)
    Button btnSingUp;

    @Bind(R.id.progressBar)
    ProgressBar progressBar;

    @Bind(R.id.container)
    View container;

    private LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        loginPresenter = new LoginPresenterImpl(this);
        loginPresenter.onCreate();
        loginPresenter.checkForAuthenticatedUser();
    }

    @Override
    protected void onDestroy() {
        loginPresenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void enableInputs() {
        setInputs(true);
    }

    @Override
    public void disableInputs() {
        setInputs(false);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @OnClick(R.id.btn_login_sing_up)
    @Override
    public void handleSingUp() {
        loginPresenter.registerNewUser(inputUsername.getText().toString(), inputPassword.getText().toString());
    }

    @OnClick(R.id.btn_login_sing_in)
    @Override
    public void handleSingIn() {
        loginPresenter.validateLogin(inputUsername.getText().toString(), inputPassword.getText().toString());
    }

    @Override
    public void navigateToMainScreen() {
        startActivity(new Intent(this, ContactListActivity.class));
    }

    @Override
    public void loginError(String error) {
        inputPassword.setText("");
        String msgError = String.format(getString(R.string.login_error_message_singin), error);
        inputPassword.setError(msgError);
    }

    @Override
    public void newUserSuccess() {
        Snackbar.make(container, R.string.login_notice_message_singup, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void newUserError(String error) {
        inputPassword.setText("");
        String msgError = String.format(getString(R.string.login_error_message_singup), error);
        inputPassword.setError(msgError);
    }

    private void setInputs(boolean enabled) {
        inputUsername.setEnabled(enabled);
        inputPassword.setEnabled(enabled);
        btnSingIn.setEnabled(enabled);
        btnSingUp.setEnabled(enabled);
    }
}
