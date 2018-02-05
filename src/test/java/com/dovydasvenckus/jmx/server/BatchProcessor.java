package com.dovydasvenckus.jmx.server;

public class BatchProcessor implements BatchProcessorMBean {

    private String name;

    private int batchSize;

    BatchProcessor(String name, int batchSize) {
        this.name = name;
        this.batchSize = batchSize;
    }

    @Override
    public void execute() {
        return;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int batchSize() {
        return batchSize;
    }

    @Override
    public void setBatchSize(int batchSize) {
        this.batchSize = batchSize;
    }
}
