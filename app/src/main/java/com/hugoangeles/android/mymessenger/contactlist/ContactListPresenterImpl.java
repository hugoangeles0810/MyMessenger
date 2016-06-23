package com.hugoangeles.android.mymessenger.contactlist;

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
        sessionInteractor.changeConnectionStatus(User.OFFLINE);
        listInteractor.unsubscribe();
    }

    @Override
    public void onResume() {
        listInteractor.subscribe();
        sessionInteractor.changeConnectionStatus(User.ONLINE);
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
                break;
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
