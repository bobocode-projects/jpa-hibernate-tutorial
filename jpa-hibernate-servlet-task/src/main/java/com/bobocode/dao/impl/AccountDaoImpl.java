package com.bobocode.dao.impl;

import com.bobocode.dao.AccountDao;
import com.bobocode.model.Account;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
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
        Account queryAccount = em.createQuery("select a FROM Account a where a.id = :id", Account.class)
                .setParameter("id", id)
                .getSingleResult();
        em.getTransaction().commit();
        em.close();
        // todo: return account
        return queryAccount;
    }

    @Override
    public Account findByEmail(String email) {
        // todo: implement search by email via EntityManager
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Account queryEmail = em.createQuery("select a from Account a where a.email = :email", Account.class)
                .setParameter("email", email)
                .getSingleResult();
        em.getTransaction().commit();
        em.close();
        return queryEmail;
    }

    @Override
    public List<Account> findAll() {
        //todo: find and return all accounts using EntityManagers
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        List<Account> queryAccountList = em.createQuery("select a from Account a", Account.class)
                .getResultList();
        em.getTransaction().commit();
        em.close();
        return queryAccountList;
    }

    @Override
    public void save(Account account) {
        // todo: save an account sing EntityManager
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(account);
        System.out.println(account.toString() + " saccesfully saved!");
        em.getTransaction().commit();
        em.close();
    }


}
