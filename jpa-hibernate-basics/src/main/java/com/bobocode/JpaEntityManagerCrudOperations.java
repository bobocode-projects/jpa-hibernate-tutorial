package com.bobocode;


import com.bobocode.model.Account;
import com.bobocode.util.TestDataGenerator;

import java.util.List;

import static com.bobocode.util.JpaUtil.*;
import static com.bobocode.util.JpaUtil.performWithinPersistenceContext;

/**
 * This code example shows a typical usage of {@link javax.persistence.EntityManager} and its Create, Read, Update,
 * Delete (CRUD) operations
 */
public class JpaEntityManagerCrudOperations {

    public static void main(String[] args) {
        init("BasicEntitiesH2");

        Account account = TestDataGenerator.generateAccount();
        System.out.printf("Account before save: %s%n", account);

        saveAccount(account);
        System.out.printf("Stored account: %s%n", account);

        printAllAccounts();

        Account foundAccount = findAccountByEmail(account.getEmail());
        System.out.printf("Account found by email (%s): %s%n", account.getEmail(), foundAccount);

        account.setFirstName("UPDATED");
        account = updateAccount(account);
        System.out.printf("Updated account: %s%n", account);

        printAllAccounts();

        removeAccount(account);
        printAllAccounts();

        close();
    }

    private static void saveAccount(Account account) {
        performWithinPersistenceContext(em -> em.persist(account));
    }

    private static void printAllAccounts() {
        System.out.printf("All accounts: %s%n", findAllAccounts());
    }

    private static List<Account> findAllAccounts() {
        return performReturningWithinPersistenceContext(em ->
                em.createQuery("select a from Account a", Account.class).getResultList()
        );

    }

    private static Account findAccountByEmail(String email) {
        return performReturningWithinPersistenceContext(em ->
                em.createQuery("select a from Account a where a.email = :email", Account.class)
                        .setParameter("email", email)
                        .getSingleResult()
        );
    }

    private static Account updateAccount(Account account) {
        return performReturningWithinPersistenceContext(entityManager -> entityManager.merge(account));
    }

    private static void removeAccount(Account account) {
        performWithinPersistenceContext(em -> {
            Account managedAccount = em.merge(account); // only managed entities can be removed
            em.remove(managedAccount);
        });
    }
}
