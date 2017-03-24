package com.dovydasvenckus.jfxconsole.domain

import javax.management.ObjectName

class MBeanNode(val textRepresentation: String, val objectName : ObjectName? = null) {

    fun getDomain() : String? = objectName?.domain

    fun getName() : String? = objectName?.getKeyProperty("name")

    fun getType() : String? = objectName?.getKeyProperty("type")

    override fun toString(): String {
        return textRepresentation
    }
}