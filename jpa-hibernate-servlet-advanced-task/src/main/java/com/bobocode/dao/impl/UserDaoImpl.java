package com.bobocode.dao.impl;


import com.bobocode.dao.UserDao;
import com.bobocode.model.User;

import java.time.LocalDate;
import java.util.List;

public class UserDaoImpl implements UserDao {
    @Override
    public List<User> findByBirthday(LocalDate birthday) {
        throw new UnsupportedOperationException("This method is not implemented yet!");
    }
}
