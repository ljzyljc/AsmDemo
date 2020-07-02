package com.jackie.asmdemo;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

/**
 * Description:
 *
 * @author feifei5292190@gmail.com
 * @date 2020/6/10
 */
public class TestA implements Opcodes {

    public static final String PAGE_FIRST_LOADING_TYPE = "page_load";

//    public static void generateClass(){
//        ClassWriter cw = new ClassWriter(0);
//        TraceClassVisitor cv = new TraceClassVisitor(cw, new PrintWriter(System.out));
//        cv.visit(Opcodes.ASM5, Opcodes.ACC_PUBLIC,"com/ssy/Studen",null,"java/lang/Object",null);
//        cv.visitEnd();
//    }

    public static void main(String[] args) {

        //String
        boolean flag = 10 == age(20) || 12 == age(12);
        System.out.println(flag);

//        try {
//            //FileInputStream fis = new FileInputStream("/Users/jackie/Desktop/WorkPlace/AsmDemo/app/src/main/java/com/jackie/asmdemo/Test.class");
//
//            File file = new File("/Users/jackie/Desktop/WorkPlace/AsmDemo/app/src/main/java/com/jackie/asmdemo/");
//            file.mkdirs();
//            FileOutputStream fos = new FileOutputStream("/Users/jackie/Desktop/WorkPlace/AsmDemo/app/src/main/java/com/jackie/asmdemo/Test.class");
//            fos.write(geerateClass());
//            fos.close();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }

    public static int age(int age){
        return age;
    }

    public static byte[] geerateClass(){

        ClassWriter cw = new ClassWriter(0);
        FieldVisitor fv;
        MethodVisitor mv;
        AnnotationVisitor av0;

        cw.visit(V1_7, ACC_PUBLIC + ACC_SUPER, "com/jackie/asmdemo/Test", null, "java/lang/Object", null);

        cw.visitSource("Test.java", null);

        {
            mv = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
            mv.visitCode();
            Label l0 = new Label();
            mv.visitLabel(l0);
            mv.visitLineNumber(9, l0);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
            mv.visitInsn(RETURN);
            Label l1 = new Label();
            mv.visitLabel(l1);
            mv.visitLocalVariable("this", "Lcom/jackie/asmdemo/Test;", null, l0, l1, 0);
            mv.visitMaxs(1, 1);
            mv.visitEnd();
        }
        {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "main", "([Ljava/lang/String;)V", null, null);
            mv.visitCode();
            Label l0 = new Label();
            mv.visitLabel(l0);
            mv.visitLineNumber(12, l0);
            mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            mv.visitLdcInsn("===========");
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
            Label l1 = new Label();
            mv.visitLabel(l1);
            mv.visitLineNumber(13, l1);
            mv.visitInsn(RETURN);
            Label l2 = new Label();
            mv.visitLabel(l2);
            mv.visitLocalVariable("args", "[Ljava/lang/String;", null, l0, l2, 0);
            mv.visitMaxs(2, 1);
            mv.visitEnd();
        }
        cw.visitEnd();

        return cw.toByteArray();
    }
}
