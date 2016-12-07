package com.lrest;

/**
 * 深入讨论初始化
 * 为什么static{}与构造方法{}语句块不许有return呢？
 * 为什么static{}语句块里不可用抛出异常呢?
 * 为什么构造方法{}语句块抛出异常后所有构造方法都需要抛出这个异常呢？
 * -----------------------------
 * 经过整理后的class如【InitQuestion_2.java内容所示】
 */
class InitQuestion {

    private int x;
    private int y = 20;

    private static int xStatic;
    private static int yStatic = 20;

    public static void main(String arg[]) throws Exception {
        InitQuestion initQuestion = new InitQuestion();
    }

    {
        x = 20;
        if (y < 20) {
            throw new Exception();
        }

        //return ;
    }

    static {
        xStatic = 20;
        if (yStatic < 20) {
            //   throw new Exception();
        }
        // return ;
    }

    public InitQuestion() throws Exception {
        this(0, 0);
    }

    public InitQuestion(int x) throws Exception {
    }

    public InitQuestion(int x, int y) throws Exception {

    }
}

/**
 * 静态变量的初始化工作默认放入static{}语句块前执行
 *      即：生成一个<clinit>方法
 * 实例变量的初始化工作默认放入构造方法{}语句块前执行
 * 然后把语句块放入每个构造方法的前面
 *      即：每个构造器生成一个<init>方法
 *
 * <clinit><init>证明如下【Init.java】文件执行后的代码（最后图片）
 */
class InitQuestion_2 {
    private int x;
    private int y;
    private static int xStatic;
    private static int yStatic;

    static {
        yStatic = 20;
        xStatic = 20;
        if (yStatic < 20) {
            //
        }
    }

    public InitQuestion_2() throws Exception {
        this(0, 0);
    }

    public InitQuestion_2(int x) throws Exception {
        y = 20;
        x = 20;
        if (y < 20) {
            throw new Exception();
        }
    }

    public InitQuestion_2(int x, int y) throws Exception {
        y = 20;
        x = 20;
        if (y < 20) {
            throw new Exception();
        }
    }

}

class Init {

    private int x = getX();
    private static int xStatic = getXStatic();

    static {
        try {
            throw new Exception("静态语句块执行...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    {
        try {
            throw new Exception("构造方法语句块执行...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Init() {
        try {
            throw new Exception("构造方法执行...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int getX() {
        try {
            throw new Exception("实例变量x初始化...");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 10;
    }

    private static int getXStatic() {
        try {
            throw new Exception("静态变量xStatic初始化...");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 10;
    }
}