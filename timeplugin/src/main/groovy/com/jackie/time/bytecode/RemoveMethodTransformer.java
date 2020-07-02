package com.jackie.time.bytecode;

import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

import java.util.Iterator;

/**
 * Description:
 *
 * @author feifei5292190@gmail.com
 * @date 2020/6/29
 */
public class RemoveMethodTransformer extends ClassTransfromer {

    private String methodName;
    private String methodDesc;
    public RemoveMethodTransformer(ClassTransfromer ct,String methodName,String methodDesc) {
        super(ct);
        this.methodName = methodName;
        this.methodDesc = methodDesc;
    }

    @Override
    public void transform(ClassNode cn) {
        Iterator<MethodNode> i = cn.methods.iterator();
        while (i.hasNext()){
            MethodNode mn = i.next();
            if (methodName.equals(mn.name) && methodDesc.equals(mn.desc)){
                i.remove();
            }
        }

        super.transform(cn);
    }
}
class ClassTransfromer{
    protected ClassTransfromer ct;
    public ClassTransfromer(ClassTransfromer classTransfromer){
        this.ct = classTransfromer;
    }

    public void transform(ClassNode cn){
        if (ct != null){
            ct.transform(cn);
        }
    }
}