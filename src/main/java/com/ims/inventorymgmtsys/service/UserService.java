package com.ims.inventorymgmtsys.service;

import com.ims.inventorymgmtsys.entity.User;
import com.ims.inventorymgmtsys.exception.UserAlreadyExistsException;

import java.util.Optional;

public interface UserService {
    void createUser(User user);

    boolean updateUser(User user);

    User findById(String id);

    void registerUser(User user) throws UserAlreadyExistsException;

    User getCurrentUser();

    void updateUserProfile(User userProfile);

    void changePassword(User user, String newPassword);

    Optional<User> findByEmail(String email);

}
