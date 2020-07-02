package com.jackie.trycatch


class TryCatchExtension {

    public static final String PATHNAME = "pathName"
    public static final String METHOD_NAME = "methodName"
    public static final String EXCEPTION_NAME = "exceptionName"
    public static final boolean RETURNVALUE = "returnValue"

    String pathName
    String methodName
    String exceptionName
    long returnValue

    TryCatchExtension(){
        pathName = null
        methodName = null
        exceptionName = null
        returnValue = 0
    }

    @Override
    public String toString() {
        return "TryCatchExtension.groovy{" +
                "pathName=" + pathName +
                "methodName=" + methodName +
                "exceptionName=" + exceptionName +
                ", returnValue='" + returnValue + '\'' +
                '}'
    }

}