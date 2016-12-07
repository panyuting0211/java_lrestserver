package com.lrest.server.utils;

import java.util.Random;

/**
    * @Description:
    * @Return: 随机数类
    * @Author: 韩武洽 @Wyshown
    * @Date: 2016/10/26 14:34
    * @Version 2.0
    */
public final class RandomUtils {
    private static final String[] CHARS = new String[]{"a","b","c","d","e"
            ,"f","g","h","i","j"
            ,"k","l","m","n","o"
            ,"p","q","r","s","t"
            ,"u","v","w","x","y"
            ,"z"
            ,"A","B","C","D","E"
            ,"F","G","H","I","J"
            ,"K","L","M","N","O"
            ,"P","Q","R","S","T"
            ,"U","V","W","X","Y"
            ,"Z"
            ,"0","1","2","3","4"
            ,"5","6","7","8","9"
    };
    private static final String[] CHARS_NUM = new String[]{
            "0","1","2","3","4"
            ,"5","6","7","8","9"
    };

    private RandomUtils(){

    }

    /**
     * 随机生成指定位数的字符串（大小写字母以及阿拉伯数字组成）
     * @param len
     * 			 -- 需要生成字符串的长度
     * @return
     * @author 韩武洽
     * [2014年11月10日 上午10:50:28]
     */
    public static String randomString(Integer len){
        if(null == len || 0 == len){
            throw new IllegalArgumentException("非法参数：len不能为空，且大于0");
        }
        StringBuffer buffer = new StringBuffer();
        Random random = new Random();
        for(Integer i = 0;i < len;i++){
            Integer index = random.nextInt(CHARS.length);
            buffer.append(CHARS[index]);
        }
        return buffer.toString();
    }

    /**
     * 随机生成指定位数的字符串（大小写字母以及阿拉伯数字组成）
     * @param len
     * 			 -- 需要生成字符串的长度
     * @return
     * @author 韩武洽
     * [2014年11月10日 上午10:50:28]
     */
    public static String randomInt(Integer len){
        if(null == len || 0 == len){
            throw new IllegalArgumentException("非法参数：len不能为空，且大于0");
        }
        StringBuffer buffer = new StringBuffer();
        Random random = new Random();
        for(Integer i = 0;i < len;i++){
            Integer index = random.nextInt(CHARS_NUM.length);
            buffer.append(CHARS_NUM[index]);
        }
        return buffer.toString();
    }


    public static void main(String[] args) {
        for(int i = 0;i < 20;i++){
            System.out.println(randomInt(32));
        }
    }

}

