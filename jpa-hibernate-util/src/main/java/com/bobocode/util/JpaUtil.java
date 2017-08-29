package com.bobocode.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.function.Consumer;

public class JpaUtil {
    private static EntityManagerFactory emf;

    public static void init(EntityManagerFactory entityManagerFactory) {
        emf = entityManagerFactory;
    }

    public static void performWithinPersistenceContext(Consumer<EntityManager> operation) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        operation.accept(em);

        em.getTransaction().commit();
        em.close();
    }
}
