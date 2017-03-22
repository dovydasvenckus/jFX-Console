package com.dovydasvenckus.jfxconsole

import javax.management.remote.JMXConnectorFactory
import javax.management.remote.JMXServiceURL

val HOST = "localhost"
val PORT = "1234"

fun main(args: Array<String>) {
    val url = JMXServiceURL("service:jmx:rmi:///jndi/rmi://$HOST:$PORT/jmxrmi")

    val jmxConnector = JMXConnectorFactory.connect(url)
    val mbeanServerConnection = jmxConnector.mBeanServerConnection

    println(mbeanServerConnection.queryMBeans(null, null))

    jmxConnector.close()
}