package com.ims.inventorymgmtsys.repository;

import com.ims.inventorymgmtsys.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface UserRepository {
    User selectById(String id);

    List<User> selectAll();

    void insert(User user);

    boolean update(User user);

    User selectByUserName(String userName);
}
