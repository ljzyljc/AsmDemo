package com.jackie.asmdemo;

/**
 * Description:
 *
 * @author feifei5292190@gmail.com
 * @date 2020/7/2
 */
public class CustomThread extends Thread {

    @Override
    public void run() {
        super.run();
        System.out.println("Custom Thread");
    }
}
