package com.jackie.trycatch

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.objectweb.asm.ClassReader

import javax.inject.Inject


//class TryCatchTask extends DefaultTask{
//
//    TryCatchExtension tryCatchExtension
//
//    TryCatchTask(){
//        tryCatchExtension = project.tryCatchInfo
//        println("====TryCatchTask======Constructor======"+tryCatchExtension.toString())
//    }
//
//    @TaskAction
//    void run(){
//        println("====Task run====")
//        println(tryCatchExtension.toString())
//    }
//
//}