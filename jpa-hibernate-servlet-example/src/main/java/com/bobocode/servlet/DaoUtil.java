package com.bobocode.servlet;


import com.bobocode.dao.AccountDao;
import com.bobocode.dao.impl.AccountDaoImpl;
import com.bobocode.util.DBUtil;

import javax.persistence.EntityManagerFactory;

public class DaoUtil {

    private static AccountDao accountDao;

    public static void init(){
        EntityManagerFactory emf = DBUtil.getEntityManagerFactory();
        accountDao = new AccountDaoImpl(emf);
    }

    public static AccountDao getAccountDao(){
        if (accountDao == null){
            init();
        }
        return accountDao;
    }
}
