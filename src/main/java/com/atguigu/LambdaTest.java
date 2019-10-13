package com.atguigu;

/**
 * 口诀：拷贝小括号，写死右箭头，落地大括号
 * 拷贝接口中方法中的小括号,右箭头是固定写法，大括号是具体的业务逻辑
 * <p>
 * 要使用lambda表达式，前提就是接口中有且只有一个方法，且不能有方法体
 * 但是有新特性增加了 default，static ，这两个可以在接口中存在多个，且可以有方法体
 * 当一个接口中只有一个方法时，系统底层默认此接口是一个函数式接口（隐式的）。
 * 要显示表达一个函数式接口(只有一个方法的接口)时，要在接口上使用@FunctionalInterface
 * <p>
 * 接口是可以new的，在匿名内部类的方式下。
 */
@FunctionalInterface
interface Foo {
    public int add(int x, int y);

    default int div(int a, int b) {//新特性，可以在lambda表达式的接口中存在多个
        return a - b;
    }

    static int mul(int s, int d) {
        return s / d;
    }
    //public void Hello();
}

public class LambdaTest {
    public static void main(String[] args) {
        //lambda表达式的例子
        Foo f = (int x, int y) -> {
            System.out.println("lambda表达式有参");
            return x + y;
        };
        System.out.println(f.add(3, 4));

        //java新特性default，static新特性
        System.out.println(f.div(3, 1));
        System.out.println(Foo.mul(4, 2));//static的要使用接口名去调用
       /*
       匿名内部类方式
       Foo f = new Foo(){
            @Override
            public void Hello() {
                System.out.println("***hello");
            }
        };
        f.Hello();*/
    }
}
