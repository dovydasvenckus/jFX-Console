package com.dovydasvenckus.jfxconsole

import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.layout.StackPane
import javafx.scene.text.Text
import javafx.stage.Stage

class JFXConsole : Application() {
    override fun start(stage: Stage) {
        val btn = Text()
        btn.text = "Empty panel"

        val root = StackPane()
        root.children.add(btn)

        val scene = Scene(root, 300.0, 250.0)

        stage.title = "jFX Console!"
        stage.scene = scene
        stage.show()
    }

    fun init(args: Array<String>) {
        launch(*args)
    }
}