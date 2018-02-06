# jFX-Console
[![Run Status](https://api.shippable.com/projects/59fe1aafe07b7707001c66df/badge?branch=master)](https://app.shippable.com/github/dovydasvenckus/jFX-Console) 

## Starting fake JMX server
In this project fake mBean server is provided.
You can start it by launching `com.dovydasvenckus.jmx.server.JmxServer` class
and setting up correct VM options.

### Basic server without authorization VM options.
    -Dcom.sun.management.jmxremote
    -Dcom.sun.management.jmxremote.port=1234
    -Dcom.sun.management.jmxremote.authenticate=false
    -Dcom.sun.management.jmxremote.ssl=false