package com.hugoangeles.android.mymessenger.contactlist;

import android.util.Log;

import com.hugoangeles.android.mymessenger.MyMessengerApp;
import com.hugoangeles.android.mymessenger.contactlist.events.ContactListEvent;
import com.hugoangeles.android.mymessenger.contactlist.ui.ContactListView;
import com.hugoangeles.android.mymessenger.entities.User;
import com.hugoangeles.android.mymessenger.lib.EventBus;
import com.hugoangeles.android.mymessenger.lib.GreenRobotEventBus;

import org.greenrobot.eventbus.Subscribe;

/**
 * Created by hugo on 22/06/16.
 */
public class ContactListPresenterImpl implements ContactListPresenter {

    public static final String TAG = ContactListPresenterImpl.class.getSimpleName();

    private EventBus eventBus;
    private ContactListView view;
    private ContactListInteractor listInteractor;
    private ContactListSessionInteractor sessionInteractor;

    public ContactListPresenterImpl(ContactListView view) {
        this.view = view;
        this.eventBus = GreenRobotEventBus.getInstance();
        this.listInteractor = new ContactListInteractorImpl();
        this.sessionInteractor = new ContactListSessionInteractorImpl();
    }

    @Override
    public void onCreate() {
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        eventBus.unregister(this);
        listInteractor.destroyListener();
        view = null;
    }

    @Override
    public void onPause() {
        try {
            sessionInteractor.changeConnectionStatus(User.OFFLINE);
            listInteractor.unsubscribe();
        } catch (NullPointerException e) {
            Log.e(TAG, "Handle Null Pointer on Logout", e);
        }
    }

    @Override
    public void onResume() {
        try {
            listInteractor.subscribe();
            sessionInteractor.changeConnectionStatus(User.ONLINE);
        } catch (NullPointerException e) {
            Log.e(TAG, "Handle Null Pointer on Logout", e);
        }

    }

    @Override
    public void signOff() {
        sessionInteractor.changeConnectionStatus(User.OFFLINE);
        listInteractor.unsubscribe();
        listInteractor.destroyListener();
        sessionInteractor.singOff();
    }

    @Override
    public String getCurrentUserEmail() {
        return sessionInteractor.getCurrentUserEmail();
    }

    @Override
    public void removeContact(String email) {
        listInteractor.removeContact(email);
    }

    @Subscribe
    @Override
    public void onEventMainThread(ContactListEvent event) {
        switch (event.getEventType()) {
            case ContactListEvent.onContactAdded:
                onContactAdded(event.getUser());
                break;
            case ContactListEvent.onContactChanged:
                onContactChanged(event.getUser());
                break;
            case ContactListEvent.onContactRemoved:
                onContactRemoved(event.getUser());
            case ContactListEvent.onUserLogout:
                onUserLogout();
                break;
        }
    }

    private void onUserLogout() {
        if (view != null) {
            view.onUserLogout();
        }
    }

    private void onContactAdded(User user) {
        if (view != null) {
            view.onContactAdded(user);
        }
    }

    private void onContactChanged(User user) {
        if (view != null) {
            view.onContactChanged(user);
        }
    }

    private void onContactRemoved(User user) {
        if (view != null) {
            view.onContactRemoved(user);
        }
    }

}
