package com.bobocode;

import com.bobocode.model.Account;
import com.bobocode.util.JpaUtil;
import com.bobocode.util.TestDataGenerator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * {@link JpaEntryPoint} shows an example of using basic JPA classes {@link EntityManagerFactory} and {@link EntityManager}.
 * <p>
 * {@link EntityManager} allows to perform database operations with JPA entities. It represents a db session. E.g. each
 * user should get a new instance of {@link EntityManager} each time to perform db operations on JPA entity.
 * <p>
 * {@link EntityManagerFactory} is a thread-safe factory that allow to create {@link EntityManager} instances.
 */
public class JpaEntryPoint {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = createEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Account account = TestDataGenerator.generateAccount();

        entityManager.getTransaction().begin();
        entityManager.persist(account);
        entityManager.getTransaction().commit();

        System.out.println(account);

        entityManager.close();
        entityManagerFactory.close();
    }

    /**
     * Creates an instance of {@link EntityManagerFactory}. It uses JPA util class {@link Persistence} that allows
     * to create an instance by its name. All JPA configuration required to create {@link EntityManagerFactory} are
     * located in resources  /META-INF/persistence.xml which is a default location for JPA configs.
     *
     * @return instance of entity manager factory
     */
    private static EntityManagerFactory createEntityManagerFactory() {
        return Persistence.createEntityManagerFactory("BasicEntitiesH2");
    }
}
