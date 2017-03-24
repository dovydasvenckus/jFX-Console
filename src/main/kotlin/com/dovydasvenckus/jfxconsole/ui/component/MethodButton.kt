package com.dovydasvenckus.jfxconsole.ui.component

import javafx.scene.control.Button
import javafx.scene.layout.HBox
import javafx.scene.text.Text

class MethodButton(returnType: String, methodName: String, callback: Runnable) : HBox(20.0) {

    init {
        val button = Button(methodName)
        button.setOnAction { callback.run() }

        this.children.add(Text(returnType))
        this.children.add(button)
    }

}