package com.bobocode;

import com.bobocode.model.Account;
import com.bobocode.util.TestDataGenerator;
import org.hibernate.Session;

import static com.bobocode.util.JpaUtil.*;

/**
 * This code example shows how to turn of dirty checking mechanism for a particular entity
 */
public class ReadOnlySessionExample {
    public static void main(String[] args) {
        init("BasicEntitiesH2");

        long accountId = saveRandomAccount();

        performWithinPersistenceContext(entityManager -> {
            Session session = entityManager.unwrap(Session.class);
            session.setDefaultReadOnly(true); // turns off dirty checking for this session (for this entityManager)
            //todo: try to comment the previous line and run it again

            Account managedAccount = entityManager.find(Account.class, accountId);
            managedAccount.setFirstName("XXX"); // won't cause SQL UPDATE statement since dirty checking is disabled
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

