package com.atguigu;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 获取多线程的四种方式中的第三种：实现callable
 * callable可以起到异步请求的作用：我要上课，但是我要喝水，就让别人去买。最后线程汇合，我上课，也能喝到水了
 * 详见21
 * 要使用callable，但是Thread(Runnable target, String name) 。里面只能放Runnable。
 * 所以需要使用到Runnable的一个实现类：FutureTask
 * 同时FutureTask(Callable<V> callable) ，FutureTask中又有我们需要的Callable（这就是构造注入）
 * 这样Runnable就和Callable就产生了联系
 * <p>
 * FutureTask<String> futureTask = new FutureTask<>(new M());
 * 就是原本是new Thread（Runnable target,String name）,所以要用Callable，所以Runable改变成实现类FutureTask
 * FutureTask装好callable，就是Runnable。然后在装到Thread中（30,31）
 *
 *
 */

class M implements Callable { //一个类实现Callable
    @Override
    public Object call() throws Exception {
        System.out.println(Thread.currentThread().getName() + "````Come into This Thread");
        return "java0615";
    }
}
//get方法一般放在最后最后一行，确保异步请求中主方法 不会因为某个线程时间长而阻塞在那边。正确的是要让主线程顺利走下去，
//分支自己按照自己的步伐走，最后只要和主线程汇合就可以
public class CallabledDemo {
    public static void main(String[] args) throws Exception {
      /*  FutureTask<String> futureTask = new FutureTask<>(new M());
        new Thread(futureTask, "SAN").start();
        String s = futureTask.get();
        System.out.println(s);//得到return返回值*/

      //下面是把资源类改成lambda的
        FutureTask<String> futureTask = new FutureTask<>(()->{
            System.out.println(Thread.currentThread().getName() + "````Come into This Thread");
            return "java0615";
        });
        new Thread(futureTask, "SAN").start();
        System.out.println(Thread.currentThread().getName()+"主线程");
        String s = futureTask.get();
        System.out.println(s);//得到return返回值

    }
}
