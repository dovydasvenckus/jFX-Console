package com.dovydasvenckus.jfxconsole.jmx

import javax.management.MBeanInfo
import javax.management.ObjectName
import javax.management.remote.JMXConnectorFactory
import javax.management.remote.JMXServiceURL

class JMXConnector(host: String, port: Int) {

    private val url = JMXServiceURL("service:jmx:rmi:///jndi/rmi://$host:$port/jmxrmi")
    private val jmxConnector = JMXConnectorFactory.connect(url)
    private val mbeanServerConnection = jmxConnector.mBeanServerConnection

    fun getMBeansNames(): List<ObjectName> {
        return mbeanServerConnection.domains.flatMap {
            mbeanServerConnection.queryNames(ObjectName("$it:*"), null)
        }
    }

    fun getMbeanInfo(name: ObjectName): MBeanInfo {
        return mbeanServerConnection.getMBeanInfo(name)
    }

    fun invoke(name: ObjectName, functionName: String): Any? {
        return mbeanServerConnection.invoke(name, functionName, emptyArray<Any>(), emptyArray<String>())
    }

    fun close() {
        jmxConnector.close()
    }

}