package com.dovydasvenckus.jfxconsole.ui

import javax.management.ObjectName

class MBeanTreeNode(val domain: String,
                    val value: String,
                    val type: String = "",
                    val name: String = "") {

    override fun toString(): String {
        return value
    }

    fun getObjectName(): ObjectName {
        return ObjectName(getFullIndentifier())
    }

    private fun getNameKey(): String {
        return if (name.isNotEmpty()) "name=$name" else ""
    }

    private fun getTypeKey(): String {
        return if (type.isNotEmpty()) "type=$type" else ""
    }

    private fun getFullIndentifier(): String {
        return if (name.isNotEmpty()) getIdentifierWithTypeAndName() else getIdentifierWithType()

    }

    private fun getIdentifierWithTypeAndName(): String {
        return "$domain:${getTypeKey()},${getNameKey()}"
    }

    private fun getIdentifierWithType() : String {
        return "$domain:${getTypeKey()}"
    }
}