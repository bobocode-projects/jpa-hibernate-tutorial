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

        //todo: find account by id using EntityManager

        em.getTransaction().commit();
        em.close();

        throw new UnsupportedOperationException("Method is not implemented yet. It's your homework!");
        // todo: return account
    }

    @Override
    public Account findByEmail(String email) {
        // todo: implement search by email via EntityManager
        throw new UnsupportedOperationException("Method is not implemented yet. It's your homework!");
    }

    @Override
    public List<Account> findAll() {
        //todo: find and return all accounts using EntityManagers
        throw new UnsupportedOperationException("Method is not implemented yet. It's your homework!");
    }

    @Override
    public void save(Account account) {
        // todo: save an account sing EntityManager
    }


}
