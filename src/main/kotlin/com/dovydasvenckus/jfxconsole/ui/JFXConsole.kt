package com.dovydasvenckus.jfxconsole.ui

import com.dovydasvenckus.jfxconsole.jmx.JMXConnector
import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage


class JFXConsole : Application() {
    override fun start(stage: Stage) {
        val root = FXMLLoader.load<Parent>(javaClass.classLoader.getResource("templates/main.fxml"))

        val scene = Scene(root, 300.0, 250.0)

        stage.title = "jFX Console!"
        stage.scene = scene
        stage.show()
    }

    fun init(args: Array<String>) {
        val connector = JMXConnector("localhost", 1234)
        println(connector.getMbeansNames())
        launch(*args)
    }
}