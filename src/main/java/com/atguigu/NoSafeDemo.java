package com.atguigu;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Collections.synchronizedXXX(...)来确保线程的安全
 * <p>
 * ;ArrayList是线程不安全的，它的前生Vetory是线程安全的，线程安全是牺牲性能，确保安全性的
 * Vetory的底层源码上是有synchorized的，而Arraylist是没有的
 * 还有一种方法是使用Collections工具类中的安全锁：Collections.synchronzedXXX(...);XXX可以是list，set
 * 可以确保线程有性能又安全
 * <p>
 * 并发线程不安全的解决办法：
 * Vectory
 * Collections.synchronizedXXX(...)  [Collections.synchronizedSet(),Collections.synchronizedArraylist(),ConcurrentHashMap()]
 * CopyonwriteArraylist-->读写分离，写时复制（这个就不是加锁的解决办法）
 * <p>
 * ArrayList底层扩容：Arrays.copyOf（老版本，新版本）
 * HashSet底层是HashMap。Map是键值对，set是一个值。为什么set底层是Map呢？因为set使用的是key值，value是写死的一个PRESENT的常量
 */
public class NoSafeDemo {
    public static void main(String[] args) {
//        List list=new ArrayList();这是线程不安全的，下面两种才是线程安全的模式
        //List list=new Vector();
        List list = Collections.synchronizedList(new ArrayList<>());
        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0, 6));
                System.out.println(list);
            }, String.valueOf(i)).start();
        }
    }
}
