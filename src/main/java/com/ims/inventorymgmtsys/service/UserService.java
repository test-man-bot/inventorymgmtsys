package com.ims.inventorymgmtsys.service;

import com.ims.inventorymgmtsys.entity.User;
import com.ims.inventorymgmtsys.exception.UserAlreadyExistsException;

public interface UserService {
    void createUser(User user);

    boolean updateUser(User user);

    User selectById(String id);

    void registerUser(User user) throws UserAlreadyExistsException;

}
