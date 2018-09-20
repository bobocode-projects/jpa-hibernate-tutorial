package com.bobocode;

import com.bobocode.model.Account;
import com.bobocode.util.JpaUtil;
import com.bobocode.util.TestDataGenerator;

import java.util.List;
import java.util.stream.Stream;

import static com.bobocode.util.JpaUtil.performReturningWithinPersistenceContext;
import static com.bobocode.util.JpaUtil.performWithinPersistenceContext;
import static java.util.stream.Collectors.toList;

/**
 * This code examples show how to use pagination in JPA queries.
 */
public class Pagination {
    public static void main(String[] args) {
        JpaUtil.init("BasicEntitiesH2");
        List<Account> accounts = Stream.generate(TestDataGenerator::generateAccount).limit(100).collect(toList());
        performWithinPersistenceContext(entityManager -> accounts.forEach(entityManager::persist));

        List<Account> pageAccounts = loadAccounts(20, 10);
        pageAccounts.forEach(System.out::println);
        JpaUtil.close();
    }

    /**
     * Loads all account starting from {@code offset} position. The number of loaded accounts is limited by
     * {@code limit} number
     *
     * @param offset starting position
     * @param limit  number of account to load
     * @return list of accounts
     */
    private static List<Account> loadAccounts(int offset, int limit) {
        return performReturningWithinPersistenceContext(entityManager -> entityManager
                .createQuery("select a from Account a", Account.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList());
    }
}
