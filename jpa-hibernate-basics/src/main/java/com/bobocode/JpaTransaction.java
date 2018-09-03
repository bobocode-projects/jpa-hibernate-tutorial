package com.bobocode;

import com.bobocode.model.Account;
import com.bobocode.util.TestDataGenerator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * This examples shows how to handle database transactions using JPA {@link EntityManager}
 */
public class JpaTransaction {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("BasicEntitiesH2");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Account account = TestDataGenerator.generateAccount();

        saveAccountWithinTx(account, entityManager);

        entityManagerFactory.close();
    }

    /**
     * In order to handle database transactions using JPA you need to get {@link javax.persistence.EntityTransaction}
     * instance that allow you to start, commit and rollback the transaction
     *
     * @param account
     * @param entityManager
     */
    private static void saveAccountWithinTx(Account account, EntityManager entityManager) {
        entityManager.getTransaction().begin(); // calls Connection#setAutoCommit(false)
        try {
            entityManager.persist(account);
            entityManager.getTransaction().commit();// calls Connection#commit()
            System.out.printf("Account %s has been saved.%n", account);
        } catch (Exception e) {
            entityManager.getTransaction().rollback(); // calls Connection#rollback()
            System.err.printf("Error saving account %s. Transaction has been rolled back.%n", account);
        } finally {
            entityManager.close();
        }
    }
}
