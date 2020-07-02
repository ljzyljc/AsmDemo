package com.jackie.time.bytecode;


import com.quinn.hunter.transform.asm.BaseWeaver;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

/**
 * Description:
 *
 * @author feifei5292190@gmail.com
 * @date 2020/6/17
 */
public class TimingWeaver extends BaseWeaver {

//    @Override
//    public boolean isWeavableClass(String fullQualifiedClassName) {
//        boolean flag = super.isWeavableClass(fullQualifiedClassName);
//        System.out.println("=======jackie======"+flag+"     "+fullQualifiedClassName);
//        return flag;
//    }

    @Override
    protected ClassVisitor wrapClassWriter(ClassWriter classWriter) {
        return new TimingClassAdapter(classWriter);
    }

    @Override
    public boolean isWeavableClass(String fullQualifiedClassName){
        boolean flag = fullQualifiedClassName.endsWith(".class")
                && !fullQualifiedClassName.contains("R$")
                && !fullQualifiedClassName.contains("R.class")
                && !fullQualifiedClassName.startsWith("android")
                && !fullQualifiedClassName.contains("BuildConfig.class");
        if (flag) {
            System.out.println("=======jackie======" + flag + "     " + fullQualifiedClassName);
        }
        return flag;
    }
}
