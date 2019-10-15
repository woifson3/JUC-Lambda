package com.atguigu;

import java.util.Random;
import java.util.concurrent.*;

import static java.util.concurrent.Executors.newSingleThreadExecutor;

/**
 * 手写线程池：注意线程池有7个参数：
 * 1. corePoolSize:线程池中常驻核心线程数量--》当值的银行柜员
 * 2. maximumPoolSize:最大线程数，必须大于1--》所有的柜台全开数量
 * 3. keepAliveTime:存活时间--》一段时间过去了，顾客数量逐渐减少。最大线程数变成常驻线程数。缩容
 * 4. TimeUtil.XXX:存活事件的时间单位
 * 5. new ArrayBlockingQueue<>(?):阻塞队列--》候客区
 * 6. Executor.defaultThreadFactory():线程工程--》制作工牌的
 * 7. new ThreadPoolExecutor.DiscardOldestPolicy():拒绝策略--》银行不再接待顾客
 * <p>
 * 线程池的拒绝策略有四种：
 * AbortrPolicy(默认)：处理超过的线程就直接报异常
 * CallerRunsPolicy:把多的任务直接退还给调用者（不建议）此处调用者就是main
 * DiscardOldestPolicy:抛弃队列中等待时间最久的任务，
 * （推荐使用）DiscardPolicy：默默丢弃无法完成的任务，不报异常（如果允许任务丢失，这是最好的一个办法）
 * <p>
 * * 为什么不用下面的线程池呢？
 * 因为它们或多或少会浪费线程。同时他们的底层都是ThreadPoolExecutor。所以干脆手写线程池了
 * Executors是Executor并发的工具类。这里的使用方法类似：List list=Arrays.aslist(A,B,C);
 * Executors.newFixedThreadPool(num) 一池num线程
 * Executors.newSingleThreadPool()  一池一线程
 * Executors.newCachedThreadPoolZ()  一池n线程
 * <p>
 * 下面的例子是使用线程池，模拟10个客户来办理业务
 */
public class MyThreadPoolDemo {
    public static void main(String[] args) {
        //这就是手写线程池，要传7个参数
        ExecutorService executor = new ThreadPoolExecutor(
                2,
                5,
                2L,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.DiscardPolicy()//线程池的拒绝策略有4个,一般使用DiscardPolicy
        );
        try {
            for (int i = 1; i <= 80; i++) {//模拟n个客户来银行办理业务
                executor.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + "/t 办理业务" + new Random().nextInt(6));
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }

    }

}
