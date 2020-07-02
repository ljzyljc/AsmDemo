package com.jackie.time

import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

class TimePlugin implements Plugin<Project>{

    @Override
    void apply(Project project) {

        AppExtension appExtension = (AppExtension)project.getProperties().get("android");
        appExtension.registerTransform(new TimeTransform(project), Collections.EMPTY_LIST);
//        def android = project.extensions.getByType(AppExtension)
//        android.registerTransform(new TimeTransform())

        println("============register=====time transform===")
    }
}