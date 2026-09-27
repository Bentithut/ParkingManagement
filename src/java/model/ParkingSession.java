package model;

import java.sql.Timestamp;

public class ParkingSession {
    private int sessionId;
    private int vehicleId;
    private int ticketId;
    private Integer lotId;
    private Timestamp entryTime;
    private Timestamp exitTime;
    private Double totalAmount;
    private String status;

    public ParkingSession() {
    }

    public ParkingSession(int sessionId, int vehicleId, int ticketId, Integer lotId, Timestamp entryTime, Timestamp exitTime, Double totalAmount, String status) {
        this.sessionId = sessionId;
        this.vehicleId = vehicleId;
        this.ticketId = ticketId;
        this.lotId = lotId;
        this.entryTime = entryTime;
        this.exitTime = exitTime;
        this.totalAmount = totalAmount;
        this.status = status;
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public Integer getLotId() {
        return lotId;
    }

    public void setLotId(Integer lotId) {
        this.lotId = lotId;
    }

    public Timestamp getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(Timestamp entryTime) {
        this.entryTime = entryTime;
    }

    public Timestamp getExitTime() {
        return exitTime;
    }

    public void setExitTime(Timestamp exitTime) {
        this.exitTime = exitTime;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}