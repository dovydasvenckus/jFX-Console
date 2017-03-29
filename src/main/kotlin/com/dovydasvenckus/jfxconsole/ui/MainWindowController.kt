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
import javax.management.MBeanOperationInfo
import javax.management.ObjectName


class MainWindowController {
    @FXML var mainPane: BorderPane? = null
    @FXML var mBeansTree: TreeView<MBeanNode>? = null
    @FXML var methodsVBox: VBox? = null

    var jmxConnector: JMXConnector? = null

    fun initialize() {
        BorderPane.setMargin(methodsVBox, Insets(20.0, 0.0, 0.0, 20.0))
        mBeansTree!!.isShowRoot = false
    }

    fun initTree(jmxConnector: JMXConnector) {
        this.jmxConnector = jmxConnector
        val mBeanNames = jmxConnector.getMBeansNames()

        val rootNode = MBeanTreeMapper().getMapRootNode(mBeanNames)
        initOnClickCallBacks()

        mBeansTree!!.root = rootNode
    }

    private fun initOnClickCallBacks() {
        mBeansTree!!.selectionModel.selectedItemProperty().addListener({ observable, oldValue, newValue ->
            methodsVBox!!.children.clear()
            val objectName = newValue.value.objectName

            if (observable.value.isLeaf && objectName != null) {
                setupOperations(objectName)
            }

        })
    }

    private fun setupOperations(objectName: ObjectName) {
        val mbeanInfo = jmxConnector!!.getMbeanInfo(objectName)
        mbeanInfo.operations.filter { it.signature.isEmpty() }.forEach { operation ->
            methodsVBox!!.children.add(createOperationButton(objectName, operation))
        }
    }

    private fun createOperationButton(objectName: ObjectName, operation: MBeanOperationInfo): MethodButton {
        return MethodButton(operation,
                Runnable {
                    val result = jmxConnector!!.invoke(objectName, operation.name)
                    MethodInvocationAlert(operation.name, result, operation.returnType == "void").showAndWait()
                })
    }
}