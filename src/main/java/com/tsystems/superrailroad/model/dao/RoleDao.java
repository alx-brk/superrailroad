package main.java.com.tsystems.superrailroad.model.dao;

import main.java.com.tsystems.superrailroad.model.entity.Role;

public interface RoleDao {
    void create (Role role);
    Role read(Integer id);
    void update(Role role);
    void delete(Integer id);
    Role find(String role);
}
