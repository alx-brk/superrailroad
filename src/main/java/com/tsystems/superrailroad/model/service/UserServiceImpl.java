package main.java.com.tsystems.superrailroad.model.service;

import main.java.com.tsystems.superrailroad.model.dao.RoleDao;
import main.java.com.tsystems.superrailroad.model.dao.UserDao;
import main.java.com.tsystems.superrailroad.model.dto.UserDto;
import main.java.com.tsystems.superrailroad.model.entity.Role;
import main.java.com.tsystems.superrailroad.model.entity.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;

public class UserServiceImpl implements UserService, UserDetailsService {
    private static final Logger log = Logger.getLogger(UserServiceImpl.class);
    private UserDao userDao;
    private RoleDao roleDao;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserDao userDao, RoleDao roleDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.roleDao = roleDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean userExists(UserDto userDto) {
        try {
            userDao.find(userDto.getLogin());
            return true;
        } catch (NoResultException e){
            return false;
        }
    }

    @Override
    @Transactional
    public void createUser(UserDto userDto) {
        Role role = roleDao.find("ROLE_USER");
        User user = new User();
        user.setLogin(userDto.getLogin());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.addRole(role);

        userDao.create(user);
        log.info("User was created " + userDto.getLogin());
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String s) {
        try {
            User user = userDao.find(s);
            return new UserPrincipal(user);
        } catch (NoResultException e){
            throw new UsernameNotFoundException(s);
        }
    }
}
