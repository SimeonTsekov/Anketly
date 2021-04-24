package com.example.demo;

import java.util.UUID;

public class Enrollment {
    private UUID serviceId;
    private int customerId;
    private int queueCount;

    public UUID getServiceId() {
        return serviceId;
    }

    public void setServiceId(UUID serviceId) {
        this.serviceId = serviceId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getQueueCount() {
        return queueCount;
    }

    public void setQueueCount(int queueCount) {
        this.queueCount = queueCount;
    }

    public Enrollment(UUID serviceId, int customerId, int queueCount) {
        this.serviceId = serviceId;
        this.customerId = customerId;
        this.queueCount = queueCount;
    }
}
