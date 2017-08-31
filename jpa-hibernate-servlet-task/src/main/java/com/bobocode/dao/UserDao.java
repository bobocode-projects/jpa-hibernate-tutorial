package com.bobocode.dao;

import com.bobocode.model.User;

import java.util.List;

public interface UserDao {
    User findOne(Long id);

    User findByEmail(String email);

    List<User> findAll();

    void save(User user);
}
