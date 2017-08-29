package com.bobocode;


import com.bobocode.model.User;
import com.bobocode.util.DBUtil;
import com.bobocode.util.JpaUtil;
import com.bobocode.util.TestDataGenerator;

import static com.bobocode.util.JpaUtil.performWithinPersistenceContext;

public class EntityManagerCrudOperationsExample {

    public static void main(String[] args) {
        JpaUtil.init(DBUtil.getEntityManagerFactory());

        User user = saveFakeUser();
        findAndPrintAllUsers();
        findAndPrintUserByEmail(user.getCredentials().getEmail());
        removeAccount(user);
        findAndPrintAllUsers();

        DBUtil.destroy();
    }

    private static User saveFakeUser() {
        System.out.println("Save new account");
        System.out.println("-----------------------------");
        User user = TestDataGenerator.generateUser();
        performWithinPersistenceContext(em -> {
            em.persist(user);
            System.out.println(user);
        });
        return user;
    }

    private static void findAndPrintAllUsers() {
        System.out.println("\nGet all accounts");
        System.out.println("-----------------------------");
        performWithinPersistenceContext(em ->
                em.createQuery("select u from User u", User.class)
                        .getResultList()
                        .stream()
                        .forEach(System.out::println));
    }

    private static void findAndPrintUserByEmail(String email) {
        System.out.println("\nFind by email");
        System.out.println("-----------------------------");
        performWithinPersistenceContext(em -> {
                    User metthew = em.createQuery("select u from User u where u.credentials.email = :email", User.class)
                            .setParameter("email", email)
                            .getResultList().iterator().next();
                    System.out.println("Account by email=" + email + " " + ((metthew != null) ? metthew : " is not found"));
                }

        );
    }

    private static void removeAccount(User user) {
        System.out.println("\nRemove user with id=" + user.getId());
        System.out.println("-----------------------------");
        performWithinPersistenceContext(em -> {
            User mergedUser = em.merge(user);
            em.remove(mergedUser);
        });

    }
}
