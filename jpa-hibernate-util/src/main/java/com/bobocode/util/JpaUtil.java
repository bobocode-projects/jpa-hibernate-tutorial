package com.bobocode.util;

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

        operation.accept(em);

        em.getTransaction().commit();
        em.close();
    }
}
