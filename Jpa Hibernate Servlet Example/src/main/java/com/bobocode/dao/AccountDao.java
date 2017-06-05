package com.bobocode.dao;

import com.bobocode.model.Account;

import java.util.List;

public interface AccountDao {
    Account findOne(Long id);

    List<Account> findAll();

    void save(Account account);
}
