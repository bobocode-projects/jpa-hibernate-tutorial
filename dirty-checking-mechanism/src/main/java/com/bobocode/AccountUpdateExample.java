package com.bobocode;

import com.bobocode.model.Account;
import com.bobocode.util.TestDataGenerator;

import static com.bobocode.util.JpaUtil.*;

/**
 * This code examples shows the usage of Hibernate mechanism called Dirty Checking.
 */
public class AccountUpdateExample {
    public static void main(String[] args) {
        init("BasicEntitiesH2");

        long accountId = saveRandomAccount();
        printAccountById(accountId);
        updateAccountLastNameById(accountId, "NEW LAST NAME");
        printAccountById(accountId);

        close();
    }

    /**
     * Saves a random account into the database and returns it generated id.
     *
     * @return generated id
     */
    private static long saveRandomAccount() {
        Account account = TestDataGenerator.generateAccount();
        performWithinPersistenceContext(entityManager -> entityManager.persist(account));
        return account.getId();
    }

    /**
     * Loads an {@link Account} instance form database and prints it
     *
     * @param accountId stored account id
     */
    private static void printAccountById(long accountId) {
        performWithinPersistenceContext(entityManager -> {
            Account account = entityManager.find(Account.class, accountId);
            System.out.println(account);
        });
    }

    /**
     * Loads {@link Account} from the db by its id and updates it last name. Since Hibernate mechanism which is called
     * Dirty checking is enabled by default, it check all managed entities state changes and generates appropriate
     * UPDATE statements for the database.
     *
     * @param accountId   stored account id
     * @param newLastName new account last name
     */
    private static void updateAccountLastNameById(long accountId, String newLastName) {
        performWithinPersistenceContext(entityManager -> { // Persistence Context begins
            Account account = entityManager.find(Account.class, accountId); // account is managed by Hibernate
            account.setLastName(newLastName); // this changes will be detected by Dirty Checking mechanism
            // on flush dirty checking will generate UPDATE statement and will send it to the database
        });// Persistence Context ends
    }
}
