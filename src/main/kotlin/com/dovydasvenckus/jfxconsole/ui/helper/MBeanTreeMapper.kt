package com.dovydasvenckus.jfxconsole.ui.helper

import com.dovydasvenckus.jfxconsole.domain.MBeanNode
import javafx.scene.control.TreeItem
import javax.management.ObjectName

class MBeanTreeMapper {

    fun getMapRootNode(objectNames: List<ObjectName>): TreeItem<MBeanNode> {
        val root = TreeItem<MBeanNode>(MBeanNode("root"))
        addNonLeafNodes(root, objectNames)
        addLeafNodes(root, objectNames)

        return root
    }

    private fun addNonLeafNodes(root: TreeItem<MBeanNode>, objectNames: List<ObjectName>) {

        objectNames.groupBy { it.domain }.forEach { domain, objectNames ->
            val domainNode = TreeItem(MBeanNode(domain))
            root.children.add(domainNode)

            objectNames
                    .filter { hasName(it) }
                    .groupBy { it.getKeyProperty("type") }
                    .forEach {
                        domainNode.children.add(TreeItem(MBeanNode(it.key)))
                    }
        }
    }


    private fun addLeafNodes(root: TreeItem<MBeanNode>, objectNames: List<ObjectName>) {
        objectNames.forEach { objectName ->
            val mBeanNode = MBeanNode(getName(objectName), objectName)

            val domainNode = root.children.find { it.value.textRepresentation == mBeanNode.getDomain() }
            if (hasName(objectName)) {
                domainNode?.children
                        ?.find { it.value.textRepresentation == mBeanNode.getType() }
                        ?.children
                        ?.add(TreeItem(mBeanNode))
            } else {
                domainNode?.children?.add(TreeItem(mBeanNode))
            }
        }
    }


    private fun getName(objectName: ObjectName): String {
        return if (hasName(objectName)) objectName.getKeyProperty("name") else objectName.getKeyProperty("type")
    }

    private fun hasName(objectName: ObjectName): Boolean {
        return objectName.getKeyProperty("name") != null
    }
}