package com.bobocode.dao.impl;

import com.bobocode.dao.AccountDao;
import com.bobocode.model.Account;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.function.Function;

public class AccountDaoImpl implements AccountDao {

    private final EntityManagerFactory emf;

    public AccountDaoImpl(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public Account findOne(Long id) {
        return performSelect(entityManager -> entityManager.find(Account.class, id));
    }

    @Override
    public Account findByEmail(String email) {
        return performSelect(entityManager -> entityManager.createQuery("SELECT a FROM Account a WHERE a.email = :email", Account.class)
                    .setParameter("email", email)
                    .getSingleResult());
    }

    @Override
    public List<Account> findAll() {
        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();

        List<Account> accounts = entityManager.createQuery("SELECT a FROM Account a", Account.class).getResultList();

        entityManager.getTransaction().commit();
        entityManager.close();

        return accounts;
    }

    @Override
    public void save(Account account) {
        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();

        System.out.println(account);
        entityManager.persist(account);

        entityManager.getTransaction().commit();
        entityManager.close();
    }

    private Account performSelect(Function<EntityManager, Account> operation){
        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();

        Account account = operation.apply(entityManager);

        entityManager.getTransaction().commit();
        entityManager.close();
        return account;
    }

}
