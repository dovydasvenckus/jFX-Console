package com.dovydasvenckus.jfxconsole.ui.component

import javafx.scene.control.Alert
import javafx.scene.control.Alert.AlertType.INFORMATION
import javafx.scene.control.TextArea

class MethodInvocationAlert(method : String, result : String) : Alert(INFORMATION) {

    init {
        title = method
        headerText = "Result:"
        val textArea = TextArea(result)
        textArea.setEditable(false)
        textArea.setWrapText(true)

        dialogPane.content = textArea
    }
}