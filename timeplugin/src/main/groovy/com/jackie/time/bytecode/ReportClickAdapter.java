package com.jackie.time.bytecode;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.AdviceAdapter;

/**
 * Description:
 *
 * @author feifei5292190@gmail.com
 * @date 2020/6/17
 */
public class ReportClickAdapter extends MethodVisitor implements Opcodes {



    public ReportClickAdapter(MethodVisitor methodVisitor) {
        super(ASM6, methodVisitor);
    }

    @Override
    public void visitCode() {
        super.visitCode();
        mv.visitVarInsn(ALOAD, 1);
        mv.visitMethodInsn(INVOKESTATIC, "com/jackie/asmdemo/MyLogger", "collectClickEvent", "(Landroid/view/View;)V", false);
    }


}
