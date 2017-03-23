package com.dovydasvenckus.jfxconsole.ui

import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.text.Text
import java.net.URL
import java.util.*

class MainWindowController : Initializable {
    @FXML var textField: Text? = null

    override fun initialize(p0: URL?, p1: ResourceBundle?) {
        textField!!.text = "Some text"
    }
}