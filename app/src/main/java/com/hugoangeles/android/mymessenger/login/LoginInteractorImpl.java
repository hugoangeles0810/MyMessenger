package com.hugoangeles.android.mymessenger.login;

/**
 * Created by Hugo on 10/06/16.
 */
public class LoginInteractorImpl implements LoginInteractor {

    private LoginRepository loginRepository;

    public LoginInteractorImpl() {
        loginRepository = new LoginRepositoryImpl();
    }

    @Override
    public void checkSession() {
        loginRepository.checkSession();
    }

    @Override
    public void doSingUp(String email, String password) {
        loginRepository.singUp(email, password);
    }

    @Override
    public void doSingIn(String email, String password) {
        loginRepository.singIn(email, password);
    }
}
