package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.ParkingSession;
import utils.DBConnection;

public class ParkingSessionDAO {

    public int createSession(ParkingSession session) {
        int id = -1;
        String sql = "INSERT INTO parking_sessions(vehicle_id, ticket_id, lot_id, entry_time, exit_time, total_amount, status) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, session.getVehicleId());
            ps.setInt(2, session.getTicketId());

            if (session.getLotId() == null) {
                ps.setNull(3, java.sql.Types.INTEGER);
            } else {
                ps.setInt(3, session.getLotId());
            }

            ps.setTimestamp(4, session.getEntryTime());
            ps.setTimestamp(5, session.getExitTime());

            if (session.getTotalAmount() == null) {
                ps.setNull(6, java.sql.Types.DECIMAL);
            } else {
                ps.setDouble(6, session.getTotalAmount());
            }

            ps.setString(7, session.getStatus());

            int rows = ps.executeUpdate();
            if (rows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        id = rs.getInt(1);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return id;
    }

    public ParkingSession getActiveSessionByTicketId(int ticketId) {
        String sql = "SELECT * FROM parking_sessions WHERE ticket_id = ? AND status = 'PARKING'";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, ticketId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ParkingSession session = new ParkingSession();
                    session.setSessionId(rs.getInt("session_id"));
                    session.setVehicleId(rs.getInt("vehicle_id"));
                    session.setTicketId(rs.getInt("ticket_id"));
                    session.setLotId((Integer) rs.getObject("lot_id"));
                    session.setEntryTime(rs.getTimestamp("entry_time"));
                    session.setExitTime(rs.getTimestamp("exit_time"));

                    Object amount = rs.getObject("total_amount");
                    session.setTotalAmount(amount == null ? null : ((Number) amount).doubleValue());

                    session.setStatus(rs.getString("status"));
                    return session;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateSessionWhenVehicleOut(ParkingSession session) {
        String sql = "UPDATE parking_sessions SET exit_time = ?, total_amount = ?, status = ? WHERE session_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setTimestamp(1, session.getExitTime());

            if (session.getTotalAmount() == null) {
                ps.setNull(2, java.sql.Types.DECIMAL);
            } else {
                ps.setDouble(2, session.getTotalAmount());
            }

            ps.setString(3, session.getStatus());
            ps.setInt(4, session.getSessionId());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<ParkingSession> getAllSessions() {
        List<ParkingSession> list = new ArrayList<>();
        String sql = "SELECT * FROM parking_sessions ORDER BY session_id DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ParkingSession session = new ParkingSession();
                session.setSessionId(rs.getInt("session_id"));
                session.setVehicleId(rs.getInt("vehicle_id"));
                session.setTicketId(rs.getInt("ticket_id"));
                session.setLotId((Integer) rs.getObject("lot_id"));
                session.setEntryTime(rs.getTimestamp("entry_time"));
                session.setExitTime(rs.getTimestamp("exit_time"));

                Object amount = rs.getObject("total_amount");
                session.setTotalAmount(amount == null ? null : ((Number) amount).doubleValue());

                session.setStatus(rs.getString("status"));
                list.add(session);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}