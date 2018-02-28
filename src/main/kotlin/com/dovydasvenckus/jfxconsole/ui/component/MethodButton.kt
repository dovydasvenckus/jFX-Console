package com.dovydasvenckus.jfxconsole.ui.component

import javafx.scene.control.Button
import javafx.scene.control.TextField
import javafx.scene.layout.HBox
import javafx.scene.text.Text
import javax.management.MBeanOperationInfo

class MethodButton(operation: MBeanOperationInfo, callback: Runnable) : HBox(20.0) {

    init {
        val button = Button(operation.name)
        button.setOnAction { callback.run() }

        children.add(Text(getReturnTypeName(operation)))
        children.add(button)
        children.add(Text("("))
        children.add(getMethodOperandsFieldBox(operation))
        children.add(Text(")"))
    }

    private fun getMethodOperandsFieldBox(operation: MBeanOperationInfo) : HBox{
        val operandsBox = HBox()

        operation.signature.forEach {
            operandsBox.children.add(Text(it.name))
            operandsBox.children.add(TextField(getReturnTypeName(it.type)))
        }

        return operandsBox
    }

}