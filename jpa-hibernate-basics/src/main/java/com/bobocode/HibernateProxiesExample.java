package com.bobocode;


import com.bobocode.model.Address;
import com.bobocode.model.Role;
import com.bobocode.model.User;
import com.bobocode.util.JpaUtil;
import com.bobocode.util.TestDataGenerator;

import java.util.List;

import static com.bobocode.util.JpaUtil.performWithinPersistenceContext;

/**
 * This example demonstrates how Hibernate creates proxies around entities.
 */
public class HibernateProxiesExample {

    public static void main(String[] args) {
        JpaUtil.init("bobocode");

        User user = TestDataGenerator.generateUser();
        List<Role> roles = TestDataGenerator.generateRoleList();
        Address address = TestDataGenerator.generateAddess();

        saveUser(user, address, roles);
        printProxyClass(user);

        JpaUtil.close();
    }

    private static void saveUser(User user, Address address, List<Role> roles) {
        performWithinPersistenceContext(em -> {
            user.setAddress(address);
            user.addRoles(roles);
            em.persist(user);
        });
    }

    /**
     * This examples uses {@link javax.persistence.EntityManager#getReference(Class, Object)} to create a user proxy
     * @param user
     */
    private static void printProxyClass(User user) {
        performWithinPersistenceContext(em -> {
            User userProxy = em.getReference(User.class, user.getId());
            System.out.println("\nUser proxy class is " + userProxy.getClass());
        });
    }


}
