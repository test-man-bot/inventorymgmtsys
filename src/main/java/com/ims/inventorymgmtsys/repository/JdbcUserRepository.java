package com.ims.inventorymgmtsys.repository;

import com.ims.inventorymgmtsys.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class JdbcUserRepository implements UserRepository{

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcUserRepository(JdbcTemplate jdbcTemplate) { this.jdbcTemplate = jdbcTemplate; }

    @Override
    public User findById(String id) {
        List<User> users = jdbcTemplate.query(
                "SELECT * FROM t_user WHERE id = ?",
                new BeanPropertyRowMapper<>(User.class),
                id
        );
        return users.isEmpty() ? null : users.get(0);
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query("SELECT * FROM t_user", new DataClassRowMapper<>(User.class));
    }

    @Override
    public void save(User user){
        String id = UUID.randomUUID().toString();
        user.setId(id);
        jdbcTemplate.update("INSERT into t_user VALUES (?, ?, ?, ?, ?, ?, ?)",
                user.getId(),
                user.getUserName(),
                user.getEmailAddress(),
                user.getAddress(),
                user.getPhone(),
                user.getPassword(),
                user.getEnabled()
                );
    }

    @Override
    public boolean update(User user) {
        int count = jdbcTemplate.update("UPDATE t_user SET userName=?, emailAddress=?, address=?, phone=?, password=?, enabled=? WHERE id=?",
                user.getUserName(),
                user.getEmailAddress(),
                user.getAddress(),
                user.getPhone(),
                user.getPassword(),
                user.getId(),
                user.getEnabled()
                );
        return count != 0;
    }

    @Override
    public User findByUserName(String userName) {
        List<User> users = jdbcTemplate.query("SELECT * FROM t_user WHERE userName = ?", new BeanPropertyRowMapper<>(User.class), userName);
        return users.isEmpty() ? null : users.get(0);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        List<User> users = jdbcTemplate.query("SELECT * FROM t_user WHERE emailAddress = ?", new BeanPropertyRowMapper<>(User.class),email);
        return users.isEmpty() ? Optional.empty() : Optional.of(users.get(0));
    }
}
