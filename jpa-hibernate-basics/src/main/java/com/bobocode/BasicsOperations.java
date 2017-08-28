package com.bobocode;


import com.bobocode.model.Account;
import com.bobocode.util.AccountDataUtil;
import com.bobocode.util.DBUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.function.Consumer;

public class BasicsOperations {
    private static EntityManagerFactory emf;

    public static void main(String[] args) {
        emf = DBUtil.getEntityManagerFactory();

        Account account = saveFakeAccount();
        findAndPrintAllAccounts();
        findAndPrintAccountByEmail();
        removeAccount(account);
        findAndPrintAllAccounts();

        DBUtil.destroy();
    }

    private static Account saveFakeAccount() {
        System.out.println("1 - save new account");
        Account account = AccountDataUtil.generateFakeAccount();
        performWithinPersistenseContext(em -> {
            em.persist(account);
            System.out.println(account);
        });
        return account;
    }

    private static void findAndPrintAllAccounts() {
        System.out.println("\n2 - get all accounts");
        performWithinPersistenseContext(em ->
                em.createQuery("select a from Account a", Account.class)
                        .getResultList()
                        .forEach(System.out::println));
    }

    private static void findAndPrintAccountByEmail() {
        System.out.println("\n3 - find by email");
        performWithinPersistenseContext(em -> {
                    String email = "asd@asd.asd";
                    em.createQuery("select a from Account a where a.email = :email", Account.class)
                            .setParameter("email", email)
                            .getResultList()
                            .forEach(a -> System.out.println("Account by email=" + a));
                }
        );
    }
    private static void removeAccount(Account account) {
        System.out.println("\n4 - remove account with id=" + account.getId());
        performWithinPersistenseContext(em -> {
            Account mergedAccount = em.merge(account);
            em.remove(mergedAccount);
        });

    }

    private static void performWithinPersistenseContext(Consumer<EntityManager> operation) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        operation.accept(em);

        em.getTransaction().commit();
        em.close();
    }
}
