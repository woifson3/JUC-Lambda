package com.atguigu;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * 多线程抢夺多资源
 * Semaphore:CountDownLatch是减法，CyclicBarrier是加法，Semaphore是  可加可减
 * <p>
 * 题目：停车场有3个停车位，有6辆车。三辆车停入，不定时出来，一出来剩下的3辆车就抢夺资源
 */
public class SemaphoreDemo {
    public static void main(String[] args) {
        Semaphore sp = new Semaphore(3);//模拟3个停车位
        for (int i = 1; i <= 6; i++) {
            new Thread(() -> {
                boolean flag=false; //最开始车位上是没有车的
                try {
                    sp.acquire();//设定拿到一个车位，此处3个车位一个个减少                      加
                    flag=true;      //拿到一个车位说明车位上有车了，下面可以使用replace释放车位
                    System.out.println(Thread.currentThread().getName() + "/抢到车位");
                    //在车位上停留1--5秒不定时
                    try {
                        TimeUnit.SECONDS.sleep(new Random().nextInt(5));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + "/----离开车位");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    if (true) {         //如果车位上有车，才能算是归还车位
                        sp.release();  //离开车位就是归还车位，下面车辆进来，循环继续             减
                    }
                }
            }, String.valueOf(i)).start();
        }
    }
}
