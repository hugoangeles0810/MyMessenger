package com.hugoangeles.android.mymessenger.contactlist.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.hugoangeles.android.mymessenger.R;
import com.hugoangeles.android.mymessenger.contactlist.ContactListPresenter;
import com.hugoangeles.android.mymessenger.entities.User;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ContactListActivity extends AppCompatActivity implements ContactListView {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.recyclerViewContacts)
    RecyclerView recyclerViewContacts;

    private ContactListPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
        ButterKnife.bind(this);
        
        presenter.onCreate();
        toolbar.setTitle(presenter.getCurrentUserEmail());
        setSupportActionBar(toolbar);

    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    protected void onPause() {
        presenter.onPause();
        super.onPause();
    }

    @Override
    public void onContactAdded(User user) {

    }

    @Override
    public void onContactChanged(User user) {

    }

    @Override
    public void onContactRemoved(User user) {

    }
}
