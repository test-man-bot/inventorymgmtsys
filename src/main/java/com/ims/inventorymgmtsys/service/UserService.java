package com.ims.inventorymgmtsys.service;

import com.ims.inventorymgmtsys.entity.User;

public interface UserService {
    void createUser(User user);

    Boolean updateUser(User user);

    User selectById(String id);

}
