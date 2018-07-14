package main.java.com.tsystems.superrailroad.model.dao;

import main.java.com.tsystems.superrailroad.model.entity.User;

public interface UserDao {
    void create (User user);
    User read(Integer id);
    void update(User user);
    void delete(Integer id);
}
