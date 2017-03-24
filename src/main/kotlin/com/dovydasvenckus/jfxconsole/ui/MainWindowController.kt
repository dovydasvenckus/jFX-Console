package com.dovydasvenckus.jfxconsole.ui

import com.dovydasvenckus.jfxconsole.domain.MBeanNode
import com.dovydasvenckus.jfxconsole.jmx.JMXConnector
import com.dovydasvenckus.jfxconsole.ui.helper.MBeanTreeMapper
import javafx.fxml.FXML
import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.TextArea
import javafx.scene.control.TreeView
import javafx.scene.layout.BorderPane
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.scene.text.Text
import javafx.stage.Modality
import javafx.stage.Stage


class MainWindowController {
    @FXML var mainPane: BorderPane? = null
    @FXML var mBeansTree: TreeView<MBeanNode>? = null
    @FXML var methodsVBox: VBox? = null

    fun initialize() {
        BorderPane.setMargin(methodsVBox, Insets(20.0, 0.0, 0.0, 20.0))
        mBeansTree!!.isShowRoot = false
    }

    fun initTree(primaryStage: Stage, jmxConnector: JMXConnector) {
        val mBeanNames = jmxConnector.getMBeansNames()

        val rootNode = MBeanTreeMapper().getMapRootNode(mBeanNames)
        mBeansTree!!.selectionModel.selectedItemProperty().addListener({
            observable, oldValue, newValue ->
            methodsVBox!!.children.clear()

            if (observable.value.isLeaf) {

                val objectName = newValue.value.objectName
                if (objectName != null) {
                    val mbeanInfo = jmxConnector.getMbeanInfo(objectName)
                    mbeanInfo.operations.filter { it.signature.isEmpty() }.forEach { operation ->
                        val textButtonCombo = HBox(20.0)
                        val text = Text(operation.returnType)
                        val button = Button(operation.name)
                        textButtonCombo.children.add(text)
                        textButtonCombo.children.add(button)
                        button.setOnAction({
                            createPopup(jmxConnector.invoke(objectName, operation.name), primaryStage)
                        })
                        methodsVBox!!.children.add(textButtonCombo)
                    }
                }
            }
        })

        mBeansTree!!.root = rootNode
    }

    private fun createPopup(mesage: String, parent: Stage) {
        val dialog = Stage()
        dialog.minHeight = 50.0
        dialog.minWidth = 200.0
        dialog.maxWidth = 800.0
        dialog.initModality(Modality.APPLICATION_MODAL)
        val dialogVbox = VBox(20.0)
        val textBox = TextArea(mesage)
        textBox.setEditable(false)
        dialogVbox.children.add(textBox)
        val dialogScene = Scene(dialogVbox)
        dialog.scene = dialogScene
        dialog.initOwner(parent)
        dialog.show()
    }
}