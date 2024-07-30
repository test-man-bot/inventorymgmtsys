package com.ims.inventorymgmtsys.service;

import com.ims.inventorymgmtsys.entity.User;
import com.ims.inventorymgmtsys.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, JdbcTemplate jdbcTemplate){

        this.userRepository = userRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void createUser(User user) {
        System.out.println("received data :" + user);
        User createuser = new User();
        createuser.setId(UUID.randomUUID().toString());
        createuser.setUserName(user.getUserName());
        createuser.setEmailAddress(user.getEmailAddress());
        createuser.setAddress(null);
        createuser.setPhone(null);
        createuser.setPassword(passwordEncoder.encode(user.getPassword())); // 修正
        userRepository.insert(createuser);
        jdbcTemplate.update("INSERT INTO authorities (username, authority) VALUES (?, ?)", user.getUserName(), "ROLE_USER");
        jdbcTemplate.update("UPDATE t_user set enabled=TRUE WHERE username=?", user.getUserName());

    }

    @Override
    public Boolean updateUser(User user) {
        return true;
    }

    @Override
    public User selectById(String id) {
        return userRepository.selectById(id);
    }

}
