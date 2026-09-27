package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.VehicleType;
import utils.DBConnection;

public class VehicleTypeDAO {

    public List<VehicleType> getAllVehicleTypes() {
        List<VehicleType> list = new ArrayList<>();
        String sql = "SELECT * FROM vehicle_types ORDER BY type_id ASC";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                VehicleType vt = new VehicleType();
                vt.setTypeId(rs.getInt("type_id"));
                vt.setTypeName(rs.getString("type_name"));
                vt.setPricePerHour(rs.getDouble("price_per_hour"));
                vt.setStatus(rs.getString("status"));
                list.add(vt);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<VehicleType> getActiveVehicleTypes() {
        List<VehicleType> list = new ArrayList<>();
        String sql = "SELECT * FROM vehicle_types WHERE status = 'ACTIVE' ORDER BY type_id ASC";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                VehicleType vt = new VehicleType();
                vt.setTypeId(rs.getInt("type_id"));
                vt.setTypeName(rs.getString("type_name"));
                vt.setPricePerHour(rs.getDouble("price_per_hour"));
                vt.setStatus(rs.getString("status"));
                list.add(vt);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public VehicleType findById(int typeId) {
        String sql = "SELECT * FROM vehicle_types WHERE type_id = ?";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, typeId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                VehicleType vt = new VehicleType();
                vt.setTypeId(rs.getInt("type_id"));
                vt.setTypeName(rs.getString("type_name"));
                vt.setPricePerHour(rs.getDouble("price_per_hour"));
                vt.setStatus(rs.getString("status"));
                return vt;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean insertVehicleType(VehicleType vehicleType) {
        String sql = "INSERT INTO vehicle_types(type_name, price_per_hour, status) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, vehicleType.getTypeName());
            ps.setDouble(2, vehicleType.getPricePerHour());
            ps.setString(3, vehicleType.getStatus());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean updateVehicleType(VehicleType vehicleType) {
        String sql = "UPDATE vehicle_types SET type_name = ?, price_per_hour = ?, status = ? WHERE type_id = ?";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, vehicleType.getTypeName());
            ps.setDouble(2, vehicleType.getPricePerHour());
            ps.setString(3, vehicleType.getStatus());
            ps.setInt(4, vehicleType.getTypeId());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean updateStatus(int typeId, String status) {
        String sql = "UPDATE vehicle_types SET status = ? WHERE type_id = ?";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setInt(2, typeId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean isVehicleTypeUsed(int typeId) {
        String sql = "SELECT TOP 1 1 FROM vehicles WHERE type_id = ?";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, typeId);
            ResultSet rs = ps.executeQuery();
            return rs.next();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    public boolean deleteVehicleType(int typeId) {
        String sql = "DELETE FROM vehicle_types WHERE type_id = ?";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, typeId);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
