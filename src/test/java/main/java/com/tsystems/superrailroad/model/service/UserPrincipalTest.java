package main.java.com.tsystems.superrailroad.model.service;

import main.java.com.tsystems.superrailroad.model.entity.User;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class UserPrincipalTest {


    @Test
    public void getAuthorities() {
        User user = new User();
        user.setRoles(new ArrayList<>());
        UserPrincipal userPrincipal = new UserPrincipal(user);

        assertEquals(0, userPrincipal.getAuthorities().size());
    }

    @Test
    public void getPassword() {
        User user = new User();
        user.setPassword("ololo");
        UserPrincipal userPrincipal = new UserPrincipal(user);

        assertEquals(user.getPassword(), userPrincipal.getPassword());
    }

    @Test
    public void getUsername() {
        User user = new User();
        user.setLogin("ololo");
        UserPrincipal userPrincipal = new UserPrincipal(user);

        assertEquals(user.getLogin(), userPrincipal.getUsername());
    }

    @Test
    public void isAccountNonExpired() {
        User user = new User();
        UserPrincipal userPrincipal = new UserPrincipal(user);

        assertTrue(userPrincipal.isCredentialsNonExpired());
    }

    @Test
    public void isAccountNonLocked() {
        User user = new User();
        UserPrincipal userPrincipal = new UserPrincipal(user);

        assertTrue(userPrincipal.isAccountNonLocked());
    }

    @Test
    public void isCredentialsNonExpired() {
        User user = new User();
        UserPrincipal userPrincipal = new UserPrincipal(user);

        assertTrue(userPrincipal.isCredentialsNonExpired());
    }

    @Test
    public void isEnabled() {
        User user = new User();
        UserPrincipal userPrincipal = new UserPrincipal(user);

        assertTrue(userPrincipal.isEnabled());
    }
}