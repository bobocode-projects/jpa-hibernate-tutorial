package com.bobocode.dao;

import com.bobocode.model.User;

import java.time.LocalDate;
import java.util.List;

public interface UserDao {
    List<User> findByBirthday(LocalDate birthday);
}
