package com.hugoangeles.android.mymessenger.contactlist.adapters;

import com.hugoangeles.android.mymessenger.entities.User;

/**
 * Created by Hugo on 11/06/16.
 */
public interface OnItemClickListener {
    void onItemClick(User user);
    void onItemLongClick(User user);
}
