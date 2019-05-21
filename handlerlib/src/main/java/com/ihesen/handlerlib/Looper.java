package com.ihesen.handlerlib;

/**
 * Created by ihesen on 2019-05-20
 */
public final class Looper {

    static final ThreadLocal<Looper> sThreadLocal = new ThreadLocal<>();

    MessageQueue mQueue;

    private Looper() {
        mQueue = new MessageQueue();
    }

    public static Looper myLooper() {
        return sThreadLocal.get();
    }

    public static void prepare() {
        if (sThreadLocal.get() != null) {
            throw new RuntimeException("Only one Looper may be created per thread");
        }
        sThreadLocal.set(new Looper());
    }

    public static void loop() {
        Looper me = myLooper();
        MessageQueue messageQueue = me.mQueue;
        for (; ; ) {
            Message next = messageQueue.next();
            if (next == null) {
                continue;
            }
            next.target.dispatchMessage(next);

        }
    }

}
