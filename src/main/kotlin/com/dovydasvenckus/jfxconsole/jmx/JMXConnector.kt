package com.dovydasvenckus.jfxconsole.jmx

import javax.management.MBeanInfo
import javax.management.ObjectName
import javax.management.remote.JMXConnectorFactory
import javax.management.remote.JMXServiceURL

class JMXConnector(host: String, port: Int) {

    val url = JMXServiceURL("service:jmx:rmi:///jndi/rmi://$host:$port/jmxrmi")

    fun getMBeansNames() : List<ObjectName> {
        val jmxConnector = JMXConnectorFactory.connect(url)
        val mbeanServerConnection = jmxConnector.mBeanServerConnection
        
        val mbeanNames = mbeanServerConnection.domains.flatMap {
            mbeanServerConnection.queryNames(ObjectName("$it:*"), null)
        }

        jmxConnector.close()
        return mbeanNames
    }

    fun getMbeanInfo(name : ObjectName) : MBeanInfo {
        val jmxConnector = JMXConnectorFactory.connect(url)
        val mbeanServerConnection = jmxConnector.mBeanServerConnection

        val mbeanInfo = mbeanServerConnection.getMBeanInfo(name)

        jmxConnector.close()

        return mbeanInfo
    }

    fun invoke(name: ObjectName, functionName: String) : String  {
        val jmxConnector = JMXConnectorFactory.connect(url)
        val mbeanServerConnection = jmxConnector.mBeanServerConnection

        val invocationResult = mbeanServerConnection.invoke(name, functionName, emptyArray<Any>(), emptyArray<String>())

        jmxConnector.close()

        return invocationResult.toString()
    }

}