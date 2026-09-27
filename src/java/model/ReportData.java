package model;

import java.util.List;

public class ReportData {

    private int currentParkingCount;
    private int todayVehicleInCount;
    private int todayVehicleOutCount;
    private double todayRevenue;
    private double monthRevenue;
    private List<ParkingSessionView> recentSessions;

    public int getCurrentParkingCount() {
        return currentParkingCount;
    }

    public void setCurrentParkingCount(int currentParkingCount) {
        this.currentParkingCount = currentParkingCount;
    }

    public int getTodayVehicleInCount() {
        return todayVehicleInCount;
    }

    public void setTodayVehicleInCount(int todayVehicleInCount) {
        this.todayVehicleInCount = todayVehicleInCount;
    }

    public int getTodayVehicleOutCount() {
        return todayVehicleOutCount;
    }

    public void setTodayVehicleOutCount(int todayVehicleOutCount) {
        this.todayVehicleOutCount = todayVehicleOutCount;
    }

    public double getTodayRevenue() {
        return todayRevenue;
    }

    public void setTodayRevenue(double todayRevenue) {
        this.todayRevenue = todayRevenue;
    }

    public double getMonthRevenue() {
        return monthRevenue;
    }

    public void setMonthRevenue(double monthRevenue) {
        this.monthRevenue = monthRevenue;
    }

    public List<ParkingSessionView> getRecentSessions() {
        return recentSessions;
    }

    public void setRecentSessions(List<ParkingSessionView> recentSessions) {
        this.recentSessions = recentSessions;
    }
}
