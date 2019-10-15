package com.atguigu;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 多线程同时读一个资源类没有任何问题，所以共享资源是可以的
 * 但是，要是有一个线程想去写共享资源，就不应该再有其他线程对该资源进行读或者写
 *
 * 总结：
 *      读-读能共享
 *      读-写不能共享
 *      写-写不能共享
 *例子：直播时，多个观众可以读，但是写的只能是主播
 */

/**
 * 进行10次的读入，结束与写入，结束
 * 使用了新技术：针对读不需要锁，写需要锁。使用了ReadWirteLock的方法。针对同一个锁，可以手动控制，哪些方法是否需要加锁
 */
class MyCache{
    //ReentrantReadWirteLock
    private volatile Map<String,String> map=new HashMap<>();
   private ReentrantReadWriteLock rwLock =new ReentrantReadWriteLock();
    public void put (String key,String value){
        rwLock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName()+"/写入开始");
            map.put(key,value);
            System.out.println(Thread.currentThread().getName()+"/写入结束");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            rwLock.writeLock().unlock();
        }
    }
    public void get (String key){
        rwLock.readLock().lock();
        try {
            String result=null;
            System.out.println(Thread.currentThread().getName()+"/读入开始");
            result= map.get(key);
            System.out.println(Thread.currentThread().getName()+"/读入结束/:"+result);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            rwLock.readLock().unlock();
        }
    }



    /*
     原始版本，读写都需要加上锁，性能不太行
     private Lock l=new ReentrantLock();
     public void put(String key,String value){
         l.lock();
         try {
             System.out.println(Thread.currentThread().getName()+"/写入开始");
             System.out.println(Thread.currentThread().getName()+"/写入结束");
         } catch (Exception e) {
             e.printStackTrace();
         } finally {
             l.unlock();
         }
     }
     public void get(String key){
         l.lock();
         try {
             String result=null;
             System.out.println(Thread.currentThread().getName()+"/读取开始");
             System.out.println(Thread.currentThread().getName()+"/读取结束"+result);
         } catch (Exception e) {
             e.printStackTrace();
         } finally{
             l.unlock();
         }

     }*/
}

public class ReadWriteLockDemo {
    public static void main(String[] args) {
        MyCache m = new MyCache();
        for (int i = 0; i <10 ; i++) {
            int finalI=i;
            new Thread(()->{
                m.put(finalI+"",finalI+"");
            },String.valueOf(i)).start();
        }
        for (int i = 0; i <10 ; i++) {
            int finalI=i;
            new Thread(()->{
                m.get(finalI+"");
            },String.valueOf(i)).start();
        }
    }

}
