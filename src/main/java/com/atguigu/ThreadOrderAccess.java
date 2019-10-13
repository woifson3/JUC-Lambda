package com.atguigu;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 注意标志位的更新
 * Lock比synchronized优势的就是：synchronized只可以保证线程安全，Lock还可以保证线程运行的顺序
 * <p>
 * 题目：三个线程A，B，C
 * A加8次-》B减3次-》C加4次的顺序执行,执行5轮
 */

class ShareSource {
    private int per = 0;//A:0 B:1 C:2
    Lock l = new ReentrantLock();   //ReentrantLock是Lock的实现类
    Condition cA = l.newCondition();    //给同一把锁配了三把钥匙
    Condition cB = l.newCondition();
    Condition cC = l.newCondition();

    public void add() {
        l.lock();
        try {
            //判断
            while (per != 0) {
                cA.await();
            }
            //干活   (A加8次)
            for (int i = 0; i < 8; i++) {
                System.out.println(Thread.currentThread().getName() + "/" + i);
            }
            //通知（精准通知，下一个是B：1）
            per = 1;
            cB.signal();//唤醒B
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            l.unlock();
        }
    }

    public void div() {
        l.lock();
        try {
            //判断
            while (per != 1) {
                cB.await();
            }
            //干活   (B减3次)
            for (int i = 0; i < -3; i--) {
                System.out.println(Thread.currentThread().getName() + "/" + i);
            }
            //通知（精准通知，下一个是C：2）
            per = 2;
            cC.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            l.unlock();
        }
    }

    public void add02() {
        l.lock();
        try {
            //判断
            while (per != 2) {
                cC.await();
            }
            //干活   (c加4次)
            for (int i = 0; i < 8; i++) {
                System.out.println(Thread.currentThread().getName() + "/" + i);
            }
            //通知（精准通知，下一个是A：0）
            per = 0;
            cA.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            l.unlock();
        }
    }
}

public class ThreadOrderAccess {
    public static void main(String[] args) {
        ShareSource sh = new ShareSource();
        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                sh.add();
            }
        }, "A").start();
        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                sh.div();
            }
        }, "B").start();
        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                sh.add02();
            }
        }, "C").start();
    }
}

