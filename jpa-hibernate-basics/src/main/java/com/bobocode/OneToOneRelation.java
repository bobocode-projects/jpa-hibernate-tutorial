package com.bobocode;

import com.bobocode.model.basic.Address;
import com.bobocode.model.basic.User;
import com.bobocode.util.JpaUtil;
import com.bobocode.util.TestDataGenerator;

import static com.bobocode.util.JpaUtil.performWithinPersistenceContext;

public class OneToOneRelation {

    public static void main(String[] args) {
        JpaUtil.init("BasicEntitiesH2");

        User user = TestDataGenerator.generateUser();
        System.out.println("Generated user: " + user);

        Address address = TestDataGenerator.generateAddress();
        System.out.println("Generated address: " + address);

        saveUserWithAddress(user, address);

        printUserById(user.getId());

        JpaUtil.close();
    }

    private static void saveUserWithAddress(User user, Address address) {
        performWithinPersistenceContext(em -> {
            em.persist(user);
            address.setUser(user);
            em.persist(address);
            // the relation between User and Address is managed on the child side
            // so I can use  address.setUser(user); to link user and it's address
        });
    }

    private static void printUserById(Long userId) {
        performWithinPersistenceContext(em -> {
            User persistedUser = em.find(User.class, userId);
            System.out.println("Persisted user: " + persistedUser);
        });

    }
}
