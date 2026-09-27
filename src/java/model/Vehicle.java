package model;

public class Vehicle {

    private int vehicleId;
    private String licensePlate;
    private Integer customerId;
    private int typeId;
    private String status;
    private String typeName;
    private double pricePerHour;

    public Vehicle() {
    }

    public Vehicle(int vehicleId, String licensePlate, Integer customerId, int typeId, String status) {
        this.vehicleId = vehicleId;
        this.licensePlate = licensePlate;
        this.customerId = customerId;
        this.typeId = typeId;
        this.status = status;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public double getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(double pricePerHour) {
        this.pricePerHour = pricePerHour;
    }
}
