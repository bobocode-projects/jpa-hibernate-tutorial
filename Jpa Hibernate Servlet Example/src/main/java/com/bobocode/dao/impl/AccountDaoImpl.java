package com.bobocode.dao.impl;

import com.bobocode.dao.AccountDao;
import com.bobocode.model.Account;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;


public class AccountDaoImpl implements AccountDao {

    private final EntityManagerFactory emf;

    public AccountDaoImpl(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public Account findOne(Long id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Account account = em.find(Account.class, id);

        em.getTransaction().commit();
        em.close();

        return account;
    }

    @Override
    public List<Account> findAll() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        List<Account> accountList = em.createQuery("select a from Account a", Account.class).getResultList();

        em.getTransaction().commit();
        em.close();

        return accountList;
    }

    @Override
    public void save(Account account) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        em.persist(account);

        em.getTransaction().commit();
        em.close();
    }


}
