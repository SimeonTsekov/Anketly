package com.example.demo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.UUID;

public class Call {
    private UUID serviceId;
    private int customerId;
    @JsonIgnore
    private int deskNumber;
    @JsonIgnore
    private final int seq;

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

    public int getDeskNumber() {
        return deskNumber;
    }

    public void setDeskNumber(int deskNumber) {
        this.deskNumber = deskNumber;
    }

    public int getSeq() {
        return seq;
    }

    public Call(UUID serviceId, int customerId, int deskNumber, int seq) {
        this.serviceId = serviceId;
        this.customerId = customerId;
        this.deskNumber = deskNumber;
        this.seq = seq;
    }
}
