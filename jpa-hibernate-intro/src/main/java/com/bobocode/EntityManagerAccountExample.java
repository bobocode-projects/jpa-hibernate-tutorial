package com.bobocode;

import com.bobocode.model.Account;
import com.bobocode.util.JpaUtil;
import com.bobocode.util.TestDataGenerator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

public class EntityManagerAccountExample {
    private static EntityManagerFactory emf;

    public static void main(String[] args) {
        init();
        saveAccount();
        printAllAccounts();
        close();
    }

    /**
     * This method initializes {@link EntityManagerFactory} instance which is an entry point to JPA. Too see configs,
     * go to persistence.xml file
     */
    private static void init() {
        JpaUtil.init("SingleAccountEntityH2");
        emf = JpaUtil.getEntityManagerFactory();
    }

    private static void close() {
        JpaUtil.close();
    }

    private static void saveAccount() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Account account = TestDataGenerator.generateAccount();
        em.persist(account);

        em.getTransaction().commit();
        em.close();
    }

    private static void printAllAccounts() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        TypedQuery<Account> selectAllUsersQuery = em.createQuery("select a from Account a", Account.class);
        List<Account> accounts = selectAllUsersQuery.getResultList();
        System.out.println(accounts);

        em.getTransaction().commit();
        em.close();
    }
}
