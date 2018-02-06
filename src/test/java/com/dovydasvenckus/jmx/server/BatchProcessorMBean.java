package com.dovydasvenckus.jmx.server;

public interface BatchProcessorMBean {

    void execute();

    int execute(String jobName);

    String getName();

    int batchSize();

    void setBatchSize(int batchSize);

}
