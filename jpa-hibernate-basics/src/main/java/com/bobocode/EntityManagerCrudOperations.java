package com.bobocode;


import com.bobocode.model.Account;
import com.bobocode.util.JpaUtil;
import com.bobocode.util.TestDataGenerator;

import static com.bobocode.util.JpaUtil.performWithinPersistenceContext;

public class EntityManagerCrudOperations {

    public static void main(String[] args) {
        JpaUtil.init("BasicEntitiesH2");


        Account account = saveFakeAccount();
        findAndPrintAllAccounts();
        findAndPrintAccountByEmail(account.getEmail());
        removeAccount(account);
        findAndPrintAllAccounts();

        JpaUtil.close();
    }

    private static Account saveFakeAccount() {
        System.out.println("Save new account");
        System.out.println("-----------------------------");
        Account account = TestDataGenerator.generateAccount();
        performWithinPersistenceContext(em -> em.persist(account));
        System.out.println(account);
        return account;
    }

    private static void findAndPrintAllAccounts() {
        System.out.println("\nGet all accounts");
        System.out.println("-----------------------------");
        performWithinPersistenceContext(em ->
                em.createQuery("select a from Account a", Account.class)
                        .getResultList()
                        .stream()
                        .forEach(System.out::println));
    }

    private static void findAndPrintAccountByEmail(String email) {
        System.out.println("\nFind by email");
        System.out.println("-----------------------------");
        performWithinPersistenceContext(em -> {
                    Account account
                            = em.createQuery("select a from Account a where a.email = :email", Account.class)
                            .setParameter("email", email)
                            .getSingleResult();
                    System.out.println(account);
                }

        );
    }

    private static void removeAccount(Account account) {
        System.out.println("\nRemove account with id=" + account.getId());
        System.out.println("-----------------------------");
        performWithinPersistenceContext(em -> {
            Account managedAccount = em.merge(account);
            em.remove(managedAccount);
        });

    }
}
