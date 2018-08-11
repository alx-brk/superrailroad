package main.java.com.tsystems.superrailroad.model.service;

import main.java.com.tsystems.superrailroad.model.dao.RoleDaoImpl;
import main.java.com.tsystems.superrailroad.model.dao.UserDaoImpl;
import main.java.com.tsystems.superrailroad.model.dto.UserDto;
import main.java.com.tsystems.superrailroad.model.entity.Role;
import main.java.com.tsystems.superrailroad.model.entity.User;
import org.junit.Test;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.NoResultException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.junit.Assert.*;

public class UserServiceImplTest {
    private UserDaoImpl userDao = mock(UserDaoImpl.class);
    private RoleDaoImpl roleDao = mock(RoleDaoImpl.class);
    private PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
    private UserServiceImpl userService = new UserServiceImpl(userDao, roleDao, passwordEncoder);

    @Test
    public void userExists() {
        UserDto userDto = new UserDto();
        userDto.setLogin("ololo");
        User user = new User();
        user.setLogin("ololo");
        when(userDao.find("ololo")).thenReturn(user);
        assertTrue(userService.userExists(userDto));
    }

    @Test
    public void userExistsExcep() {
        UserDto userDto = new UserDto();
        userDto.setLogin("ololo");
        when(userDao.find("ololo")).thenThrow(NoResultException.class);
        assertFalse(userService.userExists(userDto));
    }

    @Test
    public void createUser() {
        UserDto userDto = new UserDto();
        userDto.setPassword("ololo");
        Role role = new Role();
        User user = new User();
        user.setPassword("trololo");
        user.addRole(role);

        when(roleDao.find("ROLE_USER")).thenReturn(role);
        when(passwordEncoder.encode("ololo")).thenReturn("trololo");
        userDao.create(user);
        verify(userDao).create(user);
    }

    @Test
    public void loadUserByUsername() {
        User user = new User();
        when(userDao.find("ololo")).thenReturn(user);
        assertNotNull(userService.loadUserByUsername("ololo"));
    }

    @Test(expected = UsernameNotFoundException.class)
    public void loadUserByUsernameExcep() {
        User user = new User();
        when(userDao.find("ololo")).thenThrow(NoResultException.class);
        userService.loadUserByUsername("ololo");
    }
}