package model;

import java.sql.Timestamp;

public class Ticket {
    private int ticketId;
    private String ticketCode;
    private int vehicleId;
    private String ticketType;
    private Timestamp issueTime;
    private Timestamp expireTime;
    private String status;

    // thêm để hiển thị
    private String licensePlate;
    private String vehicleTypeName;
    private Timestamp entryTime;

    public Ticket() {
    }

    public Ticket(int ticketId, String ticketCode, int vehicleId, String ticketType,
                  Timestamp issueTime, Timestamp expireTime, String status) {
        this.ticketId = ticketId;
        this.ticketCode = ticketCode;
        this.vehicleId = vehicleId;
        this.ticketType = ticketType;
        this.issueTime = issueTime;
        this.expireTime = expireTime;
        this.status = status;
    }

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public String getTicketCode() {
        return ticketCode;
    }

    public void setTicketCode(String ticketCode) {
        this.ticketCode = ticketCode;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getTicketType() {
        return ticketType;
    }

    public void setTicketType(String ticketType) {
        this.ticketType = ticketType;
    }

    public Timestamp getIssueTime() {
        return issueTime;
    }

    public void setIssueTime(Timestamp issueTime) {
        this.issueTime = issueTime;
    }

    public Timestamp getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Timestamp expireTime) {
        this.expireTime = expireTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getVehicleTypeName() {
        return vehicleTypeName;
    }

    public void setVehicleTypeName(String vehicleTypeName) {
        this.vehicleTypeName = vehicleTypeName;
    }

    public Timestamp getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(Timestamp entryTime) {
        this.entryTime = entryTime;
    }
}