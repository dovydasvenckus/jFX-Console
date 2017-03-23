package com.dovydasvenckus.jfxconsole.jmx

import javax.management.ObjectName
import javax.management.remote.JMXConnectorFactory
import javax.management.remote.JMXServiceURL

class JMXConnector(host: String, port: Int) {

    val url = JMXServiceURL("service:jmx:rmi:///jndi/rmi://$host:$port/jmxrmi")

    fun getMbeansNames() : List<ObjectName> {
        val jmxConnector = JMXConnectorFactory.connect(url)
        val mbeanServerConnection = jmxConnector.mBeanServerConnection
        
        val mbeanNames = mbeanServerConnection.domains.flatMap {
            mbeanServerConnection.queryNames(ObjectName("$it:*"), null)
        }

        jmxConnector.close()
        return mbeanNames
    }
}