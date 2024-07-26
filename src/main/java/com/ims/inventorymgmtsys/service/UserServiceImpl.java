package com.ims.inventorymgmtsys.service;

import com.ims.inventorymgmtsys.entity.User;
import com.ims.inventorymgmtsys.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public void createUser(User user) {
        System.out.println("received data :" + user);
        User userA = new User();
        userA.setId(UUID.randomUUID().toString());
        userA.setCustomerName(user.getCustomerName());
        userA.setEmailAddress(user.getEmailAddress());
        userA.setAddress(null);
        userA.setPhone(null);
        userA.setPassword(passwordEncoder.encode(user.getPassword())); // 修正
        userRepository.insert(userA);
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
