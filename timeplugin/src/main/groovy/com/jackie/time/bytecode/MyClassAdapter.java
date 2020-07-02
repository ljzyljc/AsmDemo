package com.jackie.time.bytecode;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;

/**
 * Description:
 *
 * @author feifei5292190@gmail.com
 * @date 2020/6/29
 */
public class MyClassAdapter extends ClassNode implements Opcodes {

    public MyClassAdapter(ClassVisitor cv){
        super(ASM6);
        this.cv = cv;
    }

    @Override
    public void visitEnd() {
        accept(cv);
    }


    public void test(){
//        ClassWriter cw = new ClassWriter(0);
//        ClassVisitor ca = new MyClassAdapter(cw);
//        ClassReader cr = new ClassReader();
//        cr.accept(ca,0);
//        byte[] b = cw.toByteArray();
    }

}
