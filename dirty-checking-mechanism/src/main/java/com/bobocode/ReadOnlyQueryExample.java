package com.bobocode;

import com.bobocode.model.Account;
import com.bobocode.util.TestDataGenerator;
import org.hibernate.jpa.QueryHints;

import static com.bobocode.util.JpaUtil.*;

/**
 * This code example shows how to turn of dirty checking mechanism using read-only Session
 */
public class ReadOnlyQueryExample {
    public static void main(String[] args) {
        init("BasicEntitiesH2");

        long accountId = saveRandomAccount();

        performWithinPersistenceContext(entityManager -> {
            Account managedAccount = entityManager.createQuery("select a from Account a where a.id = :id", Account.class)
                    .setParameter("id", accountId)
                    .setHint(QueryHints.HINT_READONLY, true)// turns off dirty checking for this particular entity
                    .getSingleResult();
            managedAccount.setFirstName("XXX"); // won't cause SQL UPDATE statement since dirty checking is disabled for this entity
        });

        printAccountById(accountId);

        close();
    }

    private static long saveRandomAccount() {
        Account account = TestDataGenerator.generateAccount();
        performWithinPersistenceContext(entityManager -> entityManager.persist(account));
        return account.getId();
    }

    private static void printAccountById(long accountId) {
        performWithinPersistenceContext(entityManager -> {
            Account storedAccount = entityManager.find(Account.class, accountId);
            System.out.println(storedAccount);
        });
    }
}

