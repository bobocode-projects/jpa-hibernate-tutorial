package com.bobocode.dao.impl;

import com.bobocode.dao.AccountDao;
import com.bobocode.model.Account;
import org.hibernate.query.criteria.internal.CriteriaDeleteImpl;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static java.util.Objects.nonNull;


public class AccountDaoImpl implements AccountDao {

    private final EntityManagerFactory emf;

    public AccountDaoImpl(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public Account findOne(Long id) {

        Account account = action((EntityManager entityManager) -> entityManager.find(Account.class, id));

        if (nonNull(account)) return account;

        throw new UnsupportedOperationException("Method is not implemented yet. It's your homework!");
    }

    @Override
    public Account findByEmail(String email) {

        List<Account> accountList = action((EntityManager entityManager) ->
                entityManager.createQuery("select a from Account a where Account.email = :email", Account.class)
                        .setParameter("email", email)).getResultList();

        if (nonNull(accountList) && !accountList.isEmpty()) return accountList.get(0);

        throw new UnsupportedOperationException("Method is not implemented yet. It's your homework!");
    }

    @Override
    public List<Account> findAll() {
        List<Account> accountList = action((EntityManager entityManager) ->
                entityManager.createQuery("select  a from Account a", Account.class).getResultList());

        if (nonNull(accountList) && !accountList.isEmpty()) return accountList;

        throw new UnsupportedOperationException("Method is not implemented yet. It's your homework!");
    }

    @Override
    public void save(Account account) {
        action((EntityManager entityManager) -> {
            entityManager.persist(account);
            return null;
        });
    }

    private interface Command<T> {
        T process(EntityManager entityManager);
    }

    private <T> T action(final Command<T> command) {
        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();
        try {
            return command.process(entityManager);
        } finally {
            entityManager.getTransaction().commit();
            entityManager.close();
        }
    }
}
