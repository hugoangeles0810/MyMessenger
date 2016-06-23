package com.hugoangeles.android.mymessenger.contactlist.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.hugoangeles.android.mymessenger.R;
import com.hugoangeles.android.mymessenger.addcontact.ui.AddContactFragment;
import com.hugoangeles.android.mymessenger.contactlist.ContactListPresenter;
import com.hugoangeles.android.mymessenger.contactlist.ContactListPresenterImpl;
import com.hugoangeles.android.mymessenger.contactlist.adapters.ContactListAdapter;
import com.hugoangeles.android.mymessenger.contactlist.adapters.OnItemClickListener;
import com.hugoangeles.android.mymessenger.entities.User;
import com.hugoangeles.android.mymessenger.lib.GlideImageLoader;
import com.hugoangeles.android.mymessenger.lib.ImageLoader;
import com.hugoangeles.android.mymessenger.login.ui.LoginActivity;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ContactListActivity extends AppCompatActivity implements ContactListView, OnItemClickListener {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.recyclerViewContacts)
    RecyclerView recyclerViewContacts;

    private ContactListAdapter adapter;
    private ContactListPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
        ButterKnife.bind(this);

        presenter = new ContactListPresenterImpl(this);
        setupAdapter();
        setupRecyclerView();
        setupToolbar();
        presenter.onCreate();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_contactlist, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_logout) {
            presenter.signOff();
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                    Intent.FLAG_ACTIVITY_NEW_TASK |
                    Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupRecyclerView() {
        recyclerViewContacts.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewContacts.setAdapter(adapter);
    }

    private void setupAdapter() {
        ImageLoader imageLoader = new GlideImageLoader(this);
        adapter = new ContactListAdapter(new ArrayList<User>(), imageLoader, this);
    }

    private void setupToolbar() {
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

    @OnClick(R.id.fab)
    public void addContact() {
        new AddContactFragment().show(getSupportFragmentManager(), getString(R.string.addcontact_message_title));
    }

    @Override
    public void onContactAdded(User user) {
        adapter.add(user);
    }

    @Override
    public void onContactChanged(User user) {
        adapter.update(user);
    }

    @Override
    public void onContactRemoved(User user) {
        adapter.remove(user);
    }

    @Override
    public void onItemClick(User user) {
        Toast.makeText(this, user.getEmail(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemLongClick(User user) {
        presenter.removeContact(user.getEmail());
    }
}
