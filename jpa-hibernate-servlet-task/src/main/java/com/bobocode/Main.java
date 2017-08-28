package com.bobocode;

import com.bobocode.dao.AccountDao;
import com.bobocode.dao.impl.AccountDaoImpl;
import com.bobocode.model.Account;
import com.bobocode.util.AccountDataUtil;
import com.bobocode.util.DBUtil;

import java.util.List;

/**
 * Created by hamster on 28.08.2017.
 */
public class Main {
    public static void main(String[] args) {
        AccountDao accountDao = new AccountDaoImpl(DBUtil.getEntityManagerFactory());
        System.out.println("findOne");
        System.out.println(accountDao.findOne(2L));
        System.out.println("****************************************");

        System.out.println("findByEmail");
        System.out.println(accountDao.findByEmail("bob@marly.com"));
        System.out.println("****************************************");

        System.out.println("findAll");
        List<Account> accountList = accountDao.findAll();
        for (Account element:accountList) {
            System.out.println(element);
        }
        System.out.println("****************************************");

        System.out.println("save Account");
        Account newAccount = AccountDataUtil.generateFakeAccount();
        accountDao.save(newAccount);
        System.out.println("****************************************");
    }
}
