package com.hugoangeles.android.mymessenger.chat.adapters;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hugoangeles.android.mymessenger.R;
import com.hugoangeles.android.mymessenger.entities.ChatMessage;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by hugo on 24/06/16.
 */
public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    private Context context;

    private List<ChatMessage> messages;

    public ChatAdapter(Context context, List<ChatMessage> msgs) {
        this.context = context;
        this.messages = msgs;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.content_chat, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ChatMessage message = messages.get(position);
        String msg = message.getMessage();

        holder.txtMessage.setText(msg);

        int color = fetchColor(R.attr.colorPrimary);
        int gravity = Gravity.RIGHT;

        if (!message.isSentByMe()) {
            color = fetchColor(R.attr.colorAccent);
            gravity = Gravity.LEFT;
        }

        holder.txtMessage.setBackgroundColor(color);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) holder.txtMessage.getLayoutParams();
        params.gravity = gravity;

        holder.txtMessage.setLayoutParams(params);
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public void add(ChatMessage msg) {
        if (!messages.contains(msg)) {
            messages.add(msg);
            notifyDataSetChanged();
        }
    }

    private int fetchColor(int color) {
        TypedValue typedValue = new TypedValue();
        TypedArray array = context.obtainStyledAttributes(typedValue.data, new int[]{color});
        int returnColor = array.getColor(0, 0);
        array.recycle();
        return returnColor;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.txtMessage)
        TextView txtMessage;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}
