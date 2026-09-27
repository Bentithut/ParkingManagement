package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Ticket;
import utils.DBConnection;

public class TicketDAO extends DBConnection {

    public int createTicket(Ticket ticket) {
        int id = -1;
        String sql = "INSERT INTO tickets(ticket_code, vehicle_id, ticket_type, issue_time, expire_time, status) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, ticket.getTicketCode());
            ps.setInt(2, ticket.getVehicleId());
            ps.setString(3, ticket.getTicketType());
            ps.setTimestamp(4, ticket.getIssueTime());

            if (ticket.getExpireTime() == null) {
                ps.setNull(5, java.sql.Types.TIMESTAMP);
            } else {
                ps.setTimestamp(5, ticket.getExpireTime());
            }

            ps.setString(6, ticket.getStatus());

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

    public Ticket findByTicketCode(String code) {
        String sql = "SELECT * FROM tickets WHERE ticket_code = ?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, code);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Ticket t = new Ticket();
                    t.setTicketId(rs.getInt("ticket_id"));
                    t.setTicketCode(rs.getString("ticket_code"));
                    t.setVehicleId(rs.getInt("vehicle_id"));
                    t.setTicketType(rs.getString("ticket_type"));
                    t.setIssueTime(rs.getTimestamp("issue_time"));
                    t.setExpireTime(rs.getTimestamp("expire_time"));
                    t.setStatus(rs.getString("status"));
                    return t;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateTicketStatus(int ticketId, String status) {
        String sql = "UPDATE tickets SET status = ? WHERE ticket_id = ?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setInt(2, ticketId);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Ticket> getAllTickets() {
        List<Ticket> list = new ArrayList<>();

        String sql = "SELECT t.ticket_id, t.ticket_code, "
                   + "v.license_plate, vt.type_name, ps.entry_time "
                   + "FROM tickets t "
                   + "JOIN vehicles v ON t.vehicle_id = v.vehicle_id "
                   + "JOIN vehicle_types vt ON v.type_id = vt.type_id "
                   + "JOIN parking_sessions ps ON t.ticket_id = ps.ticket_id "
                   + "WHERE t.status = 'ACTIVE' AND ps.status = 'PARKING' "
                   + "ORDER BY t.ticket_id DESC";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Ticket t = new Ticket();
                t.setTicketId(rs.getInt("ticket_id"));
                t.setTicketCode(rs.getString("ticket_code"));
                t.setLicensePlate(rs.getString("license_plate"));
                t.setVehicleTypeName(rs.getString("type_name"));
                t.setEntryTime(rs.getTimestamp("entry_time"));
                list.add(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}