package com.dovydasvenckus.jfxconsole.ui.component

import javafx.scene.control.Alert
import javafx.scene.control.Alert.AlertType.INFORMATION
import javafx.scene.control.TextArea

class MethodInvocationAlert(method : String, result : Any?, void: Boolean = false) : Alert(INFORMATION) {

    init {
        title = method

        if (!void) {
            headerText = "Result:"
            val textArea = TextArea(result?.toString() ?: "null")
            textArea.isEditable = false
            textArea.isWrapText = true

            dialogPane.content = textArea
        }
        else {
            headerText = "Method successfully invoked"
        }
    }
}