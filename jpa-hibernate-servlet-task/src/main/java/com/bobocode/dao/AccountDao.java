package com.bobocode.dao;

import com.bobocode.model.Account;

import java.util.List;

public interface AccountDao {
    Account findOne(Long id);

    Account findByEmail(String email);

    List<Account> findAll();

    void save(Account account);
}
