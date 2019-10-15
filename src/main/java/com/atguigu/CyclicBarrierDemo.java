package com.atguigu;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * CyclicBarrierDemo:所有线程都到了才能执行
 * 例子：6个人开会，一定要等到最后一个人到了才能开
 * 和CountDownLatch相反，是加法
 * <p>
 * CyclicBarrier(int parties, Runnable barrierAction) 构造方法.Runnable 可以用lambda精简  .int parties:到多少个人就可以开会
 */
public class CyclicBarrierDemo {
    public static void main(String[] args) {
        CyclicBarrier cb = new CyclicBarrier(7, () -> {
            System.out.println(Thread.currentThread().getName() + "/人到齐了，开会吧");
        });
        for (int i = 1; i <= 7; i++) {
            int finalI = i;
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "第" + finalI + "人到了");
                try {
                    cb.await(); //第一种：人没有到7个,人都在这里等着。不能说人到齐了，开会吧
                    //cb.await(2L, TimeUnit.SECONDS); 第二种：有人要是一直不来，就等2秒钟，然后开会
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }, String.valueOf(i)).start();
        }
    }
}
