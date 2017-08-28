package com.bobocode.dao.impl;

import com.bobocode.dao.AccountDao;
import com.bobocode.model.Account;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.function.Function;


public class AccountDaoImpl implements AccountDao {

    private EntityManagerFactory emf;

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
    public Account findByEmail(String email) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Account account = em.createQuery("SELECT a FROM Account a WHERE a.email = :email", Account.class).getSingleResult();

        em.getTransaction().commit();
        em.close();

        return account;
    }

    @Override
    public List<Account> findAll() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        List<Account> accounts = em.createQuery("SELECT a FROM Account a WHERE a.email = :email", Account.class).getResultList();

        em.getTransaction().commit();
        em.close();

        return accounts;
    }

    @Override
    public void save(Account account) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        em.persist(account);

        em.getTransaction().commit();
        em.close();
    }

    private <R> List<R> performWithinPersistence(Function<EntityManager, List<R>> operation) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        List<R> results = operation.apply(em);

        em.getTransaction().commit();
        em.close();

        return results;
    }
}
