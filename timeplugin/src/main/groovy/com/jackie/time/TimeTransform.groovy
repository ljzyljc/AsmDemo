package com.jackie.time


import com.android.build.api.transform.TransformException
import com.android.build.api.transform.TransformInvocation
import com.jackie.time.bytecode.TimingWeaver
import com.quinn.hunter.transform.HunterTransform
import org.gradle.api.Project

class TimeTransform extends HunterTransform {

    TimeTransform(Project project) {
        super(project)
        this.bytecodeWeaver = new TimingWeaver()
    }

    @Override
    boolean isIncremental() {
        return false
    }

    @Override

    void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        super.transform(transformInvocation)

    }


}