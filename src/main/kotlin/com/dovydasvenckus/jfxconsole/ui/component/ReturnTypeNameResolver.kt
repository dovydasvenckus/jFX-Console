package com.dovydasvenckus.jfxconsole.ui.component

import javax.management.MBeanOperationInfo

fun getReturnTypeName(operation: MBeanOperationInfo): String {
    return getReturnTypeName(operation.returnType)
}

fun getReturnTypeName(typeName : String): String {
    return typeName.replace("java.lang.", "")
}

