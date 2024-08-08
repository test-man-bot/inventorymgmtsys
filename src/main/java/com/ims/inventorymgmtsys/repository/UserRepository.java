package com.ims.inventorymgmtsys.repository;

import com.ims.inventorymgmtsys.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User findById(String id);

    List<User> findAll();

    void save(User user);

    boolean update(User user);

    User findByUserName(String userName);

    Optional<User> findByEmail(String email);  // 新しいメソッドの追加

    boolean updateAuth(User user);

    boolean updateIsEnabled(User user);
}
