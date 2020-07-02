package com.jackie.time.bytecode;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.io.File;

import static org.objectweb.asm.Opcodes.ALOAD;
import static org.objectweb.asm.Opcodes.INVOKESTATIC;

/**
 * Description:
 *
 * @author feifei5292190@gmail.com
 * @date 2020/6/17
 */
public class TimingClassAdapter extends ClassVisitor {

    private String className;
    private String[] interfaces;

    public TimingClassAdapter(ClassVisitor cv) {
        super(Opcodes.ASM6,cv);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces);
        this.className = name;
        this.interfaces = interfaces;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
//        MethodVisitor mv = cv.visitMethod(access, name, descriptor, signature, exceptions);
        //return mv == null ? null : new CustomThreadAdapter(mv,access,className + File.separator + name, descriptor,className);

        MethodVisitor mv = super.visitMethod(access, name, descriptor, signature, exceptions);
        System.out.println("========methodName====="+name);
//        if (mv != null){
//            if (name.equals("onClick") && descriptor.equals("(Landroid/view/View;)V")
//                    && isConatainInterfece(interfaces,"android/view/View$OnClickListener")){
//                System.out.println("==========onClick=========");
//                mv = new ReportClickAdapter(mv);
//            }
//        }

        //OnClick添加
        if (mv != null) {
            if (name.equals("onClick") && descriptor.equals("(Landroid/view/View;)V") && isConatainInterfece(interfaces,"android/view/View$OnClickListener")) {
                mv = new MethodVisitor(Opcodes.ASM5, mv) {
                    @Override
                    public void visitCode() {
                        super.visitCode();
                        mv.visitVarInsn(ALOAD, 1);
                        mv.visitMethodInsn(INVOKESTATIC, "com/jackie/asmdemo/MyLogger", "collectClickEvent", "(Landroid/view/View;)V", false);

                    }
                };
            }
        }

        return mv;
    }

    private static boolean isConatainInterfece(String[] mInterfaces, String mInterface) {
        for (String temp : mInterfaces) {
            if (temp.equals(mInterface)) {
                return true;
            }
        }
        return false;

    }
}
