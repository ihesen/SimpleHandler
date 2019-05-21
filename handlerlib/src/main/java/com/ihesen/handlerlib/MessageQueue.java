package com.ihesen.handlerlib;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by ihesen on 2019-05-20
 */
public class MessageQueue {

    //存储Message对象
    private Message[] messageQueue = new Message[50];
    private int inputIndex;//插入消息的角标
    private int takeIndex;//获取消息的角标
    //计数器
    private int count;

    private Lock lock;
    //限制条件(处理入队消息如果满了，就停止入队的操作，如果出队消息为0说明没有消息，停止取消息)
    private Condition notEmpty;
    private Condition notFull;

    public MessageQueue() {
        lock = new ReentrantLock();
        notEmpty = lock.newCondition();
        notFull = lock.newCondition();
    }

    //入队
    public void equeueMessage(Message message) {
        try {
            lock.lock();
            if (count == messageQueue.length) {
                notFull.await();
            }
            messageQueue[inputIndex] = message;
            //循环取值
            inputIndex = (++inputIndex == messageQueue.length) ? 0 : inputIndex;
            count++;
            notEmpty.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    //出队
    public Message next() {
        Message message = messageQueue[takeIndex];
        try {
            lock.lock();
            if (count == 0) {
                notEmpty.await();
            }
            messageQueue[takeIndex] = null;
            takeIndex = (++takeIndex == messageQueue.length) ? 0 : takeIndex;
            count--;
            notFull.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return message;
    }

}
