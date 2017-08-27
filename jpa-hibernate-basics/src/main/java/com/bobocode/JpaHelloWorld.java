package com.bobocode;


import com.bobocode.model.Account;
import com.bobocode.util.AccountDataUtil;
import com.bobocode.util.DBUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class JpaHelloWorld {
    public static void main(String[] args) {
        EntityManagerFactory emf = DBUtil.getEntityManagerFactory();

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Account account = AccountDataUtil.generateFakeAccount();
        em.persist(account);

        System.out.println(account);

        em.getTransaction().commit();
        em.close();

        DBUtil.destroy();
    }
}
