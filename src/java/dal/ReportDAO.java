package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.ParkingSessionView;
import model.ReportData;
import utils.DBConnection;

public class ReportDAO {

    public ReportData getDashboardReport() {
        ReportData data = new ReportData();
        data.setCurrentParkingCount(getCurrentParkingCount());
        data.setTodayVehicleInCount(getTodayVehicleInCount());
        data.setTodayVehicleOutCount(getTodayVehicleOutCount());
        data.setTodayRevenue(getTodayRevenue());
        data.setMonthRevenue(getMonthRevenue());
        data.setRecentSessions(getRecentSessions());
        return data;
    }

    public int getCurrentParkingCount() {
        String sql = "SELECT COUNT(*) AS total FROM parking_sessions WHERE status = 'PARKING'";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("total");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public int getTodayVehicleInCount() {
        String sql = "SELECT COUNT(*) AS total "
                + "FROM parking_sessions "
                + "WHERE CAST(entry_time AS DATE) = CAST(GETDATE() AS DATE)";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("total");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public int getTodayVehicleOutCount() {
        String sql = "SELECT COUNT(*) AS total "
                + "FROM parking_sessions "
                + "WHERE exit_time IS NOT NULL "
                + "AND CAST(exit_time AS DATE) = CAST(GETDATE() AS DATE)";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("total");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public double getTodayRevenue() {
        String sql = "SELECT ISNULL(SUM(total_amount), 0) AS revenue "
                + "FROM parking_sessions "
                + "WHERE exit_time IS NOT NULL "
                + "AND CAST(exit_time AS DATE) = CAST(GETDATE() AS DATE)";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getDouble("revenue");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public double getMonthRevenue() {
        String sql = "SELECT ISNULL(SUM(total_amount), 0) AS revenue "
                + "FROM parking_sessions "
                + "WHERE exit_time IS NOT NULL "
                + "AND YEAR(exit_time) = YEAR(GETDATE()) "
                + "AND MONTH(exit_time) = MONTH(GETDATE())";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getDouble("revenue");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public List<ParkingSessionView> getRecentSessions() {
        List<ParkingSessionView> list = new ArrayList<>();

        String sql = "SELECT TOP 20 "
                + "ps.session_id, "
                + "v.license_plate, "
                + "vt.type_name, "
                + "t.ticket_code, "
                + "ps.entry_time, "
                + "ps.exit_time, "
                + "ps.total_amount, "
                + "ps.status "
                + "FROM parking_sessions ps "
                + "JOIN vehicles v ON ps.vehicle_id = v.vehicle_id "
                + "JOIN vehicle_types vt ON v.type_id = vt.type_id "
                + "JOIN tickets t ON ps.ticket_id = t.ticket_id "
                + "ORDER BY ps.session_id DESC";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ParkingSessionView item = new ParkingSessionView();
                item.setSessionId(rs.getInt("session_id"));
                item.setLicensePlate(rs.getString("license_plate"));
                item.setVehicleTypeName(rs.getString("type_name"));
                item.setTicketCode(rs.getString("ticket_code"));
                item.setEntryTime(rs.getTimestamp("entry_time"));
                item.setExitTime(rs.getTimestamp("exit_time"));

                Object totalAmount = rs.getObject("total_amount");
                item.setTotalAmount(totalAmount == null ? null : ((Number) totalAmount).doubleValue());

                item.setStatus(rs.getString("status"));
                list.add(item);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
