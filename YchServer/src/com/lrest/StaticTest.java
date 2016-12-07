package com.lrest;

import java.util.Arrays;

public class StaticTest {

    public static void main(String[] args){
        // 3. 调用方法 , 然后执行方法
//        staticFunection();
//        new StaticTest();
  /*      String a = "a b";
        System.out.println(Arrays.asList(a));*/
    }
    /*
       1.
          new出一个对象后,会再次调用本类,
          所以会执行JVM步骤的3-8,会执行以下代码
       1.1
       {
           System.out.println("2");
       }
       int a = 110;
       System.out.println("3");
       System.out.println("a ="+ a +", b="+b);

       本步会顺序输出:
       2
       3
       a=110 b=0

    */
    static  StaticTest staticTest = new StaticTest();
    // 2. 顺序执行static 会输出 1  至此static 执行完毕
    static{
        System.out.println("1");
    }

    {
        System.out.println("2");
    }

    public StaticTest() {
        // TODO Auto-generated constructor stub
        System.out.println("3");
        System.out.println("a ="+ a +", b="+b);
    }
    // 4.  输出4
    public static void staticFunection(){
        System.out.println("4");
    }

    int a = 110;
    static int b = 112;
}