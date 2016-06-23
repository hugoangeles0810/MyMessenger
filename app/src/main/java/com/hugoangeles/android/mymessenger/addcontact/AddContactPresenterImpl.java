package com.hugoangeles.android.mymessenger.addcontact;

import com.hugoangeles.android.mymessenger.addcontact.AddContactPresenter;
import com.hugoangeles.android.mymessenger.addcontact.ui.AddContactView;
import com.hugoangeles.android.mymessenger.lib.EventBus;
import com.hugoangeles.android.mymessenger.lib.GreenRobotEventBus;

import org.greenrobot.eventbus.Subscribe;

/**
 * Created by hugo on 23/06/16.
 */
public class AddContactPresenterImpl implements AddContactPresenter {

    private EventBus eventBus;
    private AddContactInteractor interactor;
    private AddContactView view;

    public AddContactPresenterImpl(AddContactView view) {
        this.view = view;
        this.eventBus = GreenRobotEventBus.getInstance();
        this.interactor = new AddContactInteractorImpl();
    }

    @Override
    public void onShow() {
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        view = null;
        eventBus.unregister(this);
    }

    @Override
    public void addContact(String email) {
        if (view != null) {
            view.hideInput();
            view.showProgress();
        }
        interactor.execute(email);
    }

    @Subscribe
    @Override
    public void onEventMainThread(AddContactEvent event) {
        if (view != null) {
            view.showInput();
            view.hideProgress();

            if (event.isError()) {
                view.contactNotAdded();
            } else {
                view.contactAdded();
            }
        }
    }
}
