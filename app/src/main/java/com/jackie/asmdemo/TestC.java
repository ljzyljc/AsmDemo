package com.jackie.asmdemo;

import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.util.TraceClassVisitor;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * Description:
 *
 * @author feifei5292190@gmail.com
 * @date 2020/6/10
 */
public class TestC implements Opcodes{


    public static void main(String[] args) throws Exception{
        //test1();
        test2();

    }


    public static void test2(){
        ClassWriter cw = new ClassWriter(0);

        TraceClassVisitor cv = new TraceClassVisitor(cw,new PrintWriter(System.out));

        cv.visit(V1_5, ACC_PUBLIC + ACC_ABSTRACT + ACC_INTERFACE,
                "pkg/Comparable", null, "java/lang/Object",
                new String[] { "pkg/Mesurable" });
        cv.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "LESS", "I",
                null, new Integer(-1)).visitEnd();
        cv.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "EQUAL", "I",
                null, new Integer(0)).visitEnd();
        cv.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "GREATER", "I",
                null, new Integer(1)).visitEnd();
        cv.visitMethod(ACC_PUBLIC + ACC_ABSTRACT, "compareTo",
                "(Ljava/lang/Object;)I", null, null).visitEnd();
        cv.visitEnd();

        //byte[] b = cw.toByteArray();
    }





    //分析一个类
    private static void test1() throws IOException {
        TestC testC = new TestC();

        ClassPrinter cp = new ClassPrinter();
        ClassReader cr = new ClassReader("java.lang.Runnable");
//        ClassReader cr = testC.getClass().getClassLoader()
//                .getResourceAsStream("java.lang.Runnable".replace(".","/")+"class");
        cr.accept(cp,0);
    }


    static public class ClassPrinter extends ClassVisitor implements Opcodes {

        public ClassPrinter() {
            super(ASM5);
        }

        @Override
        public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
            super.visit(version, access, name, signature, superName, interfaces);
            System.out.println(name + " extends " + superName + "{");
        }

        @Override
        public void visitSource(String source, String debug) {
            super.visitSource(source, debug);
        }

        @Override
        public void visitOuterClass(String owner, String name, String descriptor) {
            super.visitOuterClass(owner, name, descriptor);
        }

        @Override
        public void visitAttribute(Attribute attribute) {
            super.visitAttribute(attribute);
        }

        @Override
        public void visitInnerClass(String name, String outerName, String innerName, int access) {
            super.visitInnerClass(name, outerName, innerName, access);
        }

        @Override
        public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
            System.out.println(" " + descriptor + name);
            return null;
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
            System.out.println("Method " + name + descriptor);
            return null;
        }

        @Override
        public void visitEnd() {
            System.out.println("}");
        }
    }
}
