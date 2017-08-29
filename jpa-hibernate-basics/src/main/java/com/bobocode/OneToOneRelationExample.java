package com.bobocode;

import com.bobocode.model.Address;
import com.bobocode.model.User;
import com.bobocode.util.DBUtil;
import com.bobocode.util.JpaUtil;
import com.bobocode.util.TestDataGenerator;

import static com.bobocode.util.JpaUtil.performWithinPersistenceContext;

public class OneToOneRelationExample {
    public static void main(String[] args) {
        JpaUtil.init(DBUtil.getEntityManagerFactory());

        User user = TestDataGenerator.generateUser();
        System.out.println("Generated user: " + user);

        Address address = TestDataGenerator.generateAddess();
        System.out.println("Generated address: " + address);

        saveUserWithAddress(user, address);

        findAndPrintUserById(user.getId());


        DBUtil.destroy();

    }
    private static void saveUserWithAddress(User user, Address address){
        performWithinPersistenceContext(em -> {
            em.persist(user);
            address.setUser(user);
            em.persist(address);
            // the relation between User and Address is managed on the child side
            // so I can use  address.setUser(user); to link user and it's address
        });
    }

    private static void findAndPrintUserById(Long userId){
        performWithinPersistenceContext(em -> {
            User persistedUser = em.find(User.class, userId);
            System.out.println("Persisted user: " + persistedUser);
        });

    }
}
