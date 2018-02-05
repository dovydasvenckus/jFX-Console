package com.dovydasvenckus.jmx.server;

public interface BatchProcessorMBean {

    void execute();

    String getName();

    int batchSize();

    void setBatchSize(int batchSize);

}
