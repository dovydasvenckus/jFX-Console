package com.dovydasvenckus.jfxconsole.ui

import com.dovydasvenckus.jfxconsole.jmx.JMXConnector
import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage


class JFXConsole : Application() {
    var mainController: MainWindowController? = null
    var connector: JMXConnector = JMXConnector("localhost", 1234)


    override fun start(stage: Stage) {
        val loader = FXMLLoader(javaClass.classLoader.getResource("templates/main.fxml"))
        val root = loader.load<Parent>()

        mainController = loader.getController()
        val scene = Scene(root, 800.0, 800.0)

        mainController!!.initTree(stage, connector)

        stage.title = "jFX Console!"
        stage.scene = scene
        stage.show()
    }

    fun init(args: Array<String>) {
        launch(*args)
    }
}