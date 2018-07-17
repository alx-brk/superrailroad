package main.java.com.tsystems.superrailroad.model.service;

import main.java.com.tsystems.superrailroad.model.dto.UserDto;

public interface UserService {
    boolean userExists(UserDto userDto);
    void createUser(UserDto userDto);
}
