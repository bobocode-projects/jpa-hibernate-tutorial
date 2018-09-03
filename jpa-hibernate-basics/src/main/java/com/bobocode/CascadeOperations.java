package com.bobocode;

import com.bobocode.model.basic.Address;
import com.bobocode.model.basic.Role;
import com.bobocode.model.basic.User;
import com.bobocode.util.JpaUtil;
import com.bobocode.util.TestDataGenerator;

import javax.persistence.OneToMany;
import java.util.List;

import static com.bobocode.util.JpaUtil.performWithinPersistenceContext;

public class CascadeOperations {

    public static void main(String[] args) {
        JpaUtil.init("BasicEntitiesH2");


        User user = TestDataGenerator.generateUser();
        List<Role> roles = TestDataGenerator.generateRoleList();
        Address address = TestDataGenerator.generateAddress();

        cascadePersist(user, address, roles);
        orphanRemoveRoleByUserId(user.getId());
        cascadeRemoveRoleByUserId(user.getId());

        JpaUtil.close();
    }

    /**
     * This methods show an example of cascade persist. Entities address, and roles will be persisted by the cascade.
     * Cascade type {@link javax.persistence.CascadeType#ALL} is specified for {@link User#address} and {@link User#roles}
     *
     * @param user    in a transient (NEW) state
     * @param address in a transient (NEW) state
     * @param roles   in a transient (NEW) state
     */
    private static void cascadePersist(User user, Address address, List<Role> roles) {
        System.out.println("\n-------------------------------------");
        performWithinPersistenceContext(em -> {
            System.out.println("Persisting user: " + user + ", with address: " + address + ", with roles: " + roles);
            // without cascading you would write persist statement for each entity
            // em.persist(address);
            // em.persist(roles);
            user.setAddress(address);
            user.addRoles(roles);
            em.persist(user);
            System.out.println("Persisted user: " + user);
        });
    }

    /**
     * This method shows an example of orphan removal. Since {@link User#roles} is marked with @{@link OneToMany#orphanRemoval()}
     * value true, roles which are removed from a {@link User#roles} should be deleted from the database.
     *
     * @param userId
     */
    private static void orphanRemoveRoleByUserId(Long userId) {
        System.out.println("\n-------------------------------------");
        performWithinPersistenceContext(em -> {
            User user = em.find(User.class, userId);
            System.out.println("User before orphan role remove: " + user);
            Role firstRole = user.getRoles().iterator().next();
            System.out.println("Role to remove: " + firstRole);
            // without orphan removal you would create a remove statement for role entity
            // em.remove(firstRole);
            user.removeRole(firstRole);
            System.out.println("User after orphan role remove: " + user);
        });
    }

    /**
     * This method shows an example of cascade remove
     */
    private static void cascadeRemoveRoleByUserId(Long userId) {
        System.out.println("\n-------------------------------------");
        performWithinPersistenceContext(em -> {
            User user = em.find(User.class, userId);
            System.out.println("Removing user: " + user);
            // without cascading you would write remove statement for each entity.
            // em.remove(address);
            // em.remove(roles);
            em.remove(user);
        });
    }
}
