package com.bobocode;

import com.bobocode.model.Account;
import com.bobocode.util.JpaUtil;
import com.bobocode.util.TestDataGenerator;

import static com.bobocode.util.JpaUtil.performWithinPersistenceContext;

/**
 * This code example shows how Hibernate's {@link org.hibernate.engine.spi.ActionQueue} work
 */
public class ActionQueue {
    public static void main(String[] args) {
        JpaUtil.init("BasicEntitiesH2");

        tryToSaveAccountWithSameEmailAfterRemove();
        tryToSaveAccountWithSameEmailAfterEmailUpdate();

        JpaUtil.close();
    }

    private static void tryToSaveAccountWithSameEmailAfterRemove() {
        try {
            saveAccountWithSameEmailAfterRemove();
        } catch (Exception e) {
            System.out.println("Second account wasn't stored, transaction is rolled back");
        }

    }

    /**
     * Stores and account, then removes it and tries to store the other account with the save email in the scope of the
     * same transaction. It does not work because Hibernate operations are sorted according to its priorities.
     * E.g. INSERT is done before UPDATE
     */
    private static void saveAccountWithSameEmailAfterRemove() {
        Account account = TestDataGenerator.generateAccount();
        performWithinPersistenceContext(entityManager -> entityManager.persist(account));
        Account secondAccount = TestDataGenerator.generateAccount();
        secondAccount.setEmail(account.getEmail());

        performWithinPersistenceContext(entityManager -> {
            Account managedAccount = entityManager.merge(account);
            entityManager.remove(managedAccount); // remove first account from the database
            // won't work because insert will be performed before remove
            entityManager.persist(secondAccount); // store second account with the same email
        });
    }


    private static void tryToSaveAccountWithSameEmailAfterEmailUpdate() {
        try {
            saveAccountWithSameEmailAfterEmailUpdate();
        } catch (Exception e) {
            System.out.println("Second account wasn't stored, transaction is rolled back");
        }

    }

    /**
     * Stores and account, then updates its email and tries to store the other account with the previous email value
     * in the scope of the same transaction. It does not work because Hibernate operations are sorted according to
     * its priorities.
     * E.g. INSERT is done before DELETE
     */
    private static void saveAccountWithSameEmailAfterEmailUpdate() {
        Account account = TestDataGenerator.generateAccount();
        Account secondAccount = TestDataGenerator.generateAccount();
        secondAccount.setEmail(account.getEmail());

        performWithinPersistenceContext(entityManager -> {
            entityManager.persist(account);
            account.setEmail("UPDATED"); // change the email of the first account
            // won't work because insert will be performed before update
            entityManager.persist(secondAccount); // store second account with the same email
        });
    }
}
