package com.bobocode.util;

import org.h2.jdbcx.JdbcDataSource;
import org.postgresql.ds.PGSimpleDataSource;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.sql.DataSource;
import java.util.function.Consumer;

public class JpaUtil {
   /* static String DEFAULT_DATABASE_NAME = "bobocode_db";
    static String DEFAULT_USERNAME = "bobouser";
    static String DEFAULT_PASSWORD = "bobodpass";

    public static DataSource createDefaultInMemoryH2DataSource() {
        String url = formH2ImMemoryDbUrl(DEFAULT_DATABASE_NAME);
        return createInMemoryH2DataSource(url, DEFAULT_USERNAME, DEFAULT_PASSWORD);
    }

    public static DataSource createInMemoryH2DataSource(String url, String username, String pass) {
        JdbcDataSource h2DataSource = new JdbcDataSource();
        h2DataSource.setUser(username);
        h2DataSource.setPassword(pass);
        h2DataSource.setUrl(url);

        return h2DataSource;
    }

    private static String formH2ImMemoryDbUrl(String databaseName) {
        return String.format("jdbc:h2:mem:%s;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=false", databaseName);
    }

    public static DataSource createDefaultPostgresDataSource() {
        String url = formPostgresDbUrl(DEFAULT_DATABASE_NAME);
        return createPostgresDataSource(url, DEFAULT_USERNAME, DEFAULT_PASSWORD);
    }

    public static DataSource createPostgresDataSource(String url, String username, String pass) {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(url);
        dataSource.setUser(username);
        dataSource.setPassword(pass);
        return dataSource;
    }

    private static String formPostgresDbUrl(String databaseName) {
        return String.format("jdbc:postgresql://localhost:5432/%s", databaseName);
    }
    */
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
            System.out.println("Transaction is rolled back");
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
}
