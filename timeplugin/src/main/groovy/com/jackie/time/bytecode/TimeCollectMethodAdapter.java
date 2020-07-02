package com.jackie.time.bytecode;

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
 * 可以用于在一个方法的开头以及任意RETRUN或ATHROW指令之前插入代码
 */
public class TimeCollectMethodAdapter extends AdviceAdapter implements Opcodes {

    private String methodName;
    private MethodVisitor methodVisitor;
    private Label l1;
    private Label l2;

    /**
     * Constructs a new {@link AdviceAdapter}.
     * <p>
     * the ASM API version implemented by this visitor. Must be one of {@link
     * Opcodes#ASM4}, {@link Opcodes#ASM5}, {@link Opcodes#ASM6} or {@link Opcodes#ASM7}.
     *
     * @param methodVisitor the method visitor to which this adapter delegates calls.
     * @param access        the method's access flags (see {@link Opcodes}).
     * @param name          the method's name.
     * @param descriptor    the method's descriptor (see {@link Type Type}).
     */
    protected TimeCollectMethodAdapter(MethodVisitor methodVisitor, int access, String name, String descriptor) {
        super(Opcodes.ASM6, methodVisitor, access, name, descriptor);
//        this.methodVisitor = methodVisitor;
        this.methodName = name;
    }

    private int timeLocalIndex = 0;

    @Override
    protected void onMethodEnter() {
        super.onMethodEnter();
        System.out.println("=========jackiee" + "======" + methodName);
        if ((methodName.contains("onResume") && methodName.contains("MainActivity"))
                || (methodName.contains("onCreate") && methodName.contains("MainActivity"))) {
            mv.visitFieldInsn(GETSTATIC, "com/jackie/asmdemo/MainActivity", "timer", "J");
            mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false);
            mv.visitInsn(LSUB);
            mv.visitFieldInsn(PUTSTATIC, "com/jackie/asmdemo/MainActivity", "timer", "J");

//            Label l1 = new Label();
//            mv.visitFieldInsn(GETSTATIC, "com/jackie/asmdemo/MainActivity", "timer", "J");
//            mv.visitLabel(l1);
        }


    }

    @Override
    protected void onMethodExit(int opcode) {
        super.onMethodExit(opcode);
        if ((methodName.contains("onResume") && methodName.contains("MainActivity"))
                || (methodName.contains("onCreate") && methodName.contains("MainActivity"))) {
            mv.visitFieldInsn(GETSTATIC, "com/jackie/asmdemo/MainActivity", "timer", "J");
            mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false);
            mv.visitInsn(LADD);
            mv.visitFieldInsn(PUTSTATIC, "com/jackie/asmdemo/MainActivity", "timer", "J");

            mv.visitTypeInsn(NEW, "java/lang/StringBuilder");
            mv.visitInsn(DUP);
            mv.visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false);
            mv.visitVarInsn(ASTORE, 3);
            mv.visitVarInsn(ALOAD, 3);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Object", "getClass", "()Ljava/lang/Class;", false);
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Class", "getSimpleName", "()Ljava/lang/String;", false);
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
            mv.visitInsn(POP);
            mv.visitVarInsn(ALOAD, 3);
            mv.visitLdcInsn("page_load");
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
            mv.visitInsn(POP);
            mv.visitVarInsn(ALOAD, 3);
            mv.visitTypeInsn(NEW, "java/lang/StringBuilder");
            mv.visitInsn(DUP);
            mv.visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false);
            mv.visitLdcInsn("time:    ");
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
            mv.visitFieldInsn(GETSTATIC, "com/jackie/asmdemo/MainActivity", "timer", "J");
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(J)Ljava/lang/StringBuilder;", false);
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
            mv.visitInsn(POP);
            mv.visitLdcInsn("jackie shimei");
            mv.visitVarInsn(ALOAD, 3);
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
            mv.visitMethodInsn(INVOKESTATIC, "android/util/Log", "d", "(Ljava/lang/String;Ljava/lang/String;)I", false);
            mv.visitInsn(POP);





//            MyLogger.collectMethodTime(timer,this.getClass().getSimpleName(),TestA.PAGE_FIRST_LOADING_TYPE,"",0);

//            mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
//            mv.visitFieldInsn(GETSTATIC, "com/jackie/asmdemo/MainActivity", "timer", "J");
//            mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(J)V", false);

//            mv.visitFieldInsn(GETSTATIC, "com/jackie/asmdemo/MainActivity", "timer", "J");
//            mv.visitVarInsn(ALOAD, 0);
//            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Object", "getClass", "()Ljava/lang/Class;", false);
//            mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Class", "getSimpleName", "()Ljava/lang/String;", false);
//            mv.visitLdcInsn("page_load");
//            mv.visitLdcInsn("");
//            mv.visitInsn(ICONST_0);
//            mv.visitMethodInsn(INVOKESTATIC, "com/jackie/asmdemo/MyLogger", "collectMethodTime", "(JLjava/lang/String;Ljava/lang/String;Ljava/lang/String;I)V", false);



        }

    }

    @Override
    public void visitMaxs(int maxStack, int maxLocals) {
        super.visitMaxs(maxStack + 4, maxLocals);
    }
}
