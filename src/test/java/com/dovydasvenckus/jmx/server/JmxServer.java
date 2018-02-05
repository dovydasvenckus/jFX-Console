package com.dovydasvenckus.jmx.server;

import javax.management.*;
import java.io.IOException;
import java.lang.management.ManagementFactory;

public class JmxServer {

    public static void main(String[] args) throws MalformedObjectNameException,
            InstanceAlreadyExistsException, MBeanRegistrationException, NotCompliantMBeanException, IOException {

        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();

        BatchProcessorMBean mbean = new BatchProcessor("MyServerMBean", 100);
        ObjectName name = new ObjectName("com.dovydasvenckus.jmx:type=BatchProcessorMBean");

        mBeanServer.registerMBean(mbean, name);

        waitForEnterPressed();
    }

    private static void waitForEnterPressed() throws IOException{
        System.out.println("Press enter to exit...");
        System.in.read();
    }
}
