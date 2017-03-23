package com.dovydasvenckus.jfxconsole.ui

import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.TreeItem
import javafx.scene.control.TreeView
import javafx.scene.text.Text
import java.net.URL
import java.util.*
import javax.management.ObjectName

class MainWindowController : Initializable {
    @FXML var textField: Text? = null
    @FXML var mBeansTree: TreeView<String>? = null

    override fun initialize(p0: URL?, p1: ResourceBundle?) {
        textField!!.text = "Some text"
    }

    fun initTree(mBeanNames : List<ObjectName>) {
        val root = TreeItem("Root")
        root.setExpanded(true)

        mBeanNames.groupBy {
            it.domain
        }.forEach {
            val domainNode = TreeItem(it.key)
            it.value.groupBy { it.getKeyProperty("type") }.forEach {
                val typeNode = TreeItem(it.key)
                domainNode.children.add(typeNode)

                if (it.value[0].getKeyProperty("name") != null) {
                    it.value.forEach {
                        domainNode.children.add(TreeItem(it.getKeyProperty("name")))
                    }
                }

            }

            root.children.add(domainNode)
        }

        mBeansTree!!.root = root
    }
}