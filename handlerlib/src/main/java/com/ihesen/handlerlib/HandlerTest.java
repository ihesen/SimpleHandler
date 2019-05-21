package com.ihesen.handlerlib;

import java.util.UUID;

/**
 * Created by ihesen on 2019-05-20
 */
public class HandlerTest {

    public static void main(String[] args) {
        Looper.prepare();


        final Handler handler = new Handler() {

            @Override
            public void handlerMessage(Message message) {
                System.out.println(Thread.currentThread().getName() + ", message info :" + message.toString());
            }
        };

        for (int i = 0; i < 10; i++) {
            new Thread() {
                @Override
                public void run() {
                    Message message = new Message();
                    message.what = 1;
                    synchronized (UUID.class) {
                        message.obj = Thread.currentThread().getName() + ",send:" + UUID.randomUUID() + "";
                    }
                    System.out.println(message);
                    handler.sendMessage(message);
                }
            }.start();
        }
        Looper.loop();
    }

}
