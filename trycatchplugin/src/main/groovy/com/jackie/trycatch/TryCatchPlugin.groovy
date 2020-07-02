package com.jackie.trycatch

import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

import java.sql.Time

class TryCatchPlugin implements Plugin<Project>{

    @Override
    void apply(Project project) {

        def android = project.extensions.getByType(AppExtension)
        def tryCatchTransForm = new TryCatchTransform(project)
        android.registerTransform(tryCatchTransForm)
        println("===================jackie=======go")
//        project.task("test_task"){
//            println("MyPlugin Start~")
//        }
        project.extensions.create("tryCatchInfo",TryCatchExtension)
//        project.extensions.create("timeHunter", TimingHunterExtension)

        println("============create TryCatchInfo Extension")
//        project.afterEvaluate {
//            println("============afterEvaluate=========")
//            project.task("tryCatchTask",type:TryCatchTask)
//        }


    }
}