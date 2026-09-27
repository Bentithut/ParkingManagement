package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Vehicle;
import utils.DBConnection;

public class VehicleDAO {

    public Vehicle findByLicensePlate(String licensePlate) {
        String sql = "SELECT * FROM vehicles WHERE license_plate = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, licensePlate);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Vehicle vehicle = new Vehicle();
                vehicle.setVehicleId(rs.getInt("vehicle_id"));
                vehicle.setLicensePlate(rs.getString("license_plate"));
                vehicle.setCustomerId((Integer) rs.getObject("customer_id"));
                vehicle.setTypeId(rs.getInt("type_id"));
                vehicle.setStatus(rs.getString("status"));
                return vehicle;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Vehicle findById(int vehicleId) {
        String sql = "SELECT v.*, vt.type_name, vt.price_per_hour "
                   + "FROM vehicles v "
                   + "JOIN vehicle_types vt ON v.type_id = vt.type_id "
                   + "WHERE v.vehicle_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, vehicleId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Vehicle vehicle = new Vehicle();
                vehicle.setVehicleId(rs.getInt("vehicle_id"));
                vehicle.setLicensePlate(rs.getString("license_plate"));
                vehicle.setCustomerId((Integer) rs.getObject("customer_id"));
                vehicle.setTypeId(rs.getInt("type_id"));
                vehicle.setStatus(rs.getString("status"));
                vehicle.setTypeName(rs.getString("type_name"));
                vehicle.setPricePerHour(rs.getDouble("price_per_hour"));
                return vehicle;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean insertVehicle(Vehicle vehicle) {
        String sql = "INSERT INTO vehicles(license_plate, customer_id, type_id, status) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, vehicle.getLicensePlate());
            if (vehicle.getCustomerId() == null) {
                ps.setNull(2, java.sql.Types.INTEGER);
            } else {
                ps.setInt(2, vehicle.getCustomerId());
            }
            ps.setInt(3, vehicle.getTypeId());
            ps.setString(4, vehicle.getStatus());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateStatus(int vehicleId, String status) {
        String sql = "UPDATE vehicles SET status = ? WHERE vehicle_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, vehicleId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Vehicle> getAllVehicles() {
        List<Vehicle> list = new ArrayList<>();
        String sql = "SELECT * FROM vehicles";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Vehicle vehicle = new Vehicle();
                vehicle.setVehicleId(rs.getInt("vehicle_id"));
                vehicle.setLicensePlate(rs.getString("license_plate"));
                vehicle.setCustomerId((Integer) rs.getObject("customer_id"));
                vehicle.setTypeId(rs.getInt("type_id"));
                vehicle.setStatus(rs.getString("status"));
                list.add(vehicle);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}