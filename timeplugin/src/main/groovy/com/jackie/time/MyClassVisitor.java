//package com.jackie.time;
//
//import org.objectweb.asm.ClassVisitor;
//import org.objectweb.asm.MethodVisitor;
//import org.objectweb.asm.Opcodes;
//import org.objectweb.asm.commons.AdviceAdapter;
//
//import java.lang.reflect.Method;
//
///**
// * Description:
// *
// * @author feifei5292190@gmail.com
// * @date 2020/6/28
// */
//public class MyClassVisitor extends ClassVisitor implements Opcodes {
//
//    MyClassVisitor(ClassVisitor classVisitor){
//        super(ASM5,classVisitor);
//    }
//
//    @Override
//    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
//        MethodVisitor mv = super.visitMethod(access, name, descriptor, signature, exceptions);
//        if (name.equals("onCreate")){
//            return new MyMethodVisitor(mv,access,name,descriptor);
//        }
//        return mv;
//    }
//
//    static class MyMethodVisitor extends AdviceAdapter{
//
//        String methodName;
//        MyMethodVisitor(MethodVisitor methodVisitor,int access, String name, String descriptor){
//            super(ASM5,methodVisitor,access,name,descriptor);
//            this.methodName = name;
//        }
//
//        @Override
//        protected void onMethodEnter() {
//            super.onMethodEnter();
//            mv.visitFieldInsn(GETSTATIC,"java/lang/System", "out", "Ljava/io/PrintStream;");
//            mv.visitLdcInsn("enter======== " + methodName);
//            mv.visitMethodInsn(INVOKESTATIC, "java/io/PrintStream", "println","(Ljava/lang/String;)V",false);
//        }
//
//
//    }
//}
