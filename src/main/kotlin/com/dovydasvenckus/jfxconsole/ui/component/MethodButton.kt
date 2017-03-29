package com.dovydasvenckus.jfxconsole.ui.component

import javafx.scene.control.Button
import javafx.scene.layout.HBox
import javafx.scene.text.Text
import javax.management.MBeanOperationInfo

class MethodButton(operation: MBeanOperationInfo, callback: Runnable) : HBox(20.0) {

    init {
        val button = Button(operation.name)
        button.setOnAction { callback.run() }

        this.children.add(Text(getReturnTypeName(operation)))
        this.children.add(button)
    }

    private fun getReturnTypeName(operation: MBeanOperationInfo) : String{
        return operation.returnType.replace("java.lang.", "")
    }

}