package com.dovydasvenckus.jfxconsole.ui.component

import javax.management.MBeanOperationInfo

fun getReturnTypeName(operation: MBeanOperationInfo) : String{
    return operation.returnType.replace("java.lang.", "")
}

