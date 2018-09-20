package com.bobocode;

import com.bobocode.dto.AccountProjection;
import com.bobocode.model.Account;
import com.bobocode.util.JpaUtil;
import com.bobocode.util.TestDataGenerator;

import static com.bobocode.util.JpaUtil.performReturningWithinPersistenceContext;
import static com.bobocode.util.JpaUtil.performWithinPersistenceContext;

/**
 * This code example shows how to fetch an entity projection instead of full entity.
 */
public class FetchingDtoProjection {
    public static void main(String[] args) {
        JpaUtil.init("BasicEntitiesH2");
        Account account = TestDataGenerator.generateAccount();
        performWithinPersistenceContext(entityManager -> entityManager.persist(account));

        AccountProjection accountProjection = fetchAccountProjectionById(account.getId());
        System.out.println(accountProjection);
        JpaUtil.close();
    }

    /**
     * Fetches {@link AccountProjection} by account id. An {@link AccountProjection} is a data transfer object (DTO) for
     * {@link Account}. (It's a short version that stores some account data, but is not manged by Hibernate)
     *
     * @param id account id
     * @return account projection
     */
    private static AccountProjection fetchAccountProjectionById(Long id) {
        return performReturningWithinPersistenceContext(entityManager ->
                entityManager.createQuery("select new com.bobocode.dto.AccountProjection(a.id, a.email) " +
                        "from Account a where a.id = :id", AccountProjection.class)
                        .setParameter("id", id)
                        .getSingleResult());
    }
}
