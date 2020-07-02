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
 *
 */
public class CustomThreadAdapter extends AdviceAdapter implements Opcodes {

    private String methodName;
    private String className;
    private MethodVisitor methodVisitor;
    private Label l1;
    private Label l2;
    private boolean findNew = false;

//    invokeVirtual 是根据 new 出来的对象来调用，所以我们只需要替换 new 对象的过程就可以了。
//    这里需要处理两个指令：一个 new、一个 InvokeSpecial。在大多数情况下这两条指令是成对出现的，
//    但是在一些特殊情况下，会遇到直接从其他位置传递过来一个已经存在的对象，并强制调用构造方法的情况。

    protected CustomThreadAdapter(MethodVisitor methodVisitor, int access
            , String name, String descriptor,String className) {
        super(Opcodes.ASM6, methodVisitor, access, name, descriptor);
//        this.methodVisitor = methodVisitor;
        this.className = className;
        this.methodName = name;
    }



    @Override
    public void visitTypeInsn(int opcode, String type) {
        if (opcode == Opcodes.NEW && "java/lang/Thread".equals(type)){
            findNew = true;
            mv.visitTypeInsn(Opcodes.NEW,"com/jackie/asmdemo/CustomThread");//替换new 指令的类名
            return;
        }
        super.visitTypeInsn(opcode, type);
    }

    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
        //需要排查CustomThread自己
        if ("java/lang/Thread".equals(owner)
                && !className.equals("com/jackie/asmdemo/CustomThread")
                && opcode == Opcodes.INVOKESPECIAL
                && findNew){
            mv.visitMethodInsn(opcode, owner, name, descriptor,false);
            return;
        }

        super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
    }
}
