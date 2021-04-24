package com.example.demo;

public class Info {
    private int deskNumber;
    private int customerId;

    public Info(int deskNumber, int customerId) {
        this.deskNumber = deskNumber;
        this.customerId = customerId;
    }

    public int getDeskNumber() {
        return deskNumber;
    }

    public void setDeskNumber(int deskNumber) {
        this.deskNumber = deskNumber;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
}
