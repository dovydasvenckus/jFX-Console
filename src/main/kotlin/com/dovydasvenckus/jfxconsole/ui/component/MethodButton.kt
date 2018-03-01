package com.dovydasvenckus.jfxconsole.ui.component

import javafx.scene.control.Button
import javafx.scene.control.TextField
import javafx.scene.layout.HBox
import javafx.scene.text.Text
import java.util.function.Function
import javax.management.MBeanOperationInfo

class MethodButton(operation: MBeanOperationInfo, callback: Function<List<String>, Any>) : HBox(20.0) {

    private val methodOperandsBox = HBox()

    init {
        val button = Button(operation.name)
        button.setOnAction { callback.apply(getOperandValues()) }

        children.add(Text(getReturnTypeName(operation)))
        children.add(button)
        children.add(Text("("))
        children.add(getMethodOperandsFieldBox(operation))
        children.add(Text(")"))
    }

    private fun getMethodOperandsFieldBox(operation: MBeanOperationInfo): HBox {
        operation.signature.forEach {
            methodOperandsBox.children.add(Text(it.name))
            methodOperandsBox.children.add(TextField(getReturnTypeName(it.type)))
        }

        return methodOperandsBox
    }

    private fun getOperandValues(): List<String> {
        return methodOperandsBox.children
                .filterIsInstance<TextField>()
                .map { node -> node.text }
    }

}