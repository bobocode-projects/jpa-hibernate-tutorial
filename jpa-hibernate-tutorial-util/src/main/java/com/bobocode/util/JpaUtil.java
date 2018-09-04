package com.bobocode.util;

import com.bobocode.util.exception.JpaUtilException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.function.Consumer;

public class JpaUtil {

    private static EntityManagerFactory emf;

    public static void init(String persistenceUnitName) {
        emf = Persistence.createEntityManagerFactory(persistenceUnitName);
    }

    public static EntityManagerFactory getEntityManagerFactory() {
        return emf;
    }

    public static void close() {
        emf.close();
    }

    public static void performWithinPersistenceContext(Consumer<EntityManager> operation) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        try {
            operation.accept(em);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new JpaUtilException("Error performing JPA operation. Transaction is rolled back", e);
        } finally {
            em.close();
        }
    }
}
