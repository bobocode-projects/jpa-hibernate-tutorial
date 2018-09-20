package com.bobocode;

import com.bobocode.model.advanced.RoleType;
import com.bobocode.model.basic.Role;
import com.bobocode.model.basic.User;
import com.bobocode.util.JpaUtil;
import com.bobocode.util.TestDataGenerator;

import static com.bobocode.util.JpaUtil.performWithinPersistenceContext;

/**
 * This example shows different ways of storing new relationship between {@link User} and {@link Role}
 */
public class StoringNewRelationship {
    public static void main(String[] args) {
        JpaUtil.init("BasicEntitiesH2");
        User user = TestDataGenerator.generateUser();

        storeNewUserWithRole(user, RoleType.USER);
        printUserById(user.getId());

        addNewRole(user, RoleType.OPERATOR);
        printUserById(user.getId());

        addNewRole(user.getId(), RoleType.ADMIN);
        printUserById(user.getId());

        JpaUtil.close();
    }

    /**
     * Stores new {@link User} with a specific role. This method uses cascade persist and a helper method
     * {@link User#addRole(Role)}, which links {@link User} object with new {@link Role} object
     *
     * @param user     NEW user
     * @param roleType a role type of NEW role
     */
    private static void storeNewUserWithRole(User user, RoleType roleType) {
        performWithinPersistenceContext(entityManager -> {
            Role role = Role.valueOf(roleType);
            user.addRole(role);
            entityManager.persist(user); // cascade operation will store also new role records
        });
    }

    /**
     * Loads and prints a {@link User} by id
     *
     * @param userId
     */
    private static void printUserById(long userId) {
        performWithinPersistenceContext(entityManager -> {
            User mangedUser = entityManager.find(User.class, userId);
            System.out.println(mangedUser);
        });
    }

    /**
     * Adds a new role to an existing user. This method receives existing user object and uses it helper method
     * {@link User#addRole(Role)} to add a new role
     *
     * @param user     DETACHED user
     * @param roleType role type of new role
     */
    private static void addNewRole(User user, RoleType roleType) {
        Role role = Role.valueOf(roleType);
        user.addRole(role);
        performWithinPersistenceContext(entityManager -> entityManager.merge(user));
    }

    /**
     * Adds a new role to an existing user by its id. This method does not require a full {@link User} object to add
     * a new role. It uses a special method {@link javax.persistence.EntityManager#getReference(Class, Object)} that
     * allow to get an entity proxy by its id. This proxy is used to store a new relation between {@link Role} and
     * {@link User}. User is is enough to store the relation because role store only user id as a foreign key.
     *
     * @param userId stored user id
     * @param roleType role type of new role
     */
    private static void addNewRole(long userId, RoleType roleType) {
        performWithinPersistenceContext(entityManager -> {
            Role role = Role.valueOf(roleType);
            User userProxy = entityManager.getReference(User.class, userId); // does not call db
            role.setUser(userProxy);
            entityManager.persist(role);
        });
    }


}
