package com.jackie.asmdemo;

import android.os.Build;
import android.os.MessageQueue;

import androidx.annotation.RequiresApi;

/**
 * Description:
 *
 * @author feifei5292190@gmail.com
 * @date 2020/6/29
 */
public class TestB {

    private MessageQueue messageQueue;

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void test(){
//        MessageQueue messageQueue = this.messageQueue;
        messageQueue.isIdle();
        System.out.println("=======test=====");
        messageQueue.isIdle();
        System.out.println("======test 2====");

        messageQueue.isIdle();
    }
}
