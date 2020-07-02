package com.jackie.asmdemo;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;

import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView textView;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //MyLogger.collectClickEvent(v);
            }
        });
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        textView = findViewById(R.id.test);
//        setContentView(R.layout.activity_main);
//        long totalTime = System.currentTimeMillis() - startTime;
//        MyLogger.collectMethodTime(totalTime);
//        System.out.println("===============Total Time: " + (System.currentTimeMillis() - startTime));
        //test(20);
//        testHasReturnInt(10);
        //MyTest myTest = testHasReturnReference();
        //System.out.println("==========="+myTest.number);
        testAThread();
        testR();
    }

    public void testClick(View v){
//        MyLogger.collectClickEvent(v);
    }

    public void testR(){
//        new CustomThread(){
//            @Override
//            public void run() {
//                super.run();
//                System.out.println("custom");
//            }
//        }.start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("testRunnable");
            }
        }).start();
    }

    public void testAThread(){
        Thread a = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("testAThread");
            }
        });

        a.start();
    }

    //作为测试
    void onClick(View v){

    }




//    private static long time;
    @Override
    protected void onResume() {
//        long time = System.currentTimeMillis();
//        timer -= System.currentTimeMillis();
        super.onResume();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        TestA testA = new TestA();
        //TestB testB = new TestB();
        MyLogger myLogger = new MyLogger();
        testA.hashCode();
        myLogger.hashCode();
//        timer += System.currentTimeMillis();
//        StringBuilder sb = new StringBuilder();
//        sb.append(this.getClass().getSimpleName());
//        sb.append(TestA.PAGE_FIRST_LOADING_TYPE);
//        sb.append("time:    "+timer);
//        Log.d("jackie shimei",sb.toString());
        //long total = System.currentTimeMillis();
//        MyLogger.collectMethodTime(timer,this.getClass().getSimpleName(),TestA.PAGE_FIRST_LOADING_TYPE,"",0);
    }



    public static long timer;

    public void m() throws Exception {
        timer -= System.currentTimeMillis();
        Thread.sleep(1000);
        timer += System.currentTimeMillis();
        System.out.println(timer);
    }

    public MyTest testHasReturnReference(){
//        try {
            MyTest t1 = new MyTest(10);
            return t1;
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return new MyTest(0);
    }

    private int testHasReturnInt(int age){
//        try {
            int a = age / 0;
            return a;
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return 0;
    }

    private void test(int age) {
        try {
            int a = age / 0;
        }catch (Exception e){
            e.printStackTrace();
        }
    }
//            int a = 20 / 0;
//    }

    static class MyTest{
        int number;
        MyTest(int number){
            if (number == 10){
                int a = 10/0;
            }
            this.number = number;
        }
    }
//
//    void onClick(View view){
//
//    }
}
