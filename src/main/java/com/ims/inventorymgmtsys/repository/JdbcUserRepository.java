package com.ims.inventorymgmtsys.repository;

import com.ims.inventorymgmtsys.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcUserRepository implements UserRepository{

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcUserRepository(JdbcTemplate jdbcTemplate) { this.jdbcTemplate = jdbcTemplate; }

    @Override
    public User selectById(String id) {
        List<User> users = jdbcTemplate.query(
                "SELECT * FROM t_user WHERE id = ?",
                new BeanPropertyRowMapper<>(User.class),
                id
        );
        return users.isEmpty() ? null : users.get(0);
    }

    @Override
    public List<User> selectAll() {
        return jdbcTemplate.query("SELECT * FROM t_user", new DataClassRowMapper<>(User.class));
    }

    @Override
    public void insert(User user){
        jdbcTemplate.update("INSERT into t_user VALUES (?, ?, ?, ?, ?, ?)",
                user.getId(),
                user.getCustomerName(),
                user.getEmailAddress(),
                user.getAddress(),
                user.getPhone(),
                user.getPassword()
                );
    }

    @Override
    public boolean update(User user) {
        int count = jdbcTemplate.update("UPDATE t_user SET customerName=?, emailAddress=?, address=?, phone=?, password=? WHERE id=?",
                user.getCustomerName(),
                user.getEmailAddress(),
                user.getAddress(),
                user.getPhone(),
                user.getPassword(),
                user.getId()
                );
        return count != 0;
    }
}
