package main.java.com.tsystems.superrailroad.model.dto;

import java.util.ArrayList;
import java.util.List;

public class RoleDto {
    private int roleId;
    private String role;
    private List<UserDto> users = new ArrayList<>();

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<UserDto> getUsers() {
        return users;
    }

    public void setUsers(List<UserDto> users) {
        this.users = users;
    }
}
