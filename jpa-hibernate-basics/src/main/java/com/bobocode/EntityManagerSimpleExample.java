package com.bobocode;

import com.bobocode.model.User;
import com.bobocode.util.JpaUtil;
import com.bobocode.util.TestDataGenerator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class EntityManagerSimpleExample {
    public static void main(String[] args) {
        JpaUtil.init("bobocode");
        EntityManagerFactory emf = JpaUtil.getEntityManagerFactory();


        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        User user = TestDataGenerator.generateUser();
        em.persist(user);

        System.out.println(user);

        em.getTransaction().commit();
        em.close();

        JpaUtil.close();
    }
}
