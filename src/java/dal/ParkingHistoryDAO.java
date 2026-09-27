package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.ParkingHistoryItem;
import utils.DBConnection;

public class ParkingHistoryDAO {

    public List<ParkingHistoryItem> searchByLicensePlate(String licensePlate) {
        List<ParkingHistoryItem> list = new ArrayList<>();

        String sql = "SELECT ps.session_id, v.license_plate, vt.type_name, "
                + "t.ticket_code, ps.entry_time, ps.exit_time, ps.total_amount, ps.status "
                + "FROM parking_sessions ps "
                + "JOIN vehicles v ON ps.vehicle_id = v.vehicle_id "
                + "JOIN vehicle_types vt ON v.type_id = vt.type_id "
                + "JOIN tickets t ON ps.ticket_id = t.ticket_id "
                + "WHERE v.license_plate LIKE ? "
                + "ORDER BY ps.session_id DESC";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + licensePlate + "%");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ParkingHistoryItem item = new ParkingHistoryItem();
                item.setSessionId(rs.getInt("session_id"));
                item.setLicensePlate(rs.getString("license_plate"));
                item.setVehicleTypeName(rs.getString("type_name"));
                item.setTicketCode(rs.getString("ticket_code"));
                item.setEntryTime(rs.getTimestamp("entry_time"));
                item.setExitTime(rs.getTimestamp("exit_time"));

                Object amount = rs.getObject("total_amount");
                item.setTotalAmount(amount == null ? null : ((Number) amount).doubleValue());

                item.setStatus(rs.getString("status"));
                list.add(item);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
