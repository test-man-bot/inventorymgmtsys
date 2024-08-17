package com.ims.inventorymgmtsys.service;

import com.ims.inventorymgmtsys.config.CustomUserDetails;
import com.ims.inventorymgmtsys.entity.User;
import com.ims.inventorymgmtsys.exception.UserAlreadyExistsException;
import com.ims.inventorymgmtsys.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
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
        userRepository.save(createuser);
        userRepository.updateAuth(createuser);
        userRepository.updateIsEnabled(createuser);

    }

    @Override
    public boolean updateUser(User user) {
        return userRepository.update(user);
    }

    @Override
    public User selectById(String id) {
        return userRepository.findById(id);
    }

    @Override
    public void registerUser(User user) throws UserAlreadyExistsException {
//        User existsUser = selectById(user.getId());
        User existsUserByUserName = userRepository.findByUserName(user.getUserName());

        Optional<User> existsUserByEmail = userRepository.findByEmail(user.getEmailAddress());
        if ( existsUserByUserName != null || existsUserByEmail.isPresent()) {
            throw new UserAlreadyExistsException("User already exists");
        }
            createUser(user);
    }

    @Override
    public String getCurrentId () {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if ( authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            return userDetails.getUserId();
        }
        return null;
    }

    @Override
    public User getCurrentUser() {
        String currentUserId = getCurrentId();
        if (currentUserId != null) {
            return userRepository.findById(currentUserId);
        }
        return null;
    }

    @Override
    public void updateUserProfile(User userProfile) {
        User user = getCurrentUser();
        if (user != null) {
            user.setId(userProfile.getId());
            user.setUserName(userProfile.getUserName());
            user.setEmailAddress(userProfile.getEmailAddress());
            user.setAddress(userProfile.getAddress());
            user.setPhone(userProfile.getPhone());
            updateUser(user);
        }
    }


}
