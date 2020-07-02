package com.jackie.time.bytecode;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.AdviceAdapter;
import org.objectweb.asm.commons.LocalVariablesSorter;

/**
 * Description:
 *
 * @author feifei5292190@gmail.com
 * @date 2020/6/17
 */
public class TimingMethodAdapter extends AdviceAdapter implements Opcodes {

    private String methodName;
    private MethodVisitor methodVisitor;
    private int timeLocalIndex = 0;
    private Label l1;
    private Label l2;

    /**
     * Constructs a new {@link AdviceAdapter}.
     *
     *          the ASM API version implemented by this visitor. Must be one of {@link
     *                      Opcodes#ASM4}, {@link Opcodes#ASM5}, {@link Opcodes#ASM6} or {@link Opcodes#ASM7}.
     * @param methodVisitor the method visitor to which this adapter delegates calls.
     * @param access        the method's access flags (see {@link Opcodes}).
     * @param name          the method's name.
     * @param descriptor    the method's descriptor (see {@link Type Type}).
     */
    protected TimingMethodAdapter(MethodVisitor methodVisitor, int access, String name, String descriptor) {
        super(Opcodes.ASM6, methodVisitor, access, name, descriptor);
//        this.methodVisitor = methodVisitor;
        this.methodName = name;
    }

    @Override
    protected void onMethodEnter() {
        super.onMethodEnter();
        System.out.println("=========jackiee" + "======" + methodName);
        if (!methodName.contains("test") || !methodName.contains("MainActivity")) {
            return;
        }
        System.out.println("=========jackiee" + "========go 0 todo=========" + methodName);
        Label l0 = new Label();
        l1 = new Label();
        l2 = new Label();
        mv.visitTryCatchBlock(l0, l1, l2, "java/lang/Exception");
        mv.visitLabel(l0);

//        添加打印
//        mv.visitFieldInsn(GETSTATIC,"java/lang/System","out","Ljava/io/PrintStream;");
//        mv.visitLdcInsn("enter===1====="+methodName);
//        mv.visitMethodInsn(INVOKEVIRTUAL,"java/io/PrintStream","println","(Ljava/lang/String;)V",false);

    }

    @Override
    protected void onMethodExit(int opcode) {
        super.onMethodExit(opcode);
        if (!methodName.contains("test") || !methodName.contains("MainActivity")) {
            return;
        }
        mv.visitLabel(l1);
        Label l3 = new Label();
        mv.visitJumpInsn(GOTO,l3);
        mv.visitLabel(l2);
        mv.visitVarInsn(ASTORE,2);
        mv.visitVarInsn(ALOAD,2);
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Exception", "printStackTrace", "()V", false);
        mv.visitLabel(l3);

    }
}
