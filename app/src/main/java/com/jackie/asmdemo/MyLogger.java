package com.jackie.asmdemo;

import android.view.View;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Description:
 *
 * @author feifei5292190@gmail.com
 * @date 2020/6/17
 */
public class MyLogger {

//    FileOutputStream fileOutputStream;
//
//    {
//        try {
//            fileOutputStream = new FileOutputStream("");
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//    }
//    public void test2(){
//        System.out.println("jackie");
//    }


//    class TestClassD{
//
//        public void test(){
//            System.out.println("jackie");
//            test2();
//        }
//    }

    public static void collectMethodTime(long totalTime,String page,String name,String url,int count){
        System.out.println(" cost time: "+totalTime + "page: "+ page);
    }

    public static void collectClickEvent(View view){
        System.out.println("click event: x: "+ view.getX()+" y: "+view.getY());
    }

    public static void main(String[] args) {
//        char c = '1';
//        int a =c;
//        System.out.println(a);
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("test");
//
//            }
//        });

        Runnable runnable = ()-> {
            System.out.println("jackie");
        };
        runnable.run();
    }

}
