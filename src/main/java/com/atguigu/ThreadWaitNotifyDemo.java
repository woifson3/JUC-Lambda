package com.atguigu;

/**
 * 口诀：高内聚低耦合情况下，线程操作资源类
 *              判断--》干活--》 通知
 * 生产消费模式，线程之间的消费和调度
 * 判断：哪个线程起    干活：轮到干活就处理    通知：自己的活干完了，通知对接的线程完成多线程的交互
 * <p>
 * notifyAll:唤醒所有等待线程
 * sleep:设定一个时间，时间到了，自己醒来接着干，控制权还在自己手上
 * wait：需要别人唤醒，一旦wait，就会把控制权交出去
 * <p>
 * =====小心多线程的虚假唤醒：判断需要使用while而不是if=====解释在13
 * 简单说就是if只判断一次，while线程进来每次都要判断
 * <p>
 * synchronized版本
 */

class AirConditioner {   //资源类
    private int num = 0;

    //设定为生产者
    public synchronized void add() throws Exception {//这些方法就是高内聚的表现，就像是制冷的功能一样，对外暴露接口，别人直接调用就行
        //1.判断
        while (num != 0) {
            this.wait();
        }
        //2.干活
        num++;
        System.out.println(Thread.currentThread().getName() + "/" + num);
        //3.通知
        this.notifyAll();
    }

    //设定为消费者
    public synchronized void div() throws Exception {
        while (num == 0) {
            this.wait();
        }
        num--;
        System.out.println(Thread.currentThread().getName() + "/" + num);
        this.notifyAll();
    }
}

//设置四个线程就可以用到while而不是if
public class ThreadWaitNotifyDemo {
    public static void main(String[] args) {
        AirConditioner airConditioner = new AirConditioner();
        new Thread(() -> {                    //线程操作资源类
            for (int i = 0; i < 10; i++) {
                try {
                    airConditioner.add();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "A").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    airConditioner.div();//线程操作资源类
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "B").start();
        new Thread(() -> {                    //线程操作资源类
            for (int i = 0; i < 10; i++) {
                try {
                    airConditioner.add();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "C").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    airConditioner.div();//线程操作资源类
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "D").start();
    }
}
