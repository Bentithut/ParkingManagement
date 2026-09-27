package model;

public class User {

    private int userId;
    private String username;
    private String password;
    private String fullName;
    private int roleId;
    private String role;
    private String status;

    public User() {
    }

    public User(int userId, String username, String password, String fullName, int roleId, String role, String status) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.roleId = roleId;
        this.role = role;
        this.status = status;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
        this.role = mapRole(roleId);
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
        if ("ADMIN".equalsIgnoreCase(role)) {
            this.roleId = 1;
        } else if ("STAFF".equalsIgnoreCase(role)) {
            this.roleId = 2;
        } else if ("MANAGER".equalsIgnoreCase(role)) {
            this.roleId = 3;
        }
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String mapRole(int roleId) {
        switch (roleId) {
            case 1:
                return "ADMIN";
            case 2:
                return "STAFF";
            case 3:
                return "MANAGER";
            default:
                return "UNKNOWN";
        }
    }
}
