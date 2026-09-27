package model;

public class VehicleType {

    private int typeId;
    private String typeName;
    private double pricePerHour;
    private String status;

    public VehicleType() {
    }

    public VehicleType(int typeId, String typeName, double pricePerHour, String status) {
        this.typeId = typeId;
        this.typeName = typeName;
        this.pricePerHour = pricePerHour;
        this.status = status;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
