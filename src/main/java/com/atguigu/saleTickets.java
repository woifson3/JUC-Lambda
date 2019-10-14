package com.atguigu;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ******************************JUC************************************
 * 题目：两个人  卖30张票   是一个多线程并发的问题
 * <p>
 * 口诀：高内聚低耦合的情况下，线程     操作      资源类
 * 高内聚：把对资源类的全部操作，以方法的形式封装进资源类（面向对象的编程思想）（空调出厂自带制冷，加热功能）,对外暴露接口，供线程调用
 *
 * 只要涉及到多线程就肯定有资源类
 * <p>
 * 并发：多个线程争抢同一个资源   （争抢手机玩。抢火车票）
 * 并行：多个线程同时各自运行资源  （一边洗脚一边玩手机）
 * <p>
 * 解决高并发的问题之前用到了synchronized
 * 现在使用JUC,JUC就是java.util.concurrent。是一个解决并发问题的类，我们使用到Juc下面Lock的一个实现类叫做:ReentrantLock
 * <p>
 * lock比synchronized更优化的地方是可以指定线程运行的顺序
 * <p>
 * lambda表达式：前提在一个接口中有且只有一个方法，且方法没有方法体
 * 在接口中：default，static是可以存在的，且可以存在多个的      ****是java的新特性*****
 */

class Tickets {  //资源类
    private int num = 30;
    /*
    1.synchronized形式控制多线程
    public synchronized void sale() {
         if (num > 0) {
             System.out.println(Thread.currentThread().getName()+"\t卖出第："+(num--)+"\t 还剩下："+num);
         }
     }*/

    //使用了JUC中的lock接口的实现类ReentrantLock来解决多线程冲突问题
    Lock l = new ReentrantLock();

    public void sale() {
        l.lock();
        try {
            if (num > 0) {
                System.out.println(Thread.currentThread().getName() + "\t卖出第：" + (num--) + "\t 还剩下：" + num);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            l.unlock();
        }
    }
}


public class  saleTickets {
    public static void main(String[] args) throws Exception {
        /*
        匿名内部类的方式实现：线程操作资源类的操作
        final Tickets t = new Tickets();//为什么我这里就要加一个final，下面t才不会报错
            new Thread(new Runnable()
            {
                public void run()
                {
                    for (int i = 1; i <=40; i++) {
                        t.sale();
                    }
                }
            }, "A").start();*/


        //lambda表达式方式实现：线程操作资源类的操作
        //lambda表达式优化匿名内部类
        Tickets t = new Tickets();
        new Thread(() -> {
            for (int i = 1; i <= 40; i++) t.sale();
        }, "A").start();
        new Thread(() -> {
            for (int i = 1; i <= 40; i++) t.sale();
        }, "B").start();

    }
}
