package com.bobocode.dao.impl;

import com.bobocode.dao.AccountDao;
import com.bobocode.model.Account;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
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

        // todo: find account by id using EntityManager
        Account account = em.find(Account.class, id);

        em.getTransaction().commit();
        em.close();

        // todo: return account
        return account;
    }

    @Override
    public Account findByEmail(String email) throws NoResultException {
        // todo: implement search by email via EntityManager
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Account account = em.createQuery("Select a from Account a where a.email = :email", Account.class)
                .setParameter("email", email)
                .getSingleResult();

        em.getTransaction().commit();
        em.close();

        return account;
    }

    @Override
    public List<Account> findAll() {
        //todo: find and return all accounts using EntityManagers
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        List<Account> accounts = em.createQuery("Select a from Account a")
                .getResultList();

        em.getTransaction().commit();
        em.close();

        return accounts;
    }

    @Override
    public void save(Account account) {
        // todo: save an account sing EntityManager
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        em.persist(account);

        em.getTransaction().commit();
        em.close();
    }
}
