package com.atguigu;

import sun.nio.ch.ThreadPool;

import java.util.concurrent.*;

/**
 * 实现多线程的方式有4种:实现Runnable,继承Thread,Callable,线程池
 * <p>
 * CountDownLatch:计数器（适用于）所有线程结束了，主线程才可以结束
 * 例子：一个7个人其中一个是队长，只有5个人走完了，队长才可以走(队长必须最后一个走)。
 * 是做减法的
 *
 * 新增：使用线程池方式
 */

public class countDownLatchDemo {
    public static void main(String[] args) throws Exception {
        CountDownLatch count = new CountDownLatch(6);
        ExecutorService executor = new ThreadPoolExecutor(//使用线程池的方法改写new Thread的方法
                2,
                5,
                3L,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.DiscardPolicy()
        );
        try {
            for (int i = 0; i <3 ; i++) {
                executor.execute(()->{
                    System.out.println(Thread.currentThread().getName()+"/t离开教室");
                    count.countDown();//一个离开就减少一个
                });
            }
            count.await(2L,TimeUnit.SECONDS);//使用的是过时不候策略
            //count.await();   不见不散策略
            System.out.println(Thread.currentThread().getName()+"/t --离开教室");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();//池子需要关闭
        }




        /*for (int i = 0; i < 4; i++) {//五个组员先走
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "/离开教室");
                count.countDown();//一个离开教室就减去一个
            }, String.valueOf(i)).start();
        }
       // count.await();//第一种：5个人没有走光，线程就会在此等待（就不能让队长关门）
        count.await(2l, TimeUnit.SECONDS);//第二种：等你2秒钟，要是两秒钟还没走，不管还有多少人，队长都要关门走人了
        System.out.println(Thread.currentThread().getName() + "/离开教室");//队长最后关门*/
    }
}
