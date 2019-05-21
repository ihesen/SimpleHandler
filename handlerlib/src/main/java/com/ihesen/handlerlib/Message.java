package com.ihesen.handlerlib;

/**
 * Created by ihesen on 2019-05-20
 */
public class Message {

    public int what;
    public Object obj;
    public Handler target;

    public Message() {
    }

    @Override
    public String toString() {
        return obj.toString();
    }
}
