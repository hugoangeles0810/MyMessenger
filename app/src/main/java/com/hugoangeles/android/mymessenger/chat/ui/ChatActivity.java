package com.hugoangeles.android.mymessenger.chat.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.hugoangeles.android.mymessenger.R;
import com.hugoangeles.android.mymessenger.chat.ChatPresenter;
import com.hugoangeles.android.mymessenger.chat.ChatPresenterImpl;
import com.hugoangeles.android.mymessenger.chat.adapters.ChatAdapter;
import com.hugoangeles.android.mymessenger.entities.ChatMessage;
import com.hugoangeles.android.mymessenger.lib.GlideImageLoader;
import com.hugoangeles.android.mymessenger.lib.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class ChatActivity extends AppCompatActivity implements ChatView {

    @Bind(R.id.imgAvatar)
    CircleImageView imgAvatar;
    @Bind(R.id.txtUser)
    TextView txtUser;
    @Bind(R.id.txtStatus)
    TextView txtStatus;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.messageRecyclerView)
    RecyclerView messageRecyclerView;
    @Bind(R.id.editTxtMessage)
    EditText editTxtMessage;
    @Bind(R.id.btnSendMessage)
    ImageButton btnSendMessage;

    public static final String EMAIL_KEY = "email";
    public static final String ONLINE_KEY = "online";

    private ChatPresenter presenter;
    private ChatAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);

        setupAdapter();
        setupRecyclerView();

        presenter =  new ChatPresenterImpl(this);
        presenter.onCreate();

        setupToolbar(getIntent());
    }

    private void setupToolbar(Intent intent) {
        String recipient = intent.getStringExtra(EMAIL_KEY);
        presenter.setChatRecipient(recipient);

        boolean online = intent.getBooleanExtra(ONLINE_KEY, false);

        String status = online ? "online" : "offline";
        int color = online ? Color.GREEN : Color.RED;
        txtUser.setText(recipient);
        txtStatus.setText(status);
        txtStatus.setTextColor(color);

        ImageLoader imageLoader = new GlideImageLoader(this);
        imageLoader.load(imgAvatar, "");

        setSupportActionBar(toolbar);
    }

    private void setupRecyclerView() {
        messageRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        messageRecyclerView.setAdapter(adapter);
    }

    private void setupAdapter() {
        ChatMessage msg1 =  new ChatMessage();
        ChatMessage msg2 =  new ChatMessage();

        msg1.setMessage("Hola!!");
        msg1.setSentByMe(true);

        msg2.setMessage("Hola!!");
        msg2.setSentByMe(false);

        List<ChatMessage> msgs = new ArrayList<>();
        msgs.add(msg1);
        msgs.add(msg2);

        adapter =  new ChatAdapter(this, msgs);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResumen();
    }

    @Override
    protected void onPause() {
        presenter.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onMessageReceived(ChatMessage msg) {
        adapter.add(msg);
        messageRecyclerView.scrollToPosition(adapter.getItemCount()-1);
    }

    @OnClick(R.id.btnSendMessage)
    public void handleSendMessage() {
        presenter.sendMessage(editTxtMessage.getText().toString());
        editTxtMessage.setText("");
    }
}
