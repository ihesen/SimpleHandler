package com.ihesen.handlerlib;

/**
 * Created by ihesen on 2019-05-20
 */
public class Handler {

    private MessageQueue messageQueue;
    private Looper mLooper;


    public Handler() {
        //获取主线程Looper对象
        mLooper = Looper.myLooper();
        messageQueue = mLooper.mQueue;
    }

    public void sendMessage(Message message) {
        message.target = this;
        messageQueue.equeueMessage(message);
    }

    public void handlerMessage(Message message) {

    }

    public void dispatchMessage(Message message){
        handlerMessage(message);
    }

}
