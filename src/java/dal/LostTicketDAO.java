package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.LostTicketInfo;
import utils.DBConnection;

public class LostTicketDAO {

    public LostTicketInfo findActiveSessionByLicensePlate(String licensePlate) {
        String sql = "SELECT TOP 1 "
                + "ps.session_id, ps.vehicle_id, ps.ticket_id, ps.entry_time, "
                + "v.license_plate, "
                + "vt.type_name, vt.price_per_hour, "
                + "t.ticket_code "
                + "FROM parking_sessions ps "
                + "JOIN vehicles v ON ps.vehicle_id = v.vehicle_id "
                + "JOIN vehicle_types vt ON v.type_id = vt.type_id "
                + "JOIN tickets t ON ps.ticket_id = t.ticket_id "
                + "WHERE v.license_plate = ? AND ps.status = 'PARKING' "
                + "ORDER BY ps.session_id DESC";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, licensePlate);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                LostTicketInfo info = new LostTicketInfo();
                info.setSessionId(rs.getInt("session_id"));
                info.setVehicleId(rs.getInt("vehicle_id"));
                info.setTicketId(rs.getInt("ticket_id"));
                info.setEntryTime(rs.getTimestamp("entry_time"));
                info.setLicensePlate(rs.getString("license_plate"));
                info.setVehicleTypeName(rs.getString("type_name"));
                info.setTicketCode(rs.getString("ticket_code"));
                return info;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
