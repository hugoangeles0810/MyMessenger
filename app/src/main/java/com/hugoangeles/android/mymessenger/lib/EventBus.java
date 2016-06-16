package com.hugoangeles.android.mymessenger.lib;

/**
 * Created by Hugo on 10/06/16.
 */
public interface EventBus {

    void register(Object subscriber);
    void unregister(Object subscriber);
    void post(Object event);
}
