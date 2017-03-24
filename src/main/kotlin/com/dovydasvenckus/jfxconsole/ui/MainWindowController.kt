package com.dovydasvenckus.jfxconsole.ui

import com.dovydasvenckus.jfxconsole.domain.MBeanNode
import com.dovydasvenckus.jfxconsole.jmx.JMXConnector
import com.dovydasvenckus.jfxconsole.ui.component.MethodButton
import com.dovydasvenckus.jfxconsole.ui.component.MethodInvocationAlert
import com.dovydasvenckus.jfxconsole.ui.helper.MBeanTreeMapper
import javafx.fxml.FXML
import javafx.geometry.Insets
import javafx.scene.control.TreeView
import javafx.scene.layout.BorderPane
import javafx.scene.layout.VBox


class MainWindowController {
    @FXML var mainPane: BorderPane? = null
    @FXML var mBeansTree: TreeView<MBeanNode>? = null
    @FXML var methodsVBox: VBox? = null

    fun initialize() {
        BorderPane.setMargin(methodsVBox, Insets(20.0, 0.0, 0.0, 20.0))
        mBeansTree!!.isShowRoot = false
    }

    fun initTree(jmxConnector: JMXConnector) {
        val mBeanNames = jmxConnector.getMBeansNames()

        val rootNode = MBeanTreeMapper().getMapRootNode(mBeanNames)
        mBeansTree!!.selectionModel.selectedItemProperty().addListener({
            observable, oldValue, newValue ->
            methodsVBox!!.children.clear()

            if (observable.value.isLeaf) {

                val objectName = newValue.value.objectName
                if (objectName != null) {
                    val mbeanInfo = jmxConnector.getMbeanInfo(objectName)
                    mbeanInfo.operations.filter { it.signature.isEmpty() && it.returnType == "java.lang.String" }.forEach { operation ->
                        val button = MethodButton(operation.returnType, operation.name,
                                Runnable {
                                    val result = jmxConnector.invoke(objectName, operation.name)
                                    MethodInvocationAlert(operation.name, result).showAndWait()
                                })
                        methodsVBox!!.children.add(button)
                    }
                }
            }
        })

        mBeansTree!!.root = rootNode
    }
}