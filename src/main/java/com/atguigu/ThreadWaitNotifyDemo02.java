package com.atguigu;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Lock版本的多线程之间的调度
 * 四个线程，A加5次，B减3次，C加8次，D减4次：Lock的优势

 * <p>
 * wait,notifyAll是属于Object的，在Lock中无法使用，就诞生了Condition中的替代方法：await和SignalAll
 *
 * 使用lock是synchronized的升级版。synchronized不争抢，但是lock是可以同时保证线程的顺序
 */

class AirConditioner02 {
    private Lock l = new ReentrantLock();
    private Condition condition = l.newCondition();
    private int num = 0;

    public void add() {
        l.lock();
        try {
            while (num != 0) {
                condition.await();  //替换的
            }
            num++;
            System.out.println(Thread.currentThread().getName()+"/"+num);
            condition.signalAll();   //替换的
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            l.unlock();
        }
    }
    public void div() {
        l.lock();
        try {
            while (num == 0) {
                condition.await();
            }
            num--;
            System.out.println(Thread.currentThread().getName()+"/"+num);
            condition.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            l.unlock();
        }
    }
}

public class ThreadWaitNotifyDemo02 {
    public static void main(String[] args) {
        AirConditioner02 airConditioner02 = new AirConditioner02();

        new Thread(()->{
            for (int i = 0; i <30 ; i++) {
                airConditioner02.add();
            }
        },"A").start();
        new Thread(()->{
            for (int i = 0; i <30 ; i++) {
                airConditioner02.div();
            }
        },"B").start();
        new Thread(()->{
            for (int i = 0; i <30 ; i++) {
                airConditioner02.add();
            }
        },"C").start();
        new Thread(()->{
            for (int i = 0; i <30 ; i++) {
                airConditioner02.div();
            }
        },"D").start();
    }
}
