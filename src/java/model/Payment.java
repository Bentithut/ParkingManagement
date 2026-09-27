package model;

import java.sql.Timestamp;

public class Payment {
    private int paymentId;
    private int sessionId;
    private double amount;
    private Timestamp paymentTime;
    private String method;

    public Payment() {
    }

    public Payment(int paymentId, int sessionId, double amount, Timestamp paymentTime, String method) {
        this.paymentId = paymentId;
        this.sessionId = sessionId;
        this.amount = amount;
        this.paymentTime = paymentTime;
        this.method = method;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Timestamp getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(Timestamp paymentTime) {
        this.paymentTime = paymentTime;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}