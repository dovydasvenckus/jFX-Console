package com.dovydasvenckus.jfxconsole.ui

import com.dovydasvenckus.jfxconsole.jmx.JMXConnector
import javafx.fxml.FXML
import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.TextArea
import javafx.scene.control.TreeItem
import javafx.scene.control.TreeView
import javafx.scene.layout.VBox
import javafx.stage.Modality
import javafx.stage.Stage


class MainWindowController {
    @FXML var mBeansTree: TreeView<MBeanTreeNode>? = null
    @FXML var methodsVBox: VBox? = null

    fun initialize() {
        methodsVBox!!.padding = Insets(10.0)
    }

    fun initTree(primaryStage: Stage, jmxConnector: JMXConnector) {
        val mBeanNames = jmxConnector.getMBeansNames()

        val rootNode = TreeItem(MBeanTreeNode("root", "root"))
        mBeansTree!!.selectionModel.selectedItemProperty().addListener({
            observable, oldValue, newValue ->
            if (observable.value.isLeaf) {
                methodsVBox!!.children.clear()

                val objectName = newValue.value.getObjectName()
                val mbeanInfo = jmxConnector.getMbeanInfo(newValue.value.getObjectName())
                mbeanInfo.operations.filter { it.signature.isEmpty() }.forEach { operation ->
                    val button = Button(operation.name)
                    button.setOnAction({
                        createPopup(jmxConnector.invoke(objectName, operation.name), primaryStage)
                    })
                    methodsVBox!!.children.add(button)
                }
            }
        })

        rootNode.setExpanded(true)
        mBeanNames.groupBy {
            it.domain
        }.forEach {
            val domainNode = TreeItem(MBeanTreeNode(it.key, it.key))
            it.value.groupBy { it.getKeyProperty("type") }.forEach {
                val typeNode = TreeItem(MBeanTreeNode(domainNode.value.domain, it.key, it.key))
                domainNode.children.add(typeNode)

                if (it.value[0].getKeyProperty("name") != null) {
                    it.value.forEach {
                        val leafNode = TreeItem(
                                MBeanTreeNode(
                                        typeNode.value.domain,
                                        it.getKeyProperty("name"),
                                        it.getKeyProperty("type"),
                                        it.getKeyProperty("name")))
                        typeNode.children.add(leafNode)
                    }
                }

            }

            rootNode.children.add(domainNode)
        }

        mBeansTree!!.root = rootNode
    }

    private fun createPopup(mesage: String, parent: Stage) {
        val dialog = Stage()
        dialog.minHeight = 50.0
        dialog.minWidth = 200.0
        dialog.maxWidth = 800.0
        dialog.initModality(Modality.APPLICATION_MODAL)
        val dialogVbox = VBox(20.0)
        dialogVbox.padding = Insets(5.0)
        val textBox = TextArea(mesage)
        textBox.setEditable(false)
        dialogVbox.children.add(textBox)
        val dialogScene = Scene(dialogVbox)
        dialog.scene = dialogScene
        dialog.initOwner(parent)
        dialog.show()
    }
}