package com.jackie.trycatch

import com.android.build.api.transform.Context
import com.android.build.api.transform.DirectoryInput
import com.android.build.api.transform.Format
import com.android.build.api.transform.JarInput
import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformException
import com.android.build.api.transform.TransformInput
import com.android.build.api.transform.TransformInvocation
import com.android.build.api.transform.TransformOutputProvider
import com.android.build.gradle.internal.pipeline.TransformManager
import org.apache.commons.codec.digest.DigestUtils
import org.gradle.api.Project
import org.apache.commons.io.FileUtils


class TryCatchTransform extends Transform {


    Project project
    TryCatchTransform(Project project) {
        this.project = project
        //super(project)
    }

    @Override
    String getName() {
        return "try-catch transform"
    }

    @Override
    public Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS;
    }

    @Override
    public Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT;
    }

    @Override
    boolean isIncremental() {
        return false
    }

    @Override
    void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        super.transform(transformInvocation)



        TryCatchExtension tryCatchExtension = project.tryCatchInfo
//
//        TimingHunterExtension timingHunterExtension = project.timeHunter

        println("=============hello, body "+ tryCatchExtension.toString())
//
//        println("=============hello hunter" + timingHunterExtension.toString())
    }

}